package com.xhcms.ucenter.web.action;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.uc.data.AccountDealResult;
import com.unison.lottery.weibo.uc.service.UserAccountService;
import com.xhcms.commons.lang.Data;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.sso.client.UserProfileThreadContextHolder;

public class WeiboUserAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private AccountDealResult data = new AccountDealResult();
    @Autowired
    private UserAccountService userAccountService;
    
    private Account account;
    @Autowired
    private AccountService accountService;
    
    public String getWeiboUser(){
    	Long uid = UserProfileThreadContextHolder.getUserProfile().getId();
    	WeiboUser weiboUser = userAccountService.findWeiboUserByLotteryUid(uid + "");
    	account = accountService.getAccount(uid);
    	weiboUser.setFreeMoney(account.getFree());
    	data.setWeiboUser(weiboUser);
    	data.setSuccess(true);
    	return SUCCESS;
    }

	public AccountDealResult getData() {
		return data;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
}
