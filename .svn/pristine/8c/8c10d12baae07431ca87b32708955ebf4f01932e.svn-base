$(function() {
	$(".reply").click(function() {
		var editor = $(this).parent().next();
		if(0 != editor.length) {
			if(editor.hasClass("hide")) {
				editor.removeClass("hide");
				editor.addClass("show");
			} else {
				editor.removeClass("show");
				editor.addClass("hide");
			}
		} else {
			var editor = $('<div class="editor"></div>');
			var replyForm = $('<form action="http://www.davcai.com" method="POST"></form>');
			
			var face  = $('<span title="插入表情" id="message_face" class="face"><s></s><span>表情</span></span>');
			var match = $('<span title="过滤赛事" class="match"><s></s><span>赛事</span></span>');
			var toHome = $('<span class="to-home"><input type="checkbox" checked="checked"/>同时转发到我的首页 </span>');
			var emotion = $('<div class="emotion"></div>');
			emotion.append(face).append(match).append(toHome);
			var emotionArea = $('<div id="emotionArea"><textarea class="text" id="status-box" name="自定义"></textarea></div>');
			var editorWrapper = $('<div class="editor-wrapper" id="publish"></div>');
			editorWrapper.append(emotionArea);
			editorWrapper.append(emotion);
			replyForm.append(editorWrapper);
			
			var editorFooter = $('<div class="editor-footer"></div>');
			var submitContainer = $('<div class="submit-container"></div>');
			var publish = $('<input type="button" value="发布" class="submit main-submit disabled">');
			var backContainer = $('<div class="back-container"></div><div class="footer-controller"></div>');
			submitContainer.append(publish);
			editorFooter.append(submitContainer);
			editorFooter.append(backContainer);
			replyForm.append(editorFooter);
			editor.append(replyForm);
			
			$(this).parent().after(editor);
		}
	});
});