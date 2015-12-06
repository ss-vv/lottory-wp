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
        pass: '<label class="lbl" _m="{0}" style="display:none;"><input type="checkbox" name="pt" value="{2}"/><span>{0}串{1}</span></label>'
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
			if(ctx.bet.passPlays && ctx.bet.passPlays.all.length < 2){
				alert('混合过关不能只有一种玩法');
				return false;
			}
			$("#bonusDetailForm > input[name='matchs']").val(ctx.bet.toBetMatchs(bms));
			//console.log("-->" + $("#bonusDetailForm > input[name='matchs']").val());
			$("#bonusDetailForm > input[name='playId']").val(ctx.playId);
			$("#bonusDetailForm > input[name='passTypes']").val(ctx.bet.getBetPass().join(','));
			$("#bonusDetailForm > input[name='multiple']").val(ctx.bet.multi);
			$("#bonusDetailForm > input[name='totalAmount']").val(ctx.bet.money);
			$("#bonusDetailForm > input[name='maxBonus']").val(theoryBonus);
			
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
    
	var loadBonusDetail = function(ctx) {
		if(true == validInputBetAmount(ctx)) {
			var _tipsFadeOut = function(e) {
				var src = e.target || window.event.srcElement;
				if(src && $(src).attr("_n") != "bonus") {
					$("#jjtips").hide();
				}
    		};
    		$("#closebtn").click(_tipsFadeOut);
    		$(document).click(_tipsFadeOut);
    		$("#jjtips").mouseover(function() {
    			$(document).unbind("click", _tipsFadeOut);
    		});
    		$("#jjtips").mouseout(function() {
    			$(document).bind("click", _tipsFadeOut);
    		});
    		$("#jjtips").bind("selectstart",function(){return false;}); 
    		
			//显示过关类型
    		$("#betPass").text("（" + ctx.bet.getBetPass().join(',') + "）");
			$("#loadingAnimation").show();
			$("#jjtips").show();
			
			//异步发送过关信息到后台,然后计算理论奖金值并返回
			$.getJSON('http://trade.davcai.com/ac/bonus/bonusEval.do?jsonp=?', {
					playId: ctx.playId, 
					matchs:ctx.bet.toBetMatchs(bms), 
					passTypes:ctx.bet.getBetPass().join(','),
					multiple:ctx.bet.multi
				}, function(json) {
					if(json.success && json.data) {
						var supposeHits = json.data.supposeHits;
						var len = supposeHits.length;
						$("#jjtipstable .bonusDetailTR").remove();
						for(var i = 0; i < len; i++) {
							var hitCount = supposeHits[i].hitCount;
							var min = supposeHits[i].minBonus;
							var max = supposeHits[i].maxBonus;
							
							//console.log("命中场次：" + hitCount + ">" + min + ">" + max);
							var betMoney = ctx.bet.money;
							var tr = $("<tr class='bonusDetailTR'></tr>");
							var hitCountTd = $('<td align="center">' + hitCount + '</td>');
							var sep = $('<td align="center"> ～ </td>');
							var minBonus,maxBonus;
							if(betMoney <= min) {
								minBonus = $('<td align="right"><span class="font_red">' + toDecimal(min) + '</span></td>');
							} else {
								minBonus = $('<td align="right"><span>' + toDecimal(min) + '</span></td>');
							}
							if(betMoney <= max) {
								maxBonus = $('<td align="left"><span class="font_red">' + toDecimal(max) + '</span></td>');
							} else {
								maxBonus = $('<td align="left"><span>' + toDecimal(max) + '</span></td>');
							}
							tr.append(hitCountTd).append(minBonus).append(sep).append(maxBonus);
							$("#jjtipstable").append(tr);
						}
						
						$("#loadingAnimation").hide();
	                } else {
	                	$("#jjtips").hide();
	                	alert("奖金评测数据加载失败.");
	                }
				}
			);
		}
	};
	
	function toDateStr(date){
		return date.getFullYear()+'-'+(date.getMonth()+1)+'-'+date.getDate();
	}
	
    var Renderer = function(){};
    Renderer.prototype = {
        render: function(ms){
            var h = [], m = ms[0], 
            // 不用比赛时间，用截止投注时间来分组. $.parseDate(m.pTime) 
            date = $.parseDate(m.deadline),
            // 不能以星期来作为判断依据，否则相邻两场比赛，日期不同，但星期相同，会导致bug。
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
                    '<td><div class="intd"><span class="sort" style="background-color:{7};color:#ffffff;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{5}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                    '<td>'+
		            	'<div>' + 
		                	'<a href="{8}" target="_blank">亚</a>/' + 
		                	'<a href="{9}" target="_blank">欧</a>/' + 
		                	'<a href="{10}" target="_blank">析</a>'+
		            	'</div>' + 
	            	'</td>' +
	            	'<td>'+
		            	'<div>' + 
		            		'<a class="matchComment" _matchStr="{11}" _matchId="{12}" _playId="{13}" href="javascript:void(0)">写</a>'+
		            	'</div>' + 
	            	'</td>' +
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:160px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif"'+fontsize+'>{1} <span class="c-i">{2}</span></span><span class="peil c-l">{3}</span></span></span></a>';
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
            var odds = m.odds, h = [];    
            var concede = m.score;
            if(concede != 0){
                concede = (concede > 0) ? '(+' + concede + ')' : '(' + concede + ')';
            } else {
                concede = '';
            }
            var analysis = matchForwardUrl(m.id, lottery);
            h.push($.format(this.optTpl, 0, m.hName, concede, odds[0]));
            h.push($.format(this.optTpl2, 1, odds[1]));
            h.push($.format(this.optTpl, 2, m.gName, '', odds[2]));
            var matchData = matchStr(m, lottery, this.playId);
            
            var entrustEndDate = this.monthDayFromStr(m.deadline);
            var matchCode = m.code.slice(1);
            
            return $.format(this.matchTpl, i, matchCode, m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		h.join(''), entrustEndDate, m.color,
            		analysis.aisanUrl, 
            		analysis.europeUrl, 
            		analysis.overview,
            		matchData,
            		m.id, this.playId);
        }
    };
    ZC1Renderer = Renderer.extend(ZC1Renderer.prototype);
    
    var ZC80Renderer = function(){};
    ZC80Renderer.prototype = {
    		init: function(option){
    			this.matchTpl = '<tr _m="{0}" _w="{6}"><td class="tdl"><div class="intd">{1}</div></td>' +
    			'<td><div class="intd"><span class="sort" style="background-color:{7};color:#ffffff;" _color>{2}</span></div></td>' +
    			'<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
    			'<td><div class="intd" '+fontsize+'>{5}</div></td>' +
    			'<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
    			'<td>'+
	    			'<div>' + 
	    				'<a href="{8}" target="_blank">亚</a>/' + 
	    				'<a href="{9}" target="_blank">欧</a>/' + 
	    				'<a href="{10}" target="_blank">析</a>'+
	    			'</div>' + 
    			'</td>' +
    			'<td>'+
	    			'<div>' + 
	    			'<a class="matchComment" _matchStr="{11}" _matchId="{12}" _playId="{13}" href="javascript:void(0)">写</a>'+
	    			'</div>' + 
    			'</td>' +
    			'</tr>';
    			this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:160px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif"'+fontsize+'>{1} <span class="c-i">{2}</span></span><span class="peil c-l">{3}</span></span></span></a>';
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
    			var odds = m.odds, h = [];    
    			var concede = m.score;

    			concede = ''; // 无让球
    			h.push($.format(this.optTpl, 0, m.hName, concede, odds[0]));
    			h.push($.format(this.optTpl2, 1, odds[1]));
    			h.push($.format(this.optTpl, 2, m.gName, '', odds[2]));
    			var analysis = matchForwardUrl(m.id, lottery);
    			var matchData = matchStr(m, lottery, this.playId);

                var entrustEndDate = this.monthDayFromStr(m.deadline);
                var matchCode = m.code.slice(1);

    			return $.format(this.matchTpl, i, matchCode, m.lName, 
    					m.deadline.substr(5, 11).replace('T', ' '), 
    					m.pTime.substr(5, 11).replace('T', ' '), 
    					h.join(''), entrustEndDate, m.color,
    					analysis.aisanUrl, analysis.europeUrl, 
    					analysis.overview,
    					matchData,m.id,this.playId);
    		}
    };
    ZC80Renderer = Renderer.extend(ZC80Renderer.prototype);
    
    var ZC2Renderer = function(){};
    ZC2Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{9};color:#ffffff;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{5} VS {6}</div></td>' +
                    '<td class="tdr"><div class="intd"><a _sh="{0}" class="btnx2 btnx2-b btnx2-b-a" href="javascript:void(0);"><span class="inbtn"><span _sh="text">显示选项</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></a></div></td>' +
                    '<td>'+
		            	'<div>' + 
		                	'<a href="{10}" target="_blank">亚</a>/' + 
		                	'<a href="{11}" target="_blank">欧</a>/' + 
		                	'<a href="{12}" target="_blank">析</a>'+
		            	'</div>' + 
	            	'</td>' +
	            	'<td>'+
		            	'<div>' + 
		            	'<a class="matchComment" _matchStr="{13}" _matchId="{14}" _playId="{15}" href="javascript:void(0)">写</a>'+
		            	'</div>' + 
	            	'</td>' +
                    '</tr>'+
                    '<tr _m="{0}" _w="{8}" _s="0" style="display:none;"><td colspan="6">{7}</td></tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:76px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif" '+fontsize+'>{1}</span><span class="peil">{2}</span></span></span></a>';
            this.optionCount = 33;
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
            var analysis = matchForwardUrl(m.id, lottery);
            var matchData = matchStr(m, lottery, this.playId);
            
            var entrustEndDate = this.monthDayFromStr(m.deadline);
            var matchCode = m.code.slice(1);

            return $.format(this.matchTpl, i, matchCode, m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		m.hName, m.gName, h.join(''), entrustEndDate, m.color,
            		analysis.aisanUrl, analysis.europeUrl, 
            		analysis.overview,
            		matchData,m.id, this.playId);
        }
    };
    ZC2Renderer = Renderer.extend(ZC2Renderer.prototype);

    var ZC3Renderer = function(){};
    ZC3Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{9};color:#ffffff;;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd"><div class="hide" '+fontsize+'>{5} VS {6}</div></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{7}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                    '<td>'+
	                	'<div>' + 
	                		'<a href="{10}" target="_blank">亚</a>/' + 
	                		'<a href="{11}" target="_blank">欧</a>/' + 
	                		'<a href="{12}" target="_blank">析</a>'+
	                	'</div>' + 
                	'</td>' +
                	'<td>'+
	                	'<div>' + 
	                	'<a class="matchComment" _matchStr="{13}" _matchId="{14}" _playId="{15}" href="javascript:void(0)">写</a>'+
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
            var odds = m.odds, h = [];    
            for(var j = 0, k = m.opts.length; j < k; j++){
                 h.push($.format(this.optTpl, j, odds[j]));
            }
            var analysis = matchForwardUrl(m.id, lottery);
            var matchData = matchStr(m, lottery, this.playId);
            
            var entrustEndDate = this.monthDayFromStr(m.deadline);
            var matchCode = m.code.slice(1);

            return $.format(this.matchTpl, i, matchCode, m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		m.hName, m.gName, h.join(''), entrustEndDate, m.color,
            		analysis.aisanUrl, analysis.europeUrl, 
            		analysis.overview,
            		matchData,m.id, this.playId);
        }
    };
    ZC3Renderer = Renderer.extend(ZC3Renderer.prototype);
    
    var ZC4Renderer = function(){};
    ZC4Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{9};color:#ffffff;;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd"><div class="hide" '+fontsize+'>{5} VS {6}</div></td>' +
                    '<td><div class="intd" '+fontsize+'>{7}</div></td>' +
                    '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                    '<td>'+
	                	'<div>' + 
	                		'<a href="{10}" target="_blank">亚</a>/' + 
	                		'<a href="{11}" target="_blank">欧</a>/' + 
	                		'<a href="{12}" target="_blank">析</a>'+
	                	'</div>' + 
                	'</td>' +
                	'<td>'+
	                	'<div>' + 
	                	'<a class="matchComment" _matchStr="{13}" _matchId="{14}" _playId="{15}" href="javascript:void(0)">写</a>'+
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
            var odds = m.odds, h = [];    
            for(var j = 0, k = m.opts.length; j < k; j++){
                h.push($.format(this.optTpl, j, odds[j]));
            }
            var analysis = matchForwardUrl(m.id, lottery);
            var matchData = matchStr(m, lottery, this.playId);
            
            var entrustEndDate = this.monthDayFromStr(m.deadline);
            var matchCode = m.code.slice(1);

            return $.format(this.matchTpl, i, matchCode, 
            		m.lName, m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		m.hName, m.gName, h.join(''), entrustEndDate, m.color,
            analysis.aisanUrl, analysis.europeUrl, 
            analysis.overview,
            matchData,m.id, this.playId);
        }
    };
    ZC4Renderer = Renderer.extend(ZC4Renderer.prototype);
    
    var ZC5Renderer = function(){};
    ZC5Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{9};color:#ffffff;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{5} VS {6}</div></td>' +
                    '<td class="tdr"><div class="intd">'+
                    '<a _sh="{0}"  _play="spf" class="btnx3 btnx3-a {10} fl" href="javascript:void(0);" style="width: 90px;"><span _play="spf"   class="btnx3_l" style="width: 90px;"><span class="btnx3_r"><span>{14}</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></span></a>'+
                    '<a _sh="{0}"  _play="brqspf" class="btnx3 btnx3-a {19} fl" href="javascript:void(0);" style="width: 65px;"><span _play="brqspf"   class="btnx3_l" style="width: 65px;"><span class="btnx3_r"><span>{18}</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></span></a>'+
                    '<a _sh="{0}"  _play="jqs"  class="btnx3 btnx3-a {11} fl" href="javascript:void(0);" style="width: 65px;"><span  _play="jqs"  class="btnx3_l" style="width: 65px;"><span class="btnx3_r"><span>{15}</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></span></a>'+
                    '<a _sh="{0}"  _play="bf"  class="btnx3 btnx3-a {12} fl" href="javascript:void(0);" style="width: 60px;"><span  _play="bf"   class="btnx3_l" style="width: 60px;"><span class="btnx3_r"><span>{16}</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></span></a>'+
                    '<a _sh="{0}"  _play="bqc"  class="btnx3 btnx3-a {13} fl" href="javascript:void(0);" style="width: 65px;"><span _play="bqc" class="btnx3_l" style="width: 65px;"><span class="btnx3_r"><span>{17}</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></span></a>'+                    
                    '</div></td>' +
                    '<td>'+
	                	'<div>' + 
	                		'<a href="{20}" target="_blank">亚</a>/' + 
	                		'<a href="{21}" target="_blank">欧</a>/' + 
	                		'<a href="{22}" target="_blank">析</a>'+
	                	'</div>' + 
                	'</td>' +
                	'<td>'+
	                	'<div>' + 
	                	'<a class="matchComment" _matchStr="{23}" _matchId="{24}" _playId="{25}" href="javascript:void(0)">写</a>'+
	                	'</div>' + 
                	'</td>' +
                    '</tr>'+
                    '<tr  _m="{0}" _w="{8}">{7}</tr>';
            this.optTD = '<td _jcstyle="spf" colspan="6"  style="display:none;">{0}</td>'+
            		'<td _jcstyle="brqspf" colspan="6"  style="display:none;">{1}</td>'+
            		'<td _jcstyle="jqs" colspan="6" style="display:none;">{2}</td>'+
            		'<td _jcstyle="bf" colspan="6"  style="display:none;">{3}</td>'+
            		'<td _jcstyle="bqc" colspan="6"  style="display:none;">{4}</td>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:80px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif"'+fontsize+'>{2} <span class="c-i"></span></span><span class="peil c-l">{1}</span></span></span></a>';
            this.optionCount = 54;
            this.playId = option.playId;
            this.names = {'胜': '胜','平': '平','负': '负','胜': '胜','平': '平','负': '负','0':'0球','1':'1球','2':'2球','3':'3球','4':'4球','5':'5球','6':'6球','7':'7+球','10': '1:0','20': '2:0','21': '2:1', '30': '3:0','31': '3:1','32': '3:2','40': '4:0','41': '4:1',
                    '42': '4:2','43': '4:3','50': '5:0','51': '5:1','52': '5:2','90': '胜其他', '00': '0:0', '11': '1:1', '22': '2:2','33': '3:3',
                    '99': '平其他', '01': '0:1','02': '0:2','12': '1:2','03': '0:3','13': '1:3', '23': '2:3','04': '0:4','14': '1:4','24': '2:4', '34': '3:4','05': '0:5', '15': '1:5',
                    '25': '2:5','09': '负其他','33': '胜胜','31': '胜平','30': '胜负','13': '平胜','11': '平平','10': '平负','03': '负胜','01': '负平','00': '负负'};
            this.optnames = ['0球','1球','2球','3球','4球','5球','6球','7+球'];
        },
      
        renderMatch: function(w, m, i){
        	var odds = m.odds, h = [];    
            for(var j = 0, k = m.opts.length; j < k; j++){
                 h.push($.format(this.optTpl, j+6, odds[j], this.optnames[j]));
            }
            var tempTD = $.format(this.optTD, this.spf(m), this.brqspf(m) , h.join(''), this.bf(m), this.bqc(m));
            var analysis = matchForwardUrl(m.id, lottery);
            var matchData = matchStr(m, lottery, this.playId);
            
            var entrustEndDate = this.monthDayFromStr(m.deadline);
            var matchCode = m.code.slice(1);

            return $.format(this.matchTpl, i, matchCode, m.lName, m.deadline.substr(5, 11).replace('T', ' '), m.pTime.substr(5, 11).replace('T', ' '), m.hName, m.gName, 
            		tempTD, entrustEndDate, m.color,
            		jQuery.inArray('spf' ,m.notOpenSale) != -1 ? 'btnx3-d' : '',
    				jQuery.inArray('jqs' ,m.notOpenSale) != -1 ?'btnx3-d' : '',
					jQuery.inArray('bf' ,m.notOpenSale) != -1 ?'btnx3-d' : '',
					jQuery.inArray('bqc' ,m.notOpenSale) != -1?'btnx3-d' : '',
					jQuery.inArray('spf' ,m.notOpenSale) != -1 ? '未开售' : '让球胜平负',
    				jQuery.inArray('jqs' ,m.notOpenSale) != -1 ? '未开售' : '进球数',
    				jQuery.inArray('bf' ,m.notOpenSale) != -1 ?  '未开售' : '比分',
    				jQuery.inArray('bqc' ,m.notOpenSale) != -1 ?  '未开售' : '半全场',
    				jQuery.inArray('brqspf' ,m.notOpenSale) != -1 ?  '未开售' : '胜平负',
    				jQuery.inArray('brqspf' ,m.notOpenSale) != -1 ? 'btnx3-d' : '',
    				analysis.aisanUrl, analysis.europeUrl, analysis.overview,
    				matchData,m.id,this.playId);
        },
        brqspf:function(m){
        	var optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);"  style="width:260px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif"'+fontsize+'>{1} <span class="c-i">{4}{2}</span></span><span class="peil c-l">{3}</span></span></span></a>';
        	var optTpl2 = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);"  style="width:100px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif"'+fontsize+'><span class="c-i">{2}</span></span><span class="peil c-l">{1}</span></span></span></a>';
        	var names =['(胜)','(平)','(负)'];
        	var odds = m.oddsZCBrqspf, h = [];    
        	h.push($.format(optTpl, 3, m.hName, '', odds[0], names[0]));
        	h.push($.format(optTpl2, 4, odds[1], names[1]));
        	h.push($.format(optTpl, 5, m.gName, '', odds[2], names[2]));
        	return h.join('');
        },
        spf:function(m){
        	var optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:260px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif"'+fontsize+'>{1} <span class="c-i">{4}{2}</span></span><span class="peil c-l">{3}</span></span></span></a>';
        	var optTpl2 = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:100px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif"'+fontsize+'><span class="c-i">{2}</span></span><span class="peil c-l">{1}</span></span></span></a>';
        	var names =['(胜)','(平)','(负)'];
            var odds = m.oddsZCSpf, h = [];    
            var concede = m.score;
            if(concede != 0){
                concede = (concede > 0) ? '(+' + concede + ')' : '(' + concede + ')';
            } else {
                concede = '';
            }
            h.push($.format(optTpl, 0, m.hName, concede, odds[0], names[0]));
            h.push($.format(optTpl2, 1, odds[1], names[1]));
            h.push($.format(optTpl, 2, m.gName, '', odds[2], names[2]));
            return h.join('');
        },
        bqc:function(m){
        	var optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:70px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif"'+fontsize+'>{2} <span class="c-i"></span></span><span class="peil c-l">{1}</span></span></span></a>';
        	var names = ['胜胜','胜平','胜负','平胜','平平','平负','负胜','负平','负负'];
        	var odds = m.oddsZCBqc, h = [];    
            for(var j=0, k = m.optsZCBqc.length; j < k; j++){
                h.push($.format(optTpl, j+45, odds[j], names[j]));
            }
            return h.join('');
        },
        bf:function(m){
        	var optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:76px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif" '+fontsize+'>{1}</span><span class="peil">{2}</span></span></span></a>';
        	var names = {'10': '1:0', '20': '2:0',   '21': '2:1','30': '3:0', '31': '3:1','32': '3:2','40': '4:0', '41': '4:1','42': '4:2', '43': '4:3','50': '5:0','51': '5:1', '52': '5:2','90': '胜其他','00': '0:0', '11': '1:1','22': '2:2','33': '3:3', '99': '平其他', '01': '0:1', '02': '0:2', '12': '1:2', '03': '0:3',  '13': '1:3','23': '2:3', '04': '0:4', '14': '1:4','24': '2:4','34': '3:4', '05': '0:5','15': '1:5','25': '2:5','09': '负其他' };
            var opts = m.optsZCBf, odds = m.oddsZCBf, h = [];
            for(var j = 0; j < 31; j++){
                h.push($.format(optTpl, j+14, names[opts[j]], odds[j]));
                if(j == 12 || j == 17){
                    h.push('<br/>');
                }
            }
            return h.join('');
        }
        
    };
    ZC5Renderer = Renderer.extend(ZC5Renderer.prototype);
    
    var LC6Renderer = function(){};
    LC6Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{6}"><td class="tdl"><div class="intd">{1}</div></td>' +
                            '<td style="background-color:{7};color:#ffffff;"><div class="intd"><span class="sort" _color>{2}</span></div></td>' +
                            '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                            '<td><div class="intd">{5}</div></td>' +
                            '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                            '<td>'+
                            	'<div>' + 
	                            	'<a href="{8}" target="_blank">亚</a>/' + 
	                            	'<a href="{9}" target="_blank">欧</a>/' + 
	                            	'<a href="{10}" target="_blank">析</a>'+
                            	'</div>' + 
                            '</td>' +
                            '<td>'+
	                        	'<div>' + 
	                        	'<a class="matchComment" _matchStr="{11}" _matchId="{12}" _playId="{13}" href="javascript:void(0)">写</a>'+
	                        	'</div>' + 
                        	'</td>' +
                            '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:250px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{1} <span class="c-i">{2}</span></span><span class="peil c-l">{3}</span></span></span></a>';
            this.optionCount = 2;
            this.playId = option.playId;
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
            
            var analysis = matchForwardUrl(m.id, lottery);
            var matchData = matchStr(m, lottery, this.playId);

            var entrustEndDate = this.monthDayFromStr(m.deadline);

            return $.format(this.matchTpl, i, m.cnCode, m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		h.join(''), entrustEndDate, m.color, 
            		analysis.aisanUrl, analysis.europeUrl, 
					analysis.overview,
					matchData,m.id, this.playId);
        }
    };
    LC6Renderer = Renderer.extend(LC6Renderer.prototype);
    
    var LC7Renderer = function(){};
    LC7Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{6}"><td class="tdl"><div class="intd">{1}</div></td>' +
                            '<td style="background-color:{7};color:#ffffff;"><div class="intd"><span class="sort" _color>{2}</span></div></td>' +
                            '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                            '<td><div class="intd">{5}</div></td>' +
                            '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                            '<td>'+
	                        	'<div>' + 
	                            	'<a href="{8}" target="_blank">亚</a>/' + 
	                            	'<a href="{9}" target="_blank">欧</a>/' + 
	                            	'<a href="{10}" target="_blank">析</a>'+
	                        	'</div>' + 
                        	'</td>' +
                        	'<td>'+
	                        	'<div>' + 
	                        	'<a class="matchComment" _matchStr="{11}" _matchId="{12}" _playId="{13}" href="javascript:void(0)">写</a>'+
	                        	'</div>' + 
                        	'</td>' +
                            '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:209px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{1}</span><span class="peil c-l">{2}</span></span></span></a>';
            this.optionCount = 2;
            this.playId = option.playId;
            this.names = {
                '1': '主胜',
                '2': '客胜'
            };
        },
        renderMatch: function(w, m, i){
            var h = [];
            h.push($.format(this.optTpl, 0, m.gName, m.odds[0]));
            h.push($.format(this.optTpl, 1, m.hName, m.odds[1]));
            var analysis = matchForwardUrl(m.id, lottery);
            var matchData = matchStr(m, lottery, this.playId);
            
            var entrustEndDate = this.monthDayFromStr(m.deadline);

            return $.format(this.matchTpl, i, m.cnCode, m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		h.join(''), entrustEndDate, m.color,
            		analysis.aisanUrl, analysis.europeUrl, 
            		analysis.overview,
            		matchData,m.id, this.playId);
        }
    };
    LC7Renderer = Renderer.extend(LC7Renderer.prototype);
    
    var LC8Renderer = function(){};
    LC8Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                            '<td style="background-color:{9};color:#ffffff;" ><div class="intd" style="height:60px;"><span class="sort" _color style="height:60px;line-height:60px">{2}</span></div></td>' +
                            '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                            '<td><div class="intd"><div class="hide">{6}<br/>{5}</div></div></td>' +
                            '<td><div class="inth"><div class="hide">客胜<br/>主胜</div></div></td>' +
                            '<td><div class="intd">{7}</div></td>' +
                            '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>' +
                            '<td class="tdr">'+
	                        	'<div>' + 
	                            	'<a href="{10}" target="_blank">亚</a>/' + 
	                            	'<a href="{11}" target="_blank">欧</a>/' + 
	                            	'<a href="{12}" target="_blank">析</a>'+
	                        	'</div>' + 
                        	'</td>' +
                        	'<td>'+
	                        	'<div>' + 
	                        	'<a class="matchComment" _matchStr="{13}" _matchId="{14}" _playId="{15}" href="javascript:void(0)">写</a>'+
	                        	'</div>' + 
                        	'</td>' +
                            '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:60px;"><span class="btnx3_l"><span class="btnx3_r">{1}</span></span></a>';
            this.optionCount = 12;
            this.playId = option.playId;
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
            var analysis = matchForwardUrl(m.id, lottery);
            var matchData = matchStr(m, lottery, this.playId);

            var entrustEndDate = this.monthDayFromStr(m.deadline);

            return $.format(this.matchTpl, i, m.cnCode, m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		m.hName, m.gName, h.join(''), entrustEndDate, m.color,
            		analysis.aisanUrl, analysis.europeUrl, 
            		analysis.overview,
            		matchData,m.id, this.playId);
        }
    };
    LC8Renderer = Renderer.extend(LC8Renderer.prototype);
    
    var LC9Renderer = function(){};
    LC9Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _m="{0}" _w="{6}">'
                + '<td class="tdl"><div class="intd">{1}</div></td>'
                + '<td><div class="intd"><span class="sort" style="background-color:{7};color:#ffffff;"  _color>{2}</span></div></td>'
                + '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>'
                + '<td><div class="intd">{5}</div></td>'
                + '<td class="tdr"><div class="intd"><a href="javascript:void(0);" _m="{0}">全</a></div></td>'
                +'<td>'+
	            	'<div>' + 
	                	'<a href="{8}" target="_blank">亚</a>/' + 
	                	'<a href="{9}" target="_blank">欧</a>/' + 
	                	'<a href="{10}" target="_blank">析</a>'+
	            	'</div>' + 
            	'</td>' +
            	'<td>'+
	            	'<div>' + 
	            	'<a class="matchComment" _matchStr="{11}" _matchId="{12}" _playId="{13}" href="javascript:void(0)">写</a>'+
	            	'</div>' + 
            	'</td>' +
                + '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:205px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{1}</span><span class="peil c-l">{2}</span></span></span></a>'
                    + '<span class="fl" style="width:101px;">{3}</span>'
                    + '<a _o="{4}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:205px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{5}</span><span class="peil c-l">{6}</span></span></span></a>';
            this.optionCount = 3;
            this.playId = option.playId;
            this.names = {
                '1': '大',
                '2': '小'
            };
        },
        renderMatch: function(w, m, i){
        	var analysis = matchForwardUrl(m.id, lottery);
        	var matchData = matchStr(m, lottery, this.playId);
            var o = $.format(this.optTpl, 0, m.gName, m.odds[0], m.score, 1, m.hName, m.odds[1]);

            var entrustEndDate = this.monthDayFromStr(m.deadline);

            return $.format(this.matchTpl, i, m.cnCode, 
            		m.lName, m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), o, entrustEndDate, m.color,
            		analysis.aisanUrl, 
            		analysis.europeUrl, 
            		analysis.overview,
            		matchData,m.id,this.playId);
        }
    };
    LC9Renderer = Renderer.extend(LC9Renderer.prototype);

    var LC10Renderer = function(){};
    LC10Renderer.prototype = {
        init: function(option){
            this.matchTpl = '<tr _w="{8}"><td class="tdl"><div class="intd">{1}</div></td>' +
                    '<td><div class="intd"><span class="sort" style="background-color:{9};color:#ffffff;" _color>{2}</span></div></td>' +
                    '<td><div class="intd"><span _i="0">{3}</span><span _i="1" style="display:none;">{4}</span></div></td>' +
                    '<td><div class="intd" '+fontsize+'>{6} VS {5}</div></td>' +
                    '<td class="tdr"><div class="intd">'+
                    '<a _sh="{0}" _play="rfsf" class="btnx3 btnx3-a {13} fl" href="javascript:void(0);" style="width: 90px;"><span _play="rfsf"   class="btnx3_l" ><span class="btnx3_r"><span>{17}</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></span></a>'+
                    '<a _sh="{0}"  _play="sf"  class="btnx3 btnx3-a {14} fl" href="javascript:void(0);" style="width: 80px;"><span  _play="sf"  class="btnx3_l" ><span class="btnx3_r"><span>{18}</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></span></a>'+
                    '<a _sh="{0}"  _play="fc" class="btnx3 btnx3-a {15} fl" href="javascript:void(0);" style="width: 80px;"><span  _play="fc"  class="btnx3_l" ><span class="btnx3_r"><span>{19}</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></span></a>'+
                    '<a _sh="{0}" _play="dxf"  class="btnx3 btnx3-a {16} fl" href="javascript:void(0);" style="width: 80px;"><span _play="dxf" class="btnx3_l" ><span class="btnx3_r"><span>{20}</span><b class="icns icns16 icns16_arrb-b" _sh="icon"></b></span></span></a>'+                    
                    '</div></td>' +
                    '<td>'+
		            	'<div>' + 
		                	'<a href="{21}" target="_blank">亚</a>/' + 
		                	'<a href="{22}" target="_blank">欧</a>/' + 
		                	'<a href="{23}" target="_blank">析</a>'+
		            	'</div>' + 
	            	'</td>' +
	            	'<td>'+
		            	'<div>' + 
		            	'<a class="matchComment" _matchStr="{24}" _matchId="{25}" _playId="{26}" href="javascript:void(0)">写</a>'+
		            	'</div>' + 
	            	'</td>' +
                    '</tr>'+
                    '<tr  _m="{0}" _w="{8}">'+
                    '<td _jcstyle="rfsf" colspan="6"  style="display:none;">{7}</td>'+
                    '<td _jcstyle="sf" colspan="6" style="display:none;">{10}</td>'+
                    '<td _jcstyle="fc" colspan="6"  style="display:none;">{11}</td>'+
                    '<td _jcstyle="dxf" colspan="6"  style="display:none;">{12}</td>'+
                    '</tr>';
            this.optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:309px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{1}</span><span class="peil c-l">{2}</span></span></span></a>';
            this.optionCount = 2;
            this.playId = option.playId;
            this.names = {'rfsf1': '主胜','rfsf2': '客胜','1': '主胜','2': '客胜','01': '主胜1-5','02': '主胜6-10','03': '主胜11-15', '04': '主胜16-20', '05': '主胜21-25', '06': '主胜26+', '11': '客胜1-5', '12': '客胜6-10',
                    '13': '客胜11-15','14': '客胜16-20', '15': '客胜21-25','16': '客胜26+','dxf1': '大','dxf2': '小'};
        },
      
        renderMatch: function(w, m, i){
            var h = [];
            h.push($.format(this.optTpl, 2, m.gName+' (客)', m.odds[0]));
            h.push($.format(this.optTpl, 3, m.hName+' (主)', m.odds[1]));
            var analysis = matchForwardUrl(m.id, lottery);
            var matchData = matchStr(m, lottery, this.playId);

            var entrustEndDate = this.monthDayFromStr(m.deadline);

            return $.format(this.matchTpl, i, m.cnCode, m.lName, 
            		m.deadline.substr(5, 11).replace('T', ' '), 
            		m.pTime.substr(5, 11).replace('T', ' '), 
            		m.hName, m.gName, 
            		this.rfsf(m), entrustEndDate, m.color,
            		h.join(''),this.fc(m),this.dxf(m),
            		jQuery.inArray('rfsf' ,m.notOpenSale) != -1 ? 'btnx3-d' : '',
            		jQuery.inArray('sf' ,m.notOpenSale) != -1 ?'btnx3-d' : '',
            		jQuery.inArray('fc' ,m.notOpenSale) != -1 ?'btnx3-d' : '',
            		jQuery.inArray('dxf' ,m.notOpenSale) != -1 ?'btnx3-d' : '',
            		jQuery.inArray('rfsf' ,m.notOpenSale) != -1 ? '未开售' : '让分胜负',
            		jQuery.inArray('sf' ,m.notOpenSale) != -1 ? '未开售' : '胜负',
            		jQuery.inArray('fc' ,m.notOpenSale) != -1 ?  '未开售' : '胜分差',
            		jQuery.inArray('dxf' ,m.notOpenSale) != -1 ?  '未开售' : '大小分',
            		analysis.aisanUrl, analysis.europeUrl, analysis.overview,
            		matchData,m.id,this.playId
            		);
        },
        rfsf:function(m){
        	var optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:300px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{1} <span class="c-i">{2}</span></span><span class="peil c-l">{3}</span></span></span></a>';
            var h = [], concede = m.score;
            if(concede != 0){
                concede = (concede > 0) ? '(+' + concede + ')' : '(' + concede + ')';
            } else {
                concede = '';
            }
            h.push($.format(optTpl, 0, m.gName+' (客)', '', m.oddsLCRfsf[0]));
            h.push($.format(optTpl, 1, m.hName+' (主)', concede, m.oddsLCRfsf[1]));
            return h.join('');
        },
        fc:function(m){
        	var optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:80px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{2} <span class="c-i"></span></span><span class="peil c-l">{1}</span></span></span></a>';
            var names = [
                '客胜1-5',
                '主胜1-5',
                '客胜6-10',
                '主胜6-10',
                '客胜11-15',
                '主胜11-15',
                '客胜16-20',
                '主胜16-20',
                '客胜21-25',
                '主胜21-25',
                '客胜26+',
                '主胜26+'
            ];
        	var odds = m.oddsLCSfc, h = [];
            for(var j = 0, k = m.optsLCSfc.length; j < k; j+=2){
                h.push($.format(optTpl, j+4, odds[j], names[j]));
            }
            h.push('<br/>');
            for(var j = 1, k = m.optsLCSfc.length; j < k; j+=2){
                h.push($.format(optTpl, j+4, odds[j], names[j]));
            }
            return h.join('');
        },
        dxf:function(m){
        	var optTpl = '<a _o="{0}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:305px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{1}</span><span class="peil c-l">{2}</span></span></span></a>'
                + '<span class="fl" style="width:101px;">{3}</span>'
                + '<a _o="{4}" _s="0" class="btnx3 btnx3-a fl" href="javascript:void(0);" style="width:305px;"><span class="btnx3_l"><span class="btnx3_r"><span class="bif">{5}</span><span class="peil c-l">{6}</span></span></span></a>';
	        var h = $.format(optTpl, 16, m.gName+' (大)', m.oddsLCDxf[0], m.score_09LC2, 17, m.hName+' (小)', m.oddsLCDxf[1]);
            return h;
        }
        
    };
    LC10Renderer = Renderer.extend(LC10Renderer.prototype);
    
    var MixUI = function(){};
    MixUI.prototype = {
    		init: function(option){
    			this.playId = option.playId;
    			this.renderer = option.renderer;
    			this.bet = new MixBet(option);
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
    			this.$showRecom = $('[_n="show_recom"]');  // 显示推荐对话框
                this.$postRecommend = $('button[_n="postRecommend"]');  // 发推荐
    			this.$bonusDetailForword = $dom.filter('[_n="bonusDetailForword"]');  //奖金明细
    			this.cache = []; // sel的前一个值
    			
    			this.playRules = {'fc':4, 'sf':8, 'rfsf':8 ,'dxf':8 ,'spf':8, 'bf':4, 'jqs':6, 'bqc':4, 'brqspf':8};
    			this.playValues = {'fc':'胜分差', 'sf':'胜负', 'rfsf':'让分胜负','dxf':'大小分','spf':'让球胜平负', 'bf':'比分', 'jqs':'进球数', 'bqc':'半全场','brqspf':'胜平负'};
    			this.$curPlayTab = [];
				
    			this.observer = {
    					clickOption: function(){
    						var $this = $(this), m = $this.closest('tr').attr('_m'), $trOpts = ctx.$grid.find('tr[_m="'+ m +'"]'), o = $this.attr('_o');
    						var play = $this.closest('td').attr('_jcstyle'), $trTab = $trOpts.prev();
    						if(typeof(play) == "undefined"  ){
    							play = $trTab.find('a[_play].btnx3_acti-a').attr('_play');
    						} 
    						ctx.$curPlayTab =  $trTab.find('a[_play="'+play+'"]');
    						var selOptCount = $trOpts.find('a[_s="1"]').length;
    						
    						if(!ctx.checkPlayOpt( selOptCount, play,  $trOpts.find('a[_o="'+ o +'"]').attr('_s'))){
    							return false;
    						}
    						ctx._clickOption(m, $this.attr('_o'));
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
    			$.getJSON(option.url, {playId: this.playId, _: new Date().getTime()}, function(json){
    				if(json.success && json.data.length > 0){
    					ctx.renderMatch(ctx.bet.parseMatchs(json.data));
    					
    					log("混合过关>赛事数据渲染完毕。");
                        if(lottery.indexOf("ZQ") > 0 || _endsWith(ctx.playId, "_1")) {
                    		setTimeout(function() {
                    			bindRecommendMatchOptions(ctx);
                        		bindPassTypeId(ctx);
                            	bindMultiple(ctx);
                    		}, 500);
                        }
    				}
    			});
    			
    			this._bind();
    		},
    		
    		// 计算已选投注选项是否符合玩法
    		checkPlayOpt:function(optCount, play, targetSeleted){
    			if(optCount === 0){
					this.bet.playResult[play] ++;
					return this.checkRule(play);
				}else if(optCount === 1 && targetSeleted == 1){
					this.bet.playResult[play] --;
					this.togglePlayTab();
				}
    			return true;
    		},
    		
    		togglePlayTab:function(){
    			if(this.$curPlayTab.filter('.btnx3_acti-a').length>0){
    				this.$curPlayTab.removeClass('btnx3_acti-a');
    			}else{
    				this.$curPlayTab.addClass('btnx3_acti-a');
    			}
    		},
    		removePlayTab:function(m){
    			
    		},
    		clearPlayTab:function(){
    			
    		},
    		checkRule:function(play){
    			var key = ['fc', 'sf', 'rfsf','dxf','spf', 'bf', 'jqs', 'bqc','brqspf'], total = 0;
    			for(var i=0, k ; k = key[i] ; i++){
    				total += this.bet.playResult[k];
    			}
    			for(var i=0, k ; k = key[i] ; i++){
    				if(this.bet.playResult[k] > 0 && total > this.playRules[k]){
    					this.bet.playResult[play] > 1 ? this.bet.playResult[play]-- : this.bet.playResult[play] = 0;
    					alert('含' + this.playValues[k]+'玩法时最大支持'+this.playRules[k]+'串关');
    					return false;
    				}
    			}
    			this.togglePlayTab();
    			return true;
    		},
    		
    		renderMatch: function(matchs){
    			for(var i = 0, len = matchs.length; i < len; i++){
    				this.cache[i] = (matchs.score_09LC2)?  new BitSet(18): new BitSet(54);
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
                	var $this = $(this), $targetOpt, $target, $tr, $optsTr, $opts;
                	$target =  $this.find('[_play]'); 				// 点击对象
                	var play = $target.attr('_play'); 				// 点击对象的玩法
                	var notOpenSale = $this.filter('.btnx3-d'); 		// 未开售
                	$tr = $this.closest('tr');							// tab行
                	ctx.$curPlayTab = $tr.find('a[_play].btnx3_acti-a'); 		// 当前已选tab					
            		$optsTr = $tr.next(); 								// 选项行
            		var _m = $optsTr.attr('_m');					// 当前赛事下标
            		$opts = $optsTr.find('td[_jcstyle]');
            		$targetOpt = $opts.filter('[_jcstyle="'+play+'"]');
            		$otherOpt = $opts.not('[_jcstyle="'+play+'"]');
            		if(notOpenSale.length > 0){
            			return;
            		}
            		if($targetOpt.is(':visible')){
            			$targetOpt.hide();
                		$this.find('[_sh="icon"]').removeClass('icns16_arrt-b').addClass('icns16_arrb-b');
                	}else{
                		var $clickedOpts=$otherOpt.find('a[_s="1"]');
            			if($clickedOpts.length != 0 ){
            				if(confirm('确定要切换玩法?确认后会取消当前选项!')){
            					var oldPlay = $clickedOpts.closest('td').attr('_jcstyle');
            					ctx.bet.playResult[oldPlay] = ctx.bet.playResult[oldPlay] - 1;
            					ctx.togglePlayTab();
                				$clickedOpts.each(function(){
                					$this = $(this);
                					var o=$this.attr('_o'); 				// 当前选项下标
                					ctx._clickOption(_m, o);
                				});
            				}else{
            					return;
            				}
        				}
                		$opts.hide();
                		$targetOpt.show();
                		$tr.find('[_sh="icon"]').removeClass('icns16_arrt-b').addClass('icns16_arrb-b');
                		$this.find('[_sh="icon"]').addClass('icns16_arrt-b').removeClass('icns16_arrb-b');
                	}
                	return false;
                });
    			
    			// 星期赛事的隐藏和显示
    			$('a[_w]').bind('click', function(){
    				var $this = $(this);
    				var w = $this.attr('_w');
    				console.log('bind 的星期：'+w);
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
    		},
    		
    		repaint: function(){
    			var ms = this.bet.matchs, c = this.cache,
    			$j, $this, sel, o, s, j;
    			
    			this.$row.filter('[_m]').each(function(){
    				$j = $(this), j = $j.attr('_m');
    				if(!c[j].equals(ms[j].sel)){
    					sel = ms[j].sel;
    					c[j] = jQuery.extend(true, {}, sel);
    					if(sel.size() == 0){
    						$j.find('a[_s="1"]').removeClass('btnx3_acti-a').attr('_s', '0');
    					}else if(sel.isFull()){
    						$j.find('a[_s="0"]').addClass('btnx3_acti-a').attr('_s', '1');
    					}else{
    						$j.find('a[_s]').each(function(){
    							$this = $(this), o = $this.attr('_o'), s = $this.attr('_s');
    							if(sel.getAt(o)){
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
    					if(ms[i].sel.size() > 0){
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
    			
    			$("#betBonusDetail").bind("click", function() {
    				$("#bonusDetailForm").submit();
    			});
    			
    			this.$bonusDetailForword.bind("click", function() {
    				if(true == validInputBetAmount(ctx)) {
    					$("#bonusDetailForm").submit();
    				}
    			});
    			
    			//显示奖金评测结果
    			this.$bonus.bind('click', function() {
    				loadBonusDetail(ctx);
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
    			var ctx = this, bms = [];
    			ctx.$form.bind('submit', function(e){
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
    				if(ctx.bet.passPlays.all.length < 2){
    					alert('混合过关不能只有一种玩法');
    					return false;
    				}
    				if(true){
    					ctx.$form.find(':input[name="playId"]').val(ctx.playId);
    					ctx.$form.find(':input[name="matchs"]').val(ctx.bet.toBetMatchs(bms));
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
    		
    		iClickOption: function(i, j){
    			this._clickOption(i, j);
            },
    		
    		// 点击赛事的某个选项
    		_clickOption: function(i, j){
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
    			this._clearPlayTab();
    		},
    		
    		// 删除指定投注的赛事
    		_removeMatch: function(i){
    			this.bet.removeMatch(i, this.getCurPlayTabPlay(i));
    			this._resolve();
    			this.repaint();
    			this.togglePlayTab();
    		},
    		
    		// 清楚所有已选玩法选项
    		_clearPlayTab:function(){
    			this.$grid.find('a[_play].btnx3_acti-a').each(function(){
    				var $this = $(this);
    				$this.removeClass('btnx3_acti-a');
    			});
    		},
    		
    		// 得到指定赛事的玩法
    		getCurPlayTabPlay:function(i){
    			this.$curPlayTab = this.$grid.find('[_m="'+ i +'"]').prev().find('.btnx3_acti-a');
    			return this.$curPlayTab.attr('_play');
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
    			
    			if(r.bonus && (r.bonus+"").length > 8) {
    				this.$bonus.text("点击查看");
    			} else {
    				this.$bonus.text(r.bonus);
    			}
    		},
    		
    		_renderMatch: function(i, m){
    			//只有足彩篮彩过关玩法可以加胆码
    			if(this.playId.indexOf('ZC_2') != -1){
    				return $.format(tmpl.matchseed, i, m.cnCode, m.hName, m.gName, this._renderOptions(m));
    			}
    			if(this.playId.indexOf('LC_2') != -1){
    				return $.format(tmpl.matchseed, i, m.cnCode, m.gName, m.hName, this._renderOptions(m));
    			}
    			if(this.playId.indexOf('LC') != -1){
    				return $.format(tmpl.match, i, m.cnCode, m.gName, m.hName, this._renderOptions(m));
    			}
    			return $.format(tmpl.match, i, m.cnCode, m.hName, m.gName, this._renderOptions(m));
    		},
    		
    		_renderOptions: function(m){
    			var o = (m.score_09LC2)? ["rfsf2","rfsf1"].concat(m.opts,m.optsLCSfc,["dxf1","dxf2"]) :["胜","平","负"].concat( ["胜","平","负"], m.opts, m.optsZCBf, m.optsZCBqc);
    			var opt = [], sel = m.sel, r = this.renderer;
    			for(var j = 0, len = o.length; j < len; j++){
    				if(sel.getAt(j)){
    					opt.push($.format(tmpl.opt, j, r.optionName(o[j])));
    				}
    			}
    			return opt.join('');
    		}
    };
    MixUI = Jooe.extend(MixUI.prototype);
    
    var MixPassUI = function(){};
    MixPassUI.prototype = {
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
                    
                    log("混合过关>过关方式渲染完毕.");
                    if(lottery.indexOf("LQ") > 0) {
                		setTimeout(function() {
                			bindRecommendMatchOptions(ctx);
                			bindPassTypeId(ctx);
                        	bindMultiple(ctx);
                		}, 500);
                    }
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
    MixPassUI = MixUI.extend(MixPassUI.prototype);
    
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
//            var colors = option.matchColor;//赛事颜色 列表
//            for (var i=0; i<colors.length; i+=2){
//            	matchColor[colors[i]] = colors[i+1];
//            }
            
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
            this.$bonusDetailForword = $dom.filter('[_n="bonusDetailForword"]');  //奖金明细
            this.cache = []; // sel的前一个值
            
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
            $.getJSON(option.url, {playId: this.playId, _: new Date().getTime()}, function(json){
                if(json.success && json.data.length > 0){
                    ctx.renderMatch(ctx.bet.parseMatchs(json.data));
                    
                    log("赛事数据渲染完毕。");
                    if(lottery.indexOf("ZQ") > 0 || _endsWith(ctx.playId, "_1")) {
                		setTimeout(function() {
                			bindRecommendMatchOptions(ctx);
                    		bindPassTypeId(ctx);
                        	bindMultiple(ctx);	
                		}, 500);
                    }
                }
            });
            
            this._bind();
        },

        renderMatch: function(matchs){
            for(var i = 0, len = matchs.length; i < len; i++){
                this.cache[i] = 0;
            }
            this.$row = $(this.renderer.render(matchs)).appendTo(this.$grid);
            var ctx = this;

            // 赛事颜色 改变背景和字体颜色
//         console.profile('b');
//            $('span.sort').each(function(i,e){
//	            //背景
//        		if(matchColor[$(e).text()] != ""){
//        			$(e).css({background:matchColor[$(e).text()]});
//        		}
//        		//字体
//           		$(e).css({color:"#ffffff"});
//            });
//         console.profileEnd('b');
            
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
            
            $("#betBonusDetail").bind("click", function() {
				$("#bonusDetailForm").submit();
			});
			
			this.$bonusDetailForword.bind("click", function() {
				if(true == validInputBetAmount(ctx)) {
					$("#bonusDetailForm").submit();
				}
			});
			
			//显示奖金评测结果
			this.$bonus.bind('click', function() {
				loadBonusDetail(ctx);
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
            this.$bonus.text(r.bonus);
        },
        
        _renderMatch: function(i, m){
        	//只有足彩篮彩过关玩法可以加胆码
			if(this.playId.indexOf('ZC_2') != -1){
				return $.format(tmpl.matchseed, i, m.cnCode, m.hName, m.gName, this._renderOptions(m));
			}
			if(this.playId.indexOf('LC_2') != -1){
				return $.format(tmpl.matchseed, i, m.cnCode, m.gName, m.hName, this._renderOptions(m));
			}
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
                    
                    log("过关方式渲染完毕.");
                    if(lottery.indexOf("LQ") > 0) {
                		setTimeout(function() {
                			bindRecommendMatchOptions(ctx);
                			bindPassTypeId(ctx);
                        	bindMultiple(ctx);
                        	
                        	//ctx.iClickOption();
                		}, 500);
                    }
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
    this.MixUI = MixUI;
    this.PassUI = PassUI;
    this.MixPassUI = MixPassUI;
    
    this.ZC1Renderer = ZC1Renderer;
    this.ZC80Renderer = ZC80Renderer;
    this.ZC2Renderer = ZC2Renderer;
    this.ZC3Renderer = ZC3Renderer;
    this.ZC4Renderer = ZC4Renderer;
    this.ZC5Renderer = ZC5Renderer;
    this.LC6Renderer = LC6Renderer;
    this.LC7Renderer = LC7Renderer;
    this.LC8Renderer = LC8Renderer;
    this.LC9Renderer = LC9Renderer;
    this.LC10Renderer = LC10Renderer;
});
