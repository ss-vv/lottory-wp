package com.unison.lottery.wap.action;

import com.xhcms.ucenter.action.BaseAction;


public class LogoutAction extends BaseAction {
	private static final long serialVersionUID = -4147508582203009946L;
	
	@Override
	public String execute(){
		request.getSession().invalidate();
		System.out.println("退出");
		return SUCCESS;
	}
}
