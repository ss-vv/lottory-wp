$(document).ready(function() {
	$("#modify_pwd_btn").click(function(event) {
		$("#modify_pwd_btn").attr("disabled", true);
		if ($("#old_pwd_err").html() != "") {
			$("#modify_pwd_btn").attr("disabled", false);
			return false;
		}
		if ($("#new_pwd_err").html() != "") {
			$("#modify_pwd_btn").attr("disabled", false);
			return false;
		}
		if ($("#new_pwds_err").html() != "") {
			$("#modify_pwd_btn").attr("disabled", false);
			return false;
		}
		var options = {
			url : "/ajax_modify_password",
			type : "POST",
			dataType : "json",
			success : function(result) {

				alert(result.desc);

				$("#modify_pwd_btn").attr("disabled", false);
				$("#oldPassword").val("");
				$("#newPassword").val("");
				$("#confirmPassword").val("");
			}
		};
		$("#settingPasswordForm").ajaxSubmit(options);
	});

	$("#oldPassword").blur(function(event) {
		$this = $(this);
		if ($this.val() == "") {
			$("#old_pwd_err").html("请输入密码");
		} else {
			$("#old_pwd_err").html("");
		}
	});
	$("#newPassword").blur(function(event) {
		$this = $(this);
		if ($this.val() == "") {
			$("#new_pwd_err").html("请输入新密码");
		} else {
			$("#new_pwd_err").html("");
		}
	});
	$("#confirmPassword").blur(function(event) {
		$this = $(this);
		if ($this.val() == "") {
			$("#new_pwds_err").html("请输入确认密码");
		} else {
			$("#new_pwds_err").html("");
		}
		if ($("#newPassword").val() != $this.val()) {
			$("#new_pwds_err").html("输入密码不一致");
		} else {
			$("#new_pwds_err").html("");
		}
	});
});