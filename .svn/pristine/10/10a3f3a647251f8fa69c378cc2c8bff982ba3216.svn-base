$(document).ready(function() {
	$('#send-email-btn').click(function(event){
		var $this = $(this);
		$(this).attr("disabled",true);
		$("#message").html("");
		var email=$('#email').val();
		if(valid_email(email)) {
			$(".loading").attr("style","width: 50px;height: 50px;margin-left:90px;display:true");
			$.ajax({
				type : "get",
				async:false,
				url : "http://login.davcai.com/getpwd_send_email.do?email=" + email,
				dataType : "jsonp",
				jsonp: "callbackFunName",//服务端用于接收callback调用的function名的参数
				jsonpCallback:"success_jsonpCallback",//callback的function名称
				success : function(result){
					if(result[0].success == 'false'){
						$this.attr("disabled",false);
					}
					$(".loading").hide();
					$("#message").html(result[0].desc);
				},
				error:function(){
					console.error('忘记密码功能出现系统错误');
				}
			});
			return true;  
		} else {
			$(this).attr("disabled",false);
			$("#message").html("email 格式不正确");
			return false;
		}
	});
	
	$('#email').click(function(event){
		if($.trim($("#message").html()) != ""){
			$('#email').val("");
			$("#message").html("");
		}
	});
});

function valid_email(email) {
	var patten = new RegExp(/^[\w-]+(\.[\w-]+)*@([\w-]+\.)+[a-zA-Z]+$/);
	return patten.test(email);
}