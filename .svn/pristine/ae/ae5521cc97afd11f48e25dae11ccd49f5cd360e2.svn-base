package com.xhcms.lottery.admin.web.action.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.AccountService;

public class ResetUserWithdrawPwdAction extends BaseAction {

    private static final long serialVersionUID = -595818439204217724L;

    @Autowired
    private AccountService accountService;
    private long id;
    
    @Override
    public String execute(){
    	accountService.resetWithdrawPassword(id);
        return SUCCESS;
    }

    public void setId(long id) {
        this.id = id;
    }
}
