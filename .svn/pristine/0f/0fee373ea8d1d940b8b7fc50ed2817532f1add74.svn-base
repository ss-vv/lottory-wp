$(document).ready(function() {
	$("#next-btn").click(function (){
		$(this).attr("disabled",true);
		var userIds = new Array();
		var lis = $(".attention-lottery-people-list li");
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
					window.location.href='http://www.davcai.com/home';
				}
			}, 'json');
		} else {
			window.location.href='http://www.davcai.com/home';
		}
	});
	
	$("#check-all-btn").click(function (){
		var className = $(this).attr("class");
		if(className == "checkbox-all-selected"){
			$(this).attr("class","checkbox-all-not-selected");
			$(".attention-lottery-people-list li").each(function (){
				$(this).find('div.attention-lottery-people-logo span').attr("class","checkbox-not-selected");
				$(this).attr("_checked","false");
			});
		} else {
			$(this).attr("class","checkbox-all-selected");
			$(".attention-lottery-people-list li").each(function (){
				$(this).find('div.attention-lottery-people-logo span').attr("class","checkbox-selected");
				$(this).attr("_checked","true");
			});
		}
	});
	
	$(".attention-lottery-people-list li").each(function (){
		$(this).find('div.attention-lottery-people-logo').click(function (){
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
			var lis = $(".attention-lottery-people-list li");
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
