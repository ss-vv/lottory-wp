package com.unison.lottery.grant.web;
import java.io.Serializable;

/**
 * 订购彩铃送彩金
 * @author zhuyongli
 *
 */
public class RingContext implements Serializable {

	private static final long serialVersionUID = 39100290835940119L;
	private String username;
    private String password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
 
}
