package com.xhcms.lottery.account.web.action.withdraw;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.service.AccountQueryService;
import com.xhcms.lottery.account.web.action.BaseListAction;

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
	private static final long serialVersionUID = 2897094808200533882L;
	@Autowired
	private AccountQueryService accountQueryService;
	
	@Override
	public String execute() {
	    init();
		accountQueryService.listWithdraw(getUserId(), from, to, -1, paging);
		return SUCCESS;
	}
}
