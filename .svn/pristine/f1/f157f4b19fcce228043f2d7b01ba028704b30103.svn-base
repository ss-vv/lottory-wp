$(document).ready(function() {
	//给关注按钮绑定关注和取消关注事件
	$(".addFriendBtn").click(function (event) {
		followAndUnFollow( $(event.target));
	});
	
});

function followAndUnFollow(followBtn){
	var followFlag = "followFlag";
	var unFollowFlag = "unFollowFlag";
	
	var followString="＋加关注";
	var unFollowString="取消关注";
	if(followFlag == followBtn.attr("flag")) {
		var userId = followBtn.attr("_userId");
		$.post("/ajax_rltship_follow",{followIds:userId}, function(data , e) {
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
		$.post("/ajax_rltship_unfollow",{unfollowIds:userId}, function(data , e) {
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