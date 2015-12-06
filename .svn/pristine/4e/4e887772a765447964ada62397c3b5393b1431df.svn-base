//用于异步加载首页赛事数据
$(function() {
	$.extend({
		getJson : function(url, callback) {
			if (url) {
				if (url.indexOf("?") != -1) {
					url = url + "&jsonp=?";
				} else {
					url = url + "?jsonp=?";
				}
			}
			$.getJSON(url, callback);
		}
	});
});

var matchsFn = {
	expand : "展开",
	collapse : "收起",
	JCZQ_SP : "竞彩胜平负SP",
	JCLQ_SP : "竞彩胜负SP",
	CTZC_SP : "胜平负SP"
};

var sendAjaxLoadMatch = function(lotteryId) {
	var url = "";
	var matchWrapper = null;
	if(lotteryName.JCZQ == lotteryId) {
//		url = "http://trade.davcai.com/df/aj_fblist.do?playId=80_ZC_2";
//		matchWrapper = "#match_JCZQ";
		
		new JCZQMatchUI({
			url : 'http://www.davcai.com/aj_fb_selector.do',
			grid : $('#jczq_matchs'),
			panel : $('#MatchPanel'),
			renderer : new JCZQRenderer(),
			playId : '80_ZC_2'
		});
		
		return;
	} else if(lotteryName.JCLQ == lotteryId) {
//		url = "http://trade.davcai.com/df/aj_bblist.do?playId=06_LC_2";
//		matchWrapper = "#match_JCLQ";
		
		new JCLQMatchUI({
			url : 'http://www.davcai.com/aj_bb_selector.do',
			grid : $('#jclq_matchs'),
			panel : $('#MatchPanel'),
			renderer : new JCLQRenderer(),
			playId : '07_LC_2'
		});
		
		return;
	} else if(lotteryName.CTZC == lotteryId) {
		url = "http://trade.davcai.com/df/aj_ctfblist.do?playId=24_ZC_14";
		matchWrapper = "#match_CTZC";
	} else if(lotteryName.SSQ == lotteryId) {
		aj_ssq_issue_info();
	} else if(lotteryName.JX11 == lotteryId) {
		aj_jx11_issue_info();
	} else if(lotteryName.BJDC == lotteryId) {
		new BJDCMatchUI({
			url : 'http://www.davcai.com/aj_bjdc_selector',
			grid : $('#bjdc_matchs'),
			panel : $('#MatchPanel'),
			renderer : new BJDCRenderer(),
			playId : '91_BJDC_SPF'
		});
	}
	//开始加载当前在售赛事列表
	if(url && matchWrapper) {
		getMatchsByLottery(url, matchWrapper, lotteryId);
	}
};

//根据彩种ID获取赛事JSON格式数据并在首页展现
var getMatchsByLottery = function(url, matchWrapper, lotteryId) {
	$.getJson(url, function(result) {
		if(result && (result.data || result.data.ctFBMatchs)) {
			var matchs = [];
			if(result.data.length > 0) {
				matchs = result.data;
				// 按照比赛code排序，确保同一星期的比赛紧靠在一起
//				matchs.sort(function(a,b){
//					var aDay = Number(a.code);
//					var bDay = Number(b.code);
//					return aDay-bDay;
//				});
			} else {
				matchs = result.data.ctFBMatchs;
			}
			showMatchsInView(matchs, matchWrapper, lotteryId);
		}
	});
};

//赛事表格模版
var tableTemplate = 
'<table cellspacing="0" cellpadding="0" class="portfolio">' +
	'<tr class="even gameBg expand" title="点击展开/收起赛事">' +
		'<td class="title">{0}' +
			'<span class="matchDay">{1}</span>' +
			'<span class="matchNums" id="{4}"></span>' +
		'</td>' +
		'<td colspan="5"></td><td class="right collapsed">{2}</td>' +
		'<td class="last"><span class="operation icon" title="查看赛事"></span></td>' +
	'</tr>' +
	'<tbody>' + 
		'<table cellspacing="0" cellpadding="0" class="portfolio matchTable">' +
			'<thead>' +
				'<tr><th class="eventTitle matchTitle">赛事</th>'+
				'<th class="eventTitle matchNoTitle">场次</th>' +
				'<th class="eventTitle">开赛时间</th>' +
				'<th class="eventTitle teamTitle">球队</th>' +
				'<th class="eventTitle oddsTitle">{3}</th>' +
				'<th class="eventTitle operationTitle">操作</th></tr>' +
			'</thead>' +
			'<tbody id="matchTR">' +
			'</tbody>' +
		'</table>' +
	'</tbody>' +
'</table>';

//定义每一场赛事的显示模版
var trTemplate = 
'<tr class="{0}">' +
	'<td style="background-color:{1}"><span class="nocolor">{2}</span></td>' +
	'<td class="center" style="padding:0px 5px 0px 5px;"><span class="stock-color stock-current">{3}</span></td>' +
	'<td class="center">' +
		'<span class="stock-color" title="比赛时间：{4}">{5}</span>' +
	'</td>' +
	'<td style="width:24%;"><span>{6}</span></td>' +
	'<td class="center">' +
		'<span class="odd">{7}</span>' + 
		'<span class="odd">{8}</span>' + 
		'<span>{9}</span>' + 
	'</td>' +
	'<td class="center" style="width:10%">'+
		'<div>'+
			'<a href="http://www.davcai.com/asian_odds?matchId={10}&lotteryType={11}" target="_blank">亚</a>/'+
			'<a href="http://www.davcai.com/europe_odds?matchId={10}&lotteryType={11}" target="_blank">欧</a>/'+
			'<a href="http://www.davcai.com/matches/{11}/{10}" target="_blank">析</a>'+
		'</div>'+
	'</td>' +
'</tr>';

//在给定的matchWrapper区域显示赛事数据
var showMatchsInView = function(matchs, matchWrapper, lotteryId) {
	var matchContent = $(matchWrapper);
	var weekCode = null;//标识是周几
	var tableBody = null;
	var rowNumOfJC = 0;//竞彩表格对应行号,默认从0开始
	var rowNumOfCT = 0;//传统足球表格对应行号,默认从0开始
	var table = null;
	var preIssue = null;//传统足彩期号
	if (matchs == null){
		return;
	}
	for(var i=0; i<matchs.length; i++) {
		var match = matchs[i];
		if(i < matchs.length) {
			if(i != 0) {
				weekCode = matchs[i - 1].code.substr(0, 1);
				preIssue = matchs[i - 1].issueNumber;
			}
		}
		if(match.code.substr(0, 1) != weekCode) {
			rowNumOfJC = 1;
		} else {
			rowNumOfJC = rowNumOfJC + 1;
		}
		if(match.issueNumber != preIssue) {
			rowNumOfCT = 1;
		} else {
			rowNumOfCT = rowNumOfCT + 1;
		}
		if(lotteryName.JCLQ == lotteryId || lotteryName.JCZQ == lotteryId) {
			tableBody = createMatchTableJC(matchContent, match, 
					weekCode, table, tableBody, rowNumOfJC, lotteryId, matchs.length);
		} else if(lotteryName.CTZC == lotteryId) {
			tableBody = createMatchTableCTZC(matchContent, match, 
					preIssue, table, tableBody, rowNumOfCT, lotteryId);
		}
	}
};

//创建按周区分的竞彩赛事table表格
var createMatchTableJC = function(matchContent, match, weekCode, 
		table, tableBody, rowNum, lotteryId, matchSize) {
	var weekMatchCode = match.code.substr(0, 1);
	if(weekCode == null || weekMatchCode != weekCode) {
		var status = matchsFn.expand;
		if(matchSize <= 14) {
			status = matchsFn.collapse;
		} else {
			status = matchsFn.expand;
		}
		var template = getTableTemplate(match, lotteryId, status);
		table = $(template);
		if(matchSize <= 14) {
			table.css({display:"inline-table"});
		}
		weekCode = weekMatchCode;
		//绑定展开/收起单击事件
		table.find(".expand").click(function(e) {
			expandMatch(e.currentTarget);
		});
		tableBody = table.find("#matchTR");
		createTr(tableBody, match, rowNum, lotteryId);
		matchContent.append(table);
	} else if(tableBody) {
		createTr(tableBody, match, rowNum, lotteryId);
	}
	// 更新比赛数
	var matchNumId = lotteryId+'-'+weekMatchCode;
	var matchCount = tableBody.find("tr").size();
	var matchNumEl = $('#'+matchNumId);
	matchNumEl.text(matchCount+'场比赛');
	return tableBody;
};

