package com.xhcms.lottery.admin.web.action.recharge;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.persist.service.RechargeService;
import com.xhcms.lottery.admin.web.action.BaseListAction;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
public class ListAction extends BaseListAction {
	private static final long serialVersionUID = 71618237566362132L;
	@Autowired
	private RechargeService rechargeService;
	
	private String username;
	private int state = -1;
	
	@Override
	public String execute() {
	    init();
		
		rechargeService.listRecharge(paging, 0L, username, state, from, to);
		return SUCCESS;
	}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
