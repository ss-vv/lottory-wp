$(document).ready(function() {
	//查询粉丝和关注数量，并设置到home左侧相关位置-----begin-----
	
	$.post("ajax_rltship_countFollowings", function(data , e) {
		if (data != null) {
			if (data.success) {
				if(data.pageRequest.recordCount > 0){
					$("#followingsNum").html(data.pageRequest.recordCount);
				} else {
					$("#followingsNum").html("0");
				}
			} else {
				console.log(data.desc);
			}
		}
	}, 'json');
	
	$.post("ajax_rltship_countFollowers", function(data , e) {
		if (data != null) {
			if (data.success) {
				if(data.pageRequest.recordCount > 0){
					$("#followersNum").html(data.pageRequest.recordCount);
				} else {
					$("#followersNum").html("0");
				}
			} else {
				console.log(data.desc);
			}
		}
	}, 'json');
	//查询粉丝和关注数量，并设置到home左侧相关位置-----end-----
	
	//加载TOP10用户 ----------------- begin -----------------
	var topTmp = {
            body:'<li>'
        	    +'<div class="headpic"  _userid="{2}">'
        	    +'<a href="http://www.davcai.com/{2}/profile"><img id="sponsor_user_headImage" width="50px" height="50px" src="{0}"></a>'
        	    +'</div>'
        	    +'<div class="content">'
        	    +'<a href="http://www.davcai.com/{2}/profile" target="_blank"><span id="sponsor_user_name">{1}</span></a>'
        	    +'<div class="userDes">推荐：100条    晒单：200条</div>'
        	    +'<div class="userDes">合买：100条    粉丝：800万</div>'
        	    +'</div>'
        	    +'<div class="userDes">'
        	    +'<span class="userGood">擅长：胜平负&nbsp;14场</span>'
        	    +'<div class="addFriendBtn" title="加关注" _userId="{2}" flag="followFlag">＋加关注</div>'
        	    +'</div>'
            +'</li>'
	};
//  加载TOP10
//	$.post("ajax_rltship_findWeiboUser", function(data , e) {
//		$("#sponsor_user_list_loading").hide();
//		if (data != null) {
//			if (data.success) {
//				loadWeiboUser(data.results);
//			} else {
//				console.log(data.desc);
//			}
//		}
//	}, 'json');
	
	function loadWeiboUser(weiboUsers){
		var body="";
		for(var i in weiboUsers){
			var weiboUser = weiboUsers[i];
			if(true == weiboUser.beFollowed){
				continue;
			}
			if(weiboUser.headImageURL == null || weiboUser.headImageURL == ""){
				weiboUser.headImageURL = "images/avatar_01.png";
			}
			body += $.format(topTmp.body,
					weiboUser.headImageURL,
					weiboUser.nickName,
					weiboUser.weiboUserId
				);
		}
		$("#sponsor_user_list").append(body);

		//给关注按钮绑定关注和取消关注事件
		$(".addFriendBtn",$("#sponsor_user_list")).click(function (event, a, b) {
			followAndUnFollow( $(event.target));
		});
	}
	
	//加载推荐用户 ----------------- end -----------------
});