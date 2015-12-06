package com.davcai.lottery.platform.client;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.davcai.lottery.platform.client.model.OrderTicketResponse;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class QueryPrizeClientWithMultiPlatformTest {
	@Test
	public void testQueryPrizeWithAnRuiZhiYing(){
		IQueryPrizeClientWithMultiPlatform client=new QueryPrizeClientWithMultiPlatformImpl();
		List<Ticket> tickets = null;
		String lotteryPlatformId = LotteryPlatformId.AN_RUI_ZHI_YING;
		QueryPrizeResponse response = client.queryPrize(lotteryPlatformId, tickets);
		System.out.println("response="+response);
		assertTrue(null!=response);
	}
	
	@Test
	public void testOrderTicketWithZunAo(){
		IQueryPrizeClientWithMultiPlatform client=new QueryPrizeClientWithMultiPlatformImpl();
		List<Ticket> tickets = null;
		String lotteryPlatformId = LotteryPlatformId.ZUN_AO;
		QueryPrizeResponse response = client.queryPrize(lotteryPlatformId,tickets);
		System.out.println("response="+response);
		assertTrue(null!=response);
	}

}
