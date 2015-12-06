$(function(){
	
	$('#registForm').bind('submit', function(){
		if($("#registForm input[accept]")[0].checked  == false){
			alert('请阅读并接受《服务条款》');
			return false;
		}
	});
	
	$('#changeCode').bind('click', function(){
		$("#validCodeImg").attr("src", "/water.do?t=" + (new Date()).getTime());
		return false;
	});
	
});