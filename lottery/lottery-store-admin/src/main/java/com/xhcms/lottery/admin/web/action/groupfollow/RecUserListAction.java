package com.xhcms.lottery.admin.web.action.groupfollow;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.FollowService;

public class RecUserListAction extends BaseListAction {

	private String tab;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5892941206694145646L;
	
	@Autowired
	private FollowService followService;
	
	@Override
	public String execute() throws Exception {
		paging = wrapPaging();
		followService.listRecUser(paging);
		return SUCCESS;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}
}
