$(document).ready(function (){
	
	$("#acc_modify_pwd_btn").click(function(event) {
		$("#acc_modify_pwd_btn").attr("disabled", true);
		if ($("#acc_old_pwd_err").html() != "") {
			$("#acc_modify_pwd_btn").attr("disabled", false);
			return false;
		}
		if ($("#acc_new_pwd_err").html() != "") {
			$("#acc_modify_pwd_btn").attr("disabled", false);
			return false;
		}
		if ($("#acc_new_pwds_err").html() != "") {
			$("#acc_modify_pwd_btn").attr("disabled", false);
			return false;
		}
		var options = {
			url : "/ajax_modify_acc_pwd",
			type : "POST",
			dataType : "json",
			success : function(result) {
				alert(result.desc);
				$("#acc_modify_pwd_btn").attr("disabled", false);
				$("#accOldPassword").val("");
				$("#accNewPassword").val("");
				$("#accConfirmPassword").val("");
			}
		};
		$("#accountPasswordForm").ajaxSubmit(options);
	});

	$("#accOldPassword").blur(function(event) {
		$this = $(this);
		if ($this.val() == "") {
			$("#acc_old_pwd_err").html("请输入密码");
		} else {
			$("#acc_old_pwd_err").html("");
		}
	});
	$("#accNewPassword").blur(function(event) {
		$this = $(this);
		if ($this.val() == "") {
			$("#acc_new_pwd_err").html("请输入新密码");
		} else {
			$("#acc_new_pwd_err").html("");
		}
	});
	$("#accConfirmPassword").blur(function(event) {
		$this = $(this);
		if ($this.val() == "") {
			$("#acc_new_pwds_err").html("请输入确认密码");
		} else {
			$("#acc_new_pwds_err").html("");
		}
		if ($("#accNewPassword").val() != $this.val()) {
			$("#acc_new_pwds_err").html("输入密码不一致");
		} else {
			$("#acc_new_pwds_err").html("");
		}
	});
});