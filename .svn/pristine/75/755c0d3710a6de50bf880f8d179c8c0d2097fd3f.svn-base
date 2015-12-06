package com.davcai.lottery.persist;

import java.util.List;

import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.unison.lottery.mc.uni.parser.TicketOrderResponse;
import com.xhcms.lottery.commons.data.Ticket;

/**
 * 订购ticket的相关服务。
 * 
 * @author yangbo
 */
public interface OrderTicketService {

	/**
	 * 对成功送给接口的票，更新他们的状态，具体更新的逻辑见:
	 * {@link TicketService#updateTicketStatusForOrder(long, String, String)}
	 * @param responses
	 */
	void updateSuccessfullySendedTickets(List<TicketOrderResponse> responses);

	/**
	 * 对接口没有“确认接收”的票，更新他们的状态为"0-未出票"。
	 * @param tickets   所有待送的票
	 * @param responses 送过去了的票
	 */
	void updateFailSendedTickets(List<Ticket> tickets, List<TicketOrderResponse> responses);

	/**
	 * 修改出票状态和彩票平台id以及对应彩票平台的投注格式
	 * @param result
	 */
	void updateTicketsByResponse(OrderTicketResponse result);
	
}
