$(document).ready(function (){
	$("#new_verify_email_btn").click(function (){
		$("#new_verify_email_btn").attr("disabled",true);
		var email = $("#new_email").attr("value");
		if(!email){
			email = $.trim($("#currentEmail").text());
		}
		var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
		if(patten.test(email)){
			$.getJSON('http://login.davcai.com/weibo_email.do?jsonp=?&email=' + email, function(json){
				$("#new_verify_email_btn").attr("disabled",false);
				alert("邮箱：" + email + "\n" + json.data);
				return false;
			});
		} else {
			$("#new_verify_email_btn").attr("disabled",false);
			alert("邮箱：" + email + "格式不正确");
		}
		return false;
	});
});