/**
 * 
 */
package com.xhcms.ucenter.sso.service;

import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

/**
 * @author bean
 * 
 */
public class UserProfile {
	private long id;
	private String username;
	private String nickname;
	private String truename;
	private String lastLoginIp;
	private Date lastLoginTime;
	
	private boolean refresh;
	
	public UserProfile(){
		refresh = true;
	}
	
	public UserProfile(long id, String username) {
		this.id = id;
		this.username = username;
		refresh = true;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	public boolean isRefresh() {
		return refresh;
	}

	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}

	@JSON(format = "yyyy-MM-dd HH:mm:ss")
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

}
