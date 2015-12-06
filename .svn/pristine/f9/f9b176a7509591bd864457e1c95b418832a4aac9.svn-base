function getQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}

function log() {
	var isDebug = true;
	if(log && console.log && true == isDebug) {
		console.log(arguments[0]);
	}
}

var cookieUtil = {
	setCookie:function (c_name,value,expiredays) {
		var exdate=new Date();
		exdate.setDate(exdate.getDate()+expiredays);
		document.cookie = c_name+ "=" +escape(value)+ 
					((expiredays==null) ? "" : ";expires="+exdate.toGMTString()) + ";domain=.davcai.com";
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
};

/**活动常量*/
var activiyStatic = {
	version:"2.0.0",				//针对活动版本
	machineType:"android",			//针对机型:Android手机
	pid:"color2013815",				//渠道
	apk:"davcai_client_orderColor.apk",	//android客户端APK包
	webPidPrefix:"0000"				//网站渠道前缀
};

//取得客户端浏览器访问时的UA，并进行相应业务跳转
function ua() {
	var ua = navigator.userAgent;
	if(ua) {
		ua = ua.toLowerCase();
		//如果为Android机访问，则直接让客户端下载指定渠道包
		if(-1 != ua.indexOf(activiyStatic.machineType)) {
			download_android();
		} else {
			injectCookie();
		}
	}
}

//种植引导的用户cookie
function injectCookie() {
	var pid = activiyStatic.webPidPrefix + "_" + activiyStatic.pid;
	//默认cookie的级别是session的
	cookieUtil.setCookie("pid", pid, 1);
}

//下载android包
function download_android() {
	location.href="http://www.davcai.com/download/davcai/" + activiyStatic.apk;
}

//跳转大V彩Web网站导航页面
function redirectGuide() {
	location.href = _BASE + "/index.do";
}















