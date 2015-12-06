// ============== ajax ==============
// 获取JX11期信息
var aj_jx11_issue_info = function() {
	$.ajax({
		url : 'http://trade.davcai.com/df/ssq/aj_issue.do?jsonp=?',
		cache : false,
		crossDomain : true,
		dataType : 'jsonp'
	}).done(function(data) {

	});
};