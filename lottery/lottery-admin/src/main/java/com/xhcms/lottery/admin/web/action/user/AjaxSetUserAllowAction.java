package com.xhcms.lottery.admin.web.action.user;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.lottery.admin.web.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.UserService;

public class AjaxSetUserAllowAction extends BaseAction{

	private static final long serialVersionUID = 1681278588687571791L;
	@Autowired
	private UserService userService;
	private long id;
	public String allow(){
		userService.allow(id);
		return SUCCESS;
	}
	public String unAllow(){
		userService.unAllow(id);
		return SUCCESS;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
}
