package com.davcai.lottery.platform.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.chooser.ILotteryPlatformChooser;
import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.xhcms.lottery.commons.data.Ticket;

public class OrderTicketClientWithMultiPlatformImpl implements
		IOrderTicketClientWithMultiPlatform {

	@Autowired
	private ILotteryPlatformChooser chooser;
	
	@Override
	public OrderTicketResponse orderTicket(String lotteryPlatformId,
			List<Ticket> tickets) {
		ILotteryPlatformClient client = chooser.chooseOnePlatformClient(lotteryPlatformId, LotteryInterfaceName.orderTicket);
		ILotteryPlatformOrderTicketClient orderTicketClient=(ILotteryPlatformOrderTicketClient) client;
		return orderTicketClient.orderTicketByLoops(tickets);
	}

	public ILotteryPlatformChooser getChooser() {
		return chooser;
	}

	public void setChooser(ILotteryPlatformChooser chooser) {
		this.chooser = chooser;
	}

}
