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
	$.getJSON("http://login.davcai.com/aj_chk_nickname.do?nickname=" + encodeURIComponent(nickname),function (data){
		if(data){
			$("#nickname_tip").html("");
			return true;
		} else {
			$("#nickname_tip").html("昵称重复");
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

	$("#agree").click(function (e){
		checkAgree();
	});
	//表单获得输入事件
	$("#nickname").keypress(function (e){
		$("#nickname_tip").html("");
	});
	$("#bind_username").keypress(function (e){
		$("#bind_username_tip").html("");
	});
	$("#bind_password").keypress(function (e){
		$("#bind_password_tip").html("");
	});
	$("#bind_username").blur(function (e){
		$("#bind_username_tip").html("");
	});
	$("#bind_password").blur(function (e){
		$("#bind_password_tip").html("");
	});
});

var activeStyle = 'border: 1px solid #DDD;border-bottom: none;border-radius: 8px 8px 0 0;background: #EEE;display: inline-block;width: 120px;color: black;text-align: center;font-size: 15px;';
var notActiveStyle = 'color: black;';
function regist() {
	var a = checkNickname();
	var g = checkAgree();
	
	if( a && g){
		if($("#nickname_tip").html() != ""){
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
function toBindLCAccount() {
	window.location.href = "http://login.davcai.com/bind_account.do?referer=$!referer&failReturnURL=$!failReturnURL";
}


function bind() {
	$("#bind_username_tip").html("");
	$("#bind_password_tip").html("");
	var username = $("#bind_username").val();
	if (username.length === 0) {
		$("#bind_username_tip").html("请填写用户名");
		return false;
	}

	var password = $("#bind_password").val();

	if (password.length === 0) {
		$("#bind_password_tip").html("请填写密码");
		$("#bind_password_tip").css("margin-right","-15px");
		return false;
	}
	$("#bind_button").attr("disabled",true);
	$.getJSON('http://login.davcai.com/bind_account.do',$("#bindForm").serialize(),function (data){
		if (data.success) {
			form = $("<form></form>");
			form.attr('action','http://login.davcai.com/login.do');
			form.attr('method', 'post');
			input1 = $("<input type='hidden' name='username' />");
			input1.attr('value', username);
			input2 = $("<input type='password' name='password' />");
			input2.attr('value', password);
			input3 = $('<input type="checkbox" name="rememberMe" checked="checked"/>');
			input4 = $('<input type="hidden" name="referer" value="http://www.davcai.com/index"/>');
			input5 = $('<input type="hidden" name="failReturnURL" value="http://www.davcai.com/welcome"/>');
			
			form.append(input1);
			form.append(input2);
			form.append(input3);
			form.append(input4);
			form.append(input5);
			form.submit();
		} else {
			$("#bind_button").attr("disabled",false);
			if(data.desc.indexOf("用户") != -1){
				$("#bind_username_tip").html(data.desc);
			} else {
				$("#bind_password_tip").html(data.desc);
				if(data.desc.length > 5){
					$("#bind_password_tip").css("margin-right","-60px");
				} else {
					$("#bind_password_tip").css("margin-right","-15px");
				}
			}
		}
	});
	
}
$(document).ready(function() {
	$("#applyTab").attr("class", "active");
	$("#loginTab").attr("class", "");
	$("#applyTab").attr("style", activeStyle);
	$("#loginTab").attr("style", notActiveStyle);
	$("#regist_form").show();
	$("#bind_form").hide();

	$("#applyTab").click(function(e) {
		$("#applyTab").attr("class", "active");
		$("#loginTab").attr("class", "");
		$("#applyTab").attr("style", activeStyle);
		$("#loginTab").attr("style", notActiveStyle);
		$("#regist_form").show();
		$("#bind_form").hide();
	});
	$("#loginTab").click(function(e) {
		$("#applyTab").attr("class", "");
		$("#loginTab").attr("class", "active");
		$("#applyTab").attr("style", notActiveStyle);
		$("#loginTab").attr("style", activeStyle);
		$("#regist_form").hide();
		$("#bind_form").show();
	});
});