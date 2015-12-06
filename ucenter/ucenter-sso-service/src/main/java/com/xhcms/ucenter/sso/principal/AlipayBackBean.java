package com.xhcms.ucenter.sso.principal;

public class AlipayBackBean extends OpenIdCredentials{

	private static final long serialVersionUID = 3413257863384924048L;
	private String is_success;//---
	private String sign_type;//---
	private String sign; //---
	private String notify_id;//---
	private String user_id;  //---
	private String real_name;//---
	private String email;
	private String user_grade;
	private String user_grade_type;
	private String gmt_decay;
	private String target_url;
	private String global_buyer_email;
	public AlipayBackBean(){};
	
	public AlipayBackBean(String is_success,String sign_type,String sign,String notify_id,
			              String user_id,String real_name,String email,String user_grade,
			              String user_grade_type,String gmt_decay,String target_url,
			              String global_buyer_email,String token,String authSrc){
		this.is_success=is_success;
		this.sign_type=sign_type;
		this.notify_id=notify_id;
		this.user_id=user_id;
		this.real_name=real_name;
		this.email=email;
		this.user_grade=user_grade;
		this.user_grade_type=user_grade_type;
		this.gmt_decay=gmt_decay;
		this.target_url=target_url;
		this.global_buyer_email=global_buyer_email;
		super.setAccessToken(token);
		super.setAuthSrc(authSrc);
	
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
	
	
	
}
