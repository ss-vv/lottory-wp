package com.unison.lottery.weibo.web.action;

import javax.servlet.http.HttpSession;

import com.xhcms.ucenter.sso.client.session.SSOConstant;
import com.xhcms.ucenter.sso.client.session.SingleSignOutHandler;

public class LogoutAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String data;
	private static final SingleSignOutHandler handler = new SingleSignOutHandler();
	public String ajax(){
		HttpSession session = request.getSession();
		session.removeAttribute(SSOConstant.SSO_CLIENT_KEY);
		handler.destroySession(request);
		data = "退出成功";
		return SUCCESS;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
