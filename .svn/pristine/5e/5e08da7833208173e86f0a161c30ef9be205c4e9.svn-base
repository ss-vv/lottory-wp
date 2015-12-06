package com.xhcms.ucenter.web.action;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.sso.principal.AlipayBackBean;
import com.xhcms.ucenter.sso.service.IAuthenticationManager;
import com.xhcms.ucenter.sso.service.IUserLoginService;
import com.xhcms.ucenter.sso.service.UserProfile;

public class AlipayLoginBackAction extends BaseAction{

	private static final long serialVersionUID = 6785841690876898150L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String is_success;//---
	private String sign_type;//---
	private String sign; //---
	private String notify_id;//---
	private String user_id;  //---
	private String real_name;//---
	private String email;
	private String token;//---
	private String user_grade;
	private String user_grade_type;
	private String gmt_decay;
	private String target_url;
	private String global_buyer_email;
	private String referer;
	private final String auth_src="Alipay_open";
	@Autowired
	private IAuthenticationManager authenticationManager;
	
	@Autowired
	@Qualifier("userLoginService")
	private IUserLoginService userLoginService;
	public String execute() throws IOException{
       /* if("T".equals(is_success)){
			
			
		}else{
		   logger.error("阿里云登录错误{}"+is_success);
		   return "alipayLoginError";  
		}*/
        AlipayBackBean alipayBean=new AlipayBackBean(is_success,sign_type,sign,notify_id,user_id,real_name,
        		                                     email,user_grade,user_grade_type,gmt_decay,target_url,
        		                                     global_buyer_email,token,auth_src);
        UserProfile profile = authenticationManager.authenticateByOpenId(alipayBean);
        String tem=request.getRequestURI();
        String tem1=request.getRequestURL().toString();
		return userLoginService.onLogin(profile, referer, request, response);
	}
	public Logger getLogger() {
		return logger;
	}
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	public String getIs_success() {
		return is_success;
	}
	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getNotify_id() {
		return notify_id;
	}
	public void setNotify_id(String notify_id) {
		this.notify_id = notify_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getReal_name() {
		return real_name;
	}
	public void setReal_name(String real_name) {
		this.real_name = real_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getUser_grade() {
		return user_grade;
	}
	public void setUser_grade(String user_grade) {
		this.user_grade = user_grade;
	}
	public String getUser_grade_type() {
		return user_grade_type;
	}
	public void setUser_grade_type(String user_grade_type) {
		this.user_grade_type = user_grade_type;
	}
	public String getGmt_decay() {
		return gmt_decay;
	}
	public void setGmt_decay(String gmt_decay) {
		this.gmt_decay = gmt_decay;
	}
	public String getTarget_url() {
		return target_url;
	}
	public void setTarget_url(String target_url) {
		this.target_url = target_url;
	}
	public String getGlobal_buyer_email() {
		return global_buyer_email;
	}
	public void setGlobal_buyer_email(String global_buyer_email) {
		this.global_buyer_email = global_buyer_email;
	}
	public String getReferer() {
		return referer;
	}
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	
}
