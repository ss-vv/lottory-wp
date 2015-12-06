/** 渲染球队不败的饼状图 */
var renderWinPie = function(homeTeam, guestTeam) {
	var itemData1 = homeTeam, 
	itemData2 = guestTeam, 
	itemOptions = {
		percentageInnerCutout : 84,
		animation : !$.browser.msie,
		segmentStrokeWidth : 0
	},
	itemCtx1 = document.getElementById('home').getContext('2d')
	itemCtx2 = document.getElementById('guest').getContext('2d');

	new Chart(itemCtx1).Doughnut(itemData1, itemOptions);
	new Chart(itemCtx2).Doughnut(itemData2, itemOptions);
};
//========================================================================

/**获取足球赛事概览和比分走势数据*/
var ajaxFBMatchScoreChart = function() {
	$.ajax({
		type:"post",
		dataType:"json",
		url : "/ajax_fb_match_score_chart.do",
		data:queryStringObject,
		success : function(result) {
			if(result && result.data) {
				renderNotLose(result.data);
				renderScoreChart(result.data);
			}
		}
	});
};

/**渲染球队不输的概率图*/
var renderNotLose = function(data) {
	var homeMatchResult = data.homeMatchResult;
	var guestMatchResult = data.guestMatchResult;
	
	var homeItem = $(".charts").find(".left");
	var guestItem = $(".charts").find(".right");
	
	var pieTpl = '<div class="title"><b>{{teamName}}</b></div>'+
        '<div class="pie">'+
        '   <span><i>{{percent}}</i>%</span>'+
        '   <canvas id="{{teamType}}" width="185" height="185"></canvas>'+
        '</div>'+
        '<div class="title">'+
        '	<span class="s">{{win}} 胜</span>'+
        '	<span class="p">{{flat}} 平</span>'+
        '	<span class="f">{{negative}} 负</span>'+
        '</div>';
	var homeSegment = $.mustache(pieTpl, {
		win:homeMatchResult[0],
		flat:homeMatchResult[1],
		negative:homeMatchResult[2],
		teamName:data.homeTeamName,
		percent:data.homeNotLosePercent,
		teamType:"home"
	});
	var guestSegment = $.mustache(pieTpl, {
		win:guestMatchResult[0],
		flat:guestMatchResult[1],
		negative:guestMatchResult[2],
		teamName:data.guestTeamName,
		percent:data.guestNotLosePercent,
		teamType:"guest"
	});
	//渲染胜平负数值
	homeItem.html(homeSegment);
	guestItem.html(guestSegment);
	
	var homeTeam = [];
	var guestTeam = [];
	if(homeMatchResult) {
		homeTeam = parseData(homeMatchResult, "home");
	}
	if(guestMatchResult) {
		guestTeam = parseData(guestMatchResult);
	}
	//渲染胜平负数量的饼图
	renderWinPie(homeTeam, guestTeam);
};

var parseData = function(result, team) {
	var arr = [];
	for(var i=0; i<result.length; i++) {
		var rs = {};
		rs.value = parseInt(result[i]);
		if(i == 0) {
			if("home" == team) {
				rs.color = "#ffd430";
			} else {
				rs.color = "#01e8d4";
			}
		} else if(i == 1) {
			if("home" == team) {
				rs.color = "#818181";
			} else {
				rs.color = "#818181";
			}
		} else if(i == 2) {
			if("home" == team) {
				rs.color = "#ebebeb";
			} else {
				rs.color = "#ebebeb";
			}
		}
		arr.push(rs);
	}
	return arr;
};

/**渲染比分走势*/
var renderScoreChart = function(data) {
	renderScoreChartLine();
	var homeItem = $(".trend_img_wrap").find(".left").find(".item");
	var guestItem = $(".trend_img_wrap").find(".right").find(".item");
	
	var homeChartList = data.homeChartList;
	var guestChartList = data.guestChartList;
	var homeTeamId = data.homeTeamId;
	var guestTeamId = data.guestTeamId;
	
	//主队比分走势
	bindScoreData(homeItem, homeChartList, homeTeamId);
	//客队比分走势
	bindScoreData(guestItem, guestChartList, guestTeamId);
};

var bindScoreData = function(item, chartList, teamId) {
	for(var i=0; i<chartList.length; i++) {
		var rs = chartList[i];
		var cm = compareMatchScore(rs, teamId);
		var score = rs.score;
		var percent = getScorePercent(i);
		if(cm == 1) {
			$(item.children().get(0)).append('<span class="sprites yellow" style="left:' + percent + '%;"><i>' + score + '</i></span>');
		} else if(cm == 0) {
			$(item.children().get(1)).append('<span class="sprites black" style="left:' + percent + '%;"><i>' + score + '</i></span>');
		} else if(cm == -1) {
			$(item.children().get(2)).append('<span class="sprites gray" style="left:' + percent + '%;"><i>' + score + '</i></span>');
		}
	}
};

