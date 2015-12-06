package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.TicketPO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-follow-spring.xml")
public class TicketDaoImplTest {

	@Autowired
	TicketDao ticketDao;
	
	@Transactional
	@Test
	public void testSumPrized() {
		LinkedList<Long> schemes = new LinkedList<Long>();
		schemes.add(87979L);
		List<Object[]> data = ticketDao.sumPrized(schemes);
		for (Object[] record : data){
			int i=0;
			for (Object field : record) {
				System.out.println("data["+i+"]" + field);
				i++;
			}
		}
	}
	
	@Transactional
	@Test
	public void testListNotPrizeAndPlayIdInTargetListTicketGroupByPlayIdAndDate() {
		List<String> targetLotteryIds=new ArrayList<String>();
		targetLotteryIds.add("JX11");
		List<TicketPO> result=ticketDao.listNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndDate(targetLotteryIds);
		if(null!=result){
			for (TicketPO ticketPO : result){
				
				System.out.println("ticketPO.id="+ticketPO.getId()+",ticketPO.playId="+ticketPO.getPlayId());
				
			}
		}
		
	}
	
	@Transactional
	@Test
	public void testListNotPrizeAndPlayIdInTargetListTicketGroupByPlayIdAndIssue() {
		List<String> targetLotteryIds=new ArrayList<String>();
		targetLotteryIds.add("JCLQ");
		targetLotteryIds.add("JCZQ");
		List<TicketPO> result=ticketDao.listNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndDate(targetLotteryIds);
		if(null!=result){
			for (TicketPO ticketPO : result){
				
				System.out.println("ticketPO.id="+ticketPO.getId()+",ticketPO.playId="+ticketPO.getPlayId());
				
			}
		}
		
	}
	
	

}
