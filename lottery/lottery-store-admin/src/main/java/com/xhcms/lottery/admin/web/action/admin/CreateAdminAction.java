package com.xhcms.lottery.admin.web.action.admin;

import org.apache.commons.lang.xwork.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.persist.service.AdminManager;
import com.xhcms.lottery.admin.web.action.BaseAction;

/**
 * 
 * @author yonglizhu
 * @version 1.0.0
 */
public class CreateAdminAction extends BaseAction {

	private static final long serialVersionUID = 4331434371208313429L;

	@Autowired
	private AdminManager adminManager;

	private String username;
	private String realName;
	private String password;
	private String mobile;
	private String email;

	@Override
	public String execute() throws Exception {
		
		if (StringUtils.isEmpty(username)) {
			return INPUT;
		}
		try {
			Admin admin = new Admin();
			admin.setUsername(username);
			admin.setRealName(realName);
			admin.setPassword(password);
			admin.setMobile(mobile);
			admin.setEmail(email);
			adminManager.saveAdmin(admin);
		} catch (Exception e) {
			addFieldError("username", getErrorText(80001));
			return INPUT;
		}
		addActionMessage(getText("create.admin.Success"));
		return SUCCESS;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
