/**
 * 
 */
package com.xhcms.ucenter.sso.web.util;

/**
 * @author bean
 *
 */
public class GrantingCookie {
	private String ticket;
	private long userId;
	private String username;
	
	public GrantingCookie(String ticket, long userId, String username) {
		this.ticket = ticket;
		this.userId = userId;
		this.username = username;
	}
	
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
