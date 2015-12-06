
$(function (){
	window.document.domain="davcai.com";
	var frameId = window.frameElement && window.frameElement.id || '';
	$("a[_matchfloatcardtype]").each(function (){
		$(this).attr("iframeid",frameId);
	});
	//reSizeParentIframe(frameId);
	parent.bindMouseInMatch$StringEvent($("body"));
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
		$iframe.height(realHeight + 20);
    }
}

function acceptRecommend(lotteryId,playType,schemaId){
	var url = 'http://trade.davcai.com/df/bet/';
	url +=  lotteryId.toLowerCase() + "_" + playType.toLowerCase() + ".html?";
	url += 'schemeId=' + schemaId;
	window.parent.location.href = url;
}