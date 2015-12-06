package com.unison.lottery.weibo.web.action.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;

public class LogoutAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public String execute() {
		WeiboUser weiboUser = (WeiboUser)ActionContext.getContext().getSession().get(Constant.User.USER);
		if(null == weiboUser){
			logger.info("logoutInfo: 未登录");
		} else {
			ActionContext.getContext().getSession().put(Constant.User.USER, null);
			logger.info("logoutInfo: username:{},注销成功 ",weiboUser.getUsername());
		}
		return "login_mobile";
	}
}
