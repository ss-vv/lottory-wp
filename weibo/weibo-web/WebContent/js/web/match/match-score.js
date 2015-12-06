//定义赛事数据对象
var MatchData = {};

//========赛事数据比分模板=========
/**足球比分模板*/
MatchData.fbTpl =
	'<ul>{{#match}}'+
	'    <li class="left">'+
	'        <p>'+
	'			{{#homeTeamLogoUrl}}'+
	'			<img src="{{homeTeamLogoUrl}}" style="width: 70px;height: 70px;">'+
	'			{{/homeTeamLogoUrl}}'+
	'            <span><b>{{homeTeamName}}</b></span>'+
	'        </p>'+
	'    </li>'+
	'    <li class="center">'+
	'        <div>'+
	'            <div class="bg clearfix">'+
	'                <span class="l_score"><b>{{homeScore}}</b></span>'+
	'                <span class="r_score"><b>{{guestScore}}</b></span>'+
	'                <div class="result"><b>{{{endMatch}}}</b></div>'+
	'            </div>'+
	'            <div class="result"><b>{{halfScoreView}}</b></div>'+
	'        </div>'+
	'    </li>'+
	'    <li class="right">'+
	'        <p>'+
	'		{{#guestTeamLogoUrl}}'+
	'		<img src="{{guestTeamLogoUrl}}" style="width: 70px;height: 70px;">'+
	'		{{/guestTeamLogoUrl}}'+
	'            <span><b>{{guestTeamName}}</b></span>'+
	'        </p>'+
	'    </li>{{/match}}'+
	'</ul>';

/**篮球比分模板*/
MatchData.bbTpl =
	'<ul>{{#match}}'+
	'    <li class="left">'+
	'        <p>'+
	'			{{#homeTeamLogoUrl}}'+
	'			<img src="{{homeTeamLogoUrl}}" style="width: 70px;height: 70px;">'+
	'			{{/homeTeamLogoUrl}}'+
	'            <span><b>{{homeTeam}}</b></span>'+
	'        </p>'+
	'    </li>'+
	'    <li class="center">'+
	'        <div>'+
	'            <div class="bg clearfix">'+
	'                <span class="l_score"><b>{{homeScore}}</b></span>'+
	'                <span class="r_score"><b>{{guestScore}}</b></span>'+
	'                <div class="result"><b>{{{endMatch}}}</b></div>'+
	'            </div>'+
	'        </div>'+
	'    </li>'+
	'    <li class="right">'+
	'        <p>'+
	'		{{#guestTeamLogoUrl}}'+
	'		<img src="{{guestTeamLogoUrl}}" style="width: 70px;height: 70px;">'+
	'		{{/guestTeamLogoUrl}}'+
	'            <span><b>{{guestTeam}}</b></span>'+
	'        </p>'+
	'    </li>{{/match}}'+
	'</ul>';

/**
 * 显示赛事状态
 */
MatchData.status = function(matchStatus) {
	var status = "--";
	switch (matchStatus) {
	case 0:
		status = "未开";
		break;
	case 1:
		status = "上半场";
		break;
	case 2:
		status = "中场";
		break;
	case 3:
		status = "下半场";
		break;
	case -11:
		status = "待定";
		break;
	case -12:
		status = "腰斩";
		break;
	case -13:
		status = "中断";
		break;
	case -14:
		status = "推迟";
		break;
	case -1:
		status = "完场";
		break;
	}
	return status;
};

/**显示赛事比分*/
MatchData.ajaxMatchScore = function() {
	var matchIdStr = getQueryString("matchId");
	var lotteryType = getQueryString("lotteryType");
	var url = "/ajax_match_score";
	var param = {matchId: matchIdStr, lotteryType : lotteryType};
	$.ajax({
		type:"post",
		dataType:"json",
		url : url,
		data:param,
		success : function(result) {
			if(result && result.data) {
				MatchData.renderMatchScore(result.data, lotteryType);
			}
		}
	});
};

/**
 * 选取对应的渲染模板
 */
MatchData.getTemplate = function(lotteryType) {
	if(lotteryName.JCZQ == lotteryType ||
			lotteryName.CTZC == lotteryType ||
			lotteryName.BJDC == lotteryType) {
		return this.fbTpl;
	} else if(lotteryName.JCLQ == lotteryType) {
		return this.bbTpl;
	}
};

/**
 * 比分内容的显示容器
 */
MatchData.scoreContainer = function() {
	return $(".game_tip");
};

/**
 * 渲染赛事比分
 */
MatchData.renderMatchScore = function(data, lotteryType) {
	var type = new lottery();
	var segment = null;
	if(null == data) {
		data = [0];
	}
	if(type.isZC(lotteryType)) {
		segment = this.fbRenderSegment(this.fbTpl, data);
	} else if(type.isLC(lotteryType)) {
		segment = this.bbRenderSegment(this.bbTpl, data);
	}
	if(segment)
		this.scoreContainer().html(segment);
};

MatchData.fbRenderSegment = function(tpl, data) {
	var segment = $.mustache(tpl, {
		match:data,
		homeScore:function() {
			if(this.score) {
				return this.score.split(":")[0];
			} else {
				return "-";
			}
		},
		guestScore:function() {
			if(this.score) {
				return this.score.split(":")[1];
			} else {
				return "-";
			}
		},
		endMatch:function() {
			if(this.matchStatus == 0){
				return "开赛时间<br/>" + this.matchTime.replace("T"," ");
			} else {
				return MatchData.status(this.matchStatus);
			}
		},
		halfScoreView:function (){
			if(this.matchStatus == 0){
				return "";
			} else {
				return "半场(" + this.halfScore + ")";
			}
		}
	});
	return segment;
}

MatchData.bbRenderSegment = function(tpl, data) {
	var segment = $.mustache(tpl, {
		match:data,
		homeScore:function() {
			if(this.homeScore) {
				return this.homeScore;
			} else {
				return "-";
			}
		},
		guestScore:function() {
			if(this.guestScore) {
				return this.guestScore;
			} else {
				return "-";
			}
		},
		endMatch:function() {
			if(this.matchState == 0){
				return "开赛时间<br/>" + this.matchTime.replace("T"," ");
			} else {
				return MatchData.status(this.matchState);
			}
		}
	});
	return segment;
}

$(document).ready(function() {
	MatchData.ajaxMatchScore();
});