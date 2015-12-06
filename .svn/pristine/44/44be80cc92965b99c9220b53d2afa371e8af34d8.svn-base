package com.xhcms.lottery.admin.web.action.match;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.admin.persist.service.MatchService;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.Constants;

public class CTFBMatchListAction extends BaseListAction {
	private static final long serialVersionUID = 5898252127026584693L;

	@Autowired
	private IssueService issueService;
	
	private int state = -1;
	
	@Override
	public String execute() {
		init();
		String lotteryId = Constants.CTZC;
		issueService.getIssue(paging, lotteryId, from, to, state);
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
