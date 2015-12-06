package com.davcai.lottery.platform.client;

import java.util.List;

import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.xhcms.lottery.commons.data.Ticket;

public interface IOrderTicketClientWithMultiPlatform {

	/**
	 * 为多平台准备的投注接口,将票列表按照各平台的要求分成n轮发送，每一轮都对应返回一个OrderTicketResponse4OneLoop
	 * @param lotteryPlatformId 
	 * @param tickets
	 * @return
	 */
	OrderTicketResponse orderTicket(String lotteryPlatformId, List<Ticket> tickets);

}
