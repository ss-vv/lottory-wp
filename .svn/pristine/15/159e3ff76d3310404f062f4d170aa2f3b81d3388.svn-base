function login() {
	var username = $.trim($("#username").val());
	var password = $.trim($("#password").val());
	var rememberMe = $("#remember_me").attr("checked");
	if(username == null || username == ""){
		alert("请输入用户名");
		$("#username").focus();
		return false;
	}
	if(password == null || password == ""){
		alert("请输入密码");
		$("#password").focus();
		return false;
	}
	if(rememberMe){
		$.cookie('username', username,{ expires: 90 });
	} else {
		$.cookie('username', null);
	}
	$("#loginForm").attr("action",
			"http://login.davcai.com/login.do");
	$("#loginForm").attr("method", "post");
	$("#loginForm").submit();
}

function loginBySina(redirect) {
	var url = "https://api.weibo.com/oauth2/authorize?client_id=525200372&response_type=code&scope=all&redirect_uri=" + redirect;
	window.location.href = url;
}
function loginByAlipay(redirect) {
	var url = "https://openauth.alipay.com/oauth2/authorize.htm?client_id=2014090100010261&redirect_uri=" + redirect;
	window.location.href = url;
}

function loginByQQ(redirect) {
	var qq = "https://graph.qq.com/oauth2.0/authorize?response_type=token&client_id=101150376&scope=get_user_info,get_info,add_t,add_pic_t,get_other_info,get_fanslist,get_idollist&redirect_uri=" + redirect;
	window.location.href = qq;
}

function loginByWeixin(redirectUrl){
	if(!redirectUrl){
		redirectUrl = 'http://www.davcai.com/weixin_login';
	}
	window.location.href='https://open.weixin.qq.com/connect/qrconnect?appid=wx8a684cf1f5b6a359&scope=snsapi_login&redirect_uri='+redirectUrl+'&state=1418271445397&login_type=jssdk&style=white';
}

function toRegistPage(referer,failReturnURL) {
	var registUrl = "http://login.davcai.com/regist.do";
	window.location.href = registUrl + "?referer=" + referer + "&failReturnURL=" + failReturnURL;
}

function logout(referer){
	window.location.href= "http://login.davcai.com/sso/logout.do?referer=" + referer;
}

function addNickname(){
 	if(jQuery("#nickname").val().length === 0 ) {
        alert("请给自己取一个昵称吧");
		return false;
    } 
    document.getElementById("add_nickname_form").submit();
}