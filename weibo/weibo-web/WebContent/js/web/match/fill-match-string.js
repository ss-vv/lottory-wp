$(document).ready(function() {
	function renderMatchString(){
		var weekArray = new Array();
				weekArray[0] = "";
				weekArray[1] = "周一";
				weekArray[2] = "周二";
				weekArray[3] = "周三";
				weekArray[4] = "周四";
				weekArray[5] = "周五";
				weekArray[6] = "周六";
				weekArray[7] = "周日";
			var lotteryType = $("#match_team_data").attr("lotteryType");
			var matchId= $("#match_team_data").attr("matchId");
			var homeTeam = $("#match_team_data").attr("homeTeamName");
			var guestTeam = $("#match_team_data").attr("guestTeamName");
			
			if(homeTeam == "" || guestTeam == ""){
				return ;
			}
			var macthTipString = "";
			if(lotteryType == "JCZQ"){
				macthTipString += "$竞彩足球 ";
				var weekNum = parseInt(matchId.substr(8,1));
				macthTipString += weekArray[weekNum];
				macthTipString += matchId.substr(9,3);
				macthTipString += (" " + homeTeam + " VS " +  guestTeam);
				macthTipString += "(JZ" + matchId.substr(2) + ")$ ";
			} else if(lotteryType == "JCLQ"){
				macthTipString += "$竞彩篮球 ";
				var weekNum = parseInt(matchId.substr(8,1));
				macthTipString += weekArray[weekNum];
				macthTipString += matchId.substr(9,3);
				macthTipString += (" " + homeTeam + " VS " +  guestTeam);
				macthTipString += "(JL" + matchId.substr(2) + ")$ ";
			}
			_wbEditor.ready(function(){
				_wbEditor.setContent(macthTipString);
				_wbEditor.focus(true);
			});
	}
	renderMatchString();
});