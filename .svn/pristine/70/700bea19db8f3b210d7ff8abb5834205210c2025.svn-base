package com.xhcms.lottery.mc.persist.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.test.DbUnitTestBase;
import com.unison.test.SchemeUtils;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.PhantomService;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.mc.persist.service.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/spring-*.xml")
public class PhantomServiceImplTest extends DbUnitTestBase{

	@Autowired
	private PhantomService pservice;
	@Autowired
	private SchemeUtils sutil;
	@Autowired
	private TicketService ticketService;
	
	private final int SU = 25;
	private final int DU = 441;
	private final int DU2 = 13;
	
	@Override
	public List<String> getDataSetNames() {
		return Arrays.asList(new String[]{
				"/dbunit/lt_pu.xml",
				"/dbunit/lt_ps.xml",
				"/dbunit/fb_match.xml",
				"/dbunit/fb_match_play.xml"
		});
	}

	@Override
	public List<String> getDataSetNames2() {
		return Arrays.asList(new String[]{
				"/dbunit/lt_account.xml",
		});
	}
	
	@Test
	public void testIsDoll() {
		assertTrue(pservice.isDoll(DU, PlayType.JCZQ_SPF));
		assertTrue(pservice.isDoll(DU2, PlayType.JCLQ_DXF));
		assertFalse(pservice.isDoll(DU, PlayType.CTZC_14));
		assertTrue(pservice.isDoll(DU, PlayType.JX11_D2));
		assertFalse(pservice.isDoll(15, PlayType.JCZQ_BF));
	}

	@Test
	public void testIsShadow() {
		assertTrue(pservice.isShadow(SU));
		assertFalse(pservice.isShadow(100));
	}

	@Test
	public void testGetSUID() {
		long suid = pservice.getSUID();
		assertTrue(suid!=0);
	}

	@Test
	public void onBetSchemeNoDollNoShadow() {
		BetScheme ds = sutil.createJCZQScheme(198, "201211014002-40023-false,201211024003-40033-false,201211024005-40053-false");
		BetScheme shadow = pservice.onBetScheme(ds);
		assertTrue(ds.getId() == shadow.getId());
	}

	@Test
	public void onBetSchemeDollShouldCopyShadow() {
		BetScheme ds = sutil.createJCZQScheme(DU, "201211014002-40023-false,201211024003-40033-false,201211024005-40053-false");
		BetSchemePO shadow = pservice.getShadowByDoll(ds.getId());
		assertTrue(ds.getId() != shadow.getId());
		assertTrue(ds.getSponsorId() != shadow.getSponsorId());
		System.out.println("ds notes: " + ds.getBetNote() + ", ds mult: " + ds.getMultiple() + ", s notes: " + shadow.getBetNote());
		assertTrue(ds.getBetNote() == shadow.getBetNote()*ds.getMultiple());
	}
	
	@Test
	public void onBetSchemeShadowShouldBeSingleMultiple() {
		BetScheme ds = sutil.createJCZQScheme(DU, "201211014002-40023-false,201211024003-40033-false,201211024005-40053-false");
		BetSchemePO shadow = pservice.getShadowByDoll(ds.getId());
		assertTrue(shadow.getMultiple()==1);
	}
	
	@Test
	public void onBetSchemeShouldInsertPsTable() {
		BetScheme ds = sutil.createJCZQScheme(DU, "201211014002-40023-false,201211024003-40033-false,201211024005-40053-false");
		BetSchemePO shadow = pservice.getShadowByDoll(ds.getId());
		BetSchemePO doll = pservice.getDollByShadow(shadow.getId());
		assertNotNull(doll);
		assertTrue(doll.getId() == ds.getId());
	}
	/*
	@Test
	// 送票时，检查方案是否为doll方案，是则设置票的状态为“出票成功”，并执行扣款、改方案状态的动作。 
	public void onSendBuySchemeShouldChageDollSchemeStatus() {
		BetScheme ds = sutil.createJCZQScheme(DU, "201211014002-40023-false,201211024003-40033-false,201211024005-40053-false");
		BetSchemePO bs = new BetSchemePO();
		BeanUtils.copyProperties(ds, bs);
		pservice.onSendBuyScheme(bs);
	}
	
	@Test
	// 送票时，检查方案是否为doll方案，是则设置票的状态为“出票成功”，并执行扣款、改方案状态的动作。 
	public void onSendBuySchemeShouldSetTicketStatusToSended() {
		
	}

	@Test
	// 送票时，检查方案是否为doll方案，是则设置票的状态为“出票成功”，并执行扣款、改方案状态的动作。 
	public void onSendBuySchemeShouldDeductDollSchemeMoney() {
		
	}
	*/
	
