$(function() {
	(function ($) {
	    
	    var weiboInput     = $('.form-control'), //$('#weibo_input_detail'),
	        weiboInputs    = null,
	        // gameBtn        = $('#game_btn'),
	        searchUserUrl  = 'http://www.davcai.com/ajax_search_user',
	        searchMatchUrl = 'http://www.davcai.com/ajax_search_match',
	        userNameList   = {},
	        matchList      = {},
	        gameNames      = {
	            JCZQ: '竞彩足球',
	            JCLQ: '竞彩篮球',
	            CTZC: '传统足彩',
	            BJDC: '北京单场'
	        },
	        gameMarks      = {
	            JCZQ: 'JZ',
	            JCLQ: 'JL',
	            CTZC: 'CZ',
	            BJDC: 'BD'
	        },
	        matchWrap, uiOverlay,
	        userItem, matchItem, _tmpList;

	    window.WB_API = {};

	    WB_API.addAtWhoEvent = function (input) {
	        addSearchEvent($(input));
	    };
	    
	    WB_API.showMatchWrapUEditor = showMatchWrapUEditor;

	    WB_API.addNickNameAtWho = addNickNameAtWho;
	    
	    WB_API.showMatchAreaDelegateEvents = addDelegateEvents;
	    WB_API.showMatchAreaDelegateEventsWith = addDelegateEventsWith;
	    
	    try {
	    	WB_API._wbEditor = _wbEditor;
		} catch (e) {
			WB_API._wbEditor = null;
		}
	    
	    function addNickNameAtWho(editor){
	    	if(!editor.atwho)
	        	return false;
	    	$(editor).atwho({
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
	                        if(typeof userItem == "object" 
	                        	&& !(userItem instanceof Array)) {
	                            render_view(userItem);
	                            self.data('active', false);
	                        } else {
	                            if (self.xhr) {
	                                self.xhr.abort();
	                            }

	                            self.xhr = $.ajax({
	                                url: searchUserUrl + '?keys=' + encodeURIComponent(thisVal),
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
	        });
	    }
	    function addSearchEvent (input) {
	        var _input = input || weiboInput;
	        if(!_input || !_input.atwho)
	        	return false;
	        _input.atwho({
	            at: '$',
	            start_with_space: false,
	            callbacks: {
	                remote_filter: function (query, render_view) {
	                	removeInputSymbol($(this));
	                	
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
	                	removeInputSymbol($(this));
	                	
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
	        });
	    }
	    
	    function removeInputSymbol(jqElement) {
	    	var jqInput = jqElement[0].$inputor;
	    	var content = jqInput.val();
	    	var pattern = /(\$|￥)((\&nbsp;)*(<\w+\/>)*(<\/\w+>)*)$/;
        	var cont = content.replace(pattern, function(str, g1, g2){
        		return g2;
        	});
        	jqInput.val(cont);
	    }
	    
	    // for UEditor
	    function showMatchWrapUEditor (target) {
	        uiOverlay = uiOverlay || $('#ui_overlay');
	        if (!matchWrap) {
	            matchWrap = $('#match_wrap');
	            createAutocomplete();
	        };

	        matchWrap.data('targetInput', target);
	        uiOverlay.show();

	        var offset = target.offset();
	        matchWrap.css({
	            top: offset.top + target.height() + 16,
	            left: offset.left - 6
	        }).show().find('input').focus();

	        addRemoveMatchWrapEvent();
	        
	        setTimeout(function(){
	        	var content = _wbEditor.getContent();
	        	// 去掉已经输入的$/￥符号
	        	var pattern = /(\$|￥)((\&nbsp;)*(<\w+\/>)*(<\/\w+>)*)$/;
	        	var html = content.replace(pattern, function(str, g1, g2){
	        		return g2;
	        	});
	        	_wbEditor.setContent(html);
	        }, 100);
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
	                };
	            },
	            onEnter: function () {
	                var content = '$' + $(this).val(),
	                    target  = matchWrap.data('targetInput');

	                try {
	                    target.insert(content);
	                } catch (e) {
	                	var insertContent = content.replace(/^\$/, '') + ' ';
	                    if (target.$inputor){
                    		target.$inputor.insertAtCaret(insertContent);
                    	}else{	// for UEditor hack
                    		_wbEditor.focus(true);
                    		_wbEditor.execCommand('inserthtml', insertContent);
                    	}
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
	                    	var insertContent = content.replace(/^\$/, '') + ' ';
	                    	if (target.$inputor){
	                    		target.$inputor.insertAtCaret(insertContent);
	                    	}else{	// for UEditor hack
	                    		_wbEditor.focus(true);
	                    		_wbEditor.execCommand('inserthtml', content);
	                    	}
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
	        if(WB_API._wbEditor) {
	        	_wbEditor.focus(true);
	        }
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
	            matchTime = /\s\d+-\d+-\d+/g,
	            reg    = /\s*$/,
	            number = '';

	        if (source) {
	            number = '(' + getGameNumber(source) + ')';
	        };
	        
	        if(value == "$" || value == "￥") {
	        	result = value;
	        } else if (!!value) {
	            value += number;
	            if (reg.test(value)) value = value + ' ';
	            result = '$' + value.replace(reg, '$').
	            	replace(htmlTagReg, '').replace(sale, '').replace(matchTime, '');
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

	        var mark = '',
	            number = '';
	        if(value.lotteryType) {
	        	mark = gameMarks[value.lotteryType.toUpperCase()];
	        }

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
	            return n + (mid.length<2?('0'+mid):mid);
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
	    	list = inFrontOfOnSaleMatch(list);
	        var label;
	        var resultList = $.map(list, function (value, i) {
	            var s = '';
	            // if (!isGameDead(value.entrustDeadline)) {
	            if (value.sale) {
	                s = '<span class="match-onsale">[在售]</span>';
	            } else {
	            	var dateStr = "";
	            	if(value.matchTime && value.matchTime.length > 10) {
	            		dateStr = value.matchTime.replace("T"," ").substr(0, 10);
	            	}
	            	s = value.matchTime ? '<span style="color:#646464;">' +dateStr+'</span>':"";
	            };
	            
	            label = gameNames[value.lotteryType.toUpperCase()] + ' ' + value.matchCode + ' ' + value.matchName + ' ' + s;
	            return {
	                source: value,
	                data: value.id,
	                value: label
	            };
	        });
	        var keyArr = [];
	        keyArr.push({"data":"", "source":{}, value:"$"});
	        keyArr.push({"data":"", "source":{}, value:"￥"});
	        
	        return keyArr.concat(resultList);
	    }

	    /**使在售的比赛按时间倒排放在前面，其他赛事记录顺序不变*/
	    function inFrontOfOnSaleMatch(list) {
	    	var sortedList = [];
	    	var onSaleList = [];
	    	$.map(list, function(value, i) {
	    		if (value.sale) {
	    			onSaleList.push(value);
	            } else {
	            	sortedList.push(value);
	            }
	    	});
	    	onSaleList.sort(sortOnSaleMatch);
	    	return onSaleList.concat(sortedList);
	    }
	    
	    function sortOnSaleMatch(eltFirst, eltSecond) {
	    	var eltFirstDeadTime = new Date(eltFirst.entrustDeadline.replace("T"," "));
	    	var eltSecondDeadTime = new Date(eltSecond.entrustDeadline.replace("T"," "));
	    	var firstTime = eltFirstDeadTime.getTime();
	    	var secondTime = eltSecondDeadTime.getTime();
	    	
	    	if(firstTime - secondTime == 0) {
	    		return 0;
	    	} else if(firstTime - secondTime > 0) {
	    		return 1;
	    	} else {
	    		return -1;
	    	}
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
	        });
	        
            $('.game_show_btn').click(function(event) {
                var app = $(this).parent().parent().parent().parent().find('.form-control').data('atwho');
                var commentEditor = $(this).parent().parent().parent().find('.commentEditor').data('atwho');
                var weiboRealFollowEditor = $(this).closest("[weiboBetForm]").find('[weiboBetTextarea]').data('atwho');
                if(app || commentEditor || weiboRealFollowEditor){
                	if(commentEditor) {
                		app = commentEditor;
                	} else if(weiboRealFollowEditor){
                		app = weiboRealFollowEditor;
                	}
                	app.$inputor.insertAtCaret('$');
                	app && $.isFunction(app.dispatch) && app.dispatch();
                	showMatchWrap(app.controllers['$']);
                }else{
                	// 使用了 ueditor 的情况
               		// for 赛事框
               		showMatchWrapUEditor($(this));
                }
	        });
	    }
	    function addDelegateEventsWith ($context) {
	    	$('body').delegate('#match_wrap i.close, #ui_overlay', 'click', function(event) {
	    		hideMatchWrap();
	    	});
	    	
	    	$('.game_show_btn',$context).click(function(event) {
	    		var app = $(this).parent().parent().parent().parent().find('.form-control').data('atwho');
	    		var commentEditor = $(this).parent().parent().parent().find('.commentEditor').data('atwho');
	    		var weiboRealFollowEditor = $(this).closest("[weiboBetForm]").find('[weiboBetTextarea]').data('atwho');
	    		if(app || commentEditor || weiboRealFollowEditor){
	    			if(commentEditor) {
	    				app = commentEditor;
	    			} else if(weiboRealFollowEditor){
	    				app = weiboRealFollowEditor;
	    			}
	    			app.$inputor.insertAtCaret('$');
	    			app && $.isFunction(app.dispatch) && app.dispatch();
	    			showMatchWrap(app.controllers['$']);
	    		}else{
	    			// 使用了 ueditor 的情况
	    			// for 赛事框
	    			showMatchWrapUEditor($(this));
	    		}
	    	});
	    }

	    (function () {
	        addSearchEvent();
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
	    });
	})(jQuery);
});