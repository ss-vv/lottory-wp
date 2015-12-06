package com.davcai.lottery.platform.client;

import java.util.List;

import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingOrderTicketResponse;
import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.davcai.lottery.platform.client.model.OrderTicketResponse4OneLoop;
import com.xhcms.lottery.commons.data.Ticket;

public interface ILotteryPlatformOrderTicketClient extends ILotteryPlatformClient{

	/**
	 * 将tickets按照平台要求的每轮最多票数分成n轮发送
	 * @param tickets
	 * @return
	 */
	OrderTicketResponse orderTicketByLoops(List<Ticket> tickets);
	/**
	 * 将tickets一轮发送
	 * @param tickets
	 * @return
	 */
	OrderTicketResponse4OneLoop orderTicketForOneLoop(List<Ticket> tickets);
	

}
