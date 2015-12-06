package com.unison.weibo.admin.action;

import com.unison.weibo.admin.commons.WeiboAdminConstants;

public class LogoutAction extends BaseAction {

	private static final long serialVersionUID = 7552001302217552377L;

	@Override
	public String execute() throws Exception {
		removeSession(WeiboAdminConstants.SES_USER);
		return SUCCESS;
	}
}