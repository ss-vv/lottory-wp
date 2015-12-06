package com.xhcms.lottery.admin.web.action.admin;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.exception.XHRuntimeException;
import com.xhcms.lottery.admin.persist.service.AdminManager;
import com.xhcms.lottery.admin.web.action.BaseAction;

/**
 * 
 * <p>Title: 重置密码</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public class ResetPasswordAction extends BaseAction {

	private static final long serialVersionUID = -5838202587792128831L;

	@Autowired
	private AdminManager adminManager;

	private String oldpassword;
	private String password;
	private String username;

	@Override
	public String execute() throws Exception {
		username = getAdmin().getUsername();
		if (StringUtils.isEmpty(password)) {
			return INPUT;
		}
		try {
			adminManager.resetPassword(getMyId(), oldpassword, password);
		} catch (XHRuntimeException e) {
			addFieldError("oldpassword", getErrorText(e.getCode()));
			return INPUT;
		}
		addActionMessage(getText("reset.pwd.Success"));
		return super.execute();
	}

	public void setOldpassword(String oldpassword) {
		this.oldpassword = oldpassword;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

}
