package com.xhcms.lottery.admin.web.action;

import com.xhcms.lottery.admin.lang.AdminConstant;

public class LogoutAction extends BaseAction {
	private static final long serialVersionUID = -4147508582203009946L;
	
	@Override
	public String execute(){
		request.getSession().removeAttribute(AdminConstant.USER);
		return SUCCESS;
	}
}
