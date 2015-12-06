package com.xhcms.lottery.dc.persist.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.BetSchemeWithIssueInfoPO;
import com.xhcms.lottery.dc.persist.service.TicketService;
import com.xhcms.lottery.lang.Constants;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-service.xml","/spring-db.xml"})
public class TicketServiceImplTest {

	@Autowired
	private TicketService ticketService;
	
	@Test
	public void fake(){}
	
//	@Test
	public void testListJCNotPrizeTicketGroupByPlayIdAndDate() {
		@SuppressWarnings("unused")
		List<Ticket> tickets = ticketService.listZunAoJCNotPrizeTicketGroupByPlayIdAndDate();
	}
	
//	@Test
	public void testListHFNotPrizeTicketGroupByPlayIdAndIssue() {
		@SuppressWarnings("unused")
		List<Ticket> tickets = ticketService.listHFNotPrizeTicketGroupByPlayIdAndIssue();
	}
	
//	@Test
	public void testListJCAllowBuySchemesGroupByPlayId(){
		Map<String, List<BetSchemePO>> schemes = ticketService.listJCAllowBuySchemesGroupByPlayId();
		System.out.println(schemes);
		System.out.println("schemes size="+schemes.size());
	}
	
//	@Test
	public void testListJCAllowBuySchemesGroupByPlayIdWhichLotteryIdIsInTargetList(){
		List<String> targetLotteryIdList=new ArrayList<String>();
		targetLotteryIdList.add(Constants.JCLQ);
		targetLotteryIdList.add(Constants.JCZQ);
		Map<String, List<BetSchemePO>> schemes = ticketService.listAllowBuySchemesWhereLotteryIdInTargetListGroupByPlayId(targetLotteryIdList);
		System.out.println(schemes);
		System.out.println("schemes size="+schemes.size());
	}
	
//	@Test
	public void testListHFAllowBuySchemesGroupByPlayId(){
		Map<String, List<BetSchemeWithIssueInfoPO>> schemes = ticketService.listHFAllowBuySchemesGroupByPlayId();
		System.out.println(schemes);
		System.out.println("schemes size="+schemes.size());
	}
	
//	@Test
	public void testListHFAllowBuySchemesGroupByPlayIdWhichLotteryIdIsInTargetList(){
		List<String> targetLotteryIdList=new ArrayList<String>();
		targetLotteryIdList.add(Constants.JCLQ);
		targetLotteryIdList.add(Constants.JCZQ);
		Map<String, List<BetSchemeWithIssueInfoPO>> schemes = ticketService.listHFAllowBuySchemesWhereLotteryIdInTargetListGroupByPlayId(targetLotteryIdList);
		System.out.println(schemes);
		System.out.println("schemes size="+schemes.size());
	}
}
