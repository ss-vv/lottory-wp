var shareWindow;
var shareContent;
var shareLink;
var shareContentRemainingWordsLabel;
var shareContentRemainingWords = 0;	// 分享内容剩余字数

function checkShareContent() {
	shareContentRemainingWords =  140 - shareContent.val().length;
	shareContentRemainingWordsLabel.html("还可输入<em style='color:red;'>" +  shareContentRemainingWords + "</em>字");
}

$(document).ready(function() {
	shareWindow = $("#shareModal"); 								// 分享窗口
	shareContent = $("#shareContent"); 								// 分享内容
	shareContentRemainingWordsLabel = $("#shareContentRemainingWords"); 
	
	// 计算剩余字数
	shareContent.blur(checkShareContent);
	shareContent.focus(checkShareContent);
	shareContent.keyup(checkShareContent);
	
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
		var sinaCheck = $("#sinaWeiboCheck").attr("checked");
		var qqCheck = $("#qqConnectCheck").attr("checked");
		if(sinaCheck != 'checked' && qqCheck != 'checked'){
			$("#tipBindInfo").html("请选择你想分享的网站");
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
});

function bindShareClick(targetWeiboList){
	// 弹出分享窗口
	$('a[_n="share"]', targetWeiboList).click(function (event, a, b) {
		var $this = $(this);
		shareLink = $(this);
		var _postId = $this.attr("_postId");
		var _weiboLink = $this.attr("_share_link");
		
		var weiboLiDOM = $("#post_" + _postId);
		var content = $.trim($("[_n='content'][_sourceDiv]",weiboLiDOM).text()); 
		var tail = " http://www.davcai.com" + _weiboLink + " (分享自@大V彩)";
		var beyondLength = content.length + tail.length - 140;
		if(beyondLength > 0){
			content = content.substr(0,content.length-beyondLength -1) + "…";
		}
		shareContent.val(content + tail);
		shareWindow.modal('show');
		checkShareContent();
		shareContent.focus();
		
		WB_API.addAtWhoEvent($("#shareContent"));
	});
}
