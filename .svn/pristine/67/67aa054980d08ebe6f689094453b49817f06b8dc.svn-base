package com.unison.lottery.weibo.web.action.pc;

import com.unison.lottery.weibo.web.action.BaseAction;

public class SearchAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private String keys;
	
	public String searchUser(){
		request.setAttribute("keys", keys);
		return SUCCESS;
	}
	public String searchWeibo(){
		request.setAttribute("keys", keys);
		return SUCCESS;
	}
	
	public String execute(){
		return SUCCESS;
	}
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
}
