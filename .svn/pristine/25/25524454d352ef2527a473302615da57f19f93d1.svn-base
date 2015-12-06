$(document).ready(function(){
   $("#head-search").keydown(function(){
		if($.trim($(this).val()) != ''){
			$(".searching-dropdown-menu").show("500");
		}else{
			$(".searching-dropdown-menu").hide("500");
		}
	});
	
	$("#head-search").focus(function(){
		$("#head-search").css("color","#000");
		if($.trim($(this).val()) != ''){
			$(".searching-dropdown-menu").show("500");
		}
	});
	
	$("#head-search").blur(function(){
		$(".searching-dropdown-menu").hide("500");
		$("#head-search").css("color","#A8A8A8");
	});
});

/*上面这段是搜索框下拉菜单部分*/

