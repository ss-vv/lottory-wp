$(function(){
	
	$("#putOn").click(function(){
		
		commons("on");
	})
	$("#getOff").click(function(){
		
		commons("off");
	})
})

 function commons(type){
	   var c=$("#recommendForm input[type='checkbox']:checked");
	   var l=c.size();
	    if(l==0){
	    	
	    	alert("请选择一场或多场赛事！");
	    	return;
	    }
	    var b="";
	    for(var i=0;i<c.length;i++){
	    	
	    	var ch=$(c[i]).attr("rstatus");
	    	if((type=="on"&&ch==0)||(type=="off"&&ch==-1)){
	    		
	    		b=false;
	    		alert("请选择合适的赛事");
	    		return;
	    	}
	    	
	    }
	    $("#recommendForm").attr("method","post");
	    var ft=$("#recommendForm").attr("ftype");
	    var action="";
	    if(type=="on"){
	    	action="/weibo/putOn.do?matchType="+ft;
	    	
	    }else if(type=="off"){
	    	
	    	action="/weibo/getOff.do?matchType="+ft;
	    }
	    $("#recommendForm").attr("action",action);
	    $("#recommendForm").submit();
	    

}