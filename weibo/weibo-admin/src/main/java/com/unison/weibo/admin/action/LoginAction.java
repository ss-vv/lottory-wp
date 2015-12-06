package com.unison.weibo.admin.action;

import com.unison.weibo.admin.commons.WeiboAdminConstants;
import com.unison.weibo.admin.model.Admin;

public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 7552001302217552377L;

	private String username;
	private String password;
	
	@Override
	public String execute() throws Exception {
		
		if (isGet()){
			return SUCCESS;
		}
		if ("admin".equals(username) && "lw321".equals(password)){
			Admin admin = new Admin();
			admin.setUsername(username);
			putSession(WeiboAdminConstants.SES_USER, admin);
			return "toHome";
		}else{
			addActionError("用户名或密码错误!");
			return LOGIN;
		}
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

}
