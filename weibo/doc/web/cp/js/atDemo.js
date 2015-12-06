

;(function ($) {
    
    var weiboInput     = $('.input_detail'), //$('#weibo_input_detail'),
        weiboInputs    = null,
        // gameBtn        = $('#game_btn'),
        searchUserUrl  = 'http://www.davcai.com/ajax_search_user.do',
        searchMatchUrl = 'http://www.davcai.com/ajax_search_match.do',
        userNameList   = {},
        matchList      = {},
        gameNames      = {
            JCZQ: '竞彩足球',
            JCLQ: '竞彩篮球',
            CTZC: '传统足彩'
        },
        gameMarks      = {
            JCZQ: 'JZ',
            JCLQ: 'JL',
            CTZC: 'CZ'
        },
        matchWrap, uiOverlay,
        userItem, matchItem, _tmpList;

    window.WB_API = {};

    WB_API.addAtWhoEvent = function (input) {
        addSearchEvent($(input));
    };

    function addSearchEvent (input) {
        var _input = input || weiboInput;
        _input.atwho({
            at: "@",
            start_with_space: false,
            callbacks: {
                remote_filter: function (query, render_view) {
                    var thisVal = query,
                    self = $(this);
                    if( !self.data('active') ) {
                        self.data('active', true);

                        this.view.clearItems();
                        if (thisVal === '') {
                            this.view.show();
                            this.view.showLabel();
                            self.data('active', false);
                            return;
                        } else {
                            this.view.hideLabel();
                        }

                        userItem = userNameList[thisVal];
                        if(typeof userItem == "object") {
                            render_view(userItem);
                            self.data('active', false);
                        } else {
                            if (self.xhr) {
                                self.xhr.abort();
                            }

                            self.xhr = $.ajax({
                                url: searchUserUrl + '?keys=' + thisVal,
                                dataType: 'jsonp',
                                success: function (res) {
                                    if (typeof res == 'object') {
                                        _tmpList = remapUserList(res.users.results);
                                        userNameList[thisVal] = _tmpList;
                                        render_view(_tmpList);
                                    };
                                    
                                    self.data('active', false);
                                },
                                error: function () {
                                    self.data('active', false);
                                }
                            });
                        }
                    }                    
                }
            }
        })
        .atwho({
            at: '$',
            start_with_space: false,
            callbacks: {
                remote_filter: function (query, render_view) {
                    var thisVal = query,
                    self = $(this);

                    if (thisVal === '') {
                        showMatchWrap(this);
                    };
                },

                before_insert: function (value, $li) {
                    return filterMatchResult(value);
                }
            }
        })
        .atwho({
            at: '￥',
            start_with_space: false,
            callbacks: {
                remote_filter: function (query, render_view) {
                    var thisVal = query,
                    self = $(this);

                    if (thisVal === '') {
                        showMatchWrap(this);
                    };
                },

                before_insert: function (value, $li) {
                    return filterMatchResult(value);
                }
            }
        })
    }

    function showMatchWrap (target) {
        uiOverlay = uiOverlay || $('#ui_overlay');
        if (!matchWrap) {
            matchWrap = $('#match_wrap');
            createAutocomplete();
        };

        matchWrap.data('targetInput', target);
        uiOverlay.show();

        var offset = target.$inputor.offset();
        matchWrap.css({
            top: offset.top + target.$inputor.height() + 16,
            left: offset.left - 6
        }).show().find('input').focus();

        addRemoveMatchWrapEvent();
    }

    function addRemoveMatchWrapEvent () {
        $('body').on('keydown.match', function (event) {
            if (event.keyCode === 27) hideMatchWrap();
        });
    }

    function removeRemoveMatchWrapEvent () {
        $('body').off('keydown.match');
    }

    function addRemoveMatchWrapEvent () {
        $('body').on('keydown', function (event) {
            if (event.keyCode === 27) hideMatchWrap();
        });
    }

    function createAutocomplete () {
        matchWrap.find('input').autocomplete({
            serviceUrl: searchMatchUrl,
            dataType: 'jsonp',
            paramName: 'key',
            transformResult: function (response, originalQuery) {
                return {
                    suggestions: remapMatchList(response)
                }
            },
            onEnter: function () {
                var content = '$' + $(this).val(),
                    target  = matchWrap.data('targetInput');

                try {
                    target.insert(content);
                } catch (e) {
                    target.$inputor.insertAtCaret(content.replace(/^\$/, '') + ' ');
                }

                hideMatchWrap();
            },
            onSelect: function (suggestion) {
                if (suggestion) {
                    var content = filterMatchResult(suggestion.value, suggestion.source),
                        target  = matchWrap.data('targetInput');

                    try {
                        target.insert(content);
                    } catch (e) {
                        target.$inputor.insertAtCaret(content.replace(/^\$/, '') + ' ');
                    }

                    hideMatchWrap();
                };
            }
        });
    }

    function hideMatchWrap () {
        if (matchWrap && uiOverlay) {
            matchWrap.hide().find('input').val('');
            uiOverlay.hide();
        };

        removeRemoveMatchWrapEvent();
    }

    function isGameDead (time) {
        if (!time) return true;

        time = time.replace(/t/i, ' ');
        var deadTime = new Date(time).getTime(),
            now = new Date().getTime();

        return now > deadTime;
    }

    function filterMatchResult (value, source) {
        var result = '',
            htmlTagReg = /<\/?[^>]*>/g,
            sale = /\s\[在售\]/g,
            reg    = /\s*$/,
            number = '';

        if (source) {
            number = '(' + getGameNumber(source) + ')';
        };

        if (!!value) {
            value += number;
            if (reg.test(value)) value = value + ' ';
            result = '$' + value.replace(reg, '$').replace(htmlTagReg, '').replace(sale, '');
        } 

        return result;
    }

    function remapUserList (list) {
        return $.map(list, function (value, i) {
            return {
                name: value.nickName
            }
        })
    }

    function getGameNumber (value) {
        if (!value) return '';

        var mark = gameMarks[value.lotteryType.toUpperCase()],
            number = '';

        switch (mark) {
            case 'CZ' :
                number = getCode();
            break;

            default :
                number = getId();
            break;
        }

        function getCode () {
            var n = value.matchCode,
                mid = value.matchId || 0;

            value = null;
            return n + (mid.length<2?(mid+'0'):mid);
        }

        function getId () {
            var n = '';
            if (value.id) {
                n = value.id.replace(/^20/, '');
            };

            value = null;
            return n;
        }

        return mark + number;
    }

    function remapMatchList (list) {
        var label;
        return $.map(list, function (value, i) {
            var s = '';
            // if (!isGameDead(value.entrustDeadline)) {
            if (value.sale) {
                s = '[在售]';
            };

            label = gameNames[value.lotteryType.toUpperCase()] + ' ' + value.matchCode + ' ' + value.matchName + ' ' + s;
            return {
                source: value,
                data: value.id,
                value: label
            }
        })
    }

    function getInputBySelector (selector) {
        var target;
        $(weiboInputs).each(function () {
            if ($(this).is(selector)) target = $(this);
        });

        return target;
    }

    function addDelegateEvents () {
        $('body').delegate('#match_wrap i.close, #ui_overlay', 'click', function(event) {
            hideMatchWrap();
        })

        .delegate('.game_show_btn', 'click', function(event) {
            var app = $(this).parent().parent().parent().parent().find('.input_detail').data('atwho');

            app.$inputor.insertAtCaret('$');
            app && $.isFunction(app.dispatch) && app.dispatch();
            showMatchWrap(app.controllers['$']);
        });
    }

    (function () {
        addSearchEvent();
        addDelegateEvents();
    })();

})(window.jQuery);



(function($){
    $.fn.extend({
        insertAtCaret: function(myValue){
            var $t=$(this)[0];
            if (document.selection) {
                this.focus();
                sel = document.selection.createRange();
                sel.text = myValue;
                this.focus();
            }
            else 
                if ($t.selectionStart || $t.selectionStart == '0') {
                    var startPos = $t.selectionStart;
                    var endPos = $t.selectionEnd;
                    var scrollTop = $t.scrollTop;
                    $t.value = $t.value.substring(0, startPos) + myValue + $t.value.substring(endPos, $t.value.length);
                    this.focus();
                    $t.selectionStart = startPos + myValue.length;
                    $t.selectionEnd = startPos + myValue.length;
                    $t.scrollTop = scrollTop;
                }
                else {
                    this.value += myValue;
                    this.focus();
                }
        }
    })  
})(jQuery);