//创建传统足彩赛事表格
var createMatchTableCTZC = function(matchContent, match, preIssue, 
		table, tableBody, rowNum, lotteryId) {
	issueNumber = match.issueNumber;
	if(preIssue == null) {
		var template = getTableTemplate(match, lotteryId, matchsFn.collapse);
		table = $(template);
		table.css({display:"inline-table"});
		//绑定展开/收起单击事件
		table.find(".expand").click(function(e) {
			expandMatch(e.currentTarget);
		});
		tableBody = table.find("#matchTR");
		createTr(tableBody, match, rowNum, lotteryId);
		matchContent.append(table);
	} else if(tableBody) {
		createTr(tableBody, match, rowNum, lotteryId);
	}
	return tableBody;
};

//创建表格TR
var createTr = function(table, match, rowNum, lotteryId) {
	//根据获得的赛事数据填充模版
	var template = getTrTemplate(match, rowNum, lotteryId);
	var tr = $(template);
	table.append(tr);
};

//返回赛事表格模版
var getTableTemplate = function(match, lotteryId, ctrl) {
	var template = null;
	var code = null;
	var sp = null;
	if(lotteryName.JCLQ == lotteryId || lotteryName.JCZQ == lotteryId) {
		code = match.cnCode.substr(0, 2);
		sp = (lotteryName.JCLQ == lotteryId) ? matchsFn.JCLQ_SP : matchsFn.JCZQ_SP;
	} else if(lotteryName.CTZC == lotteryId) {
		code = match.issueNumber + "期";
		sp = matchsFn.CTZC_SP;
	}
	var matchWeekNo = match.code.substr(0,1);
	var matchNumId = lotteryId+'-'+matchWeekNo;
	template = replacePlaceHolder(tableTemplate, 
			[match.entrustDeadline.substr(0,10), code, ctrl, sp, matchNumId]);
	return template;
};

//返回表格行数据模版
var getTrTemplate = function(match, rowNum, lotteryId) {
	var template = null;
	var code = null;
	var odds = null;
	if(lotteryName.JCLQ == lotteryId || lotteryName.JCZQ == lotteryId) {
		code = match.cnCode.substr(2);
	} else if(lotteryName.CTZC == lotteryId) {
		code = match.code;
	}
	if(lotteryName.JCLQ == lotteryId) {
		odds = match.odds.split(",");
		odds.push(" ");//由于竞彩篮球和其他彩种不同只有胜负赔率，而数据模版中设置了三个占位符，所以这里补空进行填补
	} else {
		odds = match.odds.split(",");
	}
	var trClz = rowNum % 2 == 0 ? "even_data" : "odd_data";
	//截取时间字段小时开始的索引位置
	var position = match.playingTime.length - 8;
	var time = match.playingTime.substr(position, 5);
	//根据获得的赛事数据填充模版
	var matchColor = match.color==null ? "#E22319": match.color;
	var dataArr = [trClz, matchColor, match.leagueName, 
	  			 code,match.playingTime.replace(/T/g,' '), 
				 time, match.name].concat(odds);
	dataArr.push(match.id);
	dataArr.push(lotteryId);
	template = replacePlaceHolder(trTemplate, dataArr);
	return template;
};

//展开、收起赛事列表
var expandMatch = function() {
	var cont = $(arguments[0]).find(".collapsed").text();
	if(matchsFn.expand == cont) {
		$(arguments[0]).find(".collapsed").text(matchsFn.collapse);
		$(arguments[0]).parent().parent().next().css({display:"inline-table"});
	} else {
		$(arguments[0]).find(".collapsed").text(matchsFn.expand);
		$(arguments[0]).parent().parent().next().css({display:"none"});
	}
};


/**
 * template	模版字符串
 * fillArray	占位符填充数组
 * 功能：占位符替换
 */
var replacePlaceHolder = function(template, fillArray) {
	if(template && fillArray) {
		return string_format(template, fillArray);
	}
};

var string_format = function(source, obj) {
	if(obj.length == 0) return source;
	for(var s = source, i = 0; i < obj.length; i++) {
		s = s.replace(new RegExp("\\{"+i+"\\}","g"), obj[i]);
	}
	return s;
};

var array_contains = function(source, obj) {
    var i = source.length;
    while (i--) {
        if (source[i] === obj) {
            return true;
        }
    }
    return false;
};


