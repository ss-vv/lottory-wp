package com.xhcms.lottery.admin.web.action.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.UserService;

public class UnlockedAction extends BaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
    private UserService userService;
	
	private long id;
	
	@Override
    public String execute(){
		userService.unlockUserAccount(id);
        return SUCCESS;
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
}
