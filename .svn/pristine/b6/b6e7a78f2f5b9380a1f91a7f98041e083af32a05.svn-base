package com.unison.lottery.weibo.web.action.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;

/**
 * @Description: 使用单点退出，本Action不使用 
 * @author 江浩翔   
 * @date 2013-11-12 上午10:34:51 
 * @version V1.0
 */
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
		return Action.LOGIN;
	}
}
