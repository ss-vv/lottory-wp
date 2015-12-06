var atWindow;
var atContent;
var atContentRemainingWordsLabel;
var atContentRemainingWords = 0;	// at内容剩余字数

function checkAtContent() {
	var atVal = atContent.val();
	if(atVal) {
		atContentRemainingWords =  999 - atContent.val().length;
	} else {
		atContentRemainingWords =  999;
	}
	atContentRemainingWordsLabel.html("还可输入<em style='color:red;'>" +  atContentRemainingWords + "</em>字");
}

$(document).ready(function() {
	atWindow = $("#atModal"); 								// @窗口
	atContent = $("#atContent"); 
	atContentRemainingWordsLabel = $("#atRemainingWords");
	
	// 计算剩余字数
	atContent.blur(checkAtContent);
	atContent.focus(checkAtContent);
	atContent.keyup(checkAtContent);
	
	$("#message_face_at").jqfaceedit({
    	txtAreaObj:$("#atContent"),
    	containerObj:$('#emotion_icons'),
    	textareaid:"atContent",
    	top:30,
    	left:-40
    });
	
	bindAtClick("#atBtn");
	
	$("#publishAtBtn").click(function (){
		if(atContentRemainingWords < 0){
			atContent.focus();
			return;
		}
		
		if(atContent.val()=="" || atContent == ""){
			atContent.focus();
			return;
		}
		if(atContent.val().replace(/(^\s*)|(\s*$)/g, "") == "@大V彩小助手") {
			atContent.focus();
			$.msgbox("您还没有对 “大V彩小助手” 说点什么呢！");
			return;
		}
		
		var $this = $(this);
		var par = $("#atForm").serialize();
		$this.attr("disabled",true);
		$.getJSON("http://www.davcai.com/publish?callback=?", par, function(result, e) {
			atWindow.modal('hide');
			if(result.success == true){
				$.msgbox("发送成功");
			} else {
				$.msgbox("发送失败");
			}
			$this.attr("disabled",false);
		},'json');
	});
	
	function bindAtClick(id){
		// 弹出@窗口
		$(id).click(function (event) {
			var $this = $(this);
			if( $this.attr("isLogin") == "false"){
				$("#pop-outer").fadeIn(500);
				$("#all").show();
				return ;
			}
			var nickname = $this.attr("nickname");
			if(!nickname) {
				nickname = "";
			}
			atContent.val("@" + nickname + " ");
			atWindow.modal('show');
			checkAtContent();
			atContent.focus();
			WB_API.addAtWhoEvent($("#atContent"));
			//绑定@事件
    		WB_API.addNickNameAtWho($("#atContent")[0]);
    		//绑定点击赛事按钮事件
    		WB_API.showMatchAreaDelegateEventsWith($("#atContent").closest("form"));
		});
	}
});
