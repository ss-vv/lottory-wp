package com.unison.weibo.admin.action.recommend;

import org.springframework.beans.factory.annotation.Autowired;

import com.unison.weibo.admin.action.BaseAction;
import com.xhcms.lottery.commons.persist.service.BetMatchRecService;

public class RecommendMatchListAction  extends BaseAction{
	private static final long serialVersionUID = 1L;

	@Autowired
	private BetMatchRecService matchRecService;
	
	public String execute(){
		
		
		return SUCCESS;
	}
}