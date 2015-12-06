$(document).ready(function() {
	var homeTeamName = $("#homeTeamName").html();
	var guestTeamName = $("#guestTeamName").html();
	var homeTeamId = $("#homeTeamId").val();
	var guestTeamId = $("#guestTeamId").val();
	var homeTeamData ;
	var guestTeamData ;
	var matchRowTmp = 
		 '<tr bgcolor="{4}">'
		+'    <td align="center" valign="top" height="30"><span class="t">{0}</span></td>'
		+'    <td align="center" valign="top"><span class="t"><b>{1}</b> - <b>{2}</b></span><span class="b">{3}</span></td>'
		+'</tr>'
	
	function getMatchHtml(data){
		var tmpString = "";
		for ( var i = data.length - 1; i >= 0; i--) {
			tmpString += $.format(matchRowTmp,
					data[i].matchDate.substr(2,8),
					data[i].homeTeam,
					data[i].guestTeam,
					data[i].matchName,
					i%2 == 0 ? 'f2f2f2':'');
		}
		return tmpString;
	}	
	// ajax load future match data
	var lotteryType = getQueryString("lotteryType");
	var queryStr = 'teamId=' + homeTeamId + 
			'&lotteryType=' + lotteryType + '&callback=?';
	$.getJSON('/ajax_future_match?' + queryStr, function(data) {
		homeTeamData = data;
		$("#homeTeamMatchTBody").html(getMatchHtml(data));
	});
	
	queryStr = 'teamId=' + guestTeamId + 
	'&lotteryType=' + lotteryType + '&callback=?';
	$.getJSON('/ajax_future_match?' + queryStr, function(data) {
		guestTeamData = data;
		$("#guestTeamMatchTBody").html(getMatchHtml(data));
	});
});