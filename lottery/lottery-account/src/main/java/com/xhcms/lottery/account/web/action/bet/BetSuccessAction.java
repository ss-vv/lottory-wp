package com.xhcms.lottery.account.web.action.bet;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.service.AccountService;

public class BetSuccessAction extends BaseAction {

    private static final long serialVersionUID = -3108031823168141317L;

    @Autowired
    private AccountService accountService;

    private Account account;
    
    private int type;
    
    private int showScheme;

	private Long sid;
    
    public String execute() {

        account = accountService.getAccount(getUserId());

        return SUCCESS;
    }

    public Account getAccount() {
        return account;
    }

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }
    
    public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getShowScheme() {
		return showScheme;
	}

	public void setShowScheme(int showScheme) {
		this.showScheme = showScheme;
	}
	
}
