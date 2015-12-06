package com.davcai.lottery.platform.client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class OrderTicketClientWithMultiPlatformTest {
	@Test
	public void testOrderTicketWithAnRuiZhiYing(){
		IOrderTicketClientWithMultiPlatform client=new OrderTicketClientWithMultiPlatformImpl();
		List<Ticket> tickets = null;
		String lotteryPlatformId = LotteryPlatformId.AN_RUI_ZHI_YING;
		OrderTicketResponse response = client.orderTicket(lotteryPlatformId,tickets);
		System.out.println("response="+response);
		assertTrue(null!=response);
	}
	
	@Test
	public void testOrderTicketWithZunAo(){
		IOrderTicketClientWithMultiPlatform client=new OrderTicketClientWithMultiPlatformImpl();
		List<Ticket> tickets = null;
		String lotteryPlatformId = LotteryPlatformId.ZUN_AO;
		OrderTicketResponse response = client.orderTicket(lotteryPlatformId,tickets);
		System.out.println("response="+response);
		assertTrue(null!=response);
	}

}
