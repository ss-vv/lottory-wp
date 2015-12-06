package com.unison.lottery.weibo.web.action.mobile;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.ucenter.sso.client.UserProfile;
import com.xhcms.ucenter.sso.client.session.SSOConstant;

public class AddUserInfoAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String nickname;
	private String errorMessage;
	
	private AccountDealResult accountDealResult;
	
	@Autowired
	private UserAccountService userAccountService;

	public String execute() {
		if (null == nickname) {
			return "login_mobile";
		}

		WeiboUser weiboUser = getWeiboUser();
		if (null == weiboUser) {
			return "login_mobile";
		}

		weiboUser.setNickName(nickname);
		try {
			accountDealResult = userAccountService.addUserInfo(weiboUser);
			logger.info("补充用户信息处理结果：{}", accountDealResult.getDesc());
			if(!accountDealResult.isSuccess()){
				errorMessage = accountDealResult.getDesc();
				return ERROR;
			}
			loadWeiboUserInfo(weiboUser.getId());
			return SUCCESS;
		} catch (RuntimeException e) {
			logger.error("补充用户信息出错", e);
			return "login_mobile";
		} catch (Exception e) {
			logger.error("补充用户信息出错", e);
			return "login_mobile";
		}
	}

	/**
	 * 根据userProfile 加载WeiboUser信息，并放入session，成功返回true；失败返回false
	 * 
	 * @return
	 */
	private void loadWeiboUserInfo(Long uid) {
		String lotteryUserId = "" + uid;
		WeiboUser weiboUser = userAccountService
				.findWeiboUserByLotteryUid(lotteryUserId);
		ActionContext.getContext().getSession()
				.put(Constant.User.USER, weiboUser);
		logger.info("将weiboUser放入session,大V彩微博id={},大V彩用户id={}",
				weiboUser.getWeiboUserId(), weiboUser.getId());
	}

	private WeiboUser getWeiboUser() {
		UserProfile userProfile = (UserProfile) ActionContext.getContext()
				.getSession().get(SSOConstant.SSO_USER_PROFILE);
		WeiboUser w = new WeiboUser();
		w.setId(userProfile.getId());
		w.setUsername(userProfile.getUsername());
		w.setWeiboUserCreateTime(new Date());
		return w;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
