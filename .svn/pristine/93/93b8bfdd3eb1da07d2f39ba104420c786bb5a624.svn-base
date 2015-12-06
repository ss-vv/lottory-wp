$(document).ready(function (){
	var url2TabMap = {};
	url2TabMap["home"]="navigatorHome";
	url2TabMap["dailyTopic"]="navigatorEssence";
	url2TabMap["hotRecommend"]="navigatorEssence";
	url2TabMap["hotDiscuss"]="navigatorEssence";
	url2TabMap["latestPublish"]="navigatorEssence";
	url2TabMap["recommend"]="navigatorRecommend";
	url2TabMap["bet"]="navigatorBet";
	url2TabMap["JZscore"]="navigatorLive";
	url2TabMap["JLscore"]="navigatorLive";
	url2TabMap["CTscore"]="navigatorLive";
	url2TabMap["find_people"]="navigatorFindPeople";
	
	var keys = ["home","dailyTopic","hotRecommend","hotDiscuss","latestPublish",
			"recommend","bet","JZscore","JLscore","CTscore","find_people"];
	
	var url  = location.href;
	var liElement = null;
	
	for ( var i = 0; i < keys.length; i++) {
		if(url.indexOf(keys[i]) != -1){
			liElement = $("#" + url2TabMap[keys[i]]);
			break;
		}
	}
	
	if(liElement != null){
		liElement.addClass("active");
	}
});