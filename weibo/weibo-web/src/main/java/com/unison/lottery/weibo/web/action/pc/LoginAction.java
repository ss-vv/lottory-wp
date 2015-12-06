package com.unison.lottery.weibo.web.action.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.lang.Constant;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.unison.lottery.weibo.web.action.BaseAction;
import com.xhcms.ucenter.persistent.service.exception.LoginNameNotFoundException;
import com.xhcms.ucenter.persistent.service.exception.LoginNameOrPasswordBlankException;
import com.xhcms.ucenter.persistent.service.exception.PasswordWrongException;

/**
 * @Description:  使用单点登录，本Action不使用
 * @author 江浩翔   
 * @date 2013-11-12 上午10:34:08 
 * @version V1.0
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String userName;

	private String password;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private UserAccountService userAccountService;

	public String execute() {
		ValueStack vs = ActionContext.getContext().getValueStack();
		try {
			logger.info("userName: {}", userName);
			logger.info("password: {}", password);
			WeiboUser weiboUser = new WeiboUser();
			weiboUser.setUsername(userName);
			weiboUser.setPassword(password);
			AccountDealResult accountDealResult = userAccountService
						.login(weiboUser);
			logger.info("loginInfo: {},{}", accountDealResult.getResultCode(),
					accountDealResult.getDesc());
			if (Constant.AccountCode.LoginResultCode.LOGIN_SUCCESS.equals(accountDealResult
					.getResultCode())) {
				ActionContext.getContext().getSession().put(Constant.User.USER, weiboUser);
				vs.set("accountDealResult", accountDealResult);
				return SUCCESS;
			} else {
				return Action.LOGIN;
			}
		} catch (LoginNameNotFoundException e) {
			vs.set("accountDealResult", loginError());
			return Action.LOGIN;
		} catch (PasswordWrongException e) {
			vs.set("accountDealResult", loginError());
			return Action.LOGIN;
		} catch (LoginNameOrPasswordBlankException e) {
			vs.set("accountDealResult", loginError());
			return Action.LOGIN;
		}
	}
	
	private AccountDealResult loginError(){
		AccountDealResult accountDealResult = new AccountDealResult();
		accountDealResult.setSuccess(false);
		accountDealResult.setDesc("用户名或密码错误");
		return accountDealResult;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
