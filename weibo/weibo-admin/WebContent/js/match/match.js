$(function(){
	
	$("#addrecommend").click(function(){
		
		getval();
		
	})

})

function getval(){
	
    var l=$("#matchForm input[type='checkbox']:checked").size();
    if(l==0){
    	
    	alert("请选择一场或多场推荐！");
    	return;
    }
    if(!confirm("确定要推荐吗？")){
    	return;
    }
    var type=$("#matchForm").attr("ftype");
    if(type){
    	
    	$("#matchForm").attr("method","post");
    	$("#matchForm").attr("action","/weibo/addRecommend.do?matchType="+type);
    	$("#matchForm").submit();
    }
    
}