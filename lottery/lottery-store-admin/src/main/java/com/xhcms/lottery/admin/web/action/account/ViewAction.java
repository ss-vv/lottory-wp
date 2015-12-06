package com.xhcms.lottery.admin.web.action.account;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.commons.persist.service.AccountService;

public class ViewAction extends BaseAction {

    private static final long serialVersionUID = -595818439204217794L;

    @Autowired
    private AccountService accountService;
    private long id;
    private Account a;
    
    @Override
    public String execute(){
        a = accountService.getAccount(id);
        return SUCCESS;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getA() {
        return a;
    }
    
}
