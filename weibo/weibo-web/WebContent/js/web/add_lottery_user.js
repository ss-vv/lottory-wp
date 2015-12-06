$(document).ready(function() {
	$("#next-btn").click(function (){
		$(this).attr("disabled",true);
		var userIds = new Array();
		var lis = $(".attention-kind-list li");
		lis.each(function (){
			var uid = $(this).attr("_uid");
			var checked = $(this).attr("_checked");
			if(checked == "true"){
				userIds.push(uid);
			}
		});
		if(userIds.length > 0){
			$.ajaxSettings.traditional = true;
			$.post("/ajax_rltship_follow",{followIds:userIds}, function(data , e) {
				if (data != null) {
					window.location.href='http://www.davcai.com/add_rec_user';
				}
			}, 'json');
		} else {
			window.location.href='http://www.davcai.com/add_rec_user';
		}
	});
	
	$("#check-all-btn").click(function (){
		var className = $(this).attr("class");
		if(className == "checkbox-all-selected"){
			$(this).attr("class","checkbox-all-not-selected");
			$(".attention-kind-list li").each(function (){
				$(this).find('div.attention-kind-logo span').attr("class","checkbox-not-selected");
				$(this).attr("_checked","false");
			});
		} else {
			$(this).attr("class","checkbox-all-selected");
			$(".attention-kind-list li").each(function (){
				$(this).find('div.attention-kind-logo span').attr("class","checkbox-selected");
				$(this).attr("_checked","true");
			});
		}
	});
	
	$(".attention-kind-list li").each(function (){
		$(this).find('div.attention-kind-logo').click(function (){
			var $checkbox = $(this).find("span");
			var className = $checkbox.attr("class");
			if(className == "checkbox-selected"){
				$checkbox.attr("class","checkbox-not-selected");
				$checkbox.closest("li").attr("_checked","false");
				$("#check-all-btn").attr("class","checkbox-all-not-selected");
			} else {
				$checkbox.attr("class","checkbox-selected");
				$checkbox.closest("li").attr("_checked","true");
			}
			//判断全部选中------start
			var allCheck = true;
			var lis = $(".attention-kind-list li");
			for ( var i = 0; i < lis.length; i++) {
				var checked = $(lis[i]).attr("_checked");
				if(checked == "false"){
					allCheck = false;
				}
			}
			if(allCheck){
				$("#check-all-btn").attr("class","checkbox-all-selected");
			}
			//判断全部选中------end
		});
	});
});