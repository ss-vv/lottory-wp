package com.xhcms.lottery.admin.web.action.match;

import javax.annotation.Resource;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.Constants;

public class JXMatchListAction extends BaseListAction {
	private static final long serialVersionUID = -2838062419897513893L;
	
	private IssueService issueService;
	
	private int state = -1;
	
	private String lotteryId;
	
	@Override
	public String execute() {
		init();
		lotteryId = Constants.JX11;
		issueService.getCurrentSalingIssue(paging, lotteryId, from, to, state);
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

	public IssueService getIssueService() {
		return issueService;
	}

	@Resource
	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
	
}
