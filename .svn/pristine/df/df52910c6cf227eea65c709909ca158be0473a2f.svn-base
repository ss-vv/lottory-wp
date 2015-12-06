$(function(){
	jQuery.extend({
		setCookie:function (c_name,value,expiredays) {
			var exdate=new Date();
			exdate.setDate(exdate.getDate()+expiredays);
			document.cookie = c_name+ "=" +escape(value)+ 
						((expiredays==null) ? "" : ";expires="+exdate.toGMTString()) + ";path=/;domain=davcai.com";
		},
		getCookie:function(c_name) {
			if (document.cookie.length>0) {
			  c_start=document.cookie.indexOf(c_name + "=");
			  if (c_start!=-1) { 
				c_start=c_start + c_name.length+1;
				c_end=document.cookie.indexOf(";",c_start);
				if (c_end==-1) c_end=document.cookie.length;
					return unescape(document.cookie.substring(c_start,c_end));
				} 
			  }
			return "";
		}
	});
	//根据名称获得url参数的值
	function getParamByParamName(name) { 
		var url = location.href; 
		var paramArr = url.substring(url.indexOf("?")+1, url.length).split("&");
		var obj = {};
		for (var i=0; j = paramArr[i]; i++){ 
			obj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
		} 
		var paramValue = obj[name];
		
		if("undefined" == typeof(paramValue)){ 
			return "";
		} else { 
			 return paramValue;
		}
	}
	//获取cookie渠道来源
	var channelId = $.getCookie("pid");
	if(""===channelId){
		//渠道来源
		channelId = getParamByParamName("pid");
		//记录pid到cookie中去
		$.setCookie("pid", channelId);
	}
});