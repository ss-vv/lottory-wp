package com.xhcms.lottery.account.web.action.journal;

import org.springframework.beans.factory.annotation.Autowired;
import com.xhcms.lottery.account.service.AccountQueryService;
import com.xhcms.lottery.account.web.action.BaseListAction;

/**
 * <p>Title: 流水记录</p>
 * @author wang lei
 * @version 1.0.0
 */
public class ListAction extends BaseListAction {
	private static final long serialVersionUID = 3250963577960908665L;
	
	@Autowired
	private AccountQueryService accountQueryService;
    
	int journalType=0;
	
	@Override
	public String execute() {
		init();
		accountQueryService.listJournal(paging, getUserId(), journalType, from, to);
		return SUCCESS;
	}

	public int getJournalType() {
		return journalType;
	}

	public void setJournalType(int journalType) {
		this.journalType = journalType;
	}


}
