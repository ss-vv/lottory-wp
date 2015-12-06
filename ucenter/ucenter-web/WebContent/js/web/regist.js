function checkNickname(){
	var nickname = $("#nickname").val();
	if ($.trim(nickname).length === 0) {
		$("#nickname_tip").html("请输入昵称");
		return false;
	}
	if(nickname.indexOf(" ") != -1){
		$("#nickname_tip").html("不允许空格");
		return false;
	}
	//只含有汉字、数字、字母、下划线/中划线
	if(!/^[a-zA-Z0-9_\-\u4e00-\u9fa5]+$/.test(nickname)){
		$("#nickname_tip").html("非法的字符");
		return false;
	}
	if(nickname.length < 2 || nickname.length > 24){
		$("#nickname_tip").html("2-24个字符");
		return false;
	}
	return true;
}
function ajaxCheckNickname(nickname){
	$.getJSON("http://login.davcai.com/aj_chk_nickname.do?nickname=" +encodeURIComponent(nickname),function (data){
		if(data){
			$("#nickname_tip").html("");
			return true;
		} else {
			$("#nickname_tip").html("昵称重复");
			return false;
		}
	});
}
function checkUsername(){
	var username = $("#username").val();
	if ($.trim(username).length === 0) {
		$("#username_tip").html("请输入用户名");
		return false;
	}
	if(username.indexOf(" ") != -1){
		$("#username_tip").html("不允许空格");
		return false;
	}
	//只含有汉字、数字、字母、下划线不能以下划线开头和结尾
	if(!/^(?!_)(?!.*?_$)[a-zA-Z0-9_\u4e00-\u9fa5]+$/.test(username)){
		$("#username_tip").html("非法的字符");
		return false;
	}
	if(username.length < 4 || username.length > 20){
		$("#username_tip").html("4-20个字符");
		return false;
	}
	return true;
}
function ajaxCheckUsername(username){
	$.getJSON("http://login.davcai.com/aj_chkuser.do?username=" + encodeURIComponent(username),function (data){
		if(data){
			$("#username_tip").html("");
			return true;
		} else {
			$("#username_tip").html("用户名重复");
			return false;
		}
	});
}
function checkPassword(){
	var pass = $("#password").val();
	if (pass.length === 0) {
		$("#password_tip").html("请输入密码");
		return false;
	} else if(pass.length > 15 || pass.length < 6){
		$("#password_tip").html("6-15字符");
		return false;
	} else {
		$("#password_tip").html("");
		return true;
	}
}
function checkPassword2(){
	var pass2 = $("#apply_pws").val();
	if (pass2.length === 0) {
		$("#apply_pws_tip").html("请输入确认密码");
		return false;
	} else if(pass2 != $("#password").val()){
		$("#apply_pws_tip").html("密码不一致");
		return false;
	} else {
		$("#apply_pws_tip").html("");
		return true;
	}
}
function checkMail(){
	var mail = $("#mail").val();
	if ($.trim(mail).length === 0) {
		$("#mail_tip").html("请输入邮箱");
		return false;
	} 
	if (valid_email(mail) == false) {
		$("#mail_tip").html("邮箱不合法");
		return false;
	}
	return true;
}
function ajaxCheckMail(mail){
	$.getJSON("http://login.davcai.com/aj_chkemail.do?email=" +mail,function (data){
		if(data){
			$("#mail_tip").html("");
			return true;
		} else {
			$("#mail_tip").html("邮箱重复");
			return false;
		}
	});
}
function checkRealname(){
	var realname = $("#realname").val();
	if ($.trim(realname).length === 0) {
		$("#realname_tip").html("请输入真实姓名");
		return false;
	} else {
		$("#realname_tip").html("");
		return true;
	}
}
function checkMobile(){
	var mobile = $("#mobile").val();
	if ($.trim(mobile).length === 0) {
		$("#mobile_tip").html("请输入手机号码");
		return false;
	} else if(/^0?(13[0-9]|15[0-9]|18[0-9]|14[0-9]|17[0-9]|19[0-9])[0-9]{8}$/.test(mobile) == false){
		$("#mobile_tip").html("手机号码格式不正确");
		return false;
	} 
	return true;
}
function ajaxCheckMobile(mobile){
	$.getJSON("http://login.davcai.com/aj_chkmobile.do?mobile=" +mobile,function (data){
		if(data){
			$("#mobile_tip").html("");
			$.getJSON('http://login.davcai.com/aj_smscode_change.do?jsonp=?&mobile=' + mobile, function(json){
				if(null != json && null != json.data){
					alert(json.data);
			return true;
		} else {
					alert("发送失败，请重试！多次失败请联系管理员");
					return false;
				}
			});
			return true;
		} else {
			$("#mobile_tip").html("手机号码重复");
			return false;
		}
	});
}
function checkAgree(){
	if (document.getElementById("agree").checked == false) {
		$("#agree_tip").html("请阅读并同意《大V彩服务协议》");
		return false;
	} else {
		$("#agree_tip").html("");
		return true;
	}
}
$(document).ready(function() {
	//表单失去焦点事件
	$("#nickname").blur(function (e){
		if(checkNickname()){
			ajaxCheckNickname($("#nickname").val());
		}
	});
	$("#username").blur(function (e){
		if(checkUsername()){
			ajaxCheckUsername($("#username").val());
		}
	});
	$("#password").blur(function (e){
		checkPassword();
	});
	$("#apply_pws").blur(function (e){
		checkPassword2();
	});
	$("#mail").blur(function (e){
		if(checkMail()){
			ajaxCheckMail($("#mail").val());
		}
	});
//	$("#realname").blur(function (e){
//		checkRealname();
//	});
//	$("#mobile").blur(function (e){
//		if(checkMobile()){
//			ajaxCheckMobile($("#mobile").val());
//		}
//	});
	$("#agree").click(function (e){
		checkAgree();
	});
	//表单获得输入事件
	$("#nickname").keypress(function (e){
		$("#nickname_tip").html("");
	});
	$("#username").keypress(function (e){
		$("#username_tip").html("");
	});
	$("#password").keypress(function (e){
		$("#password_tip").html("");
	});
	$("#apply_pws").keypress(function (e){
		$("#apply_pws_tip").html("");
	});
	$("#mail").keypress(function (e){
		$("#mail_tip").html("");
	});
	$("#realname").keypress(function (e){
		$("#realname_tip").html("");
	});
	$("#mobile").keypress(function (e){
		$("#mobile_tip").html("");
	});
});

