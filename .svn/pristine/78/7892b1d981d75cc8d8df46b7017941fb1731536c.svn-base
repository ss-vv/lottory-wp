$(document).ready(function() {
	ajaxLeagueRank();
});

//获取联赛排名
var ajaxLeagueRank = function() {
	var matchIdStr = getQueryString("matchId");
	var lotteryType = getQueryString("lotteryType");
	var param = {matchId: matchIdStr, lotteryType : lotteryType};
	$.ajax({
		type:"POST",
		cache:false,
		dataType:"JSON",
		url : "/ajax_league_rank",
		data:param,
		success : function(result) {
			hideLoading($(".tab_box > .rank"));
			if(null == result) {
				result = [];
			}
			if(isZQ())
				renderLeagueRank(result.data);
		}
	});
};

var renderLeagueRank = function(data) {
	var tpl =
	'        <li class="tab_item">'+
	'            <div class="title">'+
	'                <i class="sprites i1"></i><span>{{title}}</span>'+
	'            </div>'+
	'            <div class="tab_title">'+
	'                <img src="http://www.davcai.com/pic/a.jpg"><span>{{league.leagueName}}</span>'+
	'            </div>'+
	'            <div>'+
	'                <table width="100%" class="dotted" border="0" cellspacing="0" cellpadding="0">'+
	'                    <thead>'+
	'                        <tr bgcolor="#f6f5f5">'+
	'                            <td width="6%" align="center" height="23">&nbsp;</td>'+
	'                            <td width="22%">球队</td>'+
	'                            <td width="12%" align="center">已赛</td>'+
	'                            <td width="10%" align="center">胜</td>'+
	'                            <td width="10%" align="center">平</td>'+
	'                            <td width="10%" align="center">负</td>'+
	'                            <td width="16%" align="center">GF:GA</td>'+
	'                            <td width="14%" align="center">积分</td>'+
	'                        </tr>'+
	'                    </thead>'+
	'                    <tbody>'+
	'                       {{#league.leagueScoreList}}'+
	'						<tr>'+
	'                            <td width="6%" align="center" height="23">{{rank}}</td>'+
	'                            <td width="22%"><div class="ellipsis" title="{{teamName}}"><b>{{teamName}}</b></div></td>'+
	'                            <td width="12%" align="center">{{totalMatches}}</td>'+
	'                            <td width="10%" align="center">{{winMatches}}</td>'+
	'                            <td width="10%" align="center">{{drawMatches}}</td>'+
	'                            <td width="10%" align="center">{{loseMatches}}</td>'+
	'                            <td width="16%" align="center"><b class="blue">{{goal}}:{{miss}}</b></td>'+
	'                            <td width="14%" align="center"><b class="red">{{score}}</b></td>'+
	'                        </tr>'+
	'                        {{/league.leagueScoreList}}'+
	'                       {{#league.cupScoreList}}'+
	'						<tr>'+
	'                            <td width="6%" align="center" height="23">{{rank}}</td>'+
	'                            <td width="22%"><div class="ellipsis" title="{{teamName}}"><b>{{teamName}}</b></div></td>'+
	'                            <td width="12%" align="center">{{totalMatches}}</td>'+
	'                            <td width="10%" align="center">{{winMatches}}</td>'+
	'                            <td width="10%" align="center">{{drawMatches}}</td>'+
	'                            <td width="10%" align="center">{{loseMatches}}</td>'+
	'                            <td width="16%" align="center"><b class="blue">{{goal}}:{{miss}}</b></td>'+
	'                            <td width="14%" align="center"><b class="red">{{score}}</b></td>'+
	'                        </tr>'+
	'                        {{/league.cupScoreList}}'+
	'                    </tbody>'+
	'                </table>'+
	'            </div>'+
	'        </li>';
	var segment = $.mustache(tpl, {
		league:data,
		title:function() {
			if(!data) {
				return "排名";
			} else if("CUP" == data.matchTypeEnum) {
				return "杯赛排名";
			} else if ("LEAGUE" == data.matchTypeEnum) {
				return "联赛排名";
			}
		}
	});
	$(".tab_box > .rank").html(segment);
};