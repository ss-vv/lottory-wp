package com.unison.lottery.wap.action.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.ucenter.action.BaseAction;
import com.xhcms.ucenter.lang.Constant;

public class ChargeAction extends BaseAction {

	private static final long serialVersionUID = -7382065232226615519L;
	
	@Autowired
    private AccountService accountService;

	private User user;
	
	private Account account;
	
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String execute() {
		
		user = (User) request.getSession().getAttribute(Constant.USER_KEY);
		if (user != null) {
			account = accountService.getAccount(user.getId());
		} else {
			return LOGIN;
		}
		return SUCCESS;
	}

}
