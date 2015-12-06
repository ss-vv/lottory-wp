package com.davcai.lottery.platform.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryId;

public class TicketGroupUtilTest {
	
	@Test
	public void testGroupByLotteryIdWithBlankTickets(){
		List<Ticket> tickets = null;
		Map<LotteryId, List<Ticket>> result = TicketGroupUtil.groupByLotteryId(tickets);
		assertTrue(null==result);
		
		tickets = new ArrayList<Ticket>();
		result = TicketGroupUtil.groupByLotteryId(tickets);
		assertTrue(null==result);
	}
	
	@Test
	public void testGroupByLotteryId(){
		List<Ticket> tickets = new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		ticket.setPlayId("06_LC_2");
		
		Ticket ticket2=new Ticket();
		ticket2.setPlayId("06_LC_2");
		
		Ticket ticket3=new Ticket();
		ticket3.setPlayId("01_ZC_1");
		
		Ticket ticket4=new Ticket();
		ticket4.setPlayId("80_ZC_2");
		
		Ticket ticket5=new Ticket();
		ticket5.setPlayId("");
		
		tickets.add(ticket);
		tickets.add(ticket2);
		tickets.add(ticket3);
		tickets.add(ticket4);
		tickets.add(ticket5);
		
		Map<LotteryId, List<Ticket>> result = TicketGroupUtil.groupByLotteryId(tickets);
		System.out.println("result="+result);
		assertTrue(result.size()>0);
	}

}
