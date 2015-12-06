function displayLogin() {
	$("#pop-outer").fadeIn(800);
	$("#all").show();
}
//为点击加关注准备
var logind=false;
function  isLoginForAddBtn(){
	$.getJSON('http://login.davcai.com/loadmsg.do?jsonp=?', function(json) {
		if (json.success && json.data.id > 0) {
			// 获取微博信息,并填充至html片段
			$.getJSON('http://login.davcai.com/ajax_get_weibouser.do?jsonp=?',
					function(data) {
						if (data == null) {
							logind=false;
						} else {
							logind=true;
						}
					}, 'json');
		} else {
			logind=false;
		}
	});
}
function login_() {
	var username = $.trim($("#username_").val());
	var password = $.trim($("#password_").val());
	var rememberMe = $("#remember_me_").attr("checked");
	if (username == null || username == "") {
		alert("请输入用户名");
		$("#username_").focus();
		return false;
	}
	if (password == null || password == "") {
		alert("请输入密码");
		$("#password_").focus();
		return false;
	}
	if (rememberMe) {
		$.cookie('username', username, {
			expires : 30
		});
	} else {
		$.cookie('username', null);
	}
	$("#loginForm_").attr("action", "http://login.davcai.com/login.do");
	$("#loginForm_").attr("method", "post");
	$("#loginForm_").submit();
}
function alipayLogin(){
	location.href="/alipayLogin.do?referer=http://www.davcai.com/index";
}