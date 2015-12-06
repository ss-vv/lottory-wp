package com.xhcms.lottery.admin.web.action.groupfollow;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.FollowService;

public class CancelRecUserAction extends BaseAction {

	private static final long serialVersionUID = -5892921236694125686L;
	
    @Autowired
    private FollowService followService;
    private String tab;
    
    private long id;
	
	@Override
	public String execute() throws Exception {
		followService.cancelRecommendUser(id);
		return SUCCESS;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

}
