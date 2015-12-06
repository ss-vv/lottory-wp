package com.xhcms.lottery.admin.web.action.grant;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.GrantService;
import com.xhcms.lottery.admin.web.action.BaseListAction;

public class ListAction extends BaseListAction {
	private static final long serialVersionUID = 6803450343668795960L;
	@Autowired
	private GrantService grantService;
	
	private long userId;
	private int state = -1;
	
	@Override
	public String execute() {
		init();
		grantService.listGrant(paging, userId, state, from, to);
		return SUCCESS;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
