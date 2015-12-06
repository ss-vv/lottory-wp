/**
 * 用于绑定微博的评论、转发、收藏、评论，采纳等事件操作
 * */

remainingWords = 0;
$(document).ready(function() {
	// 初始值设定
	var homeUserId = $("#ajax_login_msg").attr("_weibouserId");	// 登录用户id
	forwardWindow = $("#forwardModal"); 								// 转发窗口
	forwardContent = $("#forwardContent");								// 转发内容
	$remainingWords = $("#remainingWords");							// 显示剩余字数
	remainingWords = 0;																// 剩余字数
	
	//---------------------分享相关 开始---------------------
	shareWindow = $("#shareModal"); 								// 分享窗口
	shareContent = $("#shareContent"); 								// 分享内容
	shareLink = null;
	shareContentRemainingWordsLabel = $("#shareContentRemainingWords"); 
	shareContentRemainingWords = 0;	// 分享内容剩余字数
	
	// 计算剩余字数
	shareContent.blur(checkShareContent);
	shareContent.focus(checkShareContent);
	shareContent.keyup(checkShareContent);
});

//---------------------分享相关 开始---------------------
function checkShareContent() {
	shareContentRemainingWords =  140 - shareContent.val().length;
	shareContentRemainingWordsLabel.html("还可输入<em style='color:red;'>" +  shareContentRemainingWords + "</em>字");
}

$("#message_face_share").jqfaceedit({
	txtAreaObj:$("#shareContent"),
	containerObj:$('#emotion_icons'),
	textareaid:"shareContent",
	top:30,
	left:-40
});
$("#sinaWeiboCheck").click(function (event, a, b) {
	var $this = $(this);
	if($this.attr("checked") == 'checked'){
		$("#tipBindInfo").html("");
	}
});
// 分享微博按钮事件
$("#sharePostBtn").click(function (event, a, b) {
	if(shareContentRemainingWords < 0){
		shareContent.focus();
		return;
	}
	if($("#sinaWeiboCheck").attr("checked") != 'checked'){
		$("#tipBindInfo").html("请选勾新浪微博");
		return;
	}
	if(shareContent.val()==""){
		shareContent.focus();
		return;
	}
	var $this = $(this);
	var par = $("#shareForm").serialize();
	$this.attr("disabled",true);
	$.post("share", par, function(result, e) {
		shareWindow.modal('hide');
		if(result.success == true){
			$.msgbox(result.desc,shareLink,{success:true});
		} else {
			$.msgbox(result.desc,shareLink,{success:false});
		}
		$this.attr("disabled",false);
	},'json');
});
//---------------------分享相关 结束---------------------


function check() {
	remainingWords =  140 - forwardContent.val().length;
	$remainingWords.html("还可输入<em style='color:red;'>" +  remainingWords + "</em>字");
}

function _bindFavoriate(targetWeiboList){
	// 收藏按钮
	$("a[_n='favoriate']", targetWeiboList).click(function () {
		var $this = $(this), status = $this.attr("_s"), count = $this.attr("_count");
		if("favoriate" == status){
			$.post("addFavoriate", {postId : $this.attr("_postId")}, function(result, e) {
				if (result != null) {
					if (result.success) {
						if(result.desc=="true"){
							count++;
							$this.attr("_count",count);
						}else{
							alert('已收藏过！');
						}
						$this.text('已收藏('+count+')');
						$this.attr("_s","unfavoriate");
						$.msgbox('已收藏',$this);
					} else {
						$.showMessage(result.desc);
					}
				}
				$this.attr("disabled",false);
			}, 'json').error(function(e) {
				$.showMessage( "操作失败，请刷新页面重试！");
				$this.attr("disabled",false);
			});
		}else if("unfavoriate" == status){
			$.post("delFavoriate", {postId : $this.attr("_postId")}, function(result, e) {
				if (result != null) {
					if (result.success) {
						if (result.desc == "true") {
							count--;
							$this.attr("_count",count);
						}else{
							alert('未收藏！');
						}
						if(count < 1){
							$this.text('收藏');
						}else{
							$this.text('收藏('+count+')');
						}
						$this.attr("_s","favoriate");
					} else {
						$.showMessage(result.desc);
					}
				}
				$this.attr("disabled",false);
			}, 'json').error(function(e) {
				$.showMessage( "操作失败，请刷新页面重试！");
				$this.attr("disabled",false);
			});
		}
		return;
	});
}

//格式化表情
function emotionsToHtml() {
	var $weiboList = $(".status-list");
	$weiboList.emotionsToHtml();
}

