package com.xhcms.ucenter.sso.principal;

public class OpenIdCredentials implements Credentials {
	private static final long serialVersionUID = -2689400625670785290L;
	
	// 微博认证授权码
	private String code;
	// 微博认证服务商
	private String authSrc;
	
	//QQ Login need parameter--
	private String openid;
	private String openkey;
	//QQ Login need parameter --
	
	private String accessToken;
	public OpenIdCredentials(){};
	public OpenIdCredentials(String code, String authSrc) {
		this.code = code;
		this.authSrc = authSrc;
	}
	public OpenIdCredentials(String code, String authSrc, String openid, String openkey) {
		this.code = code;
		this.authSrc = authSrc;
		this.openid = openid;
		this.openkey = openkey;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getAuthSrc() {
		return authSrc;
	}
	public void setAuthSrc(String authSrc) {
		this.authSrc = authSrc;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getOpenkey() {
		return openkey;
	}
	public void setOpenkey(String openkey) {
		this.openkey = openkey;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
}
