package com.xhcms.ucenter.sso.service;

/**
 * @Description:保存开放平台登录 用户信息 
 * @author 江浩翔   
 * @date 2013-11-18 上午10:18:47 
 * @version V1.0
 */
public class OpenUserProfile extends UserProfile {
	private String openUid;
	private String token;
	private String authSrc;
	/** 
	 * 标志使用第三方帐号登录的用户是否已经 注册/绑定 了大V彩微博帐号 
	 * 未注册/绑定的用户需要先注册/绑定
	 */
	private boolean isRegist;
	
	public String getOpenUid() {
		return openUid;
	}
	public void setOpenUid(String openUid) {
		this.openUid = openUid;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getAuthSrc() {
		return authSrc;
	}
	public void setAuthSrc(String authSrc) {
		this.authSrc = authSrc;
	}
	public boolean isRegist() {
		return isRegist;
	}
	public void setRegist(boolean isRegist) {
		this.isRegist = isRegist;
	}
}
