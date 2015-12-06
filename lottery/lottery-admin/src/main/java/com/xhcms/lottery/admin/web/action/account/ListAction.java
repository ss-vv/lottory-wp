package com.xhcms.lottery.admin.web.action.account;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.AccountService;

public class ListAction extends BaseListAction {
    private static final long serialVersionUID = -3674459180326620361L;
    
    @Autowired
    private AccountService accountService;
    private String username;
    
    @Override
    public String execute(){
        paging = wrapPaging();       
        accountService.listAccount(paging, username);
        return SUCCESS;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
