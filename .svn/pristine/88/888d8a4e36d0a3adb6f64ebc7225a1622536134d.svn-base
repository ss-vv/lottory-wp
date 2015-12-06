/**
 * 彩种标签JS服务
 */
Jet().$package("LotteryTab", function(J){
	var lotteryTab = this;
	
	var expireDate = new Date();
	expireDate.setTime(expireDate.getTime() + (365*24*60*60*1000));
	var options = {
		expires:expireDate,
		domain:".davcai.com"
	};
	
	/**
	 *	记住给定的标签;使用ID作为唯一标示
	 *	@param tab	jQuery对象
	 */
	this.remember = function(tab) {
		if(tab && tab.length > 0) {
			var key = lotteryTab.packageName;
			var tabId = tab.attr("id");
			$.cookie(key, tabId, options);
		}
	};
	/**
	 * 给出用户最后一次选择的标签
	 * 返回的是jQuery对象
	 */
	this.tellMeCurrent = function() {
		var key = lotteryTab.packageName;
		var lotteryTabVal = $.cookie(key);
		return $("#"+lotteryTabVal);
	};
	
	/**
	 * 给出用户最后一次选择的标签ID
	 */
	this.tellMeCurrentId = function() {
		var key = lotteryTab.packageName;
		var lotteryTabVal = $.cookie(key);
		return lotteryTabVal;
	};
});