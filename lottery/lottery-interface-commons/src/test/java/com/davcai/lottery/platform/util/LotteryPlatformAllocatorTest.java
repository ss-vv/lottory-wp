package com.davcai.lottery.platform.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mockit.Expectations;
import mockit.Mocked;

import org.junit.Before;
import org.junit.Test;

import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.davcai.lottery.platform.util.model.LotteryPlatformPriority;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class LotteryPlatformAllocatorTest {
	
	private ILotteryPlatformAllocator<Ticket> allocator;
	@Mocked ILotteryPlatformPriorityService lotteryPlatformPriorityService ;
	
	@Before
	public void setUp(){
		allocator=new LotteryPlatformTicketAllocatorImpl();
		((LotteryPlatformTicketAllocatorImpl)allocator).setLotteryPlatformPriorityService(lotteryPlatformPriorityService);
	}
	
	@Test
	public void testAllocateTicketsWithEmptyTickets(){
		List<Ticket> tickets = null;
		Map<String, List<Ticket>> result = allocator.allocateByPriority(tickets);
		assertTrue(null==result);
		
		tickets = new ArrayList<Ticket>();
		result = allocator.allocateByPriority(tickets);
		assertTrue(null==result);
	}
	
	@Test
	public void testAllocateOneTicket(){
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket = new Ticket();
		ticket.setLotteryId(LotteryId.JCZQ);
		tickets.add(ticket);
		
		final LotteryInterfaceName expectedInterfaceName=LotteryInterfaceName.orderTicket;
		final LotteryId expectedLotteryId=LotteryId.JCZQ;
		final List<LotteryPlatformPriority> expectedLotteryPlatformPrioritys=new ArrayList<LotteryPlatformPriority>();
		LotteryPlatformPriority lotteryPlatformPriority=new LotteryPlatformPriority();
		lotteryPlatformPriority.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		lotteryPlatformPriority.setLotteryId(LotteryId.JCZQ.toString());
		lotteryPlatformPriority.setPriority(2);
		
		LotteryPlatformPriority lotteryPlatformPriority2=new LotteryPlatformPriority();
		lotteryPlatformPriority2.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		lotteryPlatformPriority2.setLotteryId(LotteryId.JCZQ.toString());
		lotteryPlatformPriority2.setPriority(1);
		
		expectedLotteryPlatformPrioritys.add(lotteryPlatformPriority);
		expectedLotteryPlatformPrioritys.add(lotteryPlatformPriority2);
		
		new Expectations() {{
			lotteryPlatformPriorityService.loadAll(withEqual(expectedInterfaceName),withEqual(expectedLotteryId)); 
			result = expectedLotteryPlatformPrioritys; 
		}};
		Map<String, List<Ticket>> result = allocator.allocateByPriority(tickets);
		assertTrue(result.size()==2);
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).size()==1);
		assertTrue(result.get(LotteryPlatformId.ZUN_AO).size()==0);
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).get(0)==ticket);
	}
	
	

}
