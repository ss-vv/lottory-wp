package com.xhcms.ucenter.sso.service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.data.AccountDealResult;

public interface IUserLoginService {
	/**
     * 当用户登陆验证后调用本方法。
     * 主要工作：判断userProfile，userProfile == null 时处理失败情况；不为空则记录cookie、生成ticket。
     * @param profile
     * @param request
     * @param response
     */
	String onLogin(UserProfile profile, String referer,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException;
	
	AccountDealResult bindAccount(String username, String password,WeiboUser weiboUser);
}
