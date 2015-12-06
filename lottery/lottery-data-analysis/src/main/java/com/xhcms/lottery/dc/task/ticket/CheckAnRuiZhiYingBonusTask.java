package com.xhcms.lottery.dc.task.ticket;

import java.util.List;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class CheckAnRuiZhiYingBonusTask extends AbstractAnRuiZhiYingCheckBonusTask {

	@Override
	protected List<Ticket> getTicketsToHandle() {
		return ticketService.listNotPrizeTicketWithTargetLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
	}

	@Override
	public String toString() {
		return "CheckAnRuiZhiYingBonusTask";
	}

}
