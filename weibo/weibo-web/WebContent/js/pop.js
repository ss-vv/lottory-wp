$(function(){
	 $("body").append(str_login_form);
	 $(".icon-close").click(function(){
		$("#pop-outer").hide();
		$("#all").hide();
	 });
	 //弹出层记住我
	 $("#popcheckselect").click(function(){
		 $(this).toggleClass("pop-checkbox-not-selected");
		 var check=$("#remember_me_").attr("checked");
		 if(check=="checked"){
			$("#remember_me_").attr("checked",false); 
		 }else{
			 $("#remember_me_").attr("checked",true);  
		 }
	 });
});
var str_login_form= ' <div id="pop-outer">'
		+' <div id="pop">'
		+' <p class="pop-title">'
		+'   <span>登录</span>'
		+'   <label><a href="javascript:void(0);" class="icon-close"></a></label>'
		+' </p>'
		  	//----------下面开始左边登录部分-----------------------
		+' <div class="poplogin-left">'
		+' <form  id="loginForm_" method="post">'
		+'    <ul class="">'
		+'       <li class="poplogin-name">登录大V彩</li>'
		+'       <li>'
		+'         <input type="hidden" name="referer" value="http://www.davcai.com/index"/>'
		+'         <input type="hidden" name="failReturnURL" value="http://www.davcai.com/welcome"/>'
		+'         <input type="text" id="username_" name="username"  placeholder="用户名/邮箱"/>'
		+'      </li>'
		+'      <li>'
		+'         <input type="password" id="password_" name="password"  placeholder="密码"/>'
		+'         <a href="forget" class="lostpw">忘记密码？</a>'
		+'      </li>'
		+'      <li class="last-child">'
		+'          <span class="poplogin"><a href="#" onclick="login_()"></a></span>'
		+'      </li>'
		+'    </ul>'
		+' </form>'
		+'<p class="pop-cooperative-logging">或使用合作账号登录'
		+		'<span class="pop-zhifubao fist-child"><a href="#" onclick="alipayLogin()"></a></span>'
		+		'<span class="pop-sina-weibo"><a href="#" onclick="loginBySina('+"'http://www.davcai.com/sina_weibo_login'"+')"></a></span>'
		+		'<span class="pop-qq"><a href="#" onclick="loginByQQ('+"'http://www.davcai.com/qq_connect_login'"+')"></a></span>'
		+		'<span class="pop-weixin"><a href="#" onclick="loginByWeixin()"></a></span>'
		+'</p>'
		+'</div>'//-----poplogin-left左边部分-------->
		  		//----------下面开始右边登录部分----------------------->
		+'<div class="poplogin-right">'
		+		 '<p class="no-account">没有帐号？</p>'
		+		 '<p class="login">加入大V彩，讨论共同关注的<br/>彩票和话题</p>'
		+		 '<p class="pop-register"><a href="#" onclick="toRegistPage('+"'http://www.davcai.com/upload-head-img','http://www.davcai.com/welcome'"+')"></a></p>'
		+'</div>'//-----poplogin-right右边部分-------->  		
		+'</div>'//------pop部分---------->
	    +'</div>'//------pop-outer部分---------->
	    +'<div id="all"></div>';