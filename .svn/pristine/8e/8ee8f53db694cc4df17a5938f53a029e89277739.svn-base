$(document).ready(function (){
	var phoneTabLi = $(".b-i-moblie-tab li").first();
	var newPhoneTabLi = $(".b-i-moblie-tab li").last();
	phoneTabLi.addClass('active');
	newPhoneTabLi.removeClass('active');
	
	var phoneTabLiForm = $(".b-i-moblie-test-form");
	var newPhoneTabLiForm = $(".b-i-moblie-modify-form");

	phoneTabLiForm.show();	
	newPhoneTabLiForm.hide();	
	if(phoneTabLiForm.length > 0 && newPhoneTabLiForm.length > 0){
		phoneTabLi.click(function(e) {
			phoneTabLi.addClass('active');
			newPhoneTabLi.removeClass('active');
			phoneTabLiForm.show();	
			newPhoneTabLiForm.hide();	
		});
		
		newPhoneTabLi.click(function(e) {
			phoneTabLi.removeClass('active');
			newPhoneTabLi.addClass('active');
			phoneTabLiForm.hide();	
			newPhoneTabLiForm.show();	
		});
	}
	
	$("#send_code_btn").click(function (){
		$("#send_code_btn").attr("disabled",true);
		var phone = $.trim($("#currentPhone").val());
		var patten = new RegExp(/^\d{11}$/);
		if(patten.test(phone)){
			$.getJSON('http://login.davcai.com/aj_smscode.do?jsonp=?&mobile=' + phone, function(json){
				$("#send_code_btn").attr("disabled",false);	
				if(null != json && null != json.data){
					alert(json.data);
					return true;
				} else {
					alert("发送失败，请重试！多次失败请联系管理员");
					return false;
				}
			});
		} else {
			$("#send_code_btn").attr("disabled",false);
			alert("手机号码格式不正确");
			return false;
		}
	});
	
	$("#new_send_code_btn").click(function (){
		$("#new_send_code_btn").attr("disabled",true);
		var phone = $("#new_phone").attr("value");
		$("#new_phone_hidden").attr("value",phone);
		var patten = new RegExp(/^\d{11}$/);
		if(patten.test(phone)){
			$.getJSON('http://login.davcai.com/aj_smscode_change.do?jsonp=?&mobile=' + phone, function(json){
				$("#new_send_code_btn").attr("disabled",false);
				if(null != json && null != json.data){
					alert(json.data);
					return true;
				} else {
					alert("发送失败，请重试！多次失败请联系管理员");
					return false;
				}
			});
		} else {
			$("#new_send_code_btn").attr("disabled",false);
			alert("手机号码格式不正确");
			return false;
		}
	});
	
	$("#verify_phone_btn").click(function (){
		$("#verify_phone_btn").attr("disabled",true);
		var code = $("#verify_code").attr("value");
		var patten = new RegExp(/^[0-9a-zA-Z]{6}$/);
		if(patten.test(code)){
			$.getJSON('http://login.davcai.com/weibo_mobile.do?jsonp=?&code=' + code, function(json){
				$("#verify_phone_btn").attr("disabled",false);
				if(null != json && null != json.data){
					alert(json.data);
					if(json.success == true){
						window.location.href = location.href + "?reload=reload";
					}
				} else {
					alert("验证失败，请重试");
					return false;
				}
			});
		} else {
			$("#verify_phone_btn").attr("disabled",false);
			alert("验证码格式不正确");
			return false;
		}
	});
	
	$("#new_verify_phone_btn").click(function (){
		$("#new_verify_phone_btn").attr("disabled",true);
		var code = $("#new_verify_code").attr("value");
		var phone = $("#new_phone_hidden").attr("value");
		var patten = new RegExp(/^[0-9a-zA-Z]{6}$/);
		if(patten.test(code)){
			$.getJSON('http://login.davcai.com/weibo_mobilechange.do?jsonp=?&code=' + code + '&newMobile=' + phone, function(json){
				$("#new_verify_phone_btn").attr("disabled",false);
				if(null != json && null != json.data){
					alert(json.data);
					if(json.success == true){
						if(location.href.indexOf('?') > 0){
							window.location.href = location.href + "&reload=reload";
						} else {
							window.location.href = location.href + "?reload=reload";
						}
					}
				} else {
					alert("验证失败，请重试");
					return false;
				}
			});
		} else {
			$("#new_verify_phone_btn").attr("disabled",false);
			alert("验证码格式不正确");
			return false;
		}
	});
});