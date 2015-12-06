/**
 * 
 */
package com.xhcms.ucenter.sso.principal;

/**
 * @author bean
 * 
 */
public class UsernamePasswordCredentials implements Credentials {
	private static final long serialVersionUID = -7192062710971749327L;

	private int loginType;
	private String username;
	private String password;

	public UsernamePasswordCredentials(int type, String username, String password) {
		this.loginType = type;
		this.username = username;
		this.password = password;
	}

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

	public int getLoginType() {
		return loginType;
	}

	public void setLoginType(int loginType) {
		this.loginType = loginType;
	}

}
