package com.xhcms.lottery.dc.task.ticket;

import java.util.List;

import com.xhcms.lottery.commons.data.Ticket;

public class CheckCQSSBonusTask extends AbstractZunAoCheckBonusTask {

	@Override
	protected List<Ticket> getTicketsToHandle() {
		return ticketService.listCQSSNotPrizeTicketGroupByPlayIdAndIssue();
	}

	@Override
	public String toString() {
		return "CheckCQSSBonusTask";
	}
	
	

}
