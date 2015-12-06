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
	var matchResultBar ={
		sheng:'<div class="item"><span class="red" style="height:70%;"></span></div>',
		ping:'<div class="item"><span class="green" style="height:50%;"></span></div>',
		fu:'<div class="item"><span class="blue" style="height:30%;"></span></div>'
	};
	var checkboxLiTmp = 
		 '<li>'
		+'	    <label>'
		+'      <div class="checkbox"><input type="checkbox" checked="checked" _value="{0}"><span class="icon sprites"></span><span class="label">{0}</span></div>'
		+'  </label>'
		+'</li>';
	
	var homeTeamRecentData;
	var guestTeamRecentData;
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
	//计算满足当前Checkbox条件下的记录条数
	function countRecord(data,excludeMatchs){
		var j = 0;
		for ( var i = 0;i < data.length; i++) {
			if(excludeMatchs && excludeMatchs.indexOf(data[i].matchName) != -1){
				continue;
			} else {
				j++;
			}
		}
		return j;
	}
	
	//根据data 生成相关 填充数据
	//mathcFilters 需要被过滤掉的赛事串
	function getMatchRelatedHtml(data,size,teamName,excludeMatchs){
		var matchHtml = "";
		var barHtml = "";
		var shengCount = 0;
		var pingCount = 0;
		var fuCount = 0;
		var jinqiuCount = 0;
		var shiqiuCount = 0;
		
		//条件在data长度范围内遍历，  并且 已经加载的数据长度（j） 小于需要的长度（size）
		for ( var i = 0,j = 0;i < data.length && j < size; i++) {
			if(excludeMatchs && excludeMatchs.indexOf(data[i].matchName) != -1){
				continue;
			}
			j++;
			matchHtml += $.format(matchTmp,
					data[i].matchName,
					data[i].matchDate.substr(2,8),
					data[i].homeTeam == teamName ? "<b>" + data[i].homeTeam + "</b>" : data[i].homeTeam,
					data[i].matchResult == "胜" ? 
							'<b class="red">' + data[i].score.split(":")[0] + '</b>-<b>' + data[i].score.split(":")[1] + '</b>':
					data[i].matchResult == "平" ? 
							'<b class="green">' + data[i].score.split(":")[0] + '</b>-<b class="green">' + data[i].score.split(":")[1] + '</b>':
							'<b>' + data[i].score.split(":")[0] + '</b>-<b class="blue">' + data[i].score.split(":")[1] + '</b>',
					data[i].guestTeam == teamName ? "<b>" + data[i].guestTeam + "</b>" : data[i].guestTeam,
					data[i].pankou,
					data[i].matchResult == "胜" ? '<b class="red">胜</b>': data[i].matchResult == "平" ? '<b class="green">平</b>':'<b class="blue">负</b>',
					data[i].panlu == true ? '<b class="red">' + "赢" + "</b>" : '<b class="blue">' + "输" + "</b>",
					data[i].bigSmall == "大" ? '<b class="red">' + data[i].bigSmall + "</b>" : '<b class="blue">' + data[i].bigSmall + "</b>",
					data[i].matchNameColor);
			if(data[i].matchResult == "胜"){
				barHtml += matchResultBar.sheng;
				shengCount ++;
			} else if(data[i].matchResult == "平") {
				barHtml += matchResultBar.ping;
				pingCount++;
			} else if(data[i].matchResult == "负") {
				barHtml += matchResultBar.fu;
				fuCount ++;
			}
			//计算胜平负数量
			if(lotteryType == "JCZQ") {
				if(data[i].homeTeam == teamName){ //主队
					jinqiuCount += parseInt(data[i].score.split(":")[0]);
					shiqiuCount += parseInt(data[i].score.split(":")[1]);
				} else { //客队
					jinqiuCount += parseInt(data[i].score.split(":")[1]);
					shiqiuCount += parseInt(data[i].score.split(":")[0]);
				}
			}
		}
		
		var tmpData = {
			matchHtml:matchHtml,
			barHtml:barHtml,
			shengCount:shengCount,
			pingCount:pingCount,
			fuCount:fuCount,
			shiqiuCount:shiqiuCount,
			jinqiuCount:jinqiuCount,
			matchCount:fuCount + pingCount + shengCount
		}
		return tmpData;
	}
	function rendGuestTeamMatch(data,size,excludeMatchs){
		var tmpData = getMatchRelatedHtml(data,size,guestTeamName,excludeMatchs);
		$("#guestTeamTbody").html(tmpData.matchHtml);
		$("#guestTeamBar").html(tmpData.barHtml);
		$("#gtShengCountText").html(tmpData.shengCount + "胜");
		if(lotteryType == "JCZQ") {
			$("#gtPingCountText").html(tmpData.pingCount + "平");
		}
		$("#gtFuCountText").html(tmpData.fuCount + "负");
		if(lotteryType == "JCZQ") {
			$("#gtShiQiuCountText").html(tmpData.shiqiuCount + "球");
			$("#gtJinQiuCountText").html(tmpData.jinqiuCount + "球");
		}
		$("#guestTeamMatchCountB").html(tmpData.matchCount);
	}
	
	function rendHomeTeamMatch(data,size,excludeMatchs){
		var tmpData = getMatchRelatedHtml(data,size,homeTeamName,excludeMatchs);
		$("#homeTeamTbody").html(tmpData.matchHtml);
		$("#homeTeamBar").html(tmpData.barHtml);
		$("#htShengCountText").html(tmpData.shengCount + "胜");
		if(lotteryType == "JCZQ") {
			$("#htPingCountText").html(tmpData.pingCount + "平");
		}
		$("#htFuCountText").html(tmpData.fuCount + "负");
		if(lotteryType == "JCZQ") {
			$("#htShiQiuCountText").html(tmpData.shiqiuCount + "球");
			$("#htJinQiuCountText").html(tmpData.jinqiuCount + "球");
		}
		if(lotteryType == "JCLQ") {
			$(".trend_box > .result").hide();
		}
		$("#homeTeamMatchCountB").html(tmpData.matchCount);
	}
	
	//填充下拉列表数量
	function getOptionHtml(size){
		var optionHtml = "";
		for ( var i = 0; i < size - 1; i++) {
			optionHtml += '<option>' + (parseInt(i) + 1) + '</option>';
		}
		optionHtml += '<option selected="selected">' + size + '</option>';
		return optionHtml;
	}
	function setHomeTeamSelectorSize(size){
		$("#homeTeamSelector").html(getOptionHtml(size));
	}
	function setGuestTeamSelectorSize(size){
		$("#guestTeamSelector").html(getOptionHtml(size));
	}
	
	// ajax load recent match data
	var lotteryType = getQueryString("lotteryType");
	var queryStr = 'teamId=' + homeTeamId + 
			'&lotteryType=' + lotteryType + '&callback=?';
	$.getJSON('ajax_recent_match?' + queryStr, function(data) {
		homeTeamRecentData = data;
		setHomeTeamSelectorSize(data.length);
		$("#homeTeamCheckboxUL").html(getCheckboxHtml(data));
		rendHomeTeamMatch(data,10);
		$("[type='checkbox']",$("#homeTeamCheckboxUL")).change(function (){
			var checkboxs = $("[type='checkbox']",$("#homeTeamCheckboxUL"));
			var excludeMatchs = "";
			for ( var i = 0; i < checkboxs.length; i++) {
				var status = $(checkboxs[i]).attr("checked");
				if(status != "checked"){
					excludeMatchs += $(checkboxs[i]).attr("_value") + "+";
				}
			}
			var viewCount = $("#homeTeamSelector").attr("value");
			rendHomeTeamMatch(data,viewCount,excludeMatchs);
			setHomeTeamSelectorSize(countRecord(data,excludeMatchs));
			$($("#homeTeamSelector option")[viewCount - 1]).attr("selected",true);
		});
	});
	queryStr = 'teamId=' + guestTeamId + 
		'&lotteryType=' + lotteryType + '&callback=?';
	$.getJSON('ajax_recent_match?' + queryStr, function(data) {
		guestTeamRecentData = data;
		setGuestTeamSelectorSize(data.length);
		$("#guestTeamCheckboxUL").html(getCheckboxHtml(data));
		rendGuestTeamMatch(data,10);
		$("[type='checkbox']",$("#guestTeamCheckboxUL")).change(function (){
			var checkboxs = $("[type='checkbox']",$("#guestTeamCheckboxUL"));
			var excludeMatchs = "";
			for ( var i = 0; i < checkboxs.length; i++) {
				var status = $(checkboxs[i]).attr("checked");
				if(status != "checked"){
					excludeMatchs += $(checkboxs[i]).attr("_value") + "+";
				}
			}
			var viewCount = $("#guestTeamSelector").attr("value");
			rendGuestTeamMatch(data,viewCount,excludeMatchs);
			setGuestTeamSelectorSize(countRecord(data,excludeMatchs));
			$($("#guestTeamSelector option")[viewCount - 1]).attr("selected",true);
		});
	});
	
	$("#homeTeamSelector").change(function (e) {
		var $this = $(this);
		var viewCount = $this.attr("value");
		
		var checkboxs = $("[type='checkbox']",$("#homeTeamCheckboxUL"));
		var excludeMatchs = "";
		for ( var i = 0; i < checkboxs.length; i++) {
			var status = $(checkboxs[i]).attr("checked");
			if(status != "checked"){
				excludeMatchs += $(checkboxs[i]).attr("_value") + "+";
			}
		}
		rendHomeTeamMatch(homeTeamRecentData,viewCount,excludeMatchs);
	});
	
	$("#guestTeamSelector").change(function (e) {
		var $this = $(this);
		var viewCount = $this.attr("value");
		
		var checkboxs = $("[type='checkbox']",$("#guestTeamCheckboxUL"));
		var excludeMatchs = "";
		for ( var i = 0; i < checkboxs.length; i++) {
			var status = $(checkboxs[i]).attr("checked");
			if(status != "checked"){
				excludeMatchs += $(checkboxs[i]).attr("_value") + "+";
			}
		}
		rendGuestTeamMatch(guestTeamRecentData,viewCount,excludeMatchs);
	});
});