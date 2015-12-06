package com.davcai.lottery.platform.util;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.davcai.lottery.platform.util.exception.IllegalPriorityListException;
import com.davcai.lottery.platform.util.model.LotteryPlatformPriority;
import com.davcai.lottery.platform.util.model.PriorityListAndTicketsToAllocate;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class AllocatorUtilTest {
	
	
	@Test
	public void testWhenTotalTicketNumberIsZeroOrPriorityListIsBlank(){
		List<LotteryPlatformPriority> priorityList=null;
		int totalTicketNumber=11;
		Map<String, Integer> result = AllocatorUtil.computeShouldArrangeNumber(priorityList, totalTicketNumber);
		assertTrue(null==result);
		
		priorityList=new ArrayList<LotteryPlatformPriority>();
		totalTicketNumber=11;
		result = AllocatorUtil.computeShouldArrangeNumber(priorityList, totalTicketNumber);
		assertTrue(null==result);
		
		priorityList=new ArrayList<LotteryPlatformPriority>();
		LotteryPlatformPriority lotteryPlatformPriority=new LotteryPlatformPriority();
		lotteryPlatformPriority.setPriority(4);
		lotteryPlatformPriority.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		LotteryPlatformPriority lotteryPlatformPriority2=new LotteryPlatformPriority();
		lotteryPlatformPriority2.setPriority(2);
		lotteryPlatformPriority2.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		priorityList.add(lotteryPlatformPriority);
		priorityList.add(lotteryPlatformPriority2);
		totalTicketNumber=0;
		result = AllocatorUtil.computeShouldArrangeNumber(priorityList, totalTicketNumber);
		assertTrue(null!=result);
		assertTrue(result.size()==2);
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING)==0);
		assertTrue(result.get(LotteryPlatformId.ZUN_AO)==0);
	}
	
	@Test
	public void testComputeShouldArrangeNumber4OneLotteryPlatform(){
		List<LotteryPlatformPriority> priorityList=new ArrayList<LotteryPlatformPriority>();
		LotteryPlatformPriority lotteryPlatformPriority=new LotteryPlatformPriority();
		lotteryPlatformPriority.setPriority(4);
		lotteryPlatformPriority.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		priorityList.add(lotteryPlatformPriority);
		int totalTicketNumber=11;
		Map<String, Integer> result = AllocatorUtil.computeShouldArrangeNumber(priorityList, totalTicketNumber);
		//当只有一个彩票平台时，全部分配给该平台
		assertTrue(result.size()==1);
		assertTrue(result.keySet().contains(lotteryPlatformPriority.getLotteryPlatformId()));
		assertTrue(result.values().contains(new Integer(totalTicketNumber)));
		checkTotalTicketsEqualsTotalArrangedTickets(totalTicketNumber, result);
	}

	private void checkTotalTicketsEqualsTotalArrangedTickets(
			int totalTicketNumber, Map<String, Integer> result) {
		int totalArrangedTicket=0;
		for(Integer countOfArrangedTicket:result.values()){
			totalArrangedTicket=totalArrangedTicket+countOfArrangedTicket;
		}
		assertTrue(totalArrangedTicket==totalTicketNumber);
	}
	
	@Test
	public void testComputeShouldArrangeNumber4ManyLotteryPlatformAndTotalTicketNumberBiggerThanTotalPriority(){
		List<LotteryPlatformPriority> priorityList=new ArrayList<LotteryPlatformPriority>();
		LotteryPlatformPriority lotteryPlatformPriority=new LotteryPlatformPriority();
		lotteryPlatformPriority.setPriority(4);
		lotteryPlatformPriority.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		
		LotteryPlatformPriority lotteryPlatformPriority2=new LotteryPlatformPriority();
		lotteryPlatformPriority2.setPriority(1);
		lotteryPlatformPriority2.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		
		LotteryPlatformPriority lotteryPlatformPriority3=new LotteryPlatformPriority();
		lotteryPlatformPriority3.setPriority(1);
		lotteryPlatformPriority3.setLotteryPlatformId(LotteryPlatformId.OTHER);
		
		priorityList.add(lotteryPlatformPriority3);
		priorityList.add(lotteryPlatformPriority);
		priorityList.add(lotteryPlatformPriority2);
		
		
		int totalTicketNumber=11;
		Map<String, Integer> result = AllocatorUtil.computeShouldArrangeNumber(priorityList, totalTicketNumber);
		assertTrue(result.size()==priorityList.size());
		
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING)==7);
		assertTrue(result.get(LotteryPlatformId.ZUN_AO)==2);
		assertTrue(result.get(LotteryPlatformId.OTHER)==2);
		checkTotalTicketsEqualsTotalArrangedTickets(totalTicketNumber, result);
	}
	
	@Test
	public void testComputeShouldArrangeNumber4ManyLotteryPlatformAndTotalTicketNumberLessThanTotalPriority(){
		List<LotteryPlatformPriority> priorityList=new ArrayList<LotteryPlatformPriority>();
		LotteryPlatformPriority lotteryPlatformPriority=new LotteryPlatformPriority();
		lotteryPlatformPriority.setPriority(4);
		lotteryPlatformPriority.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		
		LotteryPlatformPriority lotteryPlatformPriority2=new LotteryPlatformPriority();
		lotteryPlatformPriority2.setPriority(1);
		lotteryPlatformPriority2.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		
		priorityList.add(lotteryPlatformPriority);
		priorityList.add(lotteryPlatformPriority2);
		
		int totalTicketNumber=4;
		Map<String, Integer> result = AllocatorUtil.computeShouldArrangeNumber(priorityList, totalTicketNumber);
		assertTrue(result.size()==priorityList.size());
		
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING)==3);
		
		assertTrue(result.get(LotteryPlatformId.ZUN_AO)==1);
		checkTotalTicketsEqualsTotalArrangedTickets(totalTicketNumber, result);
		
		totalTicketNumber=1;
		result = AllocatorUtil.computeShouldArrangeNumber(priorityList, totalTicketNumber);
		assertTrue(result.size()==priorityList.size());
		
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING)==1);
		assertTrue(result.get(LotteryPlatformId.ZUN_AO)==0);
		checkTotalTicketsEqualsTotalArrangedTickets(totalTicketNumber, result);
		
		
		totalTicketNumber=5;
		result = AllocatorUtil.computeShouldArrangeNumber(priorityList, totalTicketNumber);
		assertTrue(result.size()==priorityList.size());
		
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING)==4);
		assertTrue(result.get(LotteryPlatformId.ZUN_AO)==1);
		checkTotalTicketsEqualsTotalArrangedTickets(totalTicketNumber, result);
		
		
		
	}
	
	@Test
	public void testComputeShouldArrangeNumber4PrioritySamePlatformsAndOnlyOneTicket(){
		List<LotteryPlatformPriority> priorityList=new ArrayList<LotteryPlatformPriority>();
		LotteryPlatformPriority lotteryPlatformPriority=new LotteryPlatformPriority();
		lotteryPlatformPriority.setPriority(1);
		lotteryPlatformPriority.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		
		LotteryPlatformPriority lotteryPlatformPriority2=new LotteryPlatformPriority();
		lotteryPlatformPriority2.setPriority(1);
		lotteryPlatformPriority2.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		
		priorityList.add(lotteryPlatformPriority);
		priorityList.add(lotteryPlatformPriority2);
		
		int totalTicketNumber = 1;
		Map<String, Integer> result = AllocatorUtil.computeShouldArrangeNumber(priorityList, totalTicketNumber);
		assertTrue(result.size()==priorityList.size());
		
		assertTrue((result.get(LotteryPlatformId.AN_RUI_ZHI_YING) == 1 && result
				.get(LotteryPlatformId.ZUN_AO) == 0)
				||(result.get(LotteryPlatformId.AN_RUI_ZHI_YING) == 0
				&& result.get(LotteryPlatformId.ZUN_AO) == 1)
		);
		
		checkTotalTicketsEqualsTotalArrangedTickets(totalTicketNumber, result);

	}
	
	@Test
	public void testComputeShouldArrangeNumber4PrioritySamePlatformsAndTicketIsLessThanTotalPriority(){
		List<LotteryPlatformPriority> priorityList=new ArrayList<LotteryPlatformPriority>();
		LotteryPlatformPriority lotteryPlatformPriority=new LotteryPlatformPriority();
		lotteryPlatformPriority.setPriority(1);
		lotteryPlatformPriority.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		
		LotteryPlatformPriority lotteryPlatformPriority2=new LotteryPlatformPriority();
		lotteryPlatformPriority2.setPriority(1);
		lotteryPlatformPriority2.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		
		
		LotteryPlatformPriority lotteryPlatformPriority3=new LotteryPlatformPriority();
		lotteryPlatformPriority3.setPriority(1);
		lotteryPlatformPriority3.setLotteryPlatformId(LotteryPlatformId.OTHER); 
		
		priorityList.add(lotteryPlatformPriority);
		priorityList.add(lotteryPlatformPriority2);
		priorityList.add(lotteryPlatformPriority3);
		
		
		int totalTicketNumber = 2;
		Map<String, Integer> result = AllocatorUtil.computeShouldArrangeNumber(priorityList, totalTicketNumber);
		assertTrue(result.size()==priorityList.size());
		Map<Integer,Integer> map=new HashMap<Integer,Integer>();
		for(Entry<String, Integer> entry:result.entrySet()){
			if(map.containsKey(entry.getValue())){
				map.put(entry.getValue(), map.get(entry.getValue())+1);
			}
			else{
				map.put(entry.getValue(), 1);
			}
		}
		//这个时候，应该给随机的2个平台各分一张票，另外一个平台不分
		assertTrue((map.get(1) == 2 && map.get(0)==1));
		checkTotalTicketsEqualsTotalArrangedTickets(totalTicketNumber, result);
		

	}
	
	@Test(expected=IllegalPriorityListException.class)
	public void testThrowExceptionWhenPriorityListHaveSameItem(){
		List<LotteryPlatformPriority> priorityList=new ArrayList<LotteryPlatformPriority>();
		LotteryPlatformPriority lotteryPlatformPriority=new LotteryPlatformPriority();
		lotteryPlatformPriority.setPriority(1);
		lotteryPlatformPriority.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		
		LotteryPlatformPriority lotteryPlatformPriority2=new LotteryPlatformPriority();
		lotteryPlatformPriority2.setPriority(1);
		lotteryPlatformPriority2.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		
		LotteryPlatformPriority lotteryPlatformPriority3=new LotteryPlatformPriority();
		lotteryPlatformPriority3.setPriority(1);
		lotteryPlatformPriority3.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		
		priorityList.add(lotteryPlatformPriority);
		priorityList.add(lotteryPlatformPriority2);
		
		int totalTicketNumber = 1;
		AllocatorUtil.computeShouldArrangeNumber(priorityList, totalTicketNumber);
	}
	
	@Test
	public void testArrangeTicketsToMap(){
		List<Ticket> ticketsToAllocate = new ArrayList<Ticket>();
		Ticket ticket1=new Ticket();
		ticket1.setId(1L);
		Ticket ticket2=new Ticket();
		ticket2.setId(2L);
		Ticket ticket3=new Ticket();
		ticket3.setId(3L);
		Ticket ticket4=new Ticket();
		ticket4.setId(4L);
		ticketsToAllocate.add(ticket1);
		ticketsToAllocate.add(ticket2);
		ticketsToAllocate.add(ticket3);
		ticketsToAllocate.add(ticket4);
		Map<String, Integer> shouldArrangeToPriorityList = new HashMap<String, Integer>();
		shouldArrangeToPriorityList.put(LotteryPlatformId.AN_RUI_ZHI_YING, 2);
		shouldArrangeToPriorityList.put(LotteryPlatformId.ZUN_AO, 2);
		shouldArrangeToPriorityList.put(LotteryPlatformId.OTHER, 0);
		Map<String, List<Ticket>> result = AllocatorUtil.arrangeTicketsToMap(ticketsToAllocate, shouldArrangeToPriorityList);
		System.out.println("result="+result);
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).size()==2);
		assertTrue(result.get(LotteryPlatformId.ZUN_AO).size()==2);
		assertTrue(!result.containsKey(LotteryPlatformId.OTHER));
		checkListItemLotteryPlatformId(result,LotteryPlatformId.AN_RUI_ZHI_YING);
		
		checkListItemLotteryPlatformId(result,LotteryPlatformId.ZUN_AO);
	}

	private void checkListItemLotteryPlatformId(Map<String, List<Ticket>> result,String lotteryPlatformId) {
		List<Ticket> anRuiZhiYingList = result.get(lotteryPlatformId);
		for(Ticket ticket:anRuiZhiYingList){
			assertTrue(StringUtils.equals(ticket.getLotteryPlatformId(), lotteryPlatformId));
		}
	}
	
	@Test
	public void testArrangeTicketsToMapWhenTicketsOrShouldArrangeToPriorityListIsBlank(){
		List<Ticket> ticketsToAllocate=null;
		Map<String, Integer> shouldArrangeToPriorityList=null;
		Map<String, List<Ticket>> result = AllocatorUtil.arrangeTicketsToMap(ticketsToAllocate, shouldArrangeToPriorityList);
		assertTrue(null==result);
		ticketsToAllocate=new ArrayList<Ticket>();
		shouldArrangeToPriorityList=new HashMap<String, Integer>();
		result = AllocatorUtil.arrangeTicketsToMap(ticketsToAllocate, shouldArrangeToPriorityList);
		assertTrue(null==result);
	}
	
	@Test
	public void testExtractZunAoTickets(){
		List<Ticket> ticketsToAllocate=new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		Ticket ticket2=new Ticket();
		Map<String, String> platformBetCode=new HashMap<String, String>();
		platformBetCode.put(LotteryPlatformId.AN_RUI_ZHI_YING, "62887~[让球胜]~0/62888~[0,1]~0");
		platformBetCode.put(LotteryPlatformId.ZUN_AO, "SPF@2-001:[让球胜]/JQS@2-002:[0,1]");
		ticket2.setPlatformBetCodeMap(platformBetCode);
		ticketsToAllocate.add(ticket);
		ticketsToAllocate.add(ticket2);
		List<Ticket> ticketsToZunAo=AllocatorUtil.extractZunAoTickets(ticketsToAllocate);
		System.out.println("ticketsToZunAo:"+ticketsToZunAo);
		System.out.println("ticketsToAllocate:"+ticketsToAllocate);
		assertTrue(null!=ticketsToZunAo&&!ticketsToZunAo.isEmpty());
		assertTrue(null!=ticketsToAllocate&&ticketsToAllocate.size()==1);
	}
	
	
	@Test
	public void testProcessSpecialTickets(){
		List<Ticket> ticketsToAllocate=new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		ticket.setId(1l);
		ticket.setLotteryId(LotteryId.JCZQ);
		Ticket ticket2=new Ticket();
		ticket2.setId(2l);
		Map<String, String> platformBetCodeMap=new HashMap<String, String>();
		platformBetCodeMap.put("zunao", "6-301:[大,小]/6-302:[大,小]/6-303");
		platformBetCodeMap.put("anruizhiying", "63613~[大,小]~0/63614~[大,小]~0/63615~[大]~0");
		ticket2.setPlatformBetCodeMap(platformBetCodeMap);
		
		Ticket ticket3 = new Ticket();
		ticket3.setId(3l);
		ticket3.setPlatformBetCodeMap(platformBetCodeMap);
		
		Ticket ticket7 = new Ticket();
		
		ticket7.setPlatformBetCodeMap(platformBetCodeMap);
		ticket7.setId(7l);
		Ticket ticket4 = new Ticket();
		ticket4.setId(4l);
		ticket4.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		ticket4.setPlatformBetCodeMap(platformBetCodeMap);
		
		Ticket ticket5 = new Ticket();
		ticket5.setId(5l);
		ticket5.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		ticket5.setPlatformBetCodeMap(platformBetCodeMap);
		
		Ticket ticket6 = new Ticket();
		ticket6.setId(6l);
		ticket6.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		ticket6.setPlatformBetCodeMap(platformBetCodeMap);
		
		ticketsToAllocate.add(ticket);
		ticketsToAllocate.add(ticket2);
		ticketsToAllocate.add(ticket3);
		ticketsToAllocate.add(ticket4);
		ticketsToAllocate.add(ticket5);
		ticketsToAllocate.add(ticket6);
		ticketsToAllocate.add(ticket7);
		
		
		List<LotteryPlatformPriority> priorityList=new ArrayList<LotteryPlatformPriority>();
		LotteryPlatformPriority lotteryPlatformPriority=new LotteryPlatformPriority();
		lotteryPlatformPriority.setPriority(1);
		lotteryPlatformPriority.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		
		LotteryPlatformPriority lotteryPlatformPriority2=new LotteryPlatformPriority();
		lotteryPlatformPriority2.setPriority(1);
		lotteryPlatformPriority2.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		
		
		LotteryPlatformPriority lotteryPlatformPriority3=new LotteryPlatformPriority();
		lotteryPlatformPriority3.setPriority(1);
		lotteryPlatformPriority3.setLotteryPlatformId(LotteryPlatformId.OTHER); 
		
		priorityList.add(lotteryPlatformPriority);
		priorityList.add(lotteryPlatformPriority2);
		priorityList.add(lotteryPlatformPriority3);
		Map<String, PriorityListAndTicketsToAllocate> result = AllocatorUtil.mapSpecialTickets(ticketsToAllocate, priorityList, false, null);
		System.out.println("result="+result);
		
		assertTrue(ticketsToAllocate.size()==3);
		
		assertTrue(result.get("onlyToZunAo").getPriorityList().size()==1);
		assertTrue(StringUtils.equals(result.get("onlyToZunAo").getPriorityList().get(0).getLotteryPlatformId(), LotteryPlatformId.ZUN_AO));
		assertTrue(result.get("onlyToZunAo").getTicketsToAllocate().size()==1);
		assertTrue(result.get("onlyToZunAo").getTicketsToAllocate().get(0).getId()==1);
		
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).getPriorityList().size()==2);
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).getPriorityList().get(0).getLotteryPlatformId().equals(LotteryPlatformId.ZUN_AO));
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).getPriorityList().get(1).getLotteryPlatformId().equals(LotteryPlatformId.OTHER));
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).getTicketsToAllocate().size()==1);
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).getTicketsToAllocate().get(0).getId()==6l);
		
		assertTrue(result.get(LotteryPlatformId.ZUN_AO).getPriorityList().size()==2);
		assertTrue(result.get(LotteryPlatformId.ZUN_AO).getPriorityList().get(0).getLotteryPlatformId().equals(LotteryPlatformId.AN_RUI_ZHI_YING));
		assertTrue(result.get(LotteryPlatformId.ZUN_AO).getPriorityList().get(1).getLotteryPlatformId().equals(LotteryPlatformId.OTHER));
		assertTrue(result.get(LotteryPlatformId.ZUN_AO).getTicketsToAllocate().size()==2);
		assertTrue(result.get(LotteryPlatformId.ZUN_AO).getTicketsToAllocate().get(0).getId()==4l);
		assertTrue(result.get(LotteryPlatformId.ZUN_AO).getTicketsToAllocate().get(1).getId()==5l);
	}
	
	@Test
	public void testProcessSpecialTicketsWhenOnlyOnePriority(){
		List<Ticket> ticketsToAllocate=new ArrayList<Ticket>();
		Ticket ticket=new Ticket();
		ticket.setId(1l);
		ticket.setLotteryId(LotteryId.JCZQ);
		
		
		Ticket ticket2=new Ticket();
		ticket2.setId(2l);
		Map<String, String> platformBetCodeMap=new HashMap<String, String>();
		platformBetCodeMap.put("zunao", "6-301:[大,小]/6-302:[大,小]/6-303");
		platformBetCodeMap.put("anruizhiying", "63613~[大,小]~0/63614~[大,小]~0/63615~[大]~0");
		ticket2.setPlatformBetCodeMap(platformBetCodeMap);
		
		Ticket ticket3 = new Ticket();
		ticket3.setId(3l);
		ticket3.setPlatformBetCodeMap(platformBetCodeMap);
		
		Ticket ticket7 = new Ticket();
		
		ticket7.setPlatformBetCodeMap(platformBetCodeMap);
		ticket7.setId(7l);
		
		Ticket ticket4 = new Ticket();
		ticket4.setId(4l);
		ticket4.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		ticket4.setPlatformBetCodeMap(platformBetCodeMap);
		
		Ticket ticket5 = new Ticket();
		ticket5.setId(5l);
		ticket5.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		ticket5.setPlatformBetCodeMap(platformBetCodeMap);
		
		Ticket ticket6 = new Ticket();
		ticket6.setId(6l);
		ticket6.setLotteryPlatformId(LotteryPlatformId.AN_RUI_ZHI_YING);
		ticket6.setPlatformBetCodeMap(platformBetCodeMap);
		
		ticketsToAllocate.add(ticket);
		ticketsToAllocate.add(ticket2);
		ticketsToAllocate.add(ticket3);
		ticketsToAllocate.add(ticket4);
		ticketsToAllocate.add(ticket5);
		ticketsToAllocate.add(ticket6);
		ticketsToAllocate.add(ticket7);
		
		
		List<LotteryPlatformPriority> priorityList=new ArrayList<LotteryPlatformPriority>();
		
		LotteryPlatformPriority lotteryPlatformPriority2=new LotteryPlatformPriority();
		lotteryPlatformPriority2.setPriority(1);
		lotteryPlatformPriority2.setLotteryPlatformId(LotteryPlatformId.ZUN_AO);
		
		
		
		priorityList.add(lotteryPlatformPriority2);
		Map<String, PriorityListAndTicketsToAllocate> result = AllocatorUtil.mapSpecialTickets(ticketsToAllocate, priorityList, false, null);
		System.out.println("result="+result);
		for(Ticket ticketToAllocate:ticketsToAllocate){
			System.out.println("ticketToAllocate="+ticketToAllocate);
		}
		assertTrue(ticketsToAllocate.size()==5);
		
		assertTrue(result.get("onlyToZunAo").getPriorityList().size()==1);
		assertTrue(StringUtils.equals(result.get("onlyToZunAo").getPriorityList().get(0).getLotteryPlatformId(), LotteryPlatformId.ZUN_AO));
		assertTrue(result.get("onlyToZunAo").getTicketsToAllocate().size()==1);
		assertTrue(result.get("onlyToZunAo").getTicketsToAllocate().get(0).getId()==1);
		
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).getPriorityList().size()==1);
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).getPriorityList().get(0).getLotteryPlatformId().equals(LotteryPlatformId.ZUN_AO));
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).getTicketsToAllocate().size()==1);
		assertTrue(result.get(LotteryPlatformId.AN_RUI_ZHI_YING).getTicketsToAllocate().get(0).getId()==6l);
		
		assertFalse(result.containsKey(LotteryPlatformId.ZUN_AO));
	}
	
}
