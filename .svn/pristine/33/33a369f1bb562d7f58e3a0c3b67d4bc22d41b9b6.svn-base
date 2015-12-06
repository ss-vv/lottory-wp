/**
 * 用于异步加载彩种推荐列表、彩种推荐列表数据切换
 */
var category_lottery_url = "/recommend/JCZQ";
$(function() {
	var lotteryType = $("#lotteryType").val();
	if(lotteryType) {
		category_lottery_url = lotteryType;
	}
	$("." + lotteryType).addClass("active-bg");
	$("." + lotteryType).find("a").css("color", "#FFF");
	$("#lotteryRecomContainer").click(function(e) {
		liList.each(function(i, n) {
			$(n).removeClass(active);
			$(n).find("a").css("color", "#000");
		});
	});
	
	//推荐页面彩种切换
	bindTabSwithc("lotteryRecomContainer", "li", "active-bg", function() {
		if($(this).hasClass("JCZQ")) {
			category_lottery_url = "/recommend/JCZQ";
		} else if($(this).hasClass("JCLQ")) {
			category_lottery_url = "/recommend/JCLQ";
		} else if($(this).hasClass("CTZC")) {
			category_lottery_url = "/recommend/CTZC";
		} else if($(this).hasClass("SSQ")) {
			category_lottery_url = "/recommend/SSQ";
		}
		ajaxRecommend(1, category_lottery_url);
	});
	AjaxRender.init();
	//默认显示竞足推荐
	ajaxRecommend(1, "/recommend/JCZQ");
});

/**
 * 提供一个公共的方法，用于标签切换
 * 高亮当前标签
 * @param container 需要绑定切换的标签容器ID
 * @param findItem 标签jquery查找字符串(使用find查找)
 * @param active 激活使用的高亮class
 * @param fn 点击标签切换之后的回调方法
 */
var bindTabSwithc = function(container, findItem,  active, fn) {
	var liList = $("#"+container).find(findItem);
	liList.click(function(e) {
		liList.each(function(i, n) {
			$(n).removeClass(active);
			$(n).find("a").css("color", "#000");
		});
		$(this).addClass(active);
		$(this).find("a").css("color", "#FFF");
		fn.call(this);
	});
};

//异步加载彩种用户的推荐列表
var ajaxRecommend = function(page, url) {
	url = category_lottery_url;
	AjaxRender.post(page, url, ajaxRecommend, new MarrowParse(), new WeiboRenderer());
};