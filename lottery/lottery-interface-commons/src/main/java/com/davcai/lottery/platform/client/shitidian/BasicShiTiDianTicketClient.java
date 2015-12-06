package com.davcai.lottery.platform.client.shitidian;

import java.util.List;

import com.davcai.lottery.platform.client.model.OrderTicketResponse4OneLoop;
import com.xhcms.lottery.commons.data.Ticket;

public class BasicShiTiDianTicketClient extends
		AbstractShiTiDianLotteryPlatformOrderTicketClient {

	private String lotteryPlatformId;

	public void setLotteryPlatformId(String lotteryPlatformId) {
		this.lotteryPlatformId = lotteryPlatformId;
	}

	@Override
	public OrderTicketResponse4OneLoop orderTicketForOneLoop(
			List<Ticket> tickets) {
		return null;
	}

	@Override
	protected String getLotteryPlatformId() {
		return this.lotteryPlatformId;
	}
}