var renderScoreChartLine = function() {
	var scoreChartTpl = '<div class="line"></div>';
	var homeItem = $(".trend_img_wrap").find(".left").find(".item");
	var guestItem = $(".trend_img_wrap").find(".right").find(".item");
	homeItem.append($(scoreChartTpl));
	homeItem.append($(scoreChartTpl));
	homeItem.append($(scoreChartTpl));
	
	guestItem.append($(scoreChartTpl));
	guestItem.append($(scoreChartTpl));
	guestItem.append($(scoreChartTpl));
};

var compareMatchScore = function(rs, item) {
	var homeTeamId = rs.homeTeamId;
	var guestTeamId = rs.guestTeamId;
	var score = rs.score;
	if(!score)return;
	var scoreArr = score.split(":");
	if((item == homeTeamId && scoreArr[0] > scoreArr[1]) ||
			(item == guestTeamId && scoreArr[1] > scoreArr[0])) {
		return 1;
	} else if(scoreArr[0] == scoreArr[1]) {
		return 0;
	} else if((item == homeTeamId && scoreArr[0] < scoreArr[1]) ||
			(item == guestTeamId && scoreArr[1] < scoreArr[0])) {
		return -1;
	}
};

var getScorePercent = function(index) {
	var percent = 0;//表示在X轴方向上相对于左边的百分比距离
	if(index == 0) {
		percent = 2;
	} else if(index == 1) {
		percent = 20;
	} else if(index == 2) {
		percent = 40;
	} else if(index == 3) {
		percent = 60;
	} else if(index == 4) {
		percent = 80;
	}
	return percent;
};

//========================渲染篮球赛事概览页面数据=================================


/**
 * @param queryStringObject 查询条件对象
 * @param container 返回的数据需要绑定到那个容器
 */
var ajaxBBTeamPraiseList = function(queryStringObject, container) {
	$.ajax({
		type:"post",
		dataType:"json",
		url : "/ajax_bbTeam_praise.do",
		data:queryStringObject,
		success : function(result) {
			renderSupportUser(result, container);
		}
	});
};

var renderSupportUser = function(result, container) {
	var supportsTpl = 
	'{{#result}}' +
	'<div class="t2 clearfix" style="background-color: {{teamBarColor}};">' +
    	'<i class="sprites" style="background-position: 0px -121px;"></i>' +
    	'<span><b id="homeTeamName">TA们支持 {{teamName}}</b></span>' +
    '</div>' +
    '<div>' +
		'<ul style="width:100%;height:100%;float:left;margin-bottom:10px;">' +
			'<li title="{{teamName}}" class="left shadow-img" style="width:110px;height:132px;margin:10px 15px 0px 15px;">'+
				'<img src="{{logoUrl}}" style="width:100px; height:100px;padding: 10px 5px 10px 5px;">'+
			'</li>'+
			'{{#users}}'+
			'<li style="width: 55px; height: 55px; margin: 10px 10px 10px 5px;">'+
				'<div class="headpic" _userid="{{weiboUserId}}" title="{{nickName}}">'+
					'<a href="http://www.davcai.com/{{weiboUserId}}/profile" target="_blank">'+
					'<img width="55px" height="55px" src="{{headImageURL}}">'+
					'</a>'+
				'</div>'+
			'</li>'+
			'{{/users}}'+
		'</ul>'+
		'<p class="clear"></p>' +
	'</div>' +
    '{{/result}}';
	var segment = $.mustache(supportsTpl, {
		result:result,
		teamBarColor:function() {
			var homeTeamId = $("#homeTeamId").val();
			if(homeTeamId == this.teamId) {
				return "#FFD32F";
			} else {
				return "#00E8D4";
			}
		}
	});
	container.html(segment);
};

/**
 * 对球队点赞
 */
var likeTeam = function(teamId, teamType) {
	var query = {};
	$.extend(query, queryStringObject);
	query.teamId = teamId;
	$.ajax({
		type:"post",
		dataType:"json",
		url : "/ajax_like_team.do",
		data:query,
		success : function(result) {
			var supportBtn = null;
			if(teamType == "homeTeam") {
				teamType = "主队";
				supportBtn = $(".sup-home").find("input");
			} else if(teamType == "guestTeam") {
				teamType = "客队";
				supportBtn = $(".sup-guest").find("input");
			}
			if(result && result.success == true) {
				$.msgbox('成功支持' + teamType, {success:true});
				if(supportBtn) {
					supportBtn.attr("disabled", "disabled");
				}
			} else {
				$.msgbox('支持' + teamType + '失败：' + result.data, {success:false});
			}
		}
	});
};

