//var interestUserTempelate = 
//	 '<li style="position: relative;" _flag="interest_user_li" _index="{4}" _li_index="{5}">'
//	+'	<div class="headpic"  _userid="{0}">'
//	+'		<a href="http://www.davcai.com/{0}/profile">'
//	+'			<img width="50px" height="50px" src="{1}">'
//	+'		</a>'
//	+'	</div>'
//	+'	<div class="content">'
//	+'		<div class="op addFriendBtn" title="加关注" _userid="{0}" flag="followFlag" style="margin-right: 2px;">+加关注</div>'
//	+'			<a href="http://www.davcai.com/{0}/profile" target="_blank" style="max-width: 130px;">'
//	+'				<span class="screen_name" _nickname_block="_nickname_block" _userid="{0}">{2}</span>{6}'
//	+'			</a>'
//	+'		<div class="userDes">我们共同关注{3}人</div>'
//	+'	</div>'
//	+'</li>';
//
//var INTEREST_USER_SIZE = 6; //感兴趣的人列表显示条数
//var interestUserContainer; //放用户的容器
//var loadingImgForInterest; //加载logo
//var interestUserData; //用户数据
//var interestUserStatus = new Array(); //标记用户状态的数组,正数（含0）表示正常显示，-1表示未显示，-2表示已删除（已经关注）
//$(document).ready(function() {
//	loadingImgForInterest = $("#interest_user_list_loading");
//	interestUserContainer = $("#interest_user_list");
//	
//	$.post("http://www.davcai.com/interest_user?size=60", function(data , e) {
//		loadingImgForInterest.hide();
//		dealResults(data);
//	}, 'json');
//	$("#change_interest_btn").click(changeInterestUser);
//});
//
//function changeInterestUser(event){
//	var changeBtn = $("#change_interest_btn");
//	var tmpIndexArr = new Array();
//	var tmpDataArr = new Array();
//	for ( var i = 0; i < interestUserData.length; i++) {
//		if(interestUserStatus[i] == -1){ //未显示的数据
//			tmpIndexArr.push(i);
//		} else if(interestUserStatus[i] >= 0){ 
//			interestUserStatus[i] = -1;//已显示的数据 设为未显示
//			tmpIndexArr.push(i);
//		}
//	}
//	//打乱顺序，产生随机效果
//	tmpIndexArr.sort(function(){ return 0.5 - Math.random(); }); 
//	
//	for ( var i = 0; i < INTEREST_USER_SIZE && i < tmpIndexArr.length; i++) {
//		var userIndex = tmpIndexArr[i];
//		if(interestUserStatus[userIndex] == -1){ //未显示
//			tmpDataArr[i] = interestUserData[userIndex];	
//			interestUserStatus[userIndex] = userIndex; //设置为显示状态
//		}
//	}
//	interestUserContainer.html('');
//	var segment = "";
//	for ( var i = 0; i < tmpDataArr.length && i < INTEREST_USER_SIZE; i++) {
//		var user = tmpDataArr[i];
//		segment += $.format(
//				interestUserTempelate,
//				user.weiboUserId,
//				user.headImageURL,
//				user.nickName,
//				user.togetherFollowNum,
//				i,
//				i,
//				user.certificationType == 1? '<i class="niu" title="大V彩牛人" style="background:url(\'../images/niu.png\') 50% no-repeat;width: 32px;height: 20px;display: inline-block;"></i>':"");
//	}
//	interestUserContainer.html(segment);
//	//给关注按钮绑定关注和取消关注事件
//	$(".addFriendBtn",interestUserContainer).click(function (event, a, b) {
//		followForInterest( $(event.target));
//	});
//	bindMouseInHeadOrNicknameEvent(interestUserContainer);
//}
//
//function dealResults(data){
//	if (data != null) {
//		if (data.success) {
//			if(data.results.length > 0){
//				interestUserData = data.results;
//				//初始化标记用户显示的数组
//				for ( var i = 0; i < interestUserData.length; i++) {
//					interestUserStatus[i] = -1;
//				}
//				var segment = "";
//				for ( var i = 0; i < interestUserData.length && i < INTEREST_USER_SIZE; i++) {
//					var user = interestUserData[i];
//					segment += $.format(
//							interestUserTempelate,
//							user.weiboUserId,
//							user.headImageURL,
//							user.nickName,
//							user.togetherFollowNum,
//							i,
//							i,
//							user.certificationType == 1? '<i class="niu" title="大V彩牛人" style="background:url(\'../images/niu.png\') 50% no-repeat;width: 32px;height: 20px;display: inline-block;"></i>':"");
//					interestUserStatus[i] = i;
//				}
//				interestUserContainer.html(segment);
//				//给关注按钮绑定关注和取消关注事件
//				$(".addFriendBtn",interestUserContainer).click(function (event, a, b) {
//					followForInterest( $(event.target));
//				});
//				bindMouseInHeadOrNicknameEvent(interestUserContainer);
//			} else {
//				interestUserContainer.html('<li style="position: relative;">'
//						+ '没有啦'
//						+ '</li>');
//			}
//		} else {
//			$.showMessage(data.desc);
//		}
//	}
//}
//function loadOneUser(liIndex){
//	var userLi = $("[_flag='interest_user_li']");
//	if(userLi.length < 1){
//		interestUserContainer.html('<li style="position: relative;">'
//				+ '没有啦'
//				+ '</li>');
//	}
//	for ( var i = 0; i < interestUserData.length; i++) {
//		if(interestUserStatus[i] == -1){
//			var user = interestUserData[i];
//			var segment = $.format(
//					interestUserTempelate,
//					user.weiboUserId,
//					user.headImageURL,
//					user.nickName,
//					user.togetherFollowNum,
//					i,
//					liIndex,
//					user.certificationType == 1? '<i class="niu" title="大V彩牛人" style="background:url(\'../images/niu.png\') 50% no-repeat;width: 32px;height: 20px;display: inline-block;"></i>':"");
//			interestUserStatus[i] = i;
//			if(userLi.length <= liIndex){
//				$(userLi[userLi.length-1]).after(segment);
//			} else {
//				$(userLi[liIndex]).before(segment);
//			}
//			var userLi = $("[_flag='interest_user_li']");
//			//给关注按钮绑定关注和取消关注事件
//			$(".addFriendBtn",$(userLi[liIndex])).click(function (event, a, b) {
//				followForInterest( $(event.target));
//			});
//			break;
//		}
//	}
//}
//function followForInterest(followBtn){
//	var followFlag = "followFlag";
//	if(followFlag == followBtn.attr("flag")) {
//		var userId = followBtn.attr("_userId");
//		$.post("http://www.davcai.com/ajax_rltship_follow",{followIds:userId}, function(data , e) {
//			if (data != null) {
//				if (data.success) {
//					var li = followBtn.parent().parent();
//					var index = li.attr("_index");
//					var liIndex = li.attr("_li_index"); 
//					interestUserData[index] = -1;
//					interestUserStatus[index] = -2;
//					li.fadeOut("slow",function (){li.remove();loadOneUser(liIndex);});
//				} else {
//					alert(data.desc);
//					if(data.resultCode == "notlogin"){
//						window.location.href="http://www.davcai.com/";
//					}
//				}
//			}
//		}, 'json');
//	}
//}