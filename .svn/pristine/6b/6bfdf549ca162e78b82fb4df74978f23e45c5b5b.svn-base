$(function() {
	//高亮当前访问页面的标签
	url = location.pathname;
	var leftMenuWrap = $(".scores_left_menu_wrap"); 
	if(url.indexOf("favoriateList") == 1) {
		leftMenuWrap.find(".s-favorite").css("background-position", "-774px -125px");
		leftMenuWrap.find(".s-favorite > a").css("color", "#E22319");
	} else if(url.indexOf("atMeList") == 1) {
		leftMenuWrap.find(".s-atMe").css("background-position", "-774px -224px");
		leftMenuWrap.find(".s-atMe > a").css("color", "#E22319");
	} else if(url.indexOf("cmnt_me") == 1) {
		leftMenuWrap.find(".s-cmnt-me").css("background-position", "-774px -274px");
		leftMenuWrap.find(".s-cmnt-me > a").css("color", "#E22319");
	} else if(url.indexOf("private_msg") == 1) {
		leftMenuWrap.find(".s-privateMsgs").css("background-position", "-774px -320px");
		leftMenuWrap.find(".s-privateMsgs > a").css("color", "#E22319");
	} else if(url.indexOf("rltship_findFollowings") == 1) {
		leftMenuWrap.find(".s-following").css("background-position", "-774px -375px");
		leftMenuWrap.find(".s-following > a").css("color", "#E22319");
	} else if(url.indexOf("rltship_findFollowers") == 1) {
		leftMenuWrap.find(".s-follower").css("background-position", "-774px -428px");
		leftMenuWrap.find(".s-follower > a").css("color", "#E22319");
	} else if(url.indexOf("home") == 1) {
		leftMenuWrap.find(".s-home").css("background-position", "-774px -73px");
		leftMenuWrap.find(".s-home > a").css("color", "#E22319");
	}
	
	bindIconSwitchClick();
});

//绑定单击首页标签切换事件
function bindIconSwitchClick() {
	$(".scores_left_menu_wrap").find("ul > li").click(function() {
		var href = $(this).find("a").attr("href");
		window.location.href = href;
	});
}

