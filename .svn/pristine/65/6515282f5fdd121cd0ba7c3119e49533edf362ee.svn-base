$(document).ready(function() {
	function clearTip(id){
		$("#" + id + "_tip").html("");
	}
	function addTip(id,tip){
		$("#" + id + "_tip").html(tip);
	}
	function checkIdNumber(){
		var ele = $("#idNumber");
		if(ele.length == 0){
			return true;
		}
		var id = ele.attr("id");
		var num = ele.attr("value");
		if(IdCardValidate(num) == true){
			clearTip(id);
			return true;
		} else {
			addTip(id, "身份证格式不正确");
			return false;
		}
	}
	
	function ajaxCheckIdNumber(){
		var ele = $("#idNumber");
		if(ele.length == 0){
			return true;
		}
		$.getJSON("/ajax_check_idnumber?newIdNumber=" + ele.attr("value"),function (data){
			var id = ele.attr("id");
			if(data.success){
				clearTip(id);
				return true;
			} else {
				addTip(id, data.desc);
				return false;
			}
		});
	}
	
	function checkBankNumber(){
		var reg = /^\d+$/;
		var ele = $("#bank_number");
		var id = ele.attr("id");
		var num = ele.attr("value");
		if(reg.test(num) == true){
			clearTip(id);
			return true;
		} else {
			addTip(id, "银行卡号格式不正确");
			return false;
		}
	}
	function checkPassword(){
		var ele = $("#tx_password");
		var id = ele.attr("id");
		var val = ele.attr("value");
		if(val.length > 16 || val.length < 6){
			addTip(id, "提现密 码长度为6-16位字符");
			return false;
		} else {
			clearTip(id);
		}
		var ele2 = $("#tx_password2");
		if(ele2.length>0){
			var id2 = ele2.attr("id");
			var val2 = ele2.attr("value");
			if(val != val2){
				addTip(id2, "密码不一致");
				return false;
			} else {
				clearTip(id2);
			}
		}
		return true;
	}
	function checkBank(){
		var ele = $("#bank");
		var id = ele.attr("id");
		var val = ele.attr("value");
		if(val.length > 1){
			clearTip(id);
			return true;
		} else {
			addTip(id, "开户行不允许为空");
			return false;
		}
	}
	function checkRealname(){
		var ele = $("#realname");
		if(ele.length == 0){
			return true;
		}
		var ele = $("#realname");
		var id = ele.attr("id");
		var val = ele.attr("value");
		if(val.length > 1){
			clearTip(id);
			return true;
		} else {
			addTip(id, "真实姓名不允许为空");
			return false;
		}
	}
	function checkCity(){
		var provinceEle = $("#province");
		var cityEle = $("#city");
		var id = cityEle.attr("id");
		var pro = provinceEle.attr("value");
		var cit = cityEle.attr("value");
		if(pro == "" || cit == ""){
			addTip(id, "请选择地区");
			return false;
		} else {
			clearTip(id);
			return true;
		}
	}
	$("#idNumber").blur(function (){
		if(checkIdNumber()){
			ajaxCheckIdNumber();
		}
	});
	$("#bank_number").blur(function (){
		checkBankNumber();
	});
	$("#tx_password").blur(function (){
		checkPassword();
	});
	$("#bank").blur(function (){
		checkBank();
	});
	$("#city").change(function (){
		checkCity();
	});
	$("#realname").blur(function (){
		checkRealname();
	});
	$("#tx_password2").blur(function (){
		checkPassword();
	});
	
	$("#submit_form_a").click(function (){
		$(this).attr("disabled",true);
		var $bankInfoForm = $("#setting_user_account_form");
		var f = checkRealname();
		var a = checkIdNumber();
		var b = checkCity();
		var c = checkBank();
		var d = checkBankNumber();
		var e = checkPassword();
		if(a && b && c && d && e && f){
			$("#edit_bank_loading").show();
			$.ajax({
				  type: "POST",
				  url: $bankInfoForm.attr("action"),
				  data: $bankInfoForm.serialize(),
				  success: function (data){
					  $("#edit_bank_loading").hide();
					  $("#submit_form_a").attr("disabled",false);
					  $("#tx_password").attr("value","");
					  if(data.success){
						  alert("提交成功");
						  window.location.href='http://www.davcai.com/bankinfo';
					  } else {
						  alert(data.desc);  
					  }
				  },
				  dataType: "json"
			});
		} else {
			$(this).attr("disabled",false);
		}
		return false;
	});
	
});