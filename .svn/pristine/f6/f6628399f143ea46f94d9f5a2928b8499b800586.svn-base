package com.unison.lottery.wap.action;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.data.User;
import com.xhcms.lottery.commons.persist.service.AccountService;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;

public class ChargeRecordAction extends BaseListAction{

	private static final long serialVersionUID = 3213079409718440092L;

	@Autowired
    private AccountService accountService;
    
	@Autowired
	private AccountQueryService accountQueryService;

	private Account account;
	
	private Integer duration=0;
	
	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public String execute() {
		init();
		User user = getSelf();
		if(user!=null){
			account = accountService.getAccount(user.getId());
		    accountQueryService.listRecharge(user.getId(), null, null, -1, paging);
		}else{
			return LOGIN;
		}
		return SUCCESS;		
	}
}

