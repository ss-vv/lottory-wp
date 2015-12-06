package com.unison.lottery.weibo.web.action.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.xhcms.ucenter.exception.UCException;

public class RegistAction extends ActionSupport {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final long serialVersionUID = 1L;
	
	private WeiboUser weiboUser;
	
	@Autowired
	private UserAccountService userAccountService;
	
	public String toRegistPage(){
		return SUCCESS;
	}
	
	public String execute(){
		if(null == weiboUser){
			return LOGIN;
		}
		logger.info("========注册信息=========");
		logger.info("用户名：{}",weiboUser.getUsername());
		logger.info("密码：{}",weiboUser.getPassword());
		logger.info("昵称：{}",weiboUser.getNickName());
		logger.info("真实姓名：{}",weiboUser.getRealname());
		logger.info("性别：{}",weiboUser.getGender());
		logger.info("手机号：{}",weiboUser.getMobile());
		logger.info("头像地址：{}",weiboUser.getHeadImageURL());
		logger.info("新浪微博uid:{}",weiboUser.getSinaWeiboUid());
		logger.info("新浪微博token:{}",weiboUser.getSinaWeiboToken());
		logger.info("=======================");
		try {
			AccountDealResult accountDealResult = userAccountService.regist(weiboUser);
			logger.info("注册结果标识码：{}", accountDealResult.getResultCode());
			logger.info("注册结果描述：{}", accountDealResult.getDesc());
			weiboUser = accountDealResult.getWeiboUser();
			//登录信息放入session
			ActionContext.getContext().getSession()
					.put(Constant.User.USER, accountDealResult.getWeiboUser());
			//处理结果放入ValueStack
			ActionContext.getContext().getValueStack()
					.set(Constant.Result.result, accountDealResult);
			return SUCCESS;
		} catch (UCException e) {
			logger.info("注册失败：{}",e);
			return LOGIN;
		} catch (Exception e) {
			logger.info("注册失败：{}",e);
			return LOGIN;
		}
	}
	
	public WeiboUser getWeiboUser() {
		return weiboUser;
	}

	public void setWeiboUser(WeiboUser weiboUser) {
		this.weiboUser = weiboUser;
	}
	
}
