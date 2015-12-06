package com.xhcms.lottery.admin.web.action.match;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.admin.persist.service.MatchService;
import com.xhcms.lottery.admin.web.action.BaseListAction;

public class BBMatchListAction extends BaseListAction {
	private static final long serialVersionUID = -2838062419897513893L;
	@Autowired
	private MatchService matchService;
	
	private int state = -1;
	
	@Override
	public String execute() {
		init();
		matchService.listBBMatch(paging, state, from, to);
		return SUCCESS;
	}

	public Paging getPaging() {
		return paging;
	}

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
	
}
