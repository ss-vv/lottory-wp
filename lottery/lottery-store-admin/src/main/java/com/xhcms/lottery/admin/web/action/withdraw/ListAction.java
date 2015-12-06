package com.xhcms.lottery.admin.web.action.withdraw;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.WithdrawService;
import com.xhcms.lottery.admin.web.action.BaseListAction;

public class ListAction extends BaseListAction {
	private static final long serialVersionUID = -5448445978720174981L;
	@Autowired
	private WithdrawService withdrawService;
	
	private String username;
	private int state = -1;
	
	@Override
	public String execute() {
	    init();
        
		withdrawService.listWithdraw(paging, 0L, username, state, from, to);
		return SUCCESS;
	}
	
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