// 绑定多种事件
function _bind(targetWeiboList){
	emotionsToHtml();
	
	// 收藏
	_bindFavoriate(targetWeiboList)
	
	// 弹出分享窗口
	$('a[_n="share"]', targetWeiboList).click(function (event, a, b) {
		var $this = $(this);
		shareLink = $(this);
		var _postId = $this.attr("_postId");
		var weiboLiDOM = $("#post_" + _postId);
		var content = $.trim($("#sharePostContent",weiboLiDOM).text()); 
		var tail = " http://trade.davcai.com/df/ (分享自@大V彩)";
		var beyondLength = content.length + tail.length - 140;
		if(beyondLength > 0){
			content = content.substr(0,content.length-beyondLength -1) + "…";
		}
		shareContent.val(content + tail);
		shareWindow.modal('show');
		checkShareContent();
		shareContent.focus();
	});
	
	// 弹出转发窗口
	$('a[_n="forward"]', targetWeiboList).click(function (event, a, b) {
		var $this = $(this);
		var _postId = $this.attr("_postId");
		$.post("showForward", {postId:_postId}, function(result, e) {
			if (result != null) {
				if (result.success) {
					var weibo = result.results[0];
					$("#postId").val(_postId);
					if(weibo.postId>0){
						forwardContent.val("​ //@" + weibo.user.nickName + ": " + weibo.content);
					}else{
						forwardContent.val("");
					}
					check();
					forwardWindow.modal('show');
					forwardContent.focus();
				} else {
					alert(result.desc);
				}
			}
		}, 'json').error(function(e) {
			alert( "操作失败，请刷新页面重试！");});
	});
	
	// 删除微博
	$('a[_n="delete"]', targetWeiboList).click(function (event, a, b) {
		if(confirm("确定删除吗？")){
			var $this = $(this);
			var _postId = $this.attr("_postId");
			$.post("ajaxDelPost", {postId:_postId}, function(result, e) {
				if (result != null) {
					if (result.success) {
						$("#post_" + _postId).remove();
					} else {
						alert(result.desc);
					}
				}
			}, 'json').error(function(e) {
				alert( "操作失败，请刷新页面重试！");});
		}
	});
	
	// 采纳
	$("a[_n='like']", targetWeiboList).click(function () {
		var $this = $(this), status = $this.attr("_s"), count = $this.attr("_count");
		if("like" == status){
			$.post("like", {postId : $this.attr("_postId")}, function(result, e) {
				if (result != null) {
					if (result.success) {
						if(result.desc=="true"){
							count++;
							$this.attr("_count",count);
							$.msgbox('已采纳',$this);
						}else{
							alert('已采纳过！');
						}
						$this.text('已采纳('+count+')');
						$this.attr("_s","unLike");
					} else {
						$.showMessage(result.desc);
					}
				}
				$this.attr("disabled",false);
			}, 'json').error(function(e) {
				$.showMessage( "操作失败，请刷新页面重试！");
				$this.attr("disabled",false);
			});
		}else if("unLike" == status){
			$.post("unLike", {postId : $this.attr("_postId")}, function(result, e) {
				if (result != null) {
					if (result.success) {
						if (result.desc == "true") {
							count--;
							$this.attr("_count",count);
						}else{
							alert('未采纳！');
						}
						if(count < 1){
							$this.text('采纳');
						}else{
							$this.text('采纳('+count+')');
						}
						$this.attr("_s","like");
					} else {
						$.showMessage(result.desc);
					}
				}
				$this.attr("disabled",false);
			}, 'json').error(function(e) {
				$.showMessage( "操作失败，请刷新页面重试！");
				$this.attr("disabled",false);
			});
		}
		return;
	});
	
	// 显示长微博编辑窗口
	$("a[_n='edit']", targetWeiboList).click(function() {
		var $this = $(this);
		var _postId = $this.attr("_postId") || 0;
		$.post("showForward", {postId:_postId}, function(result, e) {
			if (result != null) {
				if (result.success) {
					var weibo = result.results[0];
					$("#longPostId").val(weibo.id);
					$longWeiboTitle.val(weibo.title);
					editor.html(weibo.content);
					longWeiboWindow.modal('show');
				} else {
					alert(result.desc);
				}
			}
		}, 'json').error(function(e) {
			alert( "操作失败，请刷新页面重试！");});
	});
	
	// 显示评论
	$("a[_n='comment']", targetWeiboList).click(function() {
		var commentLink = $(this);
		onComment(commentLink);
	});
	
	//  分享插入表情
	$("#message_face_share").jqfaceedit({
    	txtAreaObj:$("#shareContent"),
    	containerObj:$('#emotion_icons'),
    	textareaid:"shareContent",
    	top:30,
    	left:-40
    });
	
    //  转发插入表情
    $("#message_face_forward").jqfaceedit({
    	txtAreaObj:$("#forwardContent"),
    	containerObj:$('#emotionArea'),
    	textareaid:"forwardContent",
    	top:-50,
    	left:-450
    });
}