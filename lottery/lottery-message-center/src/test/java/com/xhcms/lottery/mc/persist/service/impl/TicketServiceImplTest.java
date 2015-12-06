package com.xhcms.lottery.mc.persist.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.dao.TicketDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.mc.persist.service.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-db.xml","/spring-service.xml"})
public class TicketServiceImplTest {

	@Autowired
	private TicketService ticketService;
	@Autowired
	private TicketDao ticketDao;
	
	
	@Transactional(value="txManager")
	@Rollback(value=false)
	@Test
	@Ignore
	public void testPrize() {
		HashMap<Long, Ticket> tickets = new HashMap<Long, Ticket>();
		
		TicketPO ticketPO = ticketDao.get(371927L);
		Ticket ticket = new Ticket();
		BeanUtils.copyProperties(ticketPO, ticket);
		ticket.setStatus(EntityStatus.TICKET_NOT_AWARD);
		ticket.setActualStatus(Constants.ZM_PRIZE_WIN);
		ticket.setPreTaxBonus(new BigDecimal(9.9));
		ticket.setAfterTaxBonus(new BigDecimal(9.9));
		tickets.put(ticket.getId(), ticket);
		ticketService.prize(tickets);
	}
	
	
	@Test
	public void testGetSchemeIdList4AutoAward(){
		HashMap<Long, BetSchemePO> sMap=new HashMap<Long, BetSchemePO>();
		BetSchemePO betSchemePO=new BetSchemePO();
		betSchemePO.setAfterTaxBonus(new BigDecimal(9.9));
		betSchemePO.setId(1L);
		betSchemePO.setLotteryId(Constants.JX11);
		betSchemePO.setPlayId("11_J5_R1");
		betSchemePO.setStatus(EntityStatus.TICKET_NOT_AWARD);
		sMap.put(1L, betSchemePO);
		List<Long> resultIds=ticketService.getSchemeIdList4AutoAward(sMap);
		assertTrue(null!=resultIds&&!resultIds.isEmpty());
		assertTrue(resultIds.get(0)==1L);
	}
	
	@Test
	public void testUpdatePlayMatchByPlatformOdds(){
		TicketService ts = new TicketServiceImpl();
		List<Ticket> tickets = new LinkedList<Ticket>();
		Ticket t = setupTicket();
		tickets.add(t);
		Map<String, PlayMatch> matches = setupMatches();
		ts.updatePlayMatchByPlatformOdds(tickets, matches);
		t = tickets.get(0);
		List<PlayMatch> ms = t.getMatches();
		PlayMatch pm1 = ms.get(0);
		assertEquals("-5.5", pm1.getConcedePoints());
		assertEquals("1.68", pm1.getResultOdd());
		PlayMatch pm2 = ms.get(1);
		assertTrue(StringUtils.isEmpty(pm2.getConcedePoints()));
		assertEquals("3.4", pm2.getResultOdd());
		
		// test 2
		tickets = new LinkedList<Ticket>();
		t = setupTicket2();
		tickets.add(t);
		matches = setupMatches2();
		ts.updatePlayMatchByPlatformOdds(tickets, matches);
		t = tickets.get(0);
		ms = t.getMatches();
		pm1 = ms.get(0);
		assertTrue(StringUtils.isEmpty(pm1.getConcedePoints()));
		assertEquals("2.5,3.25,2.4", pm1.getResultOdd());
		pm2 = ms.get(1);
		assertTrue(StringUtils.isEmpty(pm2.getConcedePoints()));
		assertEquals("9.5,4.2,3.3,3.9,5.7,10,22,30", pm2.getResultOdd());
		PlayMatch pm3 = ms.get(2);
		assertTrue(StringUtils.isEmpty(pm3.getConcedePoints()));
		assertEquals("3.1,14,34,5.2,5.1,7.5,24,14,5.1", pm3.getResultOdd());
	}


	private Map<String, PlayMatch> setupMatches2() {
		Map<String, PlayMatch> map = new HashMap<String, PlayMatch>();
		PlayMatch pm = new PlayMatch();
		pm.setCode("3001310");
		map.put("3001", pm);
		pm = new PlayMatch();
		pm.setCode("300201234567");
		map.put("3002", pm);
		pm = new PlayMatch();
		pm.setCode("3003333130131110030100");
		map.put("3003", pm);
		return map;
	}


	private Ticket setupTicket2() {
		Ticket t = new Ticket();
		t.setPlayId("05_ZC_2");
		t.setCode("3001310:SPF-300201234567:JQS-3003333130131110030100:BQC");
		t.setActualCode("SPF@3-001:[胜,平,负]/JQS@3-002:[0,1,2,3,4,5,6,7+]/BQC@3-003:[胜-胜,胜-平,胜-负,平-胜,平-平,平-负,负-胜,负-平,负-负]");
		t.setOdds("SPF@3-001:[胜=2.5,平=3.25,负=2.4]/JQS@3-002:[0=9.5,1=4.2,2=3.3,3=3.9,4=5.7,5=10,6=22,7+=30]/BQC@3-003:[胜胜=3.1,胜平=14,胜负=34,平胜=5.2,平平=5.1,平负=7.5,负胜=24,负平=14,负负=5.1]");
		t.setActualOdds("SPF@3-001:[胜=2.5,平=3.25,负=2.4]/JQS@3-002:[0=9.5,1=4.2,2=3.3,3=3.9,4=5.7,5=10,6=22,7+=30]/BQC@3-003:[胜胜=3.1,胜平=14,胜负=34,平胜=5.2,平平=5.1,平负=7.5,负胜=24,负平=14,负负=5.1]");
		return t;
	}


	/**
	 * for testUpdatePlayMatchByPlatformOdds().
	 */
	private Ticket setupTicket() {
		Ticket t = new Ticket();
		t.setPlayId("10_LC_2");
		t.setCode("53051:RFSF-53062:SF-530715:FC");
		t.setActualCode("RFSF@5-305:[主胜]/SF@5-306:[客胜]/FC@5-307:[负21-25]");
		t.setOdds("RFSF@5-305:[主胜=1.68^-5.5]/SF@5-306:[客胜=3.4]/FC@5-307:[负21-25=120]");
		t.setActualOdds("RFSF@5-305:[主胜=1.68^-5.5]/SF@5-306:[客胜=3.4]/FC@5-307:[负21-25=120]");
		return t;
	}


	/**
	 * for testUpdatePlayMatchByPlatformOdds()
	 */
	private Map<String, PlayMatch> setupMatches() {
		Map<String, PlayMatch> map = new HashMap<String, PlayMatch>();
		PlayMatch pm = new PlayMatch();
		pm.setCode("53051");
		map.put("5305", pm);
		pm = new PlayMatch();
		pm.setCode("53062");
		map.put("5306", pm);
		pm = new PlayMatch();
		pm.setCode("530715");
		map.put("5307", pm);
		return map;
	}
}
