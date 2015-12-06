$(document).ready(function() {
	$weiboList = $(".status-list");
	$("#search_keys").keypress(function (e){
		var e = e || window.event; 
		if(e.keyCode == 13){ 
			if(document.getElementById("search_user_button") != undefined){
				searchUser("",1);
			} else if (document.getElementById("search_weibo_button") != undefined){
				searchWeibo("",1);
			}
		}
	});
	//绑定搜索事件
	$("#search_user_button").click(searchUser);
	$("#search_weibo_button").click(searchWeibo);
	
});

function updateSearchTypeKey(keys){
	keys = encodeURIComponent(keys);
	$("#searchType_weibo").click(function (){
		window.location.href = "http://www.davcai.com/search_weibo?keys=" + keys;
	});
	$("#searchType_user").click(function (){
		window.location.href = "http://www.davcai.com/search_user?keys=" + keys;
	});
}

function searchWeibo(event,page) {
	if(page == undefined || page < 0){
		page = 1;
	}
	var $this = $("#search_weibo_button");
	$this.attr("disabled",true);
	
	searchKeys = $("#search_keys").val();
	if(searchKeys.trim() == ""){
		$('.loading').hide();
		alert("请输入查询条件");
		$this.attr("disabled",false);
		return false;
	}
	updateSearchTypeKey(searchKeys);
	
	var weiboListUL = $("#search_weibo_list");
	weiboListUL.html('');
	$('.loading').show();
	$("#totalResultsTip").html('');
	
	$.post("http://www.davcai.com/ajax_search_weibo?keys=" + encodeURIComponent(searchKeys) + "&page=" + page,function(result, e) {
		$('.loading').hide();
		if (result.weiboMsgVOs.success) {
			if(result.weiboMsgVOs.results.length > 0){
				weiboListUL.html("");
				$("#totalResultsTip").html('找到关于 "' + searchKeys +'" 结果<em>' + result.weiboMsgVOs.totalResults+ '</em>条');
				$("#search_weibo_list").weiboList({
					posts:result.weiboMsgVOs.results,
					weiboUserId:result.weiboMsgVOs.userId
				});
				var curPage = result.weiboMsgVOs.pageRequest.pageIndex;
				var totalPages = result.weiboMsgVOs.totalPages;
				$(".pager-wrapper").html(ajaxPager("#",curPage,totalPages));
				$(".pager-wrapper a").click(searchWeiboByPaging);
			} else {
				weiboListUL.html("<li>无记录!</li>");
				$(".pager-wrapper").html("");
			}
		} else {
			weiboListUL.html("<li>查询失败!</li>");
		}
		$this.attr("disabled",false);
	}, 'json').error(function(e) {
		$.showMessage( "操作失败，请刷新页面重试！");
		$this.attr("disabled",false);
	});
};

function searchWeiboByPaging(event){
	$this = $(this);
	var page = $this.attr("data-page");
	searchWeibo("",page);
}

function searchUser(e,page) {
	if(page == undefined || page < 0){
		page = 1;
	}
	var $this = $("#search_user_button");
	$this.attr("disabled",true);
	
	searchKeys = $("#search_keys").val();
	if(searchKeys.trim() == ""){
		$('.loading').hide();
		$this.attr("disabled",false);
		alert("请输入查询条件");
		$this.attr("disabled",false);
		return false;
	}
	updateSearchTypeKey(searchKeys);
	
	var userListUL = $("#search_user_list");
	userListUL.html('');
	
	$('.loading').show();
	$("#totalResultsTip").html('');
	
	$.post("http://www.davcai.com/ajax_search_user?keys=" + encodeURIComponent(searchKeys) + "&page=" + page,function(result, e) {
		$('.loading').hide();
		if (result.users.success) {
			if(result.users.results.length > 0){
				$("#totalResultsTip").html('找到关于 "' + searchKeys +'" 结果<em>' + result.users.totalResults+ '</em>条');
				addSearchUserList(result.users.results);
				
				var curPage = result.users.pageRequest.pageIndex;
				var totalPages = result.users.totalPages;
				$(".pager-wrapper").html(ajaxPager("#",curPage,totalPages));
				$(".pager-wrapper a").click(searchUserByPaging);
			} else {
				userListUL.html("<li>无记录!</li>");
				$(".pager-wrapper").html("");
			}
		} else {
			userListUL.html("<li>查询失败!</li>");
		}
		$this.attr("disabled",false);
	}, 'json').error(function(e) {
		$.showMessage( "操作失败，请刷新页面重试！");
		$this.attr("disabled",false);
	});
};

