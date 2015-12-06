var queryStringObject = {};
var getQueryString = function(name) {
	return queryStringObject[name];
};
var isLQ = function() {
	return queryStringObject.lotteryType == "JCLQ";
};
var isZQ = function() {
	return queryStringObject.lotteryType == "JCZQ" || 
		queryStringObject.lotteryType == "CTZC" ||
		queryStringObject.lotteryType == "BJDC" ;
};
$(function() {
	var matchIdStr = $("#matchId").val();
	var lotteryType = $("#lotteryType").val();
	queryStringObject.matchId = matchIdStr;
	queryStringObject.lotteryType = lotteryType;
});