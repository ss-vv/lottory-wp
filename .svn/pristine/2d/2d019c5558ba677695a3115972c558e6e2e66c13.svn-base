package com.xhcms.lottery.admin.web.action.groupfollow;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.UserService;

public class RecUserSearchAction extends BaseListAction {

	private String tab;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5892941206694145686L;
	
    @Autowired
    private UserService userService;
    
    private String username;
	
	@Override
	public String execute() throws Exception {
		paging = wrapPaging();
		int state = -1;
		String ip = null;
		String idNumber = null;
		userService.listUser(paging, from, to, state, username, ip, idNumber);
		return SUCCESS;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