	@Test
	public void sucShadowOnBuyCompShouldChangeDollStatus() {
		BetScheme ds = sutil.createJCZQScheme(DU, "201211014002-40023-false,201211024003-40033-false,201211024005-40053-false");
		assertTrue(ds.getStatus()==EntityStatus.TICKET_INIT);
		BetSchemePO shadow = pservice.getShadowByDoll(ds.getId());
		checkTicketsOnBuySuccess(shadow);
		
		BetSchemePO doll = pservice.getDollByShadow(shadow.getId());
		assertTrue("doll status: "+doll.getSaleStatus(), 
				doll.getStatus() == EntityStatus.TICKET_BUY_SUCCESS);
		
		List<Ticket> dts = ticketService.listTicketsOfScheme(doll.getId());
		for (Ticket t : dts){
			assertTrue(String.format("Ticket(%d) fail.", t.getId()), 
					t.getStatus() == EntityStatus.TICKET_BUY_SUCCESS);
			assertNotNull(String.format("Ticket(%d) odds is null.", t.getId()), 
					t.getOdds());
			assertEquals(20, t.getNumber().length());
		}
	}

	// 要求票都是 3串1 的单选。
	private void checkTicketsOnBuySuccess(BetSchemePO scheme) {
		List<Ticket> tickets = ticketService.listTicketsOfScheme(scheme.getId());
		List<Long> ids = new LinkedList<Long>();
		for (Ticket t : tickets){
			ids.add(t.getId());
		}
		ticketService.updateTicketsStatusTo(EntityStatus.TICKET_REQUIRED, 0, ids);
		Map<Long, Ticket> idTicketMap = new HashMap<Long, Ticket>();
		for (Ticket t : tickets){
			t.setStatus(EntityStatus.TICKET_BUY_SUCCESS);
			t.setActualStatus(0);
			t.setOdds("1.88-1.92-2.1");
			t.setActualOdds("1-002:[胜=1.88]/1-003:[胜=1.92]/1-005:[胜=2.1]");
			t.setNumber("123456");
			t.setPlatformId(RandomUtils.nextLong());
			idTicketMap.put(t.getId(), t);
			t.setPlayId(t.getPlayId());
			t.setLotteryId(PlayType.valueOfLcPlayId(t.getPlayId()).getLotteryId());
		}
		ticketService.check(idTicketMap);
	}

	@Test
	public void shadowOnPrizedShouldPrizeDoll() {
		BetScheme ds = sutil.createJCZQScheme(DU, "201211014002-40023-false,201211024003-40033-false,201211024005-40053-false");
		assertTrue(ds.getStatus()==EntityStatus.TICKET_INIT);
		BetSchemePO shadow = pservice.getShadowByDoll(ds.getId());
		checkTicketsOnBuySuccess(shadow);
		double prize = 10.0;
		prizeScheme(shadow, prize);
		
		BetSchemePO doll = pservice.getDollByShadow(shadow.getId());
		assertTrue(doll.getStatus() == EntityStatus.TICKET_NOT_AWARD);
		List<Ticket> tickets = ticketService.listTicketsOfScheme(doll.getId());
		for (Ticket t : tickets){
			assertTrue(t.getStatus() == EntityStatus.TICKET_NOT_AWARD);
			assertTrue(t.getAfterTaxBonus().compareTo(new BigDecimal(prize*doll.getMultiple()))==0);
			assertTrue(t.getPreTaxBonus().compareTo(new BigDecimal(prize*doll.getMultiple()))==0);
		}
	}

	private void prizeScheme(BetSchemePO shadow, double prize) {
		List<Ticket> tickets = ticketService.listTicketsOfScheme(shadow.getId());
		Map<Long, Ticket> idTicketMap = new HashMap<Long, Ticket>();
		for (Ticket t : tickets){
			t.setStatus(EntityStatus.TICKET_NOT_AWARD);
			t.setAfterTaxBonus(new BigDecimal(prize));
			t.setPreTaxBonus(new BigDecimal(prize));
			idTicketMap.put(t.getId(), t);
		}
		ticketService.prize(idTicketMap );
	}
}
