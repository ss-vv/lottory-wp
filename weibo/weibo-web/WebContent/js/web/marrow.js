/**
 * 今日话题、热门推荐、热门讨论、最新发表 标签切换，数据请求和渲染
 */
$(function() {
	// 精华列表标签切换按钮单击事件#marrowTab
	$("#whl li").click(function() {
		$("#whl li a").removeClass("active");
		$("#whl li").removeClass("active");
		$(this).children("a").addClass("active");
		$(this).addClass("active");
		var clz = $(this).attr("_type");
		switch (clz) {
		case "topic": // 今日话题
			ajaxDailyTopic();
			getWinnngTab().hide();
			break;
		case "recommend": // 热门推荐
			ajaxHotRecommend();
			getWinnngTab().hide();
			break;
		case "notice": // 热门讨论
			ajaxDiscuss();
			getWinnngTab().hide();
			break;
		case "latest": // 最新发表
			ajaxLatestPublish();
			getWinnngTab().hide();
			break;
		case "winningList": // 中奖喜报
			resetWinTab();
			getWinnngTab().show();
			ajaxWinningList();
			break;
		}
	});
	AjaxRender.init();
});

// 异步显示精华下的“今日话题”列表
var ajaxDailyTopic = function(page) {
	AjaxRender.post(page,"ajax-dailyTopic", ajaxDailyTopic, new DailyTopicParse(), new WeiboTopicRenderer());
};

// 异步显示精华下的“热门讨论”列表
var ajaxDiscuss = function(page) {
	var title = 
	'<div class="newTab_white down">'+
	'      <ul class="down hot_comment_tab">'+
	'      	<li class="active" data_scope="day" data_type="comment"><a href="?scope=day" class="day">最近24小时</a></li>'+
	'      </ul>'+
	'</div>';
	AjaxRender.post(page,"ajax-hot-discuss", ajaxDiscuss, new MarrowParse(), new WeiboRenderer());
};

// 异步显示精华下的“热门推荐”列表
var ajaxHotRecommend = function(page) {
	AjaxRender.post(page,"ajax-hot-recommend", ajaxHotRecommend, new MarrowParse(), new WeiboRenderer());
};

// 异步显示精华下的“最新发表”列表
var ajaxLatestPublish = function(page) {
	AjaxRender.post(page,"ajax-latest-publish", ajaxLatestPublish, new MarrowParse(), new WeiboRenderer());
};

var getWinnngTab = function() {
	// marrowTab
	
	//$("#whl").find(".davcai-nav-classify").children().remove();
	//var c=$("#whl").find(".davcai-nav-classify").children();
	return $("#whl").find(".davcai-nav-classify");
};

var ajaxWinListUrl = "ajax-winning-list";
var ajaxFollowingWinListUrl = "ajax-following-winnings";

//异步显示精华下的“中奖喜报”列表
var ajaxWinningList = function(page, type) {
	//new
	var title=' <label class="current" data_scope="win-all">全部</label>'+
	          ' <label data_scope="following-win">我关注人的</label>';
	var winTab = getWinnngTab();
	var title = $(title);
	if(winTab.length <= 0) {
		//$("#marrowTab").append(title);
         $("#whl .davcai-nav-classify").append(title);
		winCallback(title);
	}
	
	var render = null;
	if(!type || type == ajaxWinListUrl) {
		AjaxRender.post(page,ajaxWinListUrl, 
			ajaxWinningList, 
			new MarrowParse(), 
			new WeiboRenderer());
	} else {
		AjaxRender.post(page,ajaxFollowingWinListUrl, 
			ajaxWinningList, 
			new MarrowParse(), 
			new WeiboRenderer());
	}
}

var resetWinTab = function() {
	//$("li[data_scope='win-all']").addClass("active");
	//$("li[data_scope='following-win']").removeClass("active");
	$("label[data_scope='win-all']").addClass("current");
	$("label[data_scope='following-win']").removeClass("current");
};

var winCallback = function(title) {
	//var winTab = title.find("li");
	var winTab = title.find("label");
	$(winTab).click(function() {
		var winType = $(this).attr("data_scope");
		if(winType == "following-win") {
			ajaxWinningList(1, ajaxFollowingWinListUrl);
		} else {
			ajaxWinningList();
		}
		//$(winTab).removeClass("active");
		//$(this).addClass("active");
		$(winTab).removeClass("current");
		$(this).addClass("current");
	});
};