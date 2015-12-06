package com.xhcms.lottery.dc.task.ticket;

import java.util.List;

import com.xhcms.lottery.commons.data.Ticket;

public class CheckHFBonusTask extends AbstractZunAoCheckBonusTask {

	@Override
	protected List<Ticket> getTicketsToHandle() {
		return ticketService.listHFNotPrizeTicketGroupByPlayIdAndIssue();
	}

	@Override
	public String toString() {
		return "CheckHFBonusTask";
	}
	
	

}
