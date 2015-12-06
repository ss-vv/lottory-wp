package com.xhcms.lottery.dc.task.ticket;

import java.util.List;
import com.xhcms.lottery.commons.data.Ticket;

public class CheckWFBonusTask extends AbstractZunAoCheckBonusTask{

	@Override
	protected List<Ticket> getTicketsToHandle() {
		return ticketService.listWFNotPrizeTickets();
	}
	
	@Override
	public String toString() {
		return "CheckBonusTask";
	}
}