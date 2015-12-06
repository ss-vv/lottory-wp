$(document).ready(function (){
	for (var i = 1; i < 101; i++) {
		$('[name="codeMaxSize"]').append('<option value="'+i+'">'+i+'</option>');
	}
	for (var i = 1; i < 30; i++) {
		$('[name="expiryDay"]').append('<option value="'+i+'">'+i+'</option>');
	}
	$('[name="expiryDay"]').append('<option value="'+500000+'">无限期</option>');
	$('#generateCode').bind('click', function(){
		var vid=$("#genvid").val();
		var name=$("#genvid").find("option[value='"+vid+"']").text();
		var size = $("#codeMaxSize").val();
        if(confirm('确认为大V用户：'+name+'  生成  '+size+"  个邀请码吗？")){
        	$("#gForm").submit();
        }
    });
});