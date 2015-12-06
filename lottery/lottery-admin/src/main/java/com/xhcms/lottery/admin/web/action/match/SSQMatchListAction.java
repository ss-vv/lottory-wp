package com.xhcms.lottery.admin.web.action.match;

import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.lottery.admin.web.action.BaseListAction;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;

public class SSQMatchListAction extends BaseListAction {
	private static final long serialVersionUID = -2838062419897513893L;

	@Autowired
	private IssueService issueService;

	@Override
	public String execute() {
		init();
		issueService.getCurrentSalingIssue(paging, Constants.SSQ, from, to, EntityStatus.MATCH_ON_SALE);
		return SUCCESS;
	}
}
