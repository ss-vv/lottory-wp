$(function (){
	window.document.domain="davcai.com";
	var frameId = window.frameElement && window.frameElement.id || '';

	$("a[_matchfloatcardtype]").each(function (){
		$(this).attr("iframeid",frameId);
	});
	function reSizeParentIframe(id) {
        var $iframe = $("#" + id,window.parent.document);
        if($iframe.length > 0){
	        var realHeight = 0;
	 	    w = $iframe[0].contentWindow;
	    	if (navigator.userAgent.indexOf("Firefox") > 0 || navigator.userAgent.indexOf("Mozilla") > 0 || navigator.userAgent.indexOf("Safari") > 0 || navigator.userAgent.indexOf("Chrome") > 0) { // Mozilla, Safari,Chrome, ...  
	 	        realHeight = w.document.documentElement.offsetHeight + 1; 
	 	    } else if (navigator.userAgent.indexOf("MSIE") > 0) { // IE  
	 	        var bodyScrollHeight = w.document.body.scrollHeight + 21; //取得body的scrollHeight  
	 	        var elementScrollHeight = w.document.documentElement.scrollHeight + 1; //取得documentElement的scrollHeight  
	 	        realHeight = Math.max(bodyScrollHeight, elementScrollHeight); //取当中比较大的一个  
	 	    } else {//其他浏览器  
	 	        realHeight = w.document.body.scrollHeight + w.document.body.clientHeight + 1;
	 	    }
	    	$iframe.height(realHeight - 10);
        }
	}
	
	//reSizeParentIframe(frameId);
	
	parent.bindMouseInMatch$StringEvent($("body"));
	

	function getElementPosition(el) { 
		var obj=el,offset=new Object(),x=0,y=0; 
		while(obj&&obj.tagName != "BODY") { 
			x+=obj.offsetLeft; 
			y+=obj.offsetTop; 
			obj=obj.offsetParent;
		}
		offset.x=x;
		offset.y=y;
		return offset; 
	}
	var table = $(".list-paper");
	var offset = getElementPosition(table[0]);
	var y = offset.y + table.height()-$(".tie-mark-recommand").height();
	$(".tie-mark-recommand").css("top",y);
});