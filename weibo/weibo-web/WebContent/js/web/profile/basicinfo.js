$(document).ready(function() {
	function checkNickname(nickname){
		if ($.trim(nickname).length === 0) {
			$("#nickname_tip").html("请输入昵称");
			return false;
		}
		if(nickname.indexOf(" ") != -1){
			$("#nickname_tip").html("不允许空格");
			return false;
		}
		if(/[~#^$@%&!*！《》｛｝{}\/\\]/gi.test(nickname)){
			$("#nickname_tip").html("非法的字符");
			return false;
		}
		if(nickname.length < 2 || nickname.length > 10){
			$("#nickname_tip").html("2-10个字符");
			return false;
		}
		return true;
	}
	
	// 保存用户信息
	$("#save_profile_btn").click(function(event) {

		var $this = $(this);
		if (checkNickname($.trim($("#nickname").val())) == false) {
			$("#nickname").focus();
			return false;
		}
		$("#nickname_tip").html("");
		
		var par = $("#setting_user_profile_form").serialize();
		$this.attr("disabled", true);
		$.post("/ajax_save_profile", par, function(result, e) {
			if (result != null) {
				alert(result.desc);
			}
			$this.attr("disabled", false);
		}, 'json').error(function(e) {
			$.showMessage("操作失败，请刷新页面重试！");
			$this.attr("disabled", false);
		});
	});
});