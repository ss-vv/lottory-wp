/**
 * 
 */
package com.xhcms.ucenter.sso.principal;

/**
 * @author bean
 *
 */
public class RemeberMeUsernamePasswordCredentials extends
		UsernamePasswordCredentials implements RememberMeCredentials {
	private static final long serialVersionUID = 7022094280788368626L;
	
	private boolean rememberMe;
	private int maxAge;
	
	public RemeberMeUsernamePasswordCredentials(int type, String username, String password) {
		super(type, username, password);
	}

	@Override
	public boolean isRememberMe() {
		return rememberMe;
	}

	@Override
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public int getMaxAge() {
		return maxAge;
	}
}
