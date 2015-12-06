Jet().$package(function(J){

    var tmpl = {
        week: '<tr class="trcs"><td colspan="8"><div class="open_close"><b>{0} 星期{1}</b><a class="c-c" href="javascript:void(0)" _w="{2}" _v="0">点击隐藏</a><b _n="cls" class="icns icns16 icns16_arrt"></b></div></td></tr>',
        opt: '<a class="spf" href="javascript:void(0);" _o="{0}" title="删除该项">{1}</a>',
        match: '<tr _m="{0}">'
            + '<td class="tdl"><div class="intd">{1}</div></td>'
            + '<td><div class="intd">{2} VS {3}</div></td>'
            + '<td><div class="intd"><div class="tagbox tagbox-d"><div class="row">{4}</div><span class="clear"/></div></div></td>'
            + '<td style="text-align:center;"><div class="intd"><a href="javascript:void(0);" _m="{0}" title="删除该场比赛">删</a></div></td>'
            + '</tr>',
        pass: '<label class="lbl" _m="{0}" style="display:none;"><input type="checkbox" name="pt" value="{2}"/><span>{0}串{1}</span></label>'
    };    
    var weekCN = ['日','一','二','三','四','五','六'];
    
    var Renderer = function(){};
    Renderer.prototype = {
        render: function(ms){
            var h = [], m = ms[0], fw = m.code.charAt(0) % 7, date = $.parseDate(m.pTime), day = date.getDay();
            // 处理比赛开始日期和赛事编号不同的情况
            if(day != fw){
                date.setTime(date.getTime() + ((fw - day - 7) % 7) * 86400000);
            }
        
            h.push(this.renderWeek(fw, date));
            for(var i = 0, len = ms.length; i < len; i++){
                m = ms[i];
                var cw = m.code.charAt(0) % 7;
                if(cw != fw){
                    date.setTime(date.getTime() + (cw - fw) * 86400000);
                    fw = cw;
                    h.push(this.renderWeek(fw, date));
                }
                h.push(this.renderMatch(cw, m, i));
            }
            return h.join('');
        },        
        renderWeek: function(week, date){
            return $.format(tmpl.week, this.formatDate(date), weekCN[week], week);
        },
        renderMatch: function(week, match, i){
            return '';
        },
        optionName: function(o){
            return this.names[o];
        },
        formatDate: function(date){
            return date.getFullYear() + '-' + Renderer.fill(date.getMonth() + 1) + '-' + Renderer.fill(date.getDate());
        }
    };
    Renderer = Jooe.extend(Renderer.prototype);
    Renderer.fill = function(v){
        return ((v < 10) ? '0': '') + v;
    };

    var ZC1Renderer = function(){};
    ZC1Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{6}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:#393;">{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd">{5}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:260px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{1} <span class="c-i">{2}</span></span><span class="peil c-l">{3}</span></span></span></a>';
            this.optTpl2 = '<a _o="{0}" _s="0" class="btnx3 btnx3-a c-l fl" href="javascript:void(0);" style="width:100px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 3;
            this.names = {
                '3': '胜',
                '1': '平',
                '0': '负'
            };
        },
      
        renderMatch: function(w, m, i){
            var odds = m.odds, h = [];    
            var concede = m.score;
            if(concede != 0){
                concede = (concede > 0) ? '(+' + concede + ')' : '(' + concede + ')';
            } else {
                concede = '';
            }
            h.push($.format(this.optTpl, 0, m.hName, concede, odds[0]));
            h.push($.format(this.optTpl2, 1, odds[1]));
            h.push($.format(this.optTpl, 2, m.gName, '', odds[2]));
            return $.format(this.matchTpl, i, m.cnCode, m.lName, m.deadline.substr(5, 11).replace('T', ' '), m.pTime.substr(5, 11).replace('T', ' '), h.join(''), w);
        }
    };
    ZC1Renderer = Renderer.extend(ZC1Renderer.prototype);
    
    var ZC2Renderer = function(){};
    ZC2Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:#393;">{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd">{5} VS {6}</div></td>' +
                    '<td class="tdr"><div class="intd"><a _sh="{0}" class="btnx2 btnx2-b btnx2-b-a" href="javascript:void(0);"><span class="inbtn"><span _sh="text">显示选项</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></a></div></td>' +
                    '</tr>'+
                    '<tr _m="{0}" _w="{8}" _s="0" style="display:none;"><td colspan="6">{7}</td></tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:76px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{1}</span><span class="peil">{2}</span></span></span></a>';
            this.optionCount = 33;
            this.names = {
                '10': '1:0',
                '20': '2:0',
                '21': '2:1',
                '30': '3:0',
                '31': '3:1',
                '32': '3:2',
                '40': '4:0',
                '41': '4:1',
                '42': '4:2',
                '43': '4:3',
                '50': '5:0',
                '51': '5:1',
                '52': '5:2',
                '90': '胜其他',
                '00': '0:0',
                '11': '1:1',
                '22': '2:2',
                '33': '3:3',
                '99': '平其他',
                '01': '0:1',
                '02': '0:2',
                '12': '1:2',
                '03': '0:3',
                '13': '1:3',
                '23': '2:3',
                '04': '0:4',
                '14': '1:4',
                '24': '2:4',
                '34': '3:4',
                '05': '0:5',
                '15': '1:5',
                '25': '2:5',
                '09': '负其他'
            };
        },
      
        renderMatch: function(w, m, i){
            var opts = m.opts, odds = m.odds, h = [];
            
            for(var j = 0; j < 31; j++){
                h.push($.format(this.optTpl, j, this.names[opts[j]], odds[j]));
                if(j == 12 || j == 17){
                    h.push('<br/>');
                }
            }
            return $.format(this.matchTpl, i, m.cnCode, m.lName, m.deadline.substr(5, 11).replace('T', ' '), m.pTime.substr(5, 11).replace('T', ' '), m.hName, m.gName, h.join(''), w);
        }
    };
    ZC2Renderer = Renderer.extend(ZC2Renderer.prototype);

    var ZC3Renderer = function(){};
    ZC3Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:#393;">{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd"><div class="hide">{5} VS {6}</div></div></td>' +
                    '<td><div class="intd">{7}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:50px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 8;
            this.names = {
                '0': '0球',
                '1': '1球',
                '2': '2球',
                '3': '3球',
                '4': '4球',
                '5': '5球',
                '6': '6球',
                '7': '7+球'
            };
        },
      
        renderMatch: function(w, m, i){
            var odds = m.odds, h = [];    
            for(var j = 0, k = m.opts.length; j < k; j++){
                 h.push($.format(this.optTpl, j, odds[j]));
            }
            return $.format(this.matchTpl, i, m.cnCode, m.lName, m.deadline.substr(5, 11).replace('T', ' '), m.pTime.substr(5, 11).replace('T', ' '), m.hName, m.gName, h.join(''), w);
        }
    };
    ZC3Renderer = Renderer.extend(ZC3Renderer.prototype);
    
    var ZC4Renderer = function(){};
    ZC4Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:#393;">{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd"><div class="hide">{5} VS {6}</div></td>' +
                    '<td><div class="intd">{7}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:50px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 9;
            this.names = {
                '33': '胜胜',
                '31': '胜平',
                '30': '胜负',
                '13': '平胜',
                '11': '平平',
                '10': '平负',
                '03': '负胜',
                '01': '负平',
                '00': '负负'
            };
        },
      
        renderMatch: function(w, m, i){
            var odds = m.odds, h = [];    
            for(var j = 0, k = m.opts.length; j < k; j++){
                h.push($.format(this.optTpl, j, odds[j]));
            }
            return $.format(this.matchTpl, i, m.cnCode, m.lName, m.deadline.substr(5, 11).replace('T', ' '), m.pTime.substr(5, 11).replace('T', ' '), m.hName, m.gName, h.join(''), w);
        }
    };
    ZC4Renderer = Renderer.extend(ZC4Renderer.prototype);
    
    var LC6Renderer = function(){};
    LC6Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{6}"><td class="tdl"><div class="intd">{1}</div></td>' +
                            '<td style="background-color:#393;"><div class="intd"><span class="sort">{2}</span></div></td>' +
                            '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                            '<td><div class="intd">{5}</div></td>' +
                            '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                            '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:300px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{1} <span class="c-i">{2}</span></span><span class="peil c-l">{3}</span></span></span></a>';
            this.optionCount = 2;
            this.names = {
                '1': '主胜',
                '2': '客胜'
            };
        },
        renderMatch: function(w, m, i){
            var h = [], concede = m.score;
            if(concede != 0){
                concede = (concede > 0) ? '(+' + concede + ')' : '(' + concede + ')';
            } else {
                concede = '';
            }
            h.push($.format(this.optTpl, 0, m.gName, '', m.odds[0]));
            h.push($.format(this.optTpl, 1, m.hName, concede, m.odds[1]));
            return $.format(this.matchTpl, i, m.cnCode, m.lName, m.deadline.substr(5, 11).replace('T', ' '), m.pTime.substr(5, 11).replace('T', ' '), h.join(''), w);
        }
    };
    LC6Renderer = Renderer.extend(LC6Renderer.prototype);
    
    var LC7Renderer = function(){};
    LC7Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{6}"><td class="tdl"><div class="intd">{1}</div></td>' +
                            '<td style="background-color:#393;"><div class="intd"><span class="sort">{2}</span></div></td>' +
                            '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                            '<td><div class="intd">{5}</div></td>' +
                            '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                            '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:309px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{1}</span><span class="peil c-l">{2}</span></span></span></a>';
            this.optionCount = 2;
            this.names = {
                '1': '主胜',
                '2': '客胜'
            };
        },
        renderMatch: function(w, m, i){
            var h = [];
            h.push($.format(this.optTpl, 0, m.gName, m.odds[0]));
            h.push($.format(this.optTpl, 1, m.hName, m.odds[1]));
            return $.format(this.matchTpl, i, m.cnCode, m.lName, m.deadline.substr(5, 11).replace('T', ' '), m.pTime.substr(5, 11).replace('T', ' '), h.join(''), w);
        }
    };
    LC7Renderer = Renderer.extend(LC7Renderer.prototype);
    
    var LC8Renderer = function(){};
    LC8Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                            '<td style="background-color:#393;"><div class="intd"><span class="sort">{2}</span></div></td>' +
                            '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                            '<td><div class="intd"><div class="hide">{6}<br/>{5}</div></div></td>' +
                            '<td><div class="inth"><div class="hide">客胜<br/>主胜</div></div></td>' +
                            '<td><div class="intd">{7}</div></td>' +
                            '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                            '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:60px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 12;
            this.names = {
                '01': '主胜1-5',
                '02': '主胜6-10',
                '03': '主胜11-15',
                '04': '主胜16-20',
                '05': '主胜21-25',
                '06': '主胜26+',
                '11': '客胜1-5',
                '12': '客胜6-10',
                '13': '客胜11-15',
                '14': '客胜16-20',
                '15': '客胜21-25',
                '16': '客胜26+'
            };
        },
        renderMatch: function(w, m, i){
            var odds = m.odds, h = [];
            for(var j = 0, k = m.opts.length; j < k; j+=2){
                h.push($.format(this.optTpl, j, odds[j]));
            }
            h.push('<br/>');
            for(var j = 1, k = m.opts.length; j < k; j+=2){
                h.push($.format(this.optTpl, j, odds[j]));
            }
            return $.format(this.matchTpl, i, m.cnCode, m.lName, m.deadline.substr(5, 11).replace('T', ' '), m.pTime.substr(5, 11).replace('T', ' '), m.hName, m.gName, h.join(''), w);
        }
    };
    LC8Renderer = Renderer.extend(LC8Renderer.prototype);
    
    var LC9Renderer = function(){};
    LC9Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{6}">'
                + '<td class="tdl"><div class="intd">{1}</div></td>'
                + '<td><div class="intd"><span class="sort" style="background-color:#393;">{2}</span></div></td>'
                + '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>'
                + '<td><div class="intd">{5}</div></td>'
                + '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>'
                + '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:305px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{1}</span><span class="peil c-l">{2}</span></span></span></a>'
                    + '<span class="fl" style="width:101px;">{3}</span>'
                    + '<a _o="{4}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:305px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{5}</span><span class="peil c-l">{6}</span></span></span></a>';
            this.optionCount = 3;
            this.names = {
                '1': '大',
                '2': '小'
            };
        },
        renderMatch: function(w, m, i){
            var o = $.format(this.optTpl, 0, m.gName, m.odds[0], m.score, 1, m.hName, m.odds[1]);
            return $.format(this.matchTpl, i, m.cnCode, m.lName, m.deadline.substr(5, 11).replace('T', ' '), m.pTime.substr(5, 11).replace('T', ' '), o, w);
        }
    };
    LC9Renderer = Renderer.extend(LC9Renderer.prototype);
    
    
    var UI = function(){};
    UI.prototype = {
        init: function(option){
            this.playId = option.playId;
            this.renderer = option.renderer;
            this.bet = new Bet(option);
            this.$time = option.time.find('[_n]');
            this.$panel = option.panel;
            this.$grid = option.grid;
            this.$row = [];
            
            var ctx = this, $dom = $('[_n]', this.$panel);
            this.$form = $dom.filter('[_n="form"]');    // 投注表单            
            this.$mKnob = $dom.filter('[_n="mKnob"]');  // 切换“已选赛事”按钮
            this.$mGrid = $dom.filter('[_n="mGrid"]');  // 已选赛事列表
            this.$clear = $dom.filter('[_n="clear"]');  // 清除所有已选赛事
            this.$bet = $dom.filter('[_n="bet"]');      // 投注按钮      
            this.$mc = $dom.filter('[_n="mc"]');        // 已选赛事数量
            this.$money = $dom.filter('[_n="money"]');  // 投注金额
            this.$bonus = $dom.filter('[_n="bonus"]');  // 最高奖金
            this.cache = []; // sel的前一个值
            
            this.observer = {
                clickOption: function(){
                    var $this = $(this);
                    ctx._clickOption($this.closest('tr').attr('_m'), $this.attr('_o'));
                    return false;
                },
                removeMatch: function(){
                    ctx._removeMatch($(this).attr('_m'));
                    return false;
                }
            };
            
            // 加载赛事数据
            $.getJSON(option.url, {playId: this.playId, _: new Date().getTime()}, function(json){
                if(json.success && json.data.length > 0){
                    ctx.renderMatch(ctx.bet.parseMatchs(json.data));
                }
            });
            
            this._bind();
        },

        renderMatch: function(matchs){
            for(var i = 0, len = matchs.length; i < len; i++){
                this.cache[i] = 0;
            }
            this.$row = $(this.renderer.render(matchs)).appendTo(this.$grid);
            var ctx = this, $a = this.$row.find('a');
            
            // 选项按钮
            $a.filter('[_o]').bind('click', this.observer.clickOption);
            
            // 全选按钮
            $a.filter('[_m]').bind('click', function(){
                ctx._addMatch($(this).attr('_m'));
                return false;
            });
            
            // 赛事选项全清按钮
            $a.filter('[_c]').bind('click', function(){
                ctx._removeMatch($(this).attr('_c'));
                return false;
            });
            
            // 显示隐藏选项
            $a.filter('[_sh]').bind('click', function(){
                var $this = $(this), $opts = $this.closest('tr').next('tr[_m]');
                if($opts.is(':visible')){
                    $opts.hide();
                    $this.find('[_sh="text"]').text('显示选项');
                    $this.find('[_sh="icon"]').removeClass('icns16_arrt-b').addClass('icns16_arrb-b');
                }else{
                    $opts.show();
                    $this.find('[_sh="text"]').text('隐藏选项');
                    $this.find('[_sh="icon"]').addClass('icns16_arrt-b').removeClass('icns16_arrb-b');
                }
                return false;
            });
            
            // 星期赛事的隐藏和显示
            $a.filter('[_w]').bind('click', function(){
                var $this = $(this), $tr = ctx.$row.filter('[_w="' + $this.attr('_w') + '"]');
                if($this.attr('_v') == '0'){
                    $this.attr('_v', '1').text('点击显示').siblings('b[_n="cls"]').addClass('icns16_arrb');
                    $tr.hide();
                }else{
                    $this.attr('_v', '0').text('点击隐藏').siblings('b[_n="cls"]').removeClass('icns16_arrb');
                    $tr.filter(':not([_s])').show();
                }
                return false;
            });
        },

        repaint: function(){
            var mask = this.bet.mask, ms = this.bet.matchs, c = this.cache,
                $j, $this, sel, o, s, j;
            
            this.$row.filter('[_m]').each(function(){
                $j = $(this), j = $j.attr('_m');
                if(c[j] != ms[j].sel){
                    sel = ms[j].sel;
                    c[j] = sel;
                    if(sel == 0){
                        $j.find('a[_s="1"]').removeClass('btnx3_acti-a').attr('_s', '0');
                    }else if(sel == mask){
                        $j.find('a[_s="0"]').addClass('btnx3_acti-a').attr('_s', '1');
                    }else{
                        $j.find('a[_s]').each(function(){
                            $this = $(this), o = $this.attr('_o'), s = $this.attr('_s');
                            if(sel & Math.pow(2, o)){
                                if(s == '0'){
                                    $this.addClass('btnx3_acti-a').attr('_s', '1');
                                }
                            }else if(s == '1'){
                                $this.removeClass('btnx3_acti-a').attr('_s', '0');
                            }
                        });
                    }
                }
            });
            this.paintMatch();
        },

        paintMatch: function(){
            if(this.$mGrid.is(':visible')){
                this.$mc.text(this.bet.mc);
                var h = [], ms = this.bet.matchs;
                for(var i = 0, len = ms.length; i < len; i++){
                    if(ms[i].sel > 0){
                        h.push(this._renderMatch(i, ms[i]));
                    }
                }
                
                var $ms = $(h.join('')).appendTo($('tbody', this.$mGrid).empty());
                // 选项按钮
                $('td a[_o]', $ms).bind('click', this.observer.clickOption);
                // 删除赛事按钮
                $('td a[_m]', $ms).bind('click', this.observer.removeMatch);
            }
        },
        
        _bind: function(){
            var ctx = this;
            this.$mKnob.bind('click', function(){
                if(ctx.$mGrid.hasClass('moreorder_acti')){
                    ctx.$mGrid.removeClass('moreorder_acti');
                }else{
                    ctx.$mGrid.addClass('moreorder_acti');
                    ctx.paintMatch();
                }
                return false;
            });
            
            this.$bet.bind('click', function(){
                ctx._bet();
            });
            
            $('input[name="multiple"]', this.$form).bind('change', function(){
                // 检查投注倍数
                var v = $.trim($(this).val());
                if(!(/^\d+$/g.test(v))){
                    alert('投注倍数必须是正整数');
                    $(this).val('1');
                    return false;
                }
                
                v = parseInt(v);
                if(v > 0 && v < 100001){
                    ctx.bet.multi = v;
                    ctx._resolve();
                }
            });
            
            // 赛事全清按钮
            this.$clear.bind('click', function(){
                ctx._clearMatch();
            });
            
            // 时间切换
            this.$time.bind('click', function(){
                ctx._toggleTime($(this).attr('_n'));
            });
        },
        
        _toggleTime: function(n){
            var $a = this.$time.eq(n).find('>a');
            if($a.is(':visible')){
                $a.hide().siblings('span').show();
                this.$time.eq(1-n).find('>a').show().siblings('span').hide();
                var $span = this.$grid.find('span[_i]');
                $span.filter('[_i="' + n + '"]').show();
                $span.filter('[_i="' + (1-n) + '"]').hide();
            }
        },
        
        // 投注赛事的所有选项
        _addMatch: function(i){
            this.bet.addMatch(i);
            this._resolve();
            this.repaint();
        },
        
        // 添加/删除过关方式
        _togglePass: function(i){
            this.bet.togglePass(i);
            this._resolve();
        },
        
        // 投注
        _bet: function(){
            var multi = parseInt($(':input[name="multiple"]', this.$form).val()), ctx = this;
            if(this.bet.money <= 0){
                alert('请选择赛事和过关方式');
                return false;
            }
            if(this.bet.money > 1000000){
                alert('最高投注金额为100万元');
                return false;
            }
            if(multi > 100000){
                alert('最高可投注10万倍');
                return false;
            }
            if(true){
                this.$form.find(':input[name="playId"]').val(this.playId);
                this.$form.find(':input[name="matchs"]').val(this.bet.toBetMatchs());
                this.$form.find(':input[name="passTypes"]').val(this.bet.getBetPass().join(','));
                if($.browser.msie && $.browser.version == 6){
                	setTimeout(function(){
                		ctx.$form.submit();
                	}, 0);
                } else {
                	this.$form.submit();
                }
            }
        },

        // 点击赛事的某个选项
        _clickOption: function(i, j){
            this.bet.clickOption(i, j);
            this._resolve();
            this.repaint();
        },
        
        // 清除所选赛事
        _clearMatch: function(){
            this.bet.clearMatch();
            this._resolve();
            this.repaint();
        },
        
        // 删除指定投注的赛事
        _removeMatch: function(i){
            this.bet.removeMatch(i);
            this._resolve();
            this.repaint();
        },
        
        // 计算投注金额和奖金
        _resolve: function(){
            var r = this.bet.resolve();
            this.$money.text(r.money);
            this.$bonus.text(r.bonus);
        },
        
        _renderMatch: function(i, m){
            if(this.playId.indexOf('LC') != -1){
                return $.format(tmpl.match, i, m.cnCode, m.gName, m.hName, this._renderOptions(m));
            }
            return $.format(tmpl.match, i, m.cnCode, m.hName, m.gName, this._renderOptions(m));
        },
        
        _renderOptions: function(m){
            var opt = [], o = m.opts, sel = m.sel, r = this.renderer;
            for(var j = 0, len = o.length; j < len; j++){
                if(sel & (1 << j)){
                    opt.push($.format(tmpl.opt, j, r.optionName(o[j])));
                }
            }
            return opt.join('');
        }
    };
    UI = Jooe.extend(UI.prototype);
    
    /**
     * 过关
     */
    var PassUI = function(){};
    PassUI.prototype = {
        init: function(option){
            this._super.init(option);
            
            var ctx = this, $dom = $('[_n]', option.panel);
            this.$pRow = $dom.filter('[_n="pRow"]');
            this.$pGrid = $dom.filter('[_n="pGrid"]');
            this.$pTip = $dom.filter('[_n="pTip"]');

            // 加载允许的过关方式
            $.getJSON('http://trade.davcai.com/df/aj_lspasstype.do?jsonp=?', {playId: this.playId, _: new Date().getTime()}, function(json){
                if(json.success && json.data.length > 0){
                    ctx._renderPass(json.data);
                }
            });
        },
        
        repaint: function(){
            this._super.repaint();
            this._refresh();
        },
        
        _renderPass: function(pt){
            var ctx = this, m = 1, i = 0, p, len = pt.length;
            var x = [], y = [], pts = [];
            
            for(; m < 4 && i < len; m++){
                for(; i < len; i++){
                    p = pt[i].id;
                    if(m != p.charAt(0)){
                        break;
                    }
                    pts.push(p);
                    x.push($.format(tmpl.pass, m, p.substr(2), i));
                }
            }
            for(; m < 16 && i < len; m++){
                y.push('<div class="row">');
                for(; i < len; i++){
                    p = pt[i].id;
                    if(m != p.charAt(0)){
                        break;
                    }
                    pts.push(p);
                    y.push($.format(tmpl.pass, m, p.substr(2), i));
                }
                y.push('</div>');
            }
            this.bet.passTypes = pts;
            if(x.length > 0){
                this.$pGrid.before(x.join(''));
            }
            if(y.length > 0){
                this.$pGrid.find('[_n=pExRow]').after(y.join(''));
            }
            this.$pRow.find(':checkbox').bind('click', function(){
                ctx._togglePass($(this).val());
            });
        },
        
        _refresh: function(){
            var mc = this.bet.mc, $this;
            this.$pRow.find('label[_m]').each(function(){
                $this = $(this);
                if($this.attr('_m') <= mc){
                    if($this.is(':hidden')){
                        $this.show();
                    }
                }else if($this.is(':visible')){
                    $this.hide().find(':checkbox').attr('checked', false);
                }
            });
            this.$pTip.toggle(mc < 2);
            this.$pGrid.toggle(mc > 3);
        },
        
        _bind: function(){
            var ctx = this;
            $('a[_n="pKnob"]', this.$pRow).bind('click', function(){
               if(ctx.$pGrid.hasClass('morechk_acti')){
                   ctx._hide();
               }else{
                   ctx._show();
               }
               return false;
            });
            
            $('a[_n="pHide"]', this.$pRow).bind('click', function(){
                ctx._hide();
                return false;
            });
            
            this._super._bind();
        },
        
        _hide: function(){
            this.$pGrid.removeClass('morechk_acti');
        },
        _show: function(){
            this.$pGrid.addClass('morechk_acti');
            this._refresh();
        }
    };
    PassUI = UI.extend(PassUI.prototype);
    
    
    this.Renderer = Renderer;
    this.UI = UI;
    this.PassUI = PassUI;
    
    this.ZC1Renderer = ZC1Renderer;
    this.ZC2Renderer = ZC2Renderer;
    this.ZC3Renderer = ZC3Renderer;
    this.ZC4Renderer = ZC4Renderer;
    this.LC6Renderer = LC6Renderer;
    this.LC7Renderer = LC7Renderer;
    this.LC8Renderer = LC8Renderer;
    this.LC9Renderer = LC9Renderer;
});