function searchUserByPaging(event){
	$this = $(this);
	var page = $this.attr("data-page");
	searchUser("",page);
}

//渲染用户集合到页面
function addSearchUserList(userList){
	var liHtml =
		 '<li>'
		+'	<a href="http://www.davcai.com/{0}/profile"><img  _userid="{0}" src="{1}" class="user-logo"></a>'
		+' 	<span class="user-name"><a href="http://www.davcai.com/{0}/profile">{2}</a></span>'
		+'	<p class="status">主帖<em>{3}</em>条   &nbsp;&nbsp;关注<em>{4}</em>人  &nbsp;&nbsp;' 
		+'		 粉丝<em>{5}</em>人'
		+'	</p>'
		+'	<p class="review"></p>'
		+'  {6}'
		+'</li>';
	
	var tmpHtml="";
	var followBtn =  '  <div class="addFriendBtn" title="加关注" flag="followFlag" _userid="{0}" style="float: right;margin-top: -75px;margin-right: 8px;height: 26px;width: 65px;padding: 1px;">'
				    +'       +加关注'
				    +'  </div>';
	var unFollowBtn =  '  <div class="addFriendBtn" title="取消关注" flag="unFollowFlag" _userid="{0}" style="float: right;margin-top: -75px;margin-right: 8px;height: 26px;width: 65px;padding: 1px;">'
					+'       取消关注'
					+'  </div>';
	for ( var i in userList) {
		var user = userList[i];
		var followBtn = user.beFollowed ? 
			$.format(unFollowBtn,user.weiboUserId):
			$.format(followBtn,user.weiboUserId);
		tmpHtml += $.format(liHtml,
				user.weiboUserId,
				user.headImageURL,
				user.nickName,
				user.weiboCount,
				user.followingCount,
				user.followerCount,
				followBtn);
	}
	$("#search_user_list").html(tmpHtml);
	//给关注按钮绑定关注和取消关注事件
	$(".addFriendBtn").click(function (event, a, b) {
		followAndUnFollow( $(event.target));
	});
}
//关注和取消关注
function followAndUnFollow(followBtn){
	var followFlag = "followFlag";
	var unFollowFlag = "unFollowFlag";
	
	var followString="+加关注";
	var unFollowString="取消关注";
	if(followFlag == followBtn.attr("flag")) {
		var userId = followBtn.attr("_userId");
		$.post("http://www.davcai.com/ajax_rltship_follow",{followIds:userId}, function(data , e) {
			if (data != null) {
				if (data.success) {
					followBtn.html(unFollowString);
					followBtn.attr("title","取消关注");
					followBtn.attr("flag",unFollowFlag);
				} else {
					alert(data.desc);
					if(data.resultCode == "notlogin"){
						window.location.href="http://www.davcai.com/";
					}
				}
			}
		}, 'json');
	} else {
		var userId = followBtn.attr("_userId");
		$.post("http://www.davcai.com/ajax_rltship_unfollow",{unfollowIds:userId}, function(data , e) {
			if (data != null) {
				if (data.success) {
					followBtn.html(followString);
					followBtn.attr("title","加关注");
					followBtn.attr("flag",followFlag);
				} else {
					alert(data.desc);
				}
			}
		}, 'json');
	}
}