function regist() {
	var a = checkNickname();
	var b = checkUsername();
	var c = checkPassword();
	var h = checkPassword2();
	var d = checkMail();
//	var e = checkRealname();
//	var f = checkMobile();
	var e = true;
	var f = true;
	var g = checkAgree();
	
	if( a && b && c && d && e && f && g && h){
		if($("#nickname_tip").html() != "" ||
				$("#username_tip").html() != "" ||
				$("#mail_tip").html() != ""){
			return false;
		} else {
			$("#regist_button").attr("disabled", true);
			$("#regist_loading").show();
			document.getElementById("registForm").submit();
		}
	} else {
		return ;
	}
}

function bindAccount() {
	window.location.href = "http://login.davcai.com/bind.do";
}

function loginBySina(redirect) {
	var url = "https://api.weibo.com/oauth2/authorize?client_id=525200372&response_type=code&scope=all&redirect_uri="
			+ redirect;
	window.location.href = url;
}

function loginByQQ(redirect) {
	var qq = "https://graph.qq.com/oauth2.0/authorize?response_type=token&client_id=101150376&scope=get_user_info,get_info,add_t,add_pic_t,get_other_info,get_fanslist,get_idollist&redirect_uri=" + redirect;
	window.location.href = qq;
}

function valid_email(email) {
	var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
	return patten.test(email);
}
