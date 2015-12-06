$(document).ready(function() {
	var matchTmp = 
		   '<tr>'
		  +'	<td width="13%"><div class="bg blue" style="background:{9}">{0}</div></td>'
		  +'    <td width="12%">{1}</td>'
		  +'    <td width="45%">{2} {3} {4}</td>'
		  +'    <td>{5}</td>'
		  +'    <td>{6}</td>'
		  +'    <td>{7}</td>'
		  +'    <td class="no_right_border">{8}</td>'
		  +'</tr>';
	var checkboxLiTmp = 
		 '<li>'
		+'	    <label>'
		+'      <div class="checkbox"><input type="checkbox" checked="checked" _value="{0}"><span class="icon sprites"></span><span class="label">{0}</span></div>'
		+'  </label>'
		+'</li>';
	var checkboxLiTmp = 
		 '<li>'
		+'	    <label>'
		+'      <div class="checkbox"><input type="checkbox" checked="checked" _value="{0}"><span class="icon sprites"></span><span class="label">{0}</span></div>'
		+'  </label>'
		+'</li>';
	
	var fightHistoryData;
	var homeTeamName = $("#homeTeamName").html();
	var guestTeamName = $("#guestTeamName").html();
	var homeTeamId = $("#homeTeamId").val();
	var guestTeamId = $("#guestTeamId").val();
	
	//生成checkbox
	function getCheckboxHtml(data){
		var checkboxLi = "";
		var	checkboxNameString = "";
		for ( var i = 0; i < data.length; i++) {
			if(checkboxNameString.indexOf(data[i].matchName) == -1){
				checkboxNameString += data[i].matchName + "+";
			} 
		}
		var checkboxNames = checkboxNameString.split("+");
		for ( var i = 0; i < checkboxNames.length; i++) {
			if(checkboxNames[i] && checkboxNames[i] != ""){
				checkboxLi += $.format(checkboxLiTmp,checkboxNames[i]);
			}
		}
		return checkboxLi;
	}
	
	function rendHistoryMatch(data,homeTeamName,excludeMatchs){
		var matchHtml = "";
		for (var i = 0; i < data.length; i++) {
			if(excludeMatchs && excludeMatchs.indexOf(data[i].matchName) != -1){
				continue;
			}
			matchHtml += $.format(matchTmp,
					data[i].matchName,
					data[i].matchDate.substr(2,8),
					data[i].homeTeam == homeTeamName ? "<b>" + data[i].homeTeam + "</b>" : data[i].homeTeam,
					data[i].matchResult == "胜" ? 
							'<b class="red">' + data[i].score.split(":")[0] + '</b>-<b>' + data[i].score.split(":")[1] + '</b>':
					data[i].matchResult == "平" ? 
							'<b class="green">' + data[i].score.split(":")[0] + '</b>-<b class="green">' + data[i].score.split(":")[1] + '</b>':
							'<b>' + data[i].score.split(":")[0] + '</b>-<b class="blue">' + data[i].score.split(":")[1] + '</b>',
					data[i].guestTeam == homeTeamName ? "<b>" + data[i].guestTeam + "</b>" : data[i].guestTeam,
					data[i].pankou == 0 ? "" : data[i].pankou,
					data[i].matchResult == "胜" ? '<b class="red">胜</b>': data[i].matchResult == "平" ? '<b class="green">平</b>':'<b class="blue">负</b>',
					data[i].panlu == true ? '<b class="red">' + "赢" + "</b>" : '<b class="blue">' + "输" + "</b>",
					data[i].bigSmall == "大" ? '<b class="red">' + data[i].bigSmall + "</b>" : '<b class="blue">' + data[i].bigSmall + "</b>",
					data[i].matchNameColor);
		}
		$("#historyMatchTBody").html(matchHtml);
	}
	
	// ajax load fight history match data
	var lotteryType = getQueryString("lotteryType");
	var url = "homeTeamId=" + homeTeamId + "&"
		+"guestTeamId=" + guestTeamId + "&"
		+"lotteryType=" + lotteryType + "&"
		+"callback=" + "?" + "&";
	$.getJSON('/ajax_fighting_history?'+url, function(data) {
		fightHistoryData = data;
		$("#matchCheckboxUL").html(getCheckboxHtml(data));
		rendHistoryMatch(data,homeTeamName);
		
		$("[type='checkbox']",$("#matchCheckboxUL")).change(function (){
			var checkboxs = $("[type='checkbox']",$("#matchCheckboxUL"));
			var excludeMatchs = "";
			for ( var i = 0; i < checkboxs.length; i++) {
				var status = $(checkboxs[i]).attr("checked");
				if(status != "checked"){
					excludeMatchs += $(checkboxs[i]).attr("_value") + "+";
				}
			}
			rendHistoryMatch(data,homeTeamName,excludeMatchs);
		});
	});
});