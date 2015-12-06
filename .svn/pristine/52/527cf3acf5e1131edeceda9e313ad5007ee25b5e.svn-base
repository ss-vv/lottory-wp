Jet().$package(function(J){
	var fontsize="style='font-size:13.5px;'";
    var tmpl = {
        week: '<tr class="trcs"><td colspan="9"><div class="open_close"><b>{0} 星期{1}</b><a class="c-c" href="javascript:void(0)" _w="{2}" _v="0">点击隐藏</a><b _n="cls" class="icns icns16 icns16_arrt"></b></div></td></tr>',
        opt: '<a class="spf" href="javascript:void(0);" _o="{0}" title="删除该项">{1}</a>',
        match: '<tr _m="{0}">'
            + '<td class="tdl"><div class="intd">{1}</div></td>'
            + '<td><div class="intd">{2} VS {3}</div></td>'
            + '<td><div class="intd"><div class="tagbox tagbox-d"><div class="row">{4}</div><span class="clear"/></div></div></td>'
            + '<td style="text-align:center;"><div class="intd"><a href="javascript:void(0);" _m="{0}" title="删除该场比赛">删</a></div></td>'
            + '</tr>',
        matchseed: '<tr _m="{0}">'
            + '<td class="tdl"><div class="intd">{1}</div></td>'
            + '<td><div class="intd">{2} VS {3}</div></td>'
            + '<td><div class="intd"><div class="tagbox tagbox-d"><div class="row">{4}</div><span class="clear"/></div></div></td>'
            + '<td><input type="checkbox" name="dan" _dan="{0}"></td>'
            + '<td style="text-align:center;"><div class="intd"><a href="javascript:void(0);" _m="{0}" title="删除该场比赛">删</a></div></td>'
            + '</tr>',
        pass: '<label class="lbl" _m="{0}" style="display:none;"><input type="checkbox" name="pt" value="{2}"/><span>{0}串{1}</span></label>',
        passSingle: '<label class="lbl" _m="{0}" style="display:none;"><input type="checkbox" name="pt" value="{2}"/>单关</span></label>'
    };    
    var weekCN = ['日','一','二','三','四','五','六'];
    
    var validInputBetAmount = function(ctx) {
		var theoryBonus = ctx.$bonus.html();
		var parseBonus = parseInt(theoryBonus);
		if("点击查看" != theoryBonus && (isNaN(parseBonus) || parseBonus <= 0)) {
			alert("请选择过关方式!");
		} else {
			var multi = parseInt($(':input[name="multiple"]', ctx.$form).val());
			if(ctx.bet.money <= 0){
				alert('请选择赛事和过关方式');
				return false;
			}
			if(ctx.bet.money > 1000000){
				alert('最高投注金额为100万元');
				return false;
			}
			if(multi > 100000){
				alert('最高可投注10万倍');
				return false;
			}
			bms = ctx.bet._getBetMatchs();
			
			return true;
		}
	};
	
	function toDecimal(x) {  
        var f = parseFloat(x);  
        if (isNaN(f)) {  
            return;  
        }  
        f = Math.round(x*100)/100;  
        return f;  
    }
	
	function toDateStr(date){
		return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
	}
	
    var Renderer = function(){};
    Renderer.prototype = {
        render: function(ms){
            var h = [], m = ms[0], 
            date = $.parseDate(m.deadline),
            preDate = toDateStr(date);
            var currentWeek = date.getDay();
            h.push(this.renderWeek(currentWeek, date));
            for(var i = 0, len = ms.length; i < len; i++){
                m = ms[i];
                var matchEndDate = $.parseDate(m.deadline);
                var curDate = toDateStr(matchEndDate);
                
                if(curDate != preDate){
                    preDate = curDate;
                    date = $.parseDate(m.deadline);
                    currentWeek = date.getDay();
                    h.push(this.renderWeek(currentWeek, date));
                }
                h.push(this.renderMatch(currentWeek, m, i));
            }
            return h.join('');
        },        
        renderWeek: function(week, date){
            return $.format(tmpl.week, this.formatDate(date), weekCN[week], 
            		(date.getMonth()+1)+'-'+date.getDate());
        },
        renderMatch: function(week, match, i){
            return '';
        },
        optionName: function(o){
            return this.names[o];
        },
        formatDate: function(date){
            return date.getFullYear() + '-' + Renderer.fill(date.getMonth() + 1) + '-' + Renderer.fill(date.getDate());
        },
        // help functions
        monthDayFromStr: function(dateStr){
            var pattern = /(\d+)-(\d+)-(\d+).*/;
            var matched = pattern.exec(dateStr);
            var month = parseInt(matched[2], 10);
            var date = parseInt(matched[3], 10);
            return month+'-'+date;
        },
        formatOdds:function (odds){
        	for (var i = 0; i < odds.length; i++) {
				if(odds[i]){
	        		if(odds[i].indexOf(".") < 0){ //整数情况
						odds[i] += ".00";
					} else {
						var tail = odds[i].split(".");
						if(tail[1] && tail[1].length == 1){ //小数部分一位数情况
							odds[i] += "0";
						}
					}
				}
			}
        	return odds;
        }
    };
    Renderer = Jooe.extend(Renderer.prototype);
    Renderer.fill = function(v){
        return ((v < 10) ? '0': '') + v;
    };

    //胜平负玩法
    var BD_SPF_Renderer = function(){};
    BD_SPF_Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{6}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{7};color:#ffffff;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{5}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                    '<td>'+
		            	'<div>' + 
		            		'<a class="matchComment" _matchStr="{8}" _matchId="{9}" _playId="{10}" href="javascript:void(0)">写</a>'+
		            	'</div>' + 
	            	'</td>' +
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:195px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif"'+fontsize+'>{1} <span class="c-i">{2}</span></span><span class="peil c-l">{3}</span></span></span></a>';
            this.optTpl2 = '<a _o="{0}" _s="0" class="btnx3 btnx3-a c-l fl" href="javascript:void(0);" style="width:100px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 3;
            this.playId = option.playId;
            this.names = {
                '3': '胜',
                '1': '平',
                '0': '负'
            };
        },
      
        renderMatch: function(w, m, i){
            var odds = this.formatOdds(m.odds), h = [];
            var concede = m.score;
            if(concede != 0){
                concede = (concede > 0) ? '(+' + concede + ')' : '(' + concede + ')';
            } else {
                concede = '';
            }
            h.push($.format(this.optTpl, 0, m.hName, concede, odds[0]));
            h.push($.format(this.optTpl2, 1, odds[1]));
            h.push($.format(this.optTpl, 2, m.gName, '', odds[2]));
            var entrustEndDate = this.monthDayFromStr(m.deadline);
            var matchCode = m.code;
            var matchData = matchStr(m, lottery, this.playId);
            
            return $.format(this.matchTpl, i, matchCode, m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		h.join(''), entrustEndDate, m.color,
            		matchData,m.id, this.playId);
        }
    };
    BD_SPF_Renderer = Renderer.extend(BD_SPF_Renderer.prototype);
    
    //比分玩法
    var BD_BF_Renderer = function(){};
    BD_BF_Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{9};color:#ffffff;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{5} VS {6}</div></td>' +
                    '<td class="tdr"><div class="intd"><a _sh="{0}" class="btnx2 btnx2-b btnx2-b-a" href="javascript:void(0);"><span class="inbtn"><span _sh="text">显示选项</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></a></div></td>' +
	            	'<td>'+
		            	'<div>' + 
		            	'<a class="matchComment" _matchStr="{10}" _matchId="{11}" _playId="{12}" href="javascript:void(0)">写</a>'+
		            	'</div>' + 
	            	'</td>' +
                    '</tr>'+
                    '<tr _m="{0}" _w="{8}" _s="0" style="display:none;"><td colspan="6">{7}</td></tr>';
            /**
             * style="width:76px;
             */
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:100px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif" '+fontsize+'>{1}</span><span class="peil" style="width:91px;">{2}</span></span></span></a>';
            this.optionCount = 25;
            this.playId = option.playId;
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
                '09': '负其他'
            };
        },
        
        renderMatch: function(w, m, i){
            var opts = m.opts, odds = this.formatOdds(m.odds), h = [];
            
            for(var j = 0; j < this.optionCount; j++){
                h.push($.format(this.optTpl, j, this.names[opts[j]], odds[j]));
                if(j == 9 || j == 14){
                    h.push('<br/>');
                }
            }
            
            var matchData = matchStr(m, lottery, this.playId);
            var entrustEndDate = this.monthDayFromStr(m.deadline);
            var matchCode = m.code;

            return $.format(this.matchTpl, i, matchCode, m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		m.hName, m.gName, h.join(''), entrustEndDate, m.color,
            		matchData,m.id, this.playId);
        }
    };
    BD_BF_Renderer = Renderer.extend(BD_BF_Renderer.prototype);
    
    //上下单双
    var BD_SXDS_Renderer = function(){};
    BD_SXDS_Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{9};color:#ffffff;;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd"><div class="hide" '+fontsize+'>{5} VS {6}</div></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{7}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                	'<td>'+
	                	'<div>' + 
	                	'<a class="matchComment" _matchStr="{10}" _matchId="{11}" _playId="{12}" href="javascript:void(0)">写</a>'+
	                	'</div>' + 
                	'</td>' +
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:50px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 4;
            this.playId = option.playId;
            this.names = {
                '11': '上单',
                '12': '上双',
                '01': '下单',
                '02': '下双',
            };
        },
      
        renderMatch: function(w, m, i){
            var odds = this.formatOdds(m.odds), h = [];    
            for(var j = 0, k = m.opts.length; j < k; j++){
                 h.push($.format(this.optTpl, j, odds[j]));
            }
            var matchData = matchStr(m, lottery, this.playId);
            var entrustEndDate = this.monthDayFromStr(m.deadline);
            var matchCode = m.code;
            
            return $.format(this.matchTpl, i, matchCode, m.lName, 
        		m.deadline.substr(5, 11).replace('T', ' '), 
        		m.pTime.substr(5, 11).replace('T', ' '), 
        		m.hName, m.gName, h.join(''), entrustEndDate, m.color,
        		matchData,m.id, this.playId);
        }
    };
    BD_SXDS_Renderer = Renderer.extend(BD_SXDS_Renderer.prototype);
    
    //BQC
    var BD_BQC_Renderer = function(){};
    BD_BQC_Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{9};color:#ffffff;;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd"><div class="hide" '+fontsize+'>{5} VS {6}</div></td>' +
                    '<td><div class="intd" '+fontsize+'>{7}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                	'<td>'+
	                	'<div>' + 
	                	'<a class="matchComment" _matchStr="{10}" _matchId="{11}" _playId="{12}" href="javascript:void(0)">写</a>'+
	                	'</div>' + 
                	'</td>' +
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:50px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 9;
            this.playId = option.playId;
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
            var odds = this.formatOdds(m.odds), h = [];    
            for(var j = 0, k = m.opts.length; j < k; j++){
                h.push($.format(this.optTpl, j, odds[j]));
            }
            var matchData = matchStr(m, lottery, this.playId);
            var entrustEndDate = this.monthDayFromStr(m.deadline);
            var matchCode = m.code;

            return $.format(this.matchTpl, i, matchCode, 
        		m.lName, m.deadline.substr(5, 11).replace('T', ' '), 
        		m.pTime.substr(5, 11).replace('T', ' '), 
        		m.hName, m.gName, h.join(''), entrustEndDate, m.color,
        		matchData,m.id, this.playId);
        }
    };
    BD_BQC_Renderer = Renderer.extend(BD_BQC_Renderer.prototype);
    
    //JQS
    var BD_JQS_Renderer = function(){};
    BD_JQS_Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{9};color:#ffffff;;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd"><div class="hide" '+fontsize+'>{5} VS {6}</div></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{7}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                	'<td>'+
	                	'<div>' + 
	                	'<a class="matchComment" _matchStr="{10}" _matchId="{11}" _playId="{12}" href="javascript:void(0)">写</a>'+
	                	'</div>' + 
                	'</td>' +
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:50px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 8;
            this.playId = option.playId;
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
            var odds = this.formatOdds(m.odds), h = [];    
            for(var j = 0, k = m.opts.length; j < k; j++){
                 h.push($.format(this.optTpl, j, odds[j]));
            }
            var matchData = matchStr(m, lottery, this.playId);
            var entrustEndDate = this.monthDayFromStr(m.deadline);
            var matchCode = m.code;

            return $.format(this.matchTpl, i, matchCode, m.lName, 
        		m.deadline.substr(5, 11).replace('T', ' '), 
        		m.pTime.substr(5, 11).replace('T', ' '), 
        		m.hName, m.gName, h.join(''), entrustEndDate, m.color,
        		matchData,m.id, this.playId);
        }
    };
    BD_JQS_Renderer = Renderer.extend(BD_JQS_Renderer.prototype);
    
    //胜负过关玩法
    var BD_SF_Renderer = function(){};
    BD_SF_Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{6}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{7};color:#ffffff;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{5}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                    '<td>'+
		            	'<div>' + 
		            		'<a class="matchComment" _matchStr="{8}" _matchId="{9}" _playId="{10}" href="javascript:void(0)">写</a>'+
		            	'</div>' + 
	            	'</td>' +
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:245px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif"'+fontsize+'>{1} <span class="c-i">{2}</span></span><span class="peil c-l">{3}</span></span></span></a>';
            this.optionCount = 2;
            this.playId = option.playId;
            this.names = {
                '3': '胜',
                '0': '负'
            };
        },
      
        renderMatch: function(w, m, i){
            var odds = this.formatOdds(m.odds), h = [];    
            var concede = m.score;
            if(concede != 0){
                concede = (concede > 0) ? '(+' + concede + ')' : '(' + concede + ')';
            } else {
                concede = '';
            }
            h.push($.format(this.optTpl, 0, m.hName, concede, odds[0]));
            h.push($.format(this.optTpl, 1, m.gName, '', odds[1]));
            var entrustEndDate = this.monthDayFromStr(m.deadline);
            var matchCode = m.code;
            var matchData = matchStr(m, lottery, this.playId);
            
            return $.format(this.matchTpl, i, matchCode, m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		h.join(''), entrustEndDate, m.color,
            		matchData,m.id, this.playId);
        }
    };
    BD_SF_Renderer = Renderer.extend(BD_SF_Renderer.prototype);
    
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
            this.issueNumber = option.issueNumber;
            this.onSale = true;
            
            var ctx = this, $dom = $('[_n]', this.$panel);
            this.$form = $dom.filter('[_n="form"]');    // 投注表单            
            this.$mKnob = $dom.filter('[_n="mKnob"]');  // 切换“已选赛事”按钮
            this.$mGrid = $dom.filter('[_n="mGrid"]');  // 已选赛事列表
            this.$clear = $dom.filter('[_n="clear"]');  // 清除所有已选赛事
            this.$bet = $dom.filter('[_n="bet"]');      // 投注按钮      
            this.$mc = $dom.filter('[_n="mc"]');        // 已选赛事数量
            this.$money = $dom.filter('[_n="money"]');  // 投注金额
            this.$bonus = $dom.filter('[_n="bonus"]');  // 最高奖金
            this.$showRecom = $('[_n="show_recom"]');  // 显示推荐对话框
            this.$postRecommend = $('button[_n="postRecommend"]');  // 发推荐
            this.cache = []; // sel的前一个值
            this.$issueHistory = $("#issueNumbersDiv");
            
            this.observer = {
                clickOption: function(){
                    var $this = $(this);
                    ctx.iClickOption($this.closest('tr').attr('_m'), $this.attr('_o'));
                    return false;
                },
                removeMatch: function(){
                    ctx._removeMatch($(this).attr('_m'));
                    return false;
                },
                setSeed: function(){
                	//设胆码
                	var seedset = [];
                	$("input[name='dan']:checkbox:checked").each(function(){
                		seedset.push($(this).attr('_dan'));
                	});
                	ctx._updateSeed(seedset);
                }
            };
            
            // 加载赛事数据
            var loadMatchParams = {
            	issueNumber: this.issueNumber,
        		playId: this.playId,
        		_: new Date().getTime()
            };
            $.getJSON(option.url, loadMatchParams, function(json){
                if(json.success && json.data){
                	if(json.data.matchs && json.data.matchs.length > 0) {
                		ctx.renderMatch(ctx.bet.parseMatchs(json.data.matchs));
                	}
                	if(json.data.issues && json.data.issues.length > 0) {
                		ctx.renderHistoryIssue(json.data.issues);
                	}
                	if(json.data.currIssue) {
                		var qy = "#issueNumbersDiv option[value='"+json.data.currIssue+"']";
                		$(qy).attr("selected", true);
                	}
                	if(!json.data.onSale) {//非在售期不开放投注
                		ctx.onSale = false;                		
                		ctx.$bet.css("background","url(/images/bet/btn-c.png)");
                	}
                	//绑定推荐的赛事选中
                	bindRecommendMatchOptions(ctx);
                }
            });
            
            this._bind();
        },

        renderMatch: function(matchs) {
            for(var i = 0, len = matchs.length; i < len; i++){
                this.cache[i] = 0;
            }
            this.$row = $(this.renderer.render(matchs)).appendTo(this.$grid);
            var ctx = this;
            
            // 选项按钮
            $('[_m]').delegate('a[_o]','click', this.observer.clickOption);
            
            // 全选按钮
            $('a[_m]').bind('click', function(){
                ctx._addMatch($(this).attr('_m'));
                return false;
            });
            
            //加注释（赛事推荐）
            $(".matchComment").bind("click", function() {
            	showAddCommentDialog($(this), ctx);
            });
            
            // 赛事选项全清按钮
            $('a[_c]').bind('click', function(){
                ctx._removeMatch($(this).attr('_c'));
                return false;
            });

            // 显示隐藏选项
            $('a[_sh]').bind('click', function(){
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
            var weekShowHide = $('a[_w]');
            weekShowHide.bind('click', function(){
            	var $this = $(this);
            	var w = $this.attr('_w');
                var $tr = ctx.$row.filter('[_w="' + w + '"]');
                if($this.attr('_v') == '0'){
                    $this.attr('_v', '1').text('点击显示').siblings('b[_n="cls"]').addClass('icns16_arrb');
                    $tr.hide();
                }else{
                    $this.attr('_v', '0').text('点击隐藏').siblings('b[_n="cls"]').removeClass('icns16_arrb');
                    $tr.filter(':not([_s])').show();
                }
                return false;
            });
        	weekShowHide.each(function(index){
        		if(index > 0){
        			$(this).click();
        		}
        	});
        },
        
        /**渲染历史期信息*/
        renderHistoryIssue: function(issues) {
        	var historyIssueTpl = "{{#issues}}<option value='{{issueNumber}}'>{{issueNumber}}期</option>{{/issues}}";
        	this.$issueHistory.empty();
        	var segment = $.mustache(historyIssueTpl, {
        		issues:issues
        	});
        	this.$issueHistory.append($(segment));
        	
        	if(!this.issueNumber) {
        		var firstOpt = this.$issueHistory.find("option")[0];
        		var selOption = $(firstOpt).val();
        		this.issueNumber = selOption;
        	}
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
                //勾选胆码
                var seedset = this.bet.getSeedSet();

                	$("input[name='dan']:checkbox").each(function(){
                    	if($(this).attr('_dan') in seedset){
                    		$(this).attr("checked",'true');
                    	}
                	});

                // 选项按钮
                $('td a[_o]', $ms).bind('click', this.observer.clickOption);
                // 胆码选项
                if($('[_dan]')){
                	$('[_dan]').bind('click', this.observer.setSeed);
                }
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
            
            this._betSubmit();
            this.showRecommendDialog();
            this._ajaxRecommend();
            
            this.$bet.bind('click', function(){
            	if($.browser.msie && $.browser.version == 6){
                	setTimeout(function(){
                		ctx.$form.submit();
                	}, 0);
                } else {
                	ctx.$form.submit();
                }
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
        _betSubmit: function(){
        	var ctx = this;
        	ctx.$form.bind('submit', function(e){
        		var multi = parseInt($(':input[name="multiple"]', ctx.$form).val());
        		if(!ctx.onSale) {
        			alert("期已经截止，对不起您不能投注！");
        			return false;
        		}
                if(ctx.bet.money <= 0){
                    alert('请选择赛事和过关方式');
                    return false;
                }
                if(ctx.bet.money > 1000000){
                    alert('最高投注金额为100万元');
                    return false;
                }
                if(multi > 100000){
                    alert('最高可投注10万倍');
                    return false;
                }
                if(true){
                	ctx.$form.find(':input[name="issueNumber"]').val(ctx.issueNumber);
                    ctx.$form.find(':input[name="playId"]').val(ctx.playId);
                    ctx.$form.find(':input[name="matchs"]').val(ctx.bet.toBetMatchs());
                    ctx.$form.find(':input[name="passTypes"]').val(ctx.bet.getBetPass().join(','));
                    
                    //带上对赛事的注释内容
                    var matchAnnResult = BetContent.getBetContent(ctx.bet.toBetMatchs());
                    if(matchAnnResult && matchAnnResult.length > 0) {
                    	ctx.$form.find(':input[name="matchAnnotation"]').val(matchAnnResult.join());
                    }
                }else{
                	e.preventDefault();
                }
        	});
        },

        // 点击赛事的某个选项
		iClickOption: function(i, j){
            this.bet.clickOption(i, j);
            this._resolve();
            this.repaint();
        },
        
        iClearMatch : function() {
			this._clearMatch();
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
        
        //更新胆码
        _updateSeed:function(seedset){
        	this.bet.updateSeed(seedset);
        	this._resolve();
        	this.repaint();
        },
        
        // 计算投注金额和奖金
        _resolve: function(){
            var r = this.bet.resolve();
            this.$money.text(r.money);
            this.$bonus.text(r.bonus == 0 ? '0':r.bonus);
        },
        
        _renderMatch: function(i, m){
        	//"SPF,JQS,BF,BQC,SXDS"过关玩法可以加胆码
        	var spf = this.playId.indexOf('SPF');
        	var jqs = this.playId.indexOf('JQS');
        	var bf = this.playId.indexOf('BF');
        	var bqc = this.playId.indexOf('BQC');
        	var sxds = this.playId.indexOf('SXDS');
        	var sxds = this.playId.indexOf('SF');
			if(spf > -1 || jqs > -1 || bf > -1 || bqc > -1 || sxds > -1) {
				return $.format(tmpl.matchseed, i, m.cnCode, m.hName, m.gName, this._renderOptions(m));
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
        },
      //发推荐请求数据有效性验证
        validRecommendData: function() {
        	var ctx = this;
        	var multi = parseInt($(':input[name="multiple"]', ctx.$form).val());
        	var betMatchs = ctx.bet.toBetMatchs();
            if(ctx.bet.money > 1000000){
                alert('最高投注金额为100万元');
                return false;
            }
            if(multi > 100000){
                alert('最高可投注10万倍');
                return false;
            }
            if(!betMatchs) {
            	alert('请选择赛事.');
            	return false;
            }
            return true;
        },
        //显示推荐dialog
        showRecommendDialog: function() {
        	var ctx = this;
        	ctx.$showRecom.bind('click', function(e){
        		var betMatchs = ctx.bet.toBetMatchs();
        		if(ctx.validRecommendData()) {
        			showPublishRecommendDialog(betMatchs);
        		}
        	});
        },
        //推荐
		_ajaxRecommend: function(){
			var ctx = this;
        	ctx.$postRecommend.bind('click', function(e){
        		if(!ctx.validRecommendData()) {
        			return;
        		}
        		var playId = ctx.playId;
        		var betMatchs = ctx.bet.toBetMatchs();
        		var passTypes = ctx.bet.getBetPass().join(',');
        		var money = ctx.bet.money;
        		var multi = ctx.bet.multi;
        		var bonus = ctx.$bonus.html();
        		publishRecommend(playId, betMatchs, passTypes, money, multi, bonus);
        	});
		},
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
            var param = {
        		playId: this.playId, 
        		_: new Date().getTime()
        	};
            $.getJSON('http://trade.davcai.com/df/aj_lspasstype.do?jsonp=?', param, function(json){
                if(json.success && json.data.length > 0){
                	var passTypes = json.data;
                	for(var index in passTypes) {
                		var passType = passTypes[index];
                		if(passType.id == "1@1") {
                			passTypes[index].name = "单关";
                		}
                	}
                    ctx._renderPass(passTypes);
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
                    if(m == p.substr(2) && m == 1) {
                    	x.push($.format(tmpl.passSingle, m, p.substr(2), i));
                    } else {
                    	x.push($.format(tmpl.pass, m, p.substr(2), i));
                    }
                }
            }
            for(; m < 16 && i < len; m++){
                y.push('<div class="row">');
                for(; i < len; i++){
                    p = pt[i].id;
                    if(m != p.split("@")[0]){
                        break;
                    }
                    pts.push(p);
                    if(m == p.substr(2) && m == 1) {
                    	y.push($.format(tmpl.passSingle, m, p.substr(2), i));
                    } else {
                    	y.push($.format(tmpl.pass, m, p.substr(2), i));
                    }
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
        
        //隐藏显示允许的过关方式选项
        _refresh: function(){
            var mc = this.bet.mc, $this;
            //计算胆码数量
            var seedcount=0;
            var bms = this.bet._getBetMatchs();
            for(var i=0;i<bms.length;i++){
            	if(bms[i].seed){
            		seedcount++;
            	}
            }
            this.$pRow.find('label[_m]').each(function(){
                $this = $(this);
                if($this.attr('_m') <= mc){
                    if($this.is(':hidden')){
                        $this.show();
                    }
                }else if($this.is(':visible')){
                    $this.hide().find(':checkbox').attr('checked', false);
                }
                //取消胆码不支持的玩法
                if($this.attr('_m') < seedcount){
                	if($this.is(':visible')){
                        $this.hide().find(':checkbox').attr('checked', false);
                    }
                }
            });
            this.$pTip.toggle(mc < 1);
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
    this.BD_SPF_Renderer = BD_SPF_Renderer;
    this.BD_BF_Renderer = BD_BF_Renderer;
    this.BD_SXDS_Renderer = BD_SXDS_Renderer;
    this.BD_BQC_Renderer = BD_BQC_Renderer;
    this.BD_JQS_Renderer = BD_JQS_Renderer;
    this.BD_SF_Renderer = BD_SF_Renderer;
});
