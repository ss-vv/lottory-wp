var interestUserTempelate = 
	 '<li class="clearfix" style="width: 20%;float:left;margin-right: 40px;margin-top: 15px;">'
	+' 	 <img src="{1}"/>'
	+'	 <div>'
	+'		 <h3 _nickname_block="_nickname_block" _userid="{0}">{2}</h3>'
	+'		 <div style="margin-top: 8px;display: inline-block;height: 22px;line-height: 22px;padding: 0 4px;text-align: center;font-size: 12px;" class="op addFriendBtn" title="加关注" _userid="{0}" flag="followFlag" style="margin-right: 2px;">+加关注</div>'
	+'	 </div>'
	+'</li>';

var INTEREST_USER_SIZE = 8; //感兴趣的人列表显示条数
var interestUserContainer; //放用户的容器
var loadingImgForInterest; //加载logo
var interestUserData; //用户数据
var interestUserStatus = new Array(); //标记用户状态的数组,正数（含0）表示正常显示，-1表示未显示，-2表示已删除（已经关注）


function getElementPosition(el) { 
	var obj=el,offset=new Object(),x=0,y=0; 
	while(obj&&obj.tagName != "BODY") { 
		x+=obj.offsetLeft; 
		y+=obj.offsetTop; 
		obj=obj.offsetParent;
	}
	offset.x=x-320;
	offset.y=y+15;
	return offset; 
}


$(document).ready(function() {
//	loadingImgForInterest = $("#rec_user_list_loading");
//	interestUserContainer = $("#rec_user_list");
//	
//	$.post("http://www.davcai.com/aj_rec_user_list", function(data , e) {
//		loadingImgForInterest.hide();
//		dealResults(data);
//	}, 'json');
//	$("#change_rec_btn").click(changeInterestUser);
	$("#change_active_user_btn").click(changeActiveUser);
	
	 
	$("#guodanlv_tip_que").mouseover(function (){
		var ret = getElementPosition($(this)[0]);
		$("#guodanlv_tip").css("left",ret.x + 325).css("top",ret.y + 5);
		$("#guodanlv_tip").show();
	}).mouseout(function (){
		$("#guodanlv_tip").hide();
	});
	
	$("#zhongjiangjine_tip_que").mouseover(function (){
		var ret = getElementPosition($(this)[0]);
		$("#zhongjiangjine_tip").css("left",ret.x + 325).css("top",ret.y + 5);
		$("#zhongjiangjine_tip").show();
	}).mouseout(function (){
		$("#zhongjiangjine_tip").hide();
	});
	
	$("#follow_all_guodanlv_user").click(function (){
		var $this = $(this);
		$this.attr("disabled",true);
		var lis = $this.closest("div[class='someone-page-rate']").find("li");
		lis.each(function (){
			$(this).find('.addFriendBtn[flag="followFlag"]').trigger("click");
		});
	});
	$("#follow_all_zhongjiang_user").click(function (){
		var $this = $(this);
		$this.attr("disabled",true);
		var lis = $this.closest("div[class='someone-page-win-bouns']").find("li");
		lis.each(function (){
			$(this).find('.addFriendBtn[flag="followFlag"]').trigger("click");
		});
	});
	
	$("#follow_all_active_user").click(function (){
		var $this = $(this);
		$this.attr("disabled",true);
		$this.html("正在关注...");
		var lis = $this.closest("div[class='someone-page-active-user']").find("li");
		var userIds = new Array();
		lis.each(function (){
			var uid = $(this).attr("_uid");
			if(uid){
				userIds.push(uid);
			}
		});
		$.ajaxSettings.traditional = true;
		$.post("/ajax_rltship_follow",{followIds:userIds}, function(data , e) {
			if (data != null) {
				if (data.success) {
					alert("关注成功！");
					changeActiveUser("");
				} else {
					alert(data.desc);
				}
				$this.attr("disabled",false);
				$this.html("一键关注");
			}
		}, 'json');
	});
});

