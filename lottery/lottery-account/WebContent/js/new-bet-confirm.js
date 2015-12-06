$(document).ready(function (){
	$.getJSON('http://login.davcai.com/ajax_get_weibouser.do?jsonp=?', function(data){
		if (data != null && data.weiboUser) {
			var weiboUser = data.weiboUser;
			$('#balance').prepend('<font>'+weiboUser.nickName+'，您的账户余额:<i class="color-red">￥'+weiboUser.freeMoney+'</i>元</font>');
		}
	}, 'json');
	
	$("#confirmOrder").click(function() {
		var editorContent = _wbEditor.getContent();
		$("input[name='showSchemeSlogan']").val(editorContent);
		$("#betconfirm").submit();
	});
});