package com.xhcms.lottery.dc.task.ticket;

import java.util.List;
import com.xhcms.lottery.commons.data.Ticket;

public class CheckBDBonusTask extends AbstractZunAoCheckBonusTask {

	@Override
	protected List<Ticket> getTicketsToHandle() {
		return ticketService.listBJDCNotPrizeTickets();
	}

	@Override
	public String toString() {
		return "CheckBonusTask with BJDC";
	}
}
