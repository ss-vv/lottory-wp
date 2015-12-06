var userInfoBlockTpl =
	    '<div class="prompt-box" _usercard="_usercard" id="user_float_dialog_div_{{weiboUserId}}" style="position: absolute;" _userid="{{weiboUserId}}">'
	   +'	<div class="prompt-box-top">'
	   +'	<a href="/{{weiboUserId}}/profile" target="_blank"><img src="{{headImageURL}}" width="50px" height="50px" class="img_left"></a>'
	   +'	<div class="top-r">'
	   +'		<p class="name">{{nickName}}</p>'
	   +'		<p class="prompt-box-detail">'
	   +'			<a href="/{{weiboUserId}}/profile" target="_blank" class="zhutie">主贴</a><span>({{weiboCount}})</span><i>|</i>'
	   +'			<a href="/{{weiboUserId}}/profile/followings" target="_blank" class="guanzhu">关注</a><span>({{followingCount}})</span><i>|</i>'
	   +'			<a href="/{{weiboUserId}}/profile/followers" target="_blank" class="fensi">粉丝</a><span>({{followerCount}})</span>'
	   +'		</p>'
	   +'	    {{{followButton}}}'
	   +'	</div>'
	   +'	</div>'
	   +'	<div class="prompt-box-bottom">'
	   +'	  <div class="line">近7天战绩</div>'
	   +'	  <img src="http://www.davcai.com/images/real.png" class="real-img">'
	   +'	  <p>'
	   +'	  	       发<label class="fadan">{{realWeibo7Count}}</label>单，'
	   +'	  	       已开奖<label class="kaijiang">{{realWeibo7OpenCount}}</label>单，'
	   +'	  	       盈利<label class="zhongdan">{{realWeibo7GainCount}}</label>单，'
	   +'	  	       过单率<label class="guodanlv">{{guodanlv}}</label>'
	   +'	  </p>'
	   +'	</div>'
	   +'</div>';

function renderHeadImageInfoBlock(userId,event,type){
	var $block = $("#user_float_dialog_div_"+userId);
	var e = event.target;
	var x = e.offsetLeft,y = e.offsetTop;  
    while(e=e.offsetParent){
       x += e.offsetLeft;  
       y += e.offsetTop;
    }  
	var left =x - 13;
	var top = y + 50;
	if(type=="nickname"){
		var top = y+15;
	}
	if($block.length == 0){
		var url = '/' + userId + '/card';
		$.getJSON(url,function (weiboUserStatis){
			weiboUserStatis.guodanlv = weiboUserStatis.realWeibo7OpenCount == 0 ? "--" : parseInt(parseInt(weiboUserStatis.realWeibo7GainCount)/weiboUserStatis.realWeibo7OpenCount * 100) + "%";
			if(weiboUserStatis.self){
				weiboUserStatis.followButton ="";
			} else {
				weiboUserStatis.followButton = weiboUserStatis.beFollowed ?
						'<div class="attention addFriendBtn" flag="unFollowFlag" _userId="' +userId+'">取消关注</div>':
						'<div class="attention addFriendBtn" flag="followFlag" _userId="' +userId+'">+加关注</div>';
			}
			var html = $.mustache(userInfoBlockTpl,weiboUserStatis);
			$(document.body).append(html);
			$block = $("#user_float_dialog_div_"+userId);
			$block.mouseover(function (){ //浮框事件
				if(tHide){
					clearTimeout(tHide);
				}
				$block.show();
			}).mouseout(function (){
				tHide = setTimeout(function (){
					$("#user_float_dialog_div_"+userId).hide();
				},200);
			});
			$(".addFriendBtn",$block).click(function (){
				followAndUnFollow($(this));
			});
			$block.css("left",left + "px");
			$block.css("top",top + "px");
		});
	} else {
		$block.css("left",left + "px");
		$block.css("top",top + "px");
	}
	$("[_usercard]").hide();
	$block.show();
}
var tShow;
var tHide;
function bindMouseInHeadOrNicknameEvent ($context){
	var $target1 = $(".headpic",$context);
	$target1.mouseover(function (e){
		bindUserFloatCardMousecover($(this),e,"headpic");
	}).mouseout(function (e){
		bindUserFloatCardMouseout($(this),e,"headpic");
	});
	
	var $target2 = $('[_nickname_block="_nickname_block"]',$context);
	$target2.mouseover(function (e){
		bindUserFloatCardMousecover($(this),e,"nickname");
	}).mouseout(function (e){
		bindUserFloatCardMouseout($(this),e,"nickname");
	});
}
function bindUserFloatCardMousecover($this,e,type){
	if(tHide){
		clearTimeout(tHide);
	}
	tShow = setTimeout(function (){
		var userId = $this.attr("_userid");
		renderHeadImageInfoBlock(userId,e,type);
	},200);
}
function bindUserFloatCardMouseout($this,e,type){
	if(tShow){
		clearTimeout(tShow);
	}
	tHide = setTimeout(function (){
		var userId = $this.attr("_userid");
		$("#user_float_dialog_div_" + userId).hide();
	},200);
}