/**篮球赛事概览数据*/
var ajaxBBOverview = function() {
	var query = {};
	$.extend(query, queryStringObject);
	var homeTeamId = $("#homeTeamId").val();
	var guestTeamId = $("#guestTeamId").val();
	query.homeTeamId = homeTeamId;
	query.guestTeamId = guestTeamId;
	$.ajax({
		type:"post",
		dataType:"json",
		url : "/ajax_bb_overview.do",
		data:query,
		success : function(result) {
			if(result) {
				renderBBOverview(result);
			}
		}
	});
};

//渲染篮球赛事概览页面数据
var renderBBOverview = function(result) {
	var cycle = '<span style="color:red;font-size:40px;float:left">·</span>';
	//返回的数据格式：
	var jclq_msg = 
		"{{#result}}" +
		'<li>'+ cycle +' {{supportHomeTeamNum}}人支持{{fightHistoryHomeTeam.teamName}}，' +
		'	{{supportGuestTeamNum}}人支持{{fightHistoryGuestTeam.teamName}}</li>' +
        '<li class="even_bg">'+ cycle +' 近{{recentHomeTeam.size}}场，{{recentHomeTeam.teamName}} {{recentHomeTeam.shengNum}}胜{{recentHomeTeam.fuNum}}负；' +
        '	{{recentGuestTeam.teamName}} {{recentGuestTeam.shengNum}}胜{{recentGuestTeam.fuNum}}负</li>' +
        '<li>' + cycle + ' 两队近{{fightHistoryHomeTeam.size}}次交战，{{fightHistoryHomeTeam.teamName}} ' +
        '	{{fightHistoryHomeTeam.shengNum}}胜{{fightHistoryHomeTeam.fuNum}}负</li>' +
        '<li class="even_bg">'+ cycle +' 本场比赛已有{{recommendAndShowUsers}}人 发布了{{recommend}}条推荐，{{showScheme}}条晒单</li>' +
        '{{/result}}';
	var segment = $.mustache(jclq_msg, {
		result:result
	});
	var homeTeamId = $("#homeTeamId").val();
	var guestTeamId = $("#guestTeamId").val();
	if(parseInt(homeTeamId) > 0 || parseInt(guestTeamId) > 0) {
		bindTeamSupport($(".support"));
		$(".jclq_msg").html(segment);
	}
};

var isSupportedTeam = function($this) {
	supportBtn = $this.find("input");
	var disable = supportBtn.attr("disabled");
	if(disable == "disabled") {
		return true;
	}
	return false;
};

var bindTeamSupport = function(parent) {
	parent.find(".sup-home").click(function() {
		var id = $("#homeTeamId").val();
		if(isSupportedTeam($(this))) {
			$.msgbox('您已支持过该球队', {success:false});
		} else {
			likeTeam(id, "homeTeam");
		}
	});
	parent.find(".sup-guest").click(function() {
		var id = $("#guestTeamId").val();
		if(isSupportedTeam($(this))) {
			$.msgbox('您已支持过该球队', {success:false});
		} else {
			likeTeam(id, "guestTeam");
		}
	});
};

var initTeamSupportBtn = function() {
	$(".support").append('<li title="我要支持TA" class="sup-home"><input type="button" value="支持主队" class="btn btn-primary" style="width:100px;"></li>');
	$(".support").append('<li title="我要支持TA" class="sup-guest"><input type="button" value="支持客队" class="btn btn-primary" style="width:100px;"></li>');
	$(".support").css({height:"72px", width:"445px"});
};

$(document).ready(function() {
	if(isZQ()) {
		ajaxFBMatchScoreChart();
	} else if(isLQ()) {
		var homeTeamId = $("#homeTeamId").val();
		var guestTeamId = $("#guestTeamId").val();
		//分别加载支持"主客队"的用户
		if(homeTeamId && parseInt(homeTeamId) > 0) {
			var query = {};
			$.extend(query, queryStringObject);
			query.teamId = homeTeamId;
			ajaxBBTeamPraiseList(query, $(".support_home"));
		}
		if(guestTeamId && parseInt(guestTeamId) > 0) {
			var query = {};
			$.extend(query, queryStringObject);
			query.teamId = guestTeamId;
			ajaxBBTeamPraiseList(query, $(".support_guest"));
		}
	}
	
	initTeamSupportBtn();
	
	ajaxBBOverview();
});