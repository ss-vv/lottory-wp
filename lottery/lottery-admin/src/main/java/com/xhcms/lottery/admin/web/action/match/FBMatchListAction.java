package com.xhcms.lottery.admin.web.action.match;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.admin.persist.service.MatchService;
import com.xhcms.lottery.admin.web.action.BaseListAction;

public class FBMatchListAction extends BaseListAction {
	private static final long serialVersionUID = -2838062419897513893L;
	@Autowired
	private MatchService matchService;
	
	private int state = -1;
	private int matchResult = -1;
	private int maxResult;
	
	@Override
	public String execute() {
		init();
		if(maxResult > 0){
			paging.setMaxResults(maxResult);
		}
		matchService.listFBMatch(paging, state, from, to, matchResult);
		return SUCCESS;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
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

	public int getMatchResult() {
		return matchResult;
	}

	public void setMatchResult(int matchResult) {
		this.matchResult = matchResult;
	}
	
}
