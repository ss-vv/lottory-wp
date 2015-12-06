package com.davcai.orderticket.task;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.persist.OrderTicketService;
import com.davcai.lottery.platform.client.IOrderTicketClientWithMultiPlatform;
import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.xhcms.lottery.commons.data.Ticket;

public class OrderTicketTask implements Callable<OrderTicketResponse>{
	
	private String lotteryPlatformId;
	private List<Ticket> tickets;
	@Autowired
	private IOrderTicketClientWithMultiPlatform client;
	
	@Autowired
	private OrderTicketService orderTicketService;

	@Override
	public OrderTicketResponse call() throws Exception {
		OrderTicketResponse result=null;
		if(StringUtils.isNotBlank(lotteryPlatformId)&&null!=tickets&&!tickets.isEmpty()){
			result=client.orderTicket(lotteryPlatformId, tickets);
			orderTicketService.updateTicketsByResponse(result);
		}
		return result;
	}

	public String getLotteryPlatformId() {
		return lotteryPlatformId;
	}

	public void setLotteryPlatformId(String lotteryPlatformId) {
		this.lotteryPlatformId = lotteryPlatformId;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

	public IOrderTicketClientWithMultiPlatform getClient() {
		return client;
	}

	public void setClient(IOrderTicketClientWithMultiPlatform client) {
		this.client = client;
	}

	public OrderTicketService getOrderTicketService() {
		return orderTicketService;
	}

	public void setOrderTicketService(OrderTicketService orderTicketService) {
		this.orderTicketService = orderTicketService;
	}
}