//=====================渲染赛程数据=====================
Jet().$package(function(J) {
	var weekCN = ['日','一','二','三','四','五','六'];
	
	/**基础赛事渲染器*/
	var Renderer = function(){};
    Renderer.prototype = {
        renderWeek: function(week){
            return '星期'+weekCN[week];
        },
        renderMatch: function(week, match, i){
            return '';
        },
        optionName: function(o){
            return this.names[o];
        },
        formatDate: function(date){
            return date.getFullYear() + '-' + this.fill(date.getMonth() + 1) + '-' + this.fill(date.getDate());
        },
        fill : function(v){
            return ((v < 10) ? '0': '') + v;
        },
        // help functions
        monthDayFromStr: function(dateStr){
            var pattern = /(\d+)-(\d+)-(\d+).*/;
            var matched = pattern.exec(dateStr);
            var month = parseInt(matched[2], 10);
            var date = parseInt(matched[3], 10);
            return month+'-'+date;
        },
        parseDate: function(s){
            var v = new RegExp('(\\d{4})\-(\\d{2})\-(\\d{2})T(\\d{2}):(\\d{2}):(\\d{2})').exec(s);
            return new Date(v[1], v[2] - 1, v[3], v[4], v[5], v[6]);
        },
        renderLeague: function(leagues){
        	if(!leagues) return;
        	var leagueTpl = '<input type="checkbox" id="{{league}}" checked="checked" value="{{league}}"/><label for="{{league}}">{{league}}[{{position}}场]</label>';
        	var segment = "";
        	for(var league in leagues) {
        		var leagueName = league;
        		var leaguePostion = leagues[league];
        		var leagueSegment = $.mustache(leagueTpl, {
        			league:leagueName,
        			position:leaguePostion
        		});
        		segment += leagueSegment;
        	}
        	return segment;
        },
        mStatus:function (matchStatus){
        	if(matchStatus == -1){
        		return '完场';
        	}
        	if(matchStatus == 0){
        		return '未开';
        	}
        	return '--';
        },
        matchsTpl : 
        	'<ul class="jj-list {{lotteryId}}">'+
        	'<li class="jj-list-a">'+
        	'<label class="jj-list-triangle"><img src="/images/Under-big-triangle.png"></label>'+
        	'<span class="jj-list-time">{{matchDate}}&nbsp;&nbsp;{{matchWeek}}</span>'+
        		'<span class="jj-list-field">{{matchCount}}</span>'+
        	'</li>'+
        	'{{#matchs}}'+
        	'<li class="jj-list-b" _lName="{{leagueName}}" _stopMatch="{{stopMatch}}" _lotteryId="{{lotteryId}}">'+
        	'<ul class="jj-list-detail">'+
        	'<li>'+
        	'<i class="jj-list-detail-num-contest">'+
	        	'<span class="jj-list-detail-num">{{mCode}}</span>'+
	        	'<span title="{{leagueName}}" class="jj-list-detail-contest match-colora" style="background-color:{{color}}">{{leagueNameShort}}</span>'+
        	'</i>'+
        	'<span class="jj-list-detail-starttime" title="{{playingTimeFmt}}">{{mStatus}}</span>'+
        	'<span class="jj-list-detail-team {{jclq_team_width}}">'+
        	'<span class="number-left" title="fifa">{{leftTeamPos}}</span>'+
        	'<a href="http://www.davcai.com/matches/{{lotteryId}}/{{matchId}}" target="_blank">'+
        	'<span class="jj-list-detail-team-name {{teamNamePlay}} {{homeTeamNameAbsolute}}" title="{{leftTeam}}">{{leftTeamSub}}{{{jczqDefaultScoreVal}}}</span>'+
        	'<label class="{{scoreWidth}}">{{mScore}}</label>'+
        	'<span class="jj-list-detail-team-name jj-list-detail-team-right {{teamNamePlay}} {{guestTeamNameAbsolute}}" title="{{rightTeam}}">{{rightTeamSub}}{{{jclqDefaultScoreVal}}}</span>'+
        	'</a>'+
        	'<span class="number-right" title="fifa">{{rightTeamPos}}</span>'+
        	'</span>'+
        	'<span class="jj-list-detail-sp">'+
	        	'<label class="jj-list-victory {{sHighLigth}}">{{sOdds}}</label>&nbsp;'+
	        	'<label class="jj-list-draw {{pHighLigth}}">{{pOdds}}</label>&nbsp;'+
	        	'<label class="jj-list-failure {{fHighLigth}}">{{fOdds}}</label>'+
        	'</span>'+
        	'<span class="jj-list-detail-operation {{jclq_operation_width}}">'+
        	'<a href="http://www.davcai.com/asian_odds?matchId={{matchId}}&lotteryType={{lotteryId}}" target="_blank" class="jj-list-ya">亚/</a>'+
        	'<a href="http://www.davcai.com/europe_odds?matchId={{matchId}}&lotteryType={{lotteryId}}" target="_blank" class="jj-list-ou">欧/</a>'+
        	'<a href="http://www.davcai.com/matches/{{lotteryId}}/{{matchId}}" target="_blank" class="jj-list-xi">析</a>'+
        	'</span>'+
        	'</li>'+
        	'</ul>'+
        	'</li>'+
        	'{{/matchs}}'+
        	'</ul>'
    };
    Renderer = Jooe.extend(Renderer.prototype);
    
    /**竞彩足球赛事渲染器*/
    var JCZQRenderer = function(){};
    JCZQRenderer.prototype = {
        init: function(option){
        	this.stopMatchs = 0;
        	this.lotteryId = "JCZQ";
        	this.rq = "01_ZC_2";
        	this.brq = "80_ZC_2";
        	this.row = '';
        },
        highlightOdd:function(oddVal, oddArr) {
        	//低中高，赔率高亮色值数组
        	var colorArray = ["sp-colora", "sp-colorb", "sp-colorc"];
        	var result = '';
        	if(oddVal == oddArr[0]) {
        		result = colorArray[0];
			} else if(oddVal == oddArr[1]) {
				result = colorArray[1];
			} else if(oddVal == oddArr[2]) {
				result = colorArray[2];
			}
        	return result;
        },
        renderOdds:function(playId, defaultScore, 
        		qtMatchStatus, score, odds, type) {
        	var color = '';
        	if(-1 == qtMatchStatus) {
        		var scoreArr = score.split(":");
        		var homeScore = parseInt(scoreArr[0]);
        		var guestScore = parseInt(scoreArr[1]);
        		var result = '';
        		var oddArr = odds.split(",");
        		var sOddVal = oddArr[0];
        		var pOddVal = oddArr[1];
        		var fOddVal = oddArr[2];
        		oddArr = oddArr.sort(function sorNumber(a,b){
        		    return a-b;
        		});
        		
        		//判断赛果：胜平负
				if(this.brq == playId) {
					if(homeScore > guestScore) {
						result = 1;
					} else if(homeScore == guestScore) {
						result = 0;
					} else if(homeScore < guestScore) {
						result = -1;
					}
				} else if(this.rq == playId) {
					if(homeScore + defaultScore > guestScore) {
						result = 1;
					} else if(homeScore + defaultScore == guestScore) {
						result = 0;
					} else if(homeScore + defaultScore < guestScore) {
						result = -1;
					}
				}
				switch(type) {
				case 's':
					if(result == 1) {
						color = this.highlightOdd(sOddVal, oddArr);
					} else {
						color = "sp-colorg";
					}
					break;
				case 'p':
					if(result == 0) {
						color = this.highlightOdd(pOddVal, oddArr);
					} else {
						color = "sp-colorg";
					}
					break;
				case 'f':
					if(result == -1) {
						color = this.highlightOdd(fOddVal, oddArr);
					} else {
						color = "sp-colorg";
					}
					break;
				}
			}
			return color;
        },
        renderMatch: function(result){
        	var ctx = this;
        	if(!result || !result.matchs) return;
        	var ctx = this;
        	var matchs = result.matchs;
        	var matchSegment = $.mustache(ctx.matchsTpl, {
        		lotteryId:function() {
        			return ctx.lotteryId;
        		},
        		matchs:matchs,
        		matchDate:result.matchDate,
        		matchWeek:result.matchWeek,
        		leagueNameShort:function() {
        			return this.leagueName.substr(0, 3);
        		},
        		mCode:function() {
        			return this.code.substr(1);
        		},
        		mScore:function() {
        			if(this.qtMatchStatus == 0 || this.qtMatchStatus == -14) {
        				return "-:-";
        			}
        			return this.score;
        		},
        		mStatus:function() {
        			if(this.stopMatch == true) {
        				ctx.stopMatchs++;
        			}
        			if(this.qtMatchStatus == 0) {
        				return this.playingTime.substr(11,5);
        			} else if(this.qtMatchStatus == 1) {
        				return "上";
        			} else if(this.qtMatchStatus == 2) {
        				return "中场";
        			} else if(this.qtMatchStatus == 3) {
        				return "下";
        			} else if(this.qtMatchStatus == -11){
                		return '待定';
                	} else if(this.qtMatchStatus == -12){
                		return '腰斩';
                	} else if(this.qtMatchStatus == -13){
                		return '中断';
                	} else if(this.qtMatchStatus == -14){
                		return '推迟';
                	} else {
        				return ctx.mStatus(this.qtMatchStatus);
        			}
        		},
        		playingTimeFmt:function() {
        			return this.playingTime.replace("T", " ");
        		},
        		leftTeam:function() {
        			return this.homeTeamName;
        		},
        		leftTeamSub:function() {
        			return this.homeTeamName.substring(0,4);
        		},
        		leftTeamPos:function() {
        			if(null == this.homeTeamPosition || "" == this.homeTeamPosition) {
        				return this.homeTeamPosition;
        			}
        			return '['+ this.homeTeamPosition + ']';
        		},
        		rightTeam:function() {
        			return this.guestTeamName;
        		},
        		rightTeamSub:function() {
        			return this.guestTeamName.substring(0,4);
        		},
        		rightTeamPos:function() {
        			if(null == this.guestTeamPosition || "" == this.guestTeamPosition) {
        				return this.guestTeamPosition;
        			}
        			return '['+ this.guestTeamPosition + ']';
        		},
        		jczqDefaultScoreVal:function() {
        			var defScore = this.defaultScore;
        			var result = '';
        			if(defScore && this.playId == ctx.rq) {
        				var scoreVal = parseInt(this.defaultScore);
        				if(scoreVal > 0) {
        					scoreVal = '<span class="green">' + '+' + scoreVal + '</span>';
        				} else {
        					scoreVal = '<span class="red">' + defScore + '</span>';
        				}
        				result = '('+scoreVal+')';
        			}
        			return result;
        		},
        		teamNamePlay:function() {
        			if(this.playId == ctx.rq) {
        				return 'team-name-jczq-rq';
        			} else if(this.playId == ctx.brq) {
        				return 'team-name-jczq-brq';
        			}
        		},
        		homeTeamNameAbsolute:function() {
        			if(this.playId == ctx.rq) {
        				return "home-team-left";
        			}
        		},
        		guestTeamNameAbsolute:function() {
        			if(this.playId == ctx.rq) {
        				return "guest-team-right";
        			}
        		},
        		sOdds:function() {
        			return this.odds.split(",")[0];
        		},
        		pOdds:function() {
        			return this.odds.split(",")[1];
        		},
        		fOdds:function() {
        			return this.odds.split(",")[2];
        		},
        		lotteryId:function() {
        			return ctx.lotteryId;
        		},
        		matchCount:function() {
        			return '[' + matchs.length + '场]';
        		},
        		sHighLigth:function() {
        			return ctx.renderOdds(this.playId, this.defaultScore, 
        					this.qtMatchStatus, this.score, this.odds, 's');
        		},
        		pHighLigth:function() {
        			return ctx.renderOdds(this.playId, this.defaultScore, 
        					this.qtMatchStatus, this.score, this.odds, 'p');
        		},
        		fHighLigth:function() {
        			return ctx.renderOdds(this.playId, this.defaultScore, 
        					this.qtMatchStatus, this.score, this.odds, 'f');
        		}
        	});
        	return matchSegment;
        },
        showMatchLoading:function(id) {
        	$("#"+id).css("display", "block");
        },
        hideMatchLoading:function(id) {
        	$("#"+id).css("display", "none");
        }
    };
    JCZQRenderer = Renderer.extend(JCZQRenderer.prototype);

    //---------------------------北京单场 renderer -------------------------------------------------
    var BJDCRenderer = function(){};
    BJDCRenderer.prototype = {
        init: function(option){
        	this.stopMatchs = 0;
        	this.lotteryId = "BJDC";
        	this.rq = "91_BJDC_SPF";
        	this.row = '';
        },
        highlightOdd:function(oddVal, oddArr) {
        	//低中高，赔率高亮色值数组
        	var colorArray = ["sp-colora", "sp-colorb", "sp-colorc"];
        	var result = '';
        	if(oddVal == oddArr[0]) {
        		result = colorArray[0];
			} else if(oddVal == oddArr[1]) {
				result = colorArray[1];
			} else if(oddVal == oddArr[2]) {
				result = colorArray[2];
			}
        	return result;
        },
        renderOdds:function(playId, defaultScore, 
        		qtMatchStatus, score, odds, type) {
        	var color = '';
        	if(-1 == qtMatchStatus) {
        		var scoreArr = score.split(":");
        		var homeScore = parseInt(scoreArr[0]);
        		var guestScore = parseInt(scoreArr[1]);
        		var result = '';
        		var oddArr = odds.split(",");
        		var sOddVal = oddArr[0];
        		var pOddVal = oddArr[1];
        		var fOddVal = oddArr[2];
        		oddArr = oddArr.sort(function sorNumber(a,b){
        		    return a-b;
        		});
        		
        		//判断赛果：胜平负
				if(this.brq == playId) {
					if(homeScore > guestScore) {
						result = 1;
					} else if(homeScore == guestScore) {
						result = 0;
					} else if(homeScore < guestScore) {
						result = -1;
					}
				} else if(this.rq == playId) {
					if(homeScore + defaultScore > guestScore) {
						result = 1;
					} else if(homeScore + defaultScore == guestScore) {
						result = 0;
					} else if(homeScore + defaultScore < guestScore) {
						result = -1;
					}
				}
				switch(type) {
				case 's':
					if(result == 1) {
						color = this.highlightOdd(sOddVal, oddArr);
					} else {
						color = "sp-colorg";
					}
					break;
				case 'p':
					if(result == 0) {
						color = this.highlightOdd(pOddVal, oddArr);
					} else {
						color = "sp-colorg";
					}
					break;
				case 'f':
					if(result == -1) {
						color = this.highlightOdd(fOddVal, oddArr);
					} else {
						color = "sp-colorg";
					}
					break;
				}
			}
			return color;
        },
        renderMatch: function(result){
        	if(!result || !result.matchs) return;
        	var ctx = this;
        	var matchs = result.matchs;
        	var matchSegment = $.mustache(ctx.matchsTpl, {
        		lotteryId:function() {
        			return ctx.lotteryId;
        		},
        		matchs:matchs,
        		matchDate:result.matchDate,
        		matchWeek:result.matchWeek,
        		leagueNameShort:function() {
        			var l = this.leagueName; 
        			l = l.replace(/\d{2}-\d{2}/,""); //去掉14-15类似部分
        			l = l.replace(/\d{2}/,"");//去掉两位整数部分
        			return l.substr(0, 3);
        		},
        		mCode:function() {
        			var code = this.code;
        			if(code.length >= 3){
        				return code;
        			} else if (code.length == 2){
        				return "0" + code;
        			} else if (code.length == 1){
        				return "00" + code;
        			}
        		},
        		mScore:function() {
        			if(this.qtMatchStatus == 0 || this.qtMatchStatus == -14) {
        				return "-:-";
        			}
        			return this.score;
        		},
        		mStatus:function() {
        			if(this.stopMatch == true) {
        				ctx.stopMatchs++;
        			}
        			if(this.qtMatchStatus == 0) {
        				return this.playingTime.substr(11,5);
        			} else if(this.qtMatchStatus == 1) {
        				return "上";
        			} else if(this.qtMatchStatus == 2) {
        				return "中场";
        			} else if(this.qtMatchStatus == 3) {
        				return "下";
        			} else if(this.qtMatchStatus == -11){
                		return '待定';
                	} else if(this.qtMatchStatus == -12){
                		return '腰斩';
                	} else if(this.qtMatchStatus == -13){
                		return '中断';
                	} else if(this.qtMatchStatus == -14){
                		return '推迟';
                	} else {
        				return ctx.mStatus(this.qtMatchStatus);
        			}
        		},
        		playingTimeFmt:function() {
        			return this.playingTime.replace("T", " ");
        		},
        		leftTeam:function() {
        			return this.homeTeamName;
        		},
        		leftTeamSub:function() {
        			return this.homeTeamName.substring(0,4);
        		},
        		leftTeamPos:function() {
        			if(null == this.homeTeamPosition || "" == this.homeTeamPosition) {
        				return this.homeTeamPosition;
        			}
        			return '['+ this.homeTeamPosition + ']';
        		},
        		rightTeam:function() {
        			return this.guestTeamName;
        		},
        		rightTeamSub:function() {
        			return this.guestTeamName.substring(0,4);
        		},
        		rightTeamPos:function() {
        			if(null == this.guestTeamPosition || "" == this.guestTeamPosition) {
        				return this.guestTeamPosition;
        			}
        			return '['+ this.guestTeamPosition + ']';
        		},
        		jczqDefaultScoreVal:function() {
        			var defScore = this.defaultScore;
        			var result = '';
        			if(defScore && this.playId == ctx.rq) {
        				var scoreVal = parseInt(this.defaultScore);
        				if(scoreVal > 0) {
        					scoreVal = '<span class="green">' + '+' + scoreVal + '</span>';
        				} else {
        					scoreVal = '<span class="red">' + defScore + '</span>';
        				}
        				result = '('+scoreVal+')';
        			}
        			return result;
        		},
        		teamNamePlay:function() {
        			if(this.playId == ctx.rq) {
        				return 'team-name-jczq-rq';
        			} else if(this.playId == ctx.brq) {
        				return 'team-name-jczq-brq';
        			}
        		},
        		homeTeamNameAbsolute:function() {
        			if(this.playId == ctx.rq) {
        				return "home-team-left";
        			}
        		},
        		guestTeamNameAbsolute:function() {
        			if(this.playId == ctx.rq) {
        				return "guest-team-right";
        			}
        		},
        		sOdds:function() {
        			return this.odds.split(",")[0];
        		},
        		pOdds:function() {
        			return this.odds.split(",")[1];
        		},
        		fOdds:function() {
        			return this.odds.split(",")[2];
        		},
        		lotteryId:function() {
        			return ctx.lotteryId;
        		},
        		matchCount:function() {
        			return '[' + matchs.length + '场]';
        		},
        		sHighLigth:function() {
        			return ctx.renderOdds(this.playId, this.defaultScore, 
        					this.qtMatchStatus, this.score, this.odds, 's');
        		},
        		pHighLigth:function() {
        			return ctx.renderOdds(this.playId, this.defaultScore, 
        					this.qtMatchStatus, this.score, this.odds, 'p');
        		},
        		fHighLigth:function() {
        			return ctx.renderOdds(this.playId, this.defaultScore, 
        					this.qtMatchStatus, this.score, this.odds, 'f');
        		}
        	});
        	return matchSegment;
        },
        showMatchLoading:function(id) {
        	$("#"+id).css("display", "block");
        },
        hideMatchLoading:function(id) {
        	$("#"+id).css("display", "none");
        },
        renderIssues:function (issues){
        	var optionTmpl = '<option value="{0}">{0}期</option>';
        	var html = '';
        	for (var i = 0; i < issues.length; i++) {
				html += $.format(optionTmpl,issues[i].issueNumber);
			}
        	return html;
        }
    };
    BJDCRenderer = Renderer.extend(BJDCRenderer.prototype);
    //---------------------------北京单场 renderer -------------------------------------------------
    
    /**竞彩足球赛事渲染器*/
    var JCLQRenderer = function(){};
    JCLQRenderer.prototype = {
        init: function(option){
        	this.stopMatchs = 0;
        	this.lotteryId = "JCLQ";
        	this.rfsf = "06_LC_2";
			this.sf = "07_LC_2";
        	this.row = '';
        },
        highlightOdd:function(oddVal, oddArr) {
        	//低中，赔率高亮色值数组
        	var colorArray = ["sp-colora", "sp-colorc"];
        	var result = '';
        	if(oddVal == oddArr[0]) {
        		result = colorArray[0];
			} else if(oddVal == oddArr[1]) {
				result = colorArray[1];
			}
        	return result;
        },
        renderOdds:function(playId, defaultScore, 
        		qtMatchStatus, score, odds, type) {
        	var color = '';
        	if(-1 == qtMatchStatus) {
        		var scoreArr = score.split(":");
        		var homeScore = parseInt(scoreArr[0]);
        		var guestScore = parseInt(scoreArr[1]);
        		var result = '';
        		var oddArr = odds.split(",");
        		var sOddVal = oddArr[0];
        		var fOddVal = oddArr[1];
        		oddArr = oddArr.sort(function sorNumber(a,b){
        		    return a-b;
        		});
        		
        		//判断赛果：胜平负
				if(this.sf == playId) {
					if(homeScore > guestScore) {
						result = 1;
					} else if(homeScore < guestScore) {
						result = -1;
					}
				} else if(this.rfsf == playId) {
					if(homeScore + defaultScore > guestScore) {
						result = 1;
					} else if(homeScore + defaultScore < guestScore) {
						result = -1;
					}
				}
				switch(type) {
				case 's':
					if(result == 1) {
						color = this.highlightOdd(sOddVal, oddArr);
					} else {
						color = "sp-colorg";
					}
					break;
				case 'f':
					if(result == -1) {
						color = this.highlightOdd(fOddVal, oddArr);
					} else {
						color = "sp-colorg";
					}
					break;
				}
			}
			return color;
        },
        renderMatch: function(result){
        	if(!result || !result.matchs) return;
        	var ctx = this;
        	var matchs = result.matchs;
        	var matchSegment = $.mustache(ctx.matchsTpl, {
        		lotteryId:function() {
        			return ctx.lotteryId;
        		},
        		matchs:matchs,
        		matchDate:result.matchDate,
        		matchWeek:result.matchWeek,
        		leagueNameShort:function() {
        			return this.leagueName.substr(0, 3);
        		},
        		mCode:function() {
        			return this.code.substr(1);
        		},
        		mScore:function() {
        			if(this.qtMatchStatus == 0 || this.qtMatchStatus == -14) {
        				return "-:-";
        			}
        			return this.score;
        		},
        		mStatus:function() {
        			if(this.stopMatch == true) {
        				ctx.stopMatchs++;
        			}
        			if(this.qtMatchStatus == 0) {
        				return this.playingTime.substr(11,5);
        			} else if(this.qtMatchStatus == 1) {
        				return "一节";
        			} else if(this.qtMatchStatus == 2) {
        				return "二节";
        			} else if(this.qtMatchStatus == 3) {
        				return "三节";
        			} else if(this.qtMatchStatus == 4) {
        				return "四节";
        			} else if(this.qtMatchStatus == -2) {
        				return "待定";
        			} else if(this.qtMatchStatus == -3) {
        				return "中断";
        			} else if(this.qtMatchStatus == -4) {
        				return "取消";
        			} else if(this.qtMatchStatus == -5) {
        				return "推迟";
        			} else if(this.qtMatchStatus == 50) {
        				return "中场";
        			} else {
        				return ctx.mStatus(this.qtMatchStatus);
        			}
        		},
        		playingTimeFmt:function() {
        			return this.playingTime.replace("T", " ");
        		},
        		leftTeam:function() {
        			return this.guestTeamName;
        		},
        		leftTeamSub:function() {
        			return this.guestTeamName.substring(0,4);
        		},
        		leftTeamPos:function() {
        			if(null == this.guestTeamPosition || "" == this.guestTeamPosition) {
        				return this.guestTeamPosition;
        			}
        			return '['+ this.guestTeamPosition + ']';
        		},
        		rightTeam:function() {
        			return this.homeTeamName;
        		},
        		rightTeamSub:function() {
        			return this.homeTeamName.substring(0,4);
        		},
        		rightTeamPos:function() {
					if(null == this.homeTeamPosition || "" == this.homeTeamPosition) {
        				return this.homeTeamPosition;
        			}
        			return '['+ this.homeTeamPosition + ']';
        		},
        		jclqDefaultScoreVal:function() {
        			var defScore = this.defaultScore;
        			var result = '';
        			if(defScore && this.playId == ctx.rfsf) {
        				if(defScore > 0) {
        					defScore = '<span class="green">' + '+' + defScore + '</span>';
        				} else {
        					defScore = '<span class="red">' + defScore + '</span>';
        				}
        				result = '('+defScore+')';
        			}
        			return result;
        		},
        		teamNamePlay:function() {
        			if(this.playId == ctx.rfsf) {
        				return 'team-name-jclq-rfsf';
        			} else if(this.playId == ctx.sf) {
        				return 'team-name-jclq-sf';
        			}
        		},
        		scoreWidth:function() {
        			return 'jclq-score-width';
        		},
        		jclq_team_width:function() {
        			if(this.playId == ctx.rfsf) {
        				return 'jclq-team-width';
        			}
        		},
        		jclq_operation_width:function() {
        			if(this.playId == ctx.rfsf) {
        				return 'jclq-operation-width';
        			}
        		},
        		sOdds:function() {
        			return this.odds.split(",")[0];
        		},
        		fOdds:function() {
        			return this.odds.split(",")[1];
        		},
        		lotteryId:function() {
        			return ctx.lotteryId;
        		},
        		matchCount:function() {
        			return '[' + matchs.length + '场]';
        		},
        		sHighLigth:function() {
        			return ctx.renderOdds(this.playId, this.defaultScore, 
        					this.qtMatchStatus, this.score, this.odds, 's');
        		},
        		pHighLigth:function() {
        			return 'none';
        		},
        		fHighLigth:function() {
        			return ctx.renderOdds(this.playId, this.defaultScore, 
        					this.qtMatchStatus, this.score, this.odds, 'f');
        		}
        	});
        	return matchSegment;
        },
        showMatchLoading:function(id) {
        	$("#"+id).css("display", "block");
        },
        hideMatchLoading:function(id) {
        	$("#"+id).css("display", "none");
        }
    };
    JCLQRenderer = Renderer.extend(JCLQRenderer.prototype);
    
    //UI
    var UI = function(){};
    UI.prototype = {
        init: function(option){
        	this.url = option.url;
            this.playId = option.playId;
            this.playingTime = option.playingTime;
            this.renderer = option.renderer;
            this.grid = option.grid;
        }
    };
    UI = Jooe.extend(UI.prototype);
    
    var JCZQMatchUI = {};
    JCZQMatchUI.prototype = {
		init: function(option){
			this._super.init(option);
            this.rq = "01_ZC_2";
        	this.brq = "80_ZC_2";
            
            this.leagues = $("#jczq_leagues");
            this.stopMatchSum = $("#jczq_stop_MatchSum");
            this.jcCalendar = $("#jczq_calendar");
            this.jcCalendar.val(this.renderer.formatDate(new Date()));
            this.hiddenStopCell = $("#jczq_hidden_stop_cell");
            this.jczqSpfSp = $("#jczq_spf_sp");
            this.jczqRqspfSp = $("#jczq_rqspf_sp");
            this.selectAll = $(".jczq-select-all");
            this.reverseSelect = $(".jczq-reverse-select");
            this.clearCheckLeague = $(".jczq-clear-league");
            this.matchLoading = "jczq_match_loading";
            
            this._sendRequest();
            this._initBind();
        },
        _selector:function() {
        	var option = {};
        	//开赛时间
        	option.playingTime = this.jcCalendar.val();
        	
        	//玩法切换
        	option.playId = $("#jczq_spf_sp > .spf").attr("_playId");
        	return option;
        },
        _sendRequest:function(option) {
        	this.clearAll();
        	this.renderer.showMatchLoading(this.matchLoading);
        	
        	var ctx = this;
        	var param = {
				playId: this.playId,
				_: new Date().getTime()
			};
        	param = $.extend(param, option);
        	$.ajax({
        		url:ctx.url,
        		data:param,
        		type:"POST",
        		dataType:"JSON",
        		success:function(result) {
        			ctx.renderer.hideMatchLoading(ctx.matchLoading);
        			if(result && result.success == true) {
        				var leagues = result.data.leagues;
        				var matchs = result.data.matchs;
        				ctx.paintLeague(leagues);
        				ctx.splitData(matchs);
        			}
        			ctx._bind();
        		}
        	});
        },
        paintLeague: function(leagueData){
    		var leagues = this.renderer.renderLeague(leagueData);
    		this.leagues.append(leagues);
    		this._bindLeagueClick();
        },
        splitData: function(matchData) {
        	var result = [];
        	var row = '';
        	for(var i=0; i<matchData.length; i++) {
        		var match = matchData[i];
        		var deadDate = match.entrustDeadline.substr(0,10);
        		var idRow = deadDate;
        		if(!row || row == idRow) {
        			result.push(match);
        			row = idRow;
        		} else {
        			if(result.length > 0) {
        				this.createMatchObj(result, result[0].entrustDeadline);
        			}
        			result = [];
        			row = '';
        			result.push(match);
        		}
        	}
        	if(result.length > 0) {
				this.createMatchObj(result, result[0].entrustDeadline);
    		}
        },
        createMatchObj:function(matchResult, entrustDeadline) {
			var obj = {};
			obj.matchs = matchResult;
			obj.matchDate = entrustDeadline.substr(0,10);
			obj.matchWeek = this.matchWeek(entrustDeadline);
			this.paintMatch(obj);
        },
		matchWeek:function(date) {
			var date = this.renderer.parseDate(date);
            var currentWeek = date.getDay();
			return this.renderer.renderWeek(currentWeek);
		},
        paintMatch: function(matchData){
        	var matchSegment = this.renderer.renderMatch(matchData);
        	this.grid.append(matchSegment);
        	var stopCn = "["+this.renderer.stopMatchs+"场]";
        	this.stopMatchSum.text(stopCn);
        },
        leagueCheckBox:function() {
        	return this.leagues.find("input[type='checkbox']");
        },
        leagueList:function() {
        	var list = [];
        	this.leagues.find("input[type='checkbox']").each(function() {
        		if($(this).attr("checked")=="checked") {
        			var leagueName = $(this).attr("id");
        			if(leagueName) {
        				list.push(leagueName);
        			}
        		}
        	});
        	return list;
        },
        _bindLeagueClick:function() {
        	var ctx = this;
        	//联赛切换
        	this.leagueCheckBox().each(function(i, elt) {
        		$(elt).click(function() {
        			var lgName = $(this).val();
        			var matchList = $("li[_lName='"+lgName+"']");
        			if($(this).attr("checked")=="checked") {
        				ctx.renderMultipleRow(matchList, "league", true);
        			} else {
        				ctx.renderMultipleRow(matchList, "league", false);
        			}
            	});
        	});
        },
        _initBind:function() {
        	var ctx = this;
        	//日历框变换
        	this.jcCalendar.change(function() {
        		var option = ctx._selector();
        		ctx._sendRequest(option);
        	});
        	this.jczqRqspfSp.click(function() {
        		$(".jj-nav-sp ul").hide();
        		var jczq_spf_sp = $("#jczq_spf_sp > .spf");
        		var jczq_rqspf_sp = $("#jczq_rqspf_sp > .rqspf");
        		
        		var brqTxt = "胜平负sp";
        		var rqTxt = "让球胜平负sp";
        		if(jczq_rqspf_sp.attr("_playId") == ctx.rq) {
        			jczq_rqspf_sp.text(brqTxt);
        			jczq_rqspf_sp.attr("_playId", ctx.brq);
        			
        			jczq_spf_sp.text(rqTxt);
        			jczq_spf_sp.attr("_playId", ctx.rq);
        		} else {
        			jczq_spf_sp.text(brqTxt);
        			jczq_spf_sp.attr("_playId", ctx.brq);
        			
        			jczq_rqspf_sp.text(rqTxt);
        			jczq_rqspf_sp.attr("_playId", ctx.rq);
        		}
        		var option = ctx._selector();
        		ctx._sendRequest(option);
        	});
        	this.hiddenStopCell.click(function() {
        		var stopMatchFlag = $("li[_stopMatch='true']");
        		if($(this).attr("checked")=="checked") {
        			ctx.renderMultipleRow(stopMatchFlag, "stop-cell", false);
    			} else {
    				ctx.renderMultipleRow(stopMatchFlag, "stop-cell", true);
    			}
        	});
        	this.selectAll.click(function() {
        		ctx.leagueCheckBox().each(function(i, elt) {
        			var matchList = $("li[_lName='"+$(this).val()+"']");
        			if($(this).attr("checked") != "checked") {
        				ctx.renderMultipleRow(matchList, "select-all", true);
        				$(this).attr("checked", "checked");
        			}
        		});
        	});
        	this.reverseSelect.click(function() {
        		ctx.leagueCheckBox().each(function(i, elt) {
        			var matchList = $("li[_lName='"+$(this).val()+"']");
        			if($(this).attr("checked") == "checked") {
        				ctx.renderMultipleRow(matchList, "reverse-select", false);
        				$(this).removeAttr("checked");
        			} else {
        				ctx.renderMultipleRow(matchList, "reverse-select", true);
        				$(this).attr("checked", "checked");
        			}
        		});
        	});
        	this.clearCheckLeague.click(function() {
        		ctx.leagueCheckBox().each(function(i, elt) {
        			var matchList = $("li[_lName='"+$(this).val()+"']");
        			ctx.renderMultipleRow(matchList, "clear-select-all", false);
        			$(this).removeAttr("checked");
        		});
        	});
        },
        _bind: function() {
        	var ctx = this;
        	var title = $("." + ctx.renderer.lotteryId + " > .jj-list-a");
        	title.bind("click", function() {
        		var _post = $(this).attr("_pos");
        		if(_post == "up") {
        			$(this).attr("_pos", "down");
        			$(this).find("img").attr('src', '/images/Under-big-triangle.png');
        			ctx.renderMultipleRow($(this).nextAll(), "exp-coll", true);
        		} else if(_post == "down" || !_post) {
        			$(this).attr("_pos", "up");
        			$(this).find("img").attr('src', '/images/right-big-triangle.png');
        			ctx.renderMultipleRow($(this).nextAll(), "exp-coll", false);
        		}
        	});
        	title.each(function(i, elt) {
        		if(i > 1) {
        			$(this).click();
        		}
        	});
        },
        renderMultipleRow:function(jqueryRowList, operation, type) {
        	var ctx = this;
        	jqueryRowList.each(function(i, elt) {
        		if(ctx.renderer.lotteryId == $(elt).attr("_lotteryId")) {
        			ctx.renderRow(elt, operation, type);
        		}
			});
        	var title = $(jqueryRowList[0]).parent().find(".jj-list-a");
        },
        renderRow:function(li, operation, type) {
        	if(!li) {return;}
        	var row = $(li);
        	var showFlag = false;
        	if(row.hasClass("jj-list-b")) {
        		var rowLeague = row.attr("_lname");
        		var rowStopMatch = row.attr("_stopmatch");
        		var leagueList = this.leagueList();
        		var hiddenStopCell = this.hiddenStopCell.attr("checked");
        		var isContainLeague = array_contains(leagueList, rowLeague);
        		var title = row.parent().find(".jj-list-a");
        		var titlePos = title.attr("_pos");
        		
        		if("up" != titlePos) {
        			if(isContainLeague ||
        					("reverse-select" == operation && true == type)||
        					("select-all" == operation && true == type)) {
        				if(hiddenStopCell != "checked") {
        					showFlag = type;
        				}
        				if(hiddenStopCell == "checked" && rowStopMatch == "false") {
        					showFlag = type;
        				}
        			}
        		}
        	}
        	if(true == showFlag) {
        		row.css("display", "block");
        	} else {
        		row.css("display", "none");
        	}
        },
        clearLeague:function() {
        	this.leagues.empty();
        },
        clearMatch:function() {
        	this.grid.empty();
        },
        clearStop:function() {
        	this.renderer.stopMatchs = 0;
        },
        clearAll:function() {
        	this.clearStop();
        	this.clearLeague();
        	this.clearMatch();
        }
    };
    JCZQMatchUI = UI.extend(JCZQMatchUI.prototype);
    
    var JCLQMatchUI = {};
    JCLQMatchUI.prototype = {
		init: function(option){
			this._super.init(option);
			
			this.rfsf = "06_LC_2";
			this.sf = "07_LC_2";
            this.leagues = $("#jclq_leagues");
            this.stopMatchSum = $("#jclq_stop_matchSum");
            this.jcCalendar = $("#jclq_calendar");
            this.jcCalendar.val(this.renderer.formatDate(new Date()));
            this.hiddenStopCell = $("#jclq_hidden_stop_cell");
            this.jclqSfSp = $("#jclq_sf_sp");
            this.jclqRfsfSp = $("#jclq_rfsf_sp");
            this.selectAll = $(".jclq-select-all");
            this.reverseSelect = $(".jclq-reverse-select");
            this.clearCheckLeague = $(".jclq-clear-league");
            this.matchLoading = "jclq_match_loading";
            
            this._sendRequest();
            this._initBind();
        },
        _selector:function() {
        	var option = {};
        	//开赛时间
        	option.playingTime = this.jcCalendar.val();
        	
        	//玩法切换
        	option.playId = $("#jclq_sf_sp > .sf").attr("_playId");
        	return option;
        },
        _sendRequest:function(option) {
        	this.clearAll();
        	this.renderer.showMatchLoading(this.matchLoading);
        	
        	var ctx = this;
        	var param = {
				playId: this.playId,
				playingTime: this.playingTime,
				_: new Date().getTime()
			};
        	param = $.extend(param, option);
        	$.ajax({
        		url:ctx.url,
        		data:param,
        		type:"POST",
        		dataType:"JSON",
        		success:function(result) {
        			ctx.renderer.hideMatchLoading(ctx.matchLoading);
        			if(result && result.success == true) {
        				var leagues = result.data.leagues;
        				var matchs = result.data.matchs;
        				ctx.paintLeague(leagues);
        				ctx.splitData(matchs);
        			}
        			ctx._bind();
        		}
        	});
        },
        paintLeague: function(leagueData){
    		var leagues = this.renderer.renderLeague(leagueData);
    		this.leagues.append(leagues);
    		this._bindLeagueClick();
        },
        splitData: function(matchData) {
        	var result = [];
        	var row = '';
        	for(var i=0; i<matchData.length; i++) {
        		var match = matchData[i];
        		var deadDate = match.entrustDeadline.substr(0,10);
        		var idRow = deadDate;
        		if(!row || row == idRow) {
        			result.push(match);
        			row = idRow;
        		} else {
        			if(result.length > 0) {
        				this.createMatchObj(result, result[0].entrustDeadline);
        			}
        			result = [];
        			row = '';
        			result.push(match);
        		}
        	}
        	if(result.length > 0) {
				this.createMatchObj(result, result[0].entrustDeadline);
    		}
        },
        createMatchObj:function(matchResult, entrustDeadline) {
			var obj = {};
			obj.matchs = matchResult;
			obj.matchDate = entrustDeadline.substr(0,10);
			obj.matchWeek = this.matchWeek(entrustDeadline);
			this.paintMatch(obj);
        },
		matchWeek:function(date) {
			var date = this.renderer.parseDate(date);
            var currentWeek = date.getDay();
			return this.renderer.renderWeek(currentWeek);
		},
        paintMatch: function(matchData){
        	var matchSegment = this.renderer.renderMatch(matchData);
        	this.grid.append(matchSegment);
        	var stopCn = "["+this.renderer.stopMatchs+"场]";
        	this.stopMatchSum.text(stopCn);
        },
        leagueCheckBox:function() {
        	return this.leagues.find("input[type='checkbox']");
        },
        leagueList:function() {
        	var list = [];
        	this.leagues.find("input[type='checkbox']").each(function() {
        		if($(this).attr("checked")=="checked") {
        			var leagueName = $(this).attr("id");
        			if(leagueName) {
        				list.push(leagueName);
        			}
        		}
        	});
        	return list;
        },
        _bindLeagueClick:function() {
        	var ctx = this;
        	//联赛切换
        	this.leagueCheckBox().each(function(i, elt) {
        		$(elt).click(function() {
        			var lgName = $(this).val();
        			var matchList = $("li[_lName='"+lgName+"']");
        			if($(this).attr("checked")=="checked") {
        				ctx.renderMultipleRow(matchList, "league", true);
        			} else {
        				ctx.renderMultipleRow(matchList, "league", false);
        			}
            	});
        	});
        },
        _initBind:function() {
        	var ctx = this;
        	//日历框变换
        	this.jcCalendar.change(function() {
        		var option = ctx._selector();
        		ctx._sendRequest(option);
        	});
        	this.jclqRfsfSp.click(function() {
        		$(".jj-nav-sp ul").hide();
        		var jclqSfSp = $("#jclq_sf_sp > .sf");
        		var jclqRfsfSp = $("#jclq_rfsf_sp > .rfsf");
        		
        		var sfTxt = "胜负sp";
        		var rfsfTxt = "让分胜负sp";
        		if(jclqRfsfSp.attr("_playId") == ctx.rfsf) {
        			jclqRfsfSp.text(sfTxt);
        			jclqRfsfSp.attr("_playId", ctx.sf);
        			
        			jclqSfSp.text(rfsfTxt);
        			jclqSfSp.attr("_playId", ctx.rfsf);
        		} else {
        			jclqSfSp.text(sfTxt);
        			jclqSfSp.attr("_playId", ctx.sf);
        			
        			jclqRfsfSp.text(rfsfTxt);
        			jclqRfsfSp.attr("_playId", ctx.rfsf);
        		}
        		var option = ctx._selector();
        		ctx._sendRequest(option);
        	});
        	this.hiddenStopCell.click(function() {
        		var stopMatchFlag = $("li[_stopMatch='true']");
        		if($(this).attr("checked")=="checked") {
        			ctx.renderMultipleRow(stopMatchFlag, "stop-cell", false);
        		} else {
        			ctx.renderMultipleRow(stopMatchFlag, "stop-cell", true);
        		}
        	});
        	this.selectAll.click(function() {
        		ctx.leagueCheckBox().each(function(i, elt) {
        			var matchList = $("li[_lName='"+$(this).val()+"']");
        			if($(this).attr("checked") != "checked") {
        				ctx.renderMultipleRow(matchList, "select-all", true);
        				$(this).attr("checked", "checked");
        			}
        		});
        	});
        	this.reverseSelect.click(function() {
        		ctx.leagueCheckBox().each(function(i, elt) {
        			var matchList = $("li[_lName='"+$(this).val()+"']");
        			if($(this).attr("checked") == "checked") {
        				ctx.renderMultipleRow(matchList, "reverse-select", false);
        				$(this).removeAttr("checked");
        			} else {
        				ctx.renderMultipleRow(matchList, "reverse-select", true);
        				$(this).attr("checked", "checked");
        			}
        		});
        	});
        	this.clearCheckLeague.click(function() {
        		ctx.leagueCheckBox().each(function(i, elt) {
        			var matchList = $("li[_lName='"+$(this).val()+"']");
        			ctx.renderMultipleRow(matchList, "clear-select-all", false);
        			$(this).removeAttr("checked");
        		});
        	});
        },
        _bind: function() {
        	var ctx = this;
        	var title = $("." + ctx.renderer.lotteryId + " > .jj-list-a");
        	title.bind("click", function() {
        		var _post = $(this).attr("_pos");
        		if(_post == "up") {
        			$(this).attr("_pos", "down");
        			$(this).find("img").attr('src', '/images/Under-big-triangle.png');
        			ctx.renderMultipleRow($(this).nextAll(), "exp-coll", true);
        		} else if(_post == "down" || !_post) {
        			$(this).attr("_pos", "up");
        			$(this).find("img").attr('src', '/images/right-big-triangle.png');
        			ctx.renderMultipleRow($(this).nextAll(), "exp-coll", false);
        		}
        	});
        	title.each(function(i, elt) {
        		if(i > 1) {
        			$(this).click();
        		}
        	});
        },
        renderMultipleRow:function(jqueryRowList, operation, type) {
        	var ctx = this;
        	jqueryRowList.each(function(i, elt) {
        		if(ctx.renderer.lotteryId == $(elt).attr("_lotteryId")) {
        			ctx.renderRow(elt, operation, type);
        		}
			});
        	var title = $(jqueryRowList[0]).parent().find(".jj-list-a");
        },
        renderRow:function(li, operation, type) {
        	if(!li) {return;}
        	var row = $(li);
        	var showFlag = false;
        	if(row.hasClass("jj-list-b")) {
        		var rowLeague = row.attr("_lname");
        		var rowStopMatch = row.attr("_stopmatch");
        		var leagueList = this.leagueList();
        		var hiddenStopCell = this.hiddenStopCell.attr("checked");
        		var isContainLeague = array_contains(leagueList, rowLeague);
        		var title = row.parent().find(".jj-list-a");
        		var titlePos = title.attr("_pos");
        		
        		if("up" != titlePos) {
        			if(isContainLeague ||
        					("reverse-select" == operation && true == type)||
        					("select-all" == operation && true == type)) {
        				if(hiddenStopCell != "checked") {
        					showFlag = type;
        				}
        				if(hiddenStopCell == "checked" && rowStopMatch == "false") {
        					showFlag = type;
        				}
        			}
        		}
        	}
        	if(true == showFlag) {
        		row.css("display", "block");
        	} else {
        		row.css("display", "none");
        	}
        },
        clearLeague:function() {
        	this.leagues.empty();
        },
        clearMatch:function() {
        	this.grid.empty();
        },
        clearStop:function() {
        	this.renderer.stopMatchs = 0;
        },
        clearAll:function() {
        	this.clearStop();
        	this.clearLeague();
        	this.clearMatch();
        }
    };
    JCLQMatchUI = UI.extend(JCLQMatchUI.prototype);
    
//----------------------------北京单场start-------------------------
    var BJDCMatchUI = {};
    BJDCMatchUI.prototype = {
		init: function(option){
			this._super.init(option);
            this.rq = "91_BJDC_SPF";
            this.leagues = $("#bjdc_leagues");
            this.stopMatchSum = $("#bjdc_stop_MatchSum");
            this.bjdcIssuesSelector = $("#bjdc_issues");
            this.hiddenStopCell = $("#bjdc_hidden_stop_cell");
            this.bjdcSpfSp = $("#bjdc_spf_sp");
            this.selectAll = $(".bjdc-select-all");
            this.reverseSelect = $(".bjdc-reverse-select");
            this.clearCheckLeague = $(".bjdc-clear-league");
            this.matchLoading = "bjdc_match_loading";
            
            this._sendRequest();
            this._initBind();
        },
        _selector:function() {
        	var option = {};
        	//期号
        	option.issueNum = this.bjdcIssuesSelector.val();
        	return option;
        },
        _sendRequest:function(option) {
        	this.clearAll();
        	this.renderer.showMatchLoading(this.matchLoading);
        	
        	var ctx = this;
        	var param = {
				playId: this.playId,
				playingTime: this.playingTime,
				_: new Date().getTime()
			};
        	param = $.extend(param, option);
        	$.ajax({
        		url:ctx.url,
        		data:param,
        		type:"POST",
        		dataType:"JSON",
        		success:function(result) {
        			ctx.renderer.hideMatchLoading(ctx.matchLoading);
        			if(result && result.success == true) {
        				var leagues = result.data.leagues;
        				var matchs = result.data.matchs;
        				var issues = result.data.issues;
        				ctx.paintIssues(issues);
        				ctx.paintLeague(leagues);
        				ctx.splitData(matchs);
        			}
        			ctx._bind();
        		}
        	});
        },
        paintLeague: function(leagueData){
    		var leagues = this.renderer.renderLeague(leagueData);
    		this.leagues.append(leagues);
    		this._bindLeagueClick();
        },
        paintIssues: function(issues){
        	var html = this.renderer.renderIssues(issues);
        	this.bjdcIssuesSelector.append(html);
        },
        splitData: function(matchData) {
        	var result = [];
        	var row = '';
        	for(var i=0; i<matchData.length; i++) {
        		var match = matchData[i];
        		var deadDate = match.entrustDeadline.substr(0,10);
        		var idRow = deadDate;
        		if(!row || row == idRow) {
        			result.push(match);
        			row = idRow;
        		} else {
        			if(result.length > 0) {
        				this.createMatchObj(result, result[0].entrustDeadline);
        			}
        			result = [];
        			row = idRow;
        			result.push(match);
        		}
        	}
        	if(result.length > 0) {
				this.createMatchObj(result, result[0].entrustDeadline);
    		}
        },
        createMatchObj:function(matchResult, entrustDeadline) {
			var obj = {};
			obj.matchs = matchResult;
			obj.matchDate = entrustDeadline.substr(0,10);
			obj.matchWeek = this.matchWeek(entrustDeadline);
			this.paintMatch(obj);
        },
		matchWeek:function(date) {
			var date = this.renderer.parseDate(date);
            var currentWeek = date.getDay();
			return this.renderer.renderWeek(currentWeek);
		},
        paintMatch: function(matchData){
        	var matchSegment = this.renderer.renderMatch(matchData);
        	this.grid.append(matchSegment);
        	var stopCn = "["+this.renderer.stopMatchs+"场]";
        	this.stopMatchSum.text(stopCn);
        },
        leagueCheckBox:function() {
        	return this.leagues.find("input[type='checkbox']");
        },
        leagueList:function() {
        	var list = [];
        	this.leagues.find("input[type='checkbox']").each(function() {
        		if($(this).attr("checked")=="checked") {
        			var leagueName = $(this).attr("id");
        			if(leagueName) {
        				list.push(leagueName);
        			}
        		}
        	});
        	return list;
        },
        _bindLeagueClick:function() {
        	var ctx = this;
        	//联赛切换
        	this.leagueCheckBox().each(function(i, elt) {
        		$(elt).click(function() {
        			var lgName = $(this).val();
        			var matchList = $("li[_lName='"+lgName+"']");
        			if($(this).attr("checked")=="checked") {
        				ctx.renderMultipleRow(matchList, "league", true);
        			} else {
        				ctx.renderMultipleRow(matchList, "league", false);
        			}
            	});
        	});
        },
        _initBind:function() {
        	var ctx = this;
        	//北单期号框变换
        	this.bjdcIssuesSelector.change(function() {
        		var option = ctx._selector();
        		ctx._sendRequest(option);
        	});
        	this.hiddenStopCell.click(function() {
        		var stopMatchFlag = $("li[_stopMatch='true']");
        		if($(this).attr("checked")=="checked") {
        			ctx.renderMultipleRow(stopMatchFlag, "stop-cell", false);
    			} else {
    				ctx.renderMultipleRow(stopMatchFlag, "stop-cell", true);
    			}
        	});
        	this.selectAll.click(function() {
        		ctx.leagueCheckBox().each(function(i, elt) {
        			var matchList = $("li[_lName='"+$(this).val()+"']");
        			if($(this).attr("checked") != "checked") {
        				ctx.renderMultipleRow(matchList, "select-all", true);
        				$(this).attr("checked", "checked");
        			}
        		});
        	});
        	this.reverseSelect.click(function() {
        		ctx.leagueCheckBox().each(function(i, elt) {
        			var matchList = $("li[_lName='"+$(this).val()+"']");
        			if($(this).attr("checked") == "checked") {
        				ctx.renderMultipleRow(matchList, "reverse-select", false);
        				$(this).removeAttr("checked");
        			} else {
        				ctx.renderMultipleRow(matchList, "reverse-select", true);
        				$(this).attr("checked", "checked");
        			}
        		});
        	});
        	this.clearCheckLeague.click(function() {
        		ctx.leagueCheckBox().each(function(i, elt) {
        			var matchList = $("li[_lName='"+$(this).val()+"']");
        			ctx.renderMultipleRow(matchList, "clear-select-all", false);
        			$(this).removeAttr("checked");
        		});
        	});
        },
        _bind: function() {
        	var ctx = this;
        	var title = $("." + ctx.renderer.lotteryId + " > .jj-list-a");
        	title.bind("click", function() {
        		var _post = $(this).attr("_pos");
        		if(_post == "up") {
        			$(this).attr("_pos", "down");
        			$(this).find("img").attr('src', '/images/Under-big-triangle.png');
        			ctx.renderMultipleRow($(this).nextAll(), "exp-coll", true);
        		} else if(_post == "down" || !_post) {
        			$(this).attr("_pos", "up");
        			$(this).find("img").attr('src', '/images/right-big-triangle.png');
        			ctx.renderMultipleRow($(this).nextAll(), "exp-coll", false);
        		}
        	});
        	title.each(function(i, elt) {
        		if(i > 1) {
        			$(this).click();
        		}
        	});
        },
        renderMultipleRow:function(jqueryRowList, operation, type) {
        	var ctx = this;
        	jqueryRowList.each(function(i, elt) {
        		if(ctx.renderer.lotteryId == $(elt).attr("_lotteryId")) {
        			ctx.renderRow(elt, operation, type);
        		}
			});
        	var title = $(jqueryRowList[0]).parent().find(".jj-list-a");
        },
        renderRow:function(li, operation, type) {
        	if(!li) {return;}
        	var row = $(li);
        	var showFlag = false;
        	if(row.hasClass("jj-list-b")) {
        		var rowLeague = row.attr("_lname");
        		var rowStopMatch = row.attr("_stopmatch");
        		var leagueList = this.leagueList();
        		var hiddenStopCell = this.hiddenStopCell.attr("checked");
        		var isContainLeague = array_contains(leagueList, rowLeague);
        		var title = row.parent().find(".jj-list-a");
        		var titlePos = title.attr("_pos");
        		
        		if("up" != titlePos) {
        			if(isContainLeague ||
        					("reverse-select" == operation && true == type)||
        					("select-all" == operation && true == type)) {
        				if(hiddenStopCell != "checked") {
        					showFlag = type;
        				}
        				if(hiddenStopCell == "checked" && rowStopMatch == "false") {
        					showFlag = type;
        				}
        			}
        		}
        	}
        	if(true == showFlag) {
        		row.css("display", "block");
        	} else {
        		row.css("display", "none");
        	}
        },
        clearLeague:function() {
        	this.leagues.empty();
        },
        clearMatch:function() {
        	this.grid.empty();
        },
        clearStop:function() {
        	this.renderer.stopMatchs = 0;
        },
        clearAll:function() {
        	this.clearStop();
        	this.clearLeague();
        	this.clearMatch();
        }
    };
    BJDCMatchUI = UI.extend(BJDCMatchUI.prototype);
//----------------------------北京单场end-------------------------
   
    this.Renderer = Renderer;
    this.JCZQRenderer = JCZQRenderer;
    this.JCLQRenderer = JCLQRenderer;
    this.BJDCRenderer = BJDCRenderer;
    
    this.UI = UI;
    this.JCZQMatchUI = JCZQMatchUI;
    this.JCLQMatchUI = JCLQMatchUI;
    this.BJDCMatchUI = BJDCMatchUI;
});



