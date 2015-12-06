package com.unison.lottery.weibo.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.utils.HttpRequestDeviceUtils;
import com.unison.lottery.weibo.web.util.ServletUtils;
import com.xhcms.ucenter.sso.client.UserProfile;
import com.xhcms.ucenter.sso.client.session.SSOAuthentication;
import com.xhcms.ucenter.sso.client.session.SSOConstant;

public class LoginInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 1L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private UserAccountService userAccountService;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {

		ActionContext ctx = invocation.getInvocationContext();
		HttpServletRequest req = (HttpServletRequest) ctx
				.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse resp = (HttpServletResponse) ctx
				.get(StrutsStatics.HTTP_RESPONSE);
		if (!SSOAuthentication.check(req, resp)) {
			makeRedirectUrl(ctx);
			if (HttpRequestDeviceUtils.isMobileDevice(ServletActionContext
					.getRequest())) {
				//return "login_mobile"; 无mobile版本
				return Action.LOGIN;
			}

			//return "login_mobile";
			return Action.LOGIN;
		}

		if (!loadWeiboUserInfo(ctx, req)) {
			//return "add_nickname_mobile";
			return "add_nickname";
		}

		return invocation.invoke();
	}
	
	private void makeRedirectUrl(ActionContext ctx) {
		HttpServletRequest req = (HttpServletRequest) ctx
				.get(StrutsStatics.HTTP_REQUEST);
		HttpServletResponse resp = (HttpServletResponse) ctx
				.get(StrutsStatics.HTTP_RESPONSE);
		ctx.getValueStack().set("ssoLogin",
				SSOAuthentication.retriveRedirect(req, resp, true) + "&failReturnURL=" +
				ServletUtils.getBaseUrl(req) + "/welcome");
	}

	/**
	 * 根据userProfile 加载WeiboUser信息，并放入session，成功返回true；失败返回false
	 * 
	 * @param ctx
	 * @param req
	 * @return
	 */
	private boolean loadWeiboUserInfo(ActionContext ctx, HttpServletRequest req) {
		
		if(null != ctx.getSession().get(Constant.User.USER)){
			return true;
		}
		
		UserProfile userProfile = (UserProfile) req.getSession().getAttribute(
				SSOConstant.SSO_USER_PROFILE);
		String lotteryUserId = "" + userProfile.getId();
		WeiboUser weiboUser = userAccountService
				.findWeiboUserByLotteryUid(lotteryUserId);
		
		if (null == weiboUser || StringUtils.isBlank(weiboUser.getNickName())) {
			return false;
		} 
		
		ctx.getSession().put(Constant.User.USER, weiboUser);
		logger.info("将weiboUser放入session,大V彩微博id={},大V彩用户id={}",
				weiboUser.getWeiboUserId(), weiboUser.getId());
		return true;
	}
}