function changeActiveUser(e){
	var tmpl = '{{#users}}<li _uid="{{weiboUserId}}">'
			  +'	<a href="http://www.davcai.com/{{weiboUserId}}/profile"><img src="{{headImageURL}}" _nickname_block="_nickname_block" _userid="{{weiboUserId}}" class="someone-page-active-user-content-head-img"></a>'
			  +'	<span class="someone-page-active-user-content-head-name"  _nickname_block="_nickname_block" _userid="{{weiboUserId}}">'
			  +'		<a target="_blank" href="http://www.davcai.com/{{weiboUserId}}/profile">{{nickName}}{{{certificationView}}}</a>'
			  +'	</span>'
			  +'	{{{followButton}}}'
			  +'	<span class="someone-page-active-user-content-details">主贴(<i>{{weiboCount}}</i>)<em>|</em>关注(<i>{{followingCount}}</i>)<em>|</em>粉丝(<i>{{followerCount}}</i>)</span>'
			  +'</li>{{/users}}';
	var loadingHtml = '<div class="loading" style="height: 40px; display: true;" id=""></div>';
	var $this = $("#change_active_user_btn");
	var curPage = parseInt($this.attr("_page"));
	var url = "/active_users?page=" + (curPage + 1);
	$("#active_users_ul").html(loadingHtml);
	$.getJSON(url,function (data){
		if(data.results && data.results.length >0){
			var html = $.mustache(tmpl,{
				users:data.results,
				followButton:function (){
					var followBtn ='<button class="someone-page-active-user-content-attention addFriendBtn"  title="加关注" _userid="'+this.weiboUserId+'" flag="followFlag" >+加关注</button>';
					var unFollowBtn ='<button class="someone-page-active-user-content-attention addFriendBtn" title="取消关注" _userid="'+this.weiboUserId+'" flag="unFollowFlag">取消关注</button>';
					if(this.beFollowed){
						return unFollowBtn;
					} else {
						return followBtn;
					}
				},
				certificationView:function (){
					if(this.certificationType == 1){
						return '<i class="niu" title="大V彩牛人" style="background:url(\'../images/niu.png\') 50% no-repeat;width: 32px;height: 15px;position: relative;display: inline-block;"></i>';
					} else {
						return '';
					}
				}
			});
			$("#active_users_ul").html(html);
			$(".addFriendBtn",$("#active_users_ul")).click(function (event) {
				followAndUnFollow( $(event.target));
			});
			if(curPage + 1 > data.pageRequest.pageCount){
				$this.attr("_page",1);
			} else {
				$this.attr("_page",curPage + 1);
			}
		}
	});
}

function changeInterestUser(event){
	var tmpIndexArr = new Array();
	var tmpDataArr = new Array();
	for ( var i = 0; i < interestUserData.length; i++) {
		if(interestUserStatus[i] == -1){ //未显示的数据
			tmpIndexArr.push(i);
		} else if(interestUserStatus[i] >= 0){ 
			interestUserStatus[i] = -1;//已显示的数据 设为未显示
			tmpIndexArr.push(i);
		}
	}
	//打乱顺序，产生随机效果
	tmpIndexArr.sort(function(){ return 0.5 - Math.random(); }); 
	
	for ( var i = 0; i < INTEREST_USER_SIZE && i < tmpIndexArr.length; i++) {
		var userIndex = tmpIndexArr[i];
		if(interestUserStatus[userIndex] == -1){ //未显示
			tmpDataArr[i] = interestUserData[userIndex];	
			interestUserStatus[userIndex] = userIndex; //设置为显示状态
		}
	}
	interestUserContainer.html('');
	var segment = "";
	for ( var i = 0; i < tmpDataArr.length && i < INTEREST_USER_SIZE; i++) {
		var user = tmpDataArr[i];
		segment += $.format(interestUserTempelate,user.weiboUserId,user.headImageURL,user.nickName);
	}
	interestUserContainer.html(segment);
	$(".addFriendBtn",interestUserContainer).unbind();
	//给关注按钮绑定关注和取消关注事件
	$(".addFriendBtn",interestUserContainer).click(function (event, a, b) {
		followAndUnFollow( $(event.target));
	});
}

function dealResults(data){
	if (data != null) {
		if(data.length > 0){
			interestUserData = data;
			//初始化标记用户显示的数组
			for ( var i = 0; i < interestUserData.length; i++) {
				interestUserStatus[i] = -1;
			}
			var segment = "";
			for ( var i = 0; i < interestUserData.length && i < INTEREST_USER_SIZE; i++) {
				var user = interestUserData[i];
				segment += $.format(interestUserTempelate,user.weiboUserId,user.headImageURL,user.nickName);
				interestUserStatus[i] = i;
			}
			interestUserContainer.html(segment);
			//给关注按钮绑定关注和取消关注事件
			$(".addFriendBtn",interestUserContainer).unbind();
			$(".addFriendBtn",interestUserContainer).click(function (event, a, b) {
				followAndUnFollow( $(event.target));
			});
		} else {
			interestUserContainer.html('暂无推荐的用户噢~');
		}
	}
}