package com.xhcms.lottery.account.web.action.grant;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.account.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.GrantService;

public class ListAction extends BaseListAction {
	private static final long serialVersionUID = 2165483583075081426L;
	@Autowired
	private GrantService grantService;
	
	@Override
	public String execute() {
		init();
		grantService.listGrant(paging, getUserId(), from, to);
		return SUCCESS;
	}
}
