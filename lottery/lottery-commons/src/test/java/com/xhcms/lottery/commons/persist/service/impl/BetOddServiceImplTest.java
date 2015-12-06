package com.xhcms.lottery.commons.persist.service.impl;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.lang.OddAndActualOdd;
import com.xhcms.lottery.commons.persist.entity.TicketPO;

public class BetOddServiceImplTest {
	
	
	@Test
	public void testConvert01_ZC_2(){
		BetOddsServiceImpl betOddServiceImpl=new BetOddsServiceImpl();
		TicketPO ticketPO=new TicketPO();
		ticketPO.setCode("60013-60020-60033-60040");
		ticketPO.setPassTypeId("4@1");
		ticketPO.setPlayId("01_ZC_2");
		BetScheme scheme=new BetScheme();
		scheme.setLotteryId("JCZQ");
		scheme.setPlayId("05_ZC_2");
		scheme.setPassTypeIds(",4@1,5@1,6@1,");
		List<BetMatch> matchs=new ArrayList<BetMatch>();
		
		BetMatch match1=new BetMatch();
		match1.setCode("60013");
		match1.setPlayId("01_ZC_2");
		match1.setOdds("1.65");
		
		BetMatch match2=new BetMatch();
		match2.setCode("60020");
		match2.setPlayId("01_ZC_2");
		match2.setOdds("2.49");
		
		BetMatch match3=new BetMatch();
		match3.setCode("60033");
		match3.setPlayId("01_ZC_2");
		match3.setOdds("1.71");
		
		BetMatch match4=new BetMatch();
		match4.setCode("60040");
		match4.setPlayId("01_ZC_2");
		match4.setOdds("1.52");
		
		BetMatch match5=new BetMatch();
		match5.setCode("60050");
		match5.setPlayId("80_ZC_2");
		match5.setOdds("3.30");
		
		BetMatch match6=new BetMatch();
		match6.setCode("60063");
		match6.setPlayId("80_ZC_2");
		match6.setOdds("2.03");
		
		matchs.add(match1);
		matchs.add(match2);
		matchs.add(match3);
		matchs.add(match4);
		matchs.add(match5);
		matchs.add(match6);
		scheme.setMatchs(matchs);
		OddAndActualOdd odds = betOddServiceImpl.convert(ticketPO, scheme);
		System.out.println("odds="+odds);
		assertTrue(StringUtils.equals(odds.getOdds(), "1.65-2.49-1.71-1.52"));
		assertTrue(StringUtils.equals(odds.getActualOdds(), "6-001:[胜=1.65]/6-002:[负=2.49]/6-003:[胜=1.71]/6-004:[负=1.52]"));
	}
	
	@Test
	public void testConvert02_ZC_2(){
		BetOddsServiceImpl betOddServiceImpl=new BetOddsServiceImpl();
		TicketPO ticketPO=new TicketPO();
		ticketPO.setCode("500110-500220-500310");
		ticketPO.setPassTypeId("3@1");
		ticketPO.setPlayId("02_ZC_2");
		BetScheme scheme=new BetScheme();
		scheme.setLotteryId("JCZQ");
		scheme.setPlayId("05_ZC_2");
		scheme.setPassTypeIds(",2@1,3@1,4@1,4@5,");
		List<BetMatch> matchs=new ArrayList<BetMatch>();
		
		BetMatch match1=new BetMatch();
		match1.setCode("500110");
		match1.setPlayId("02_ZC_2");
		match1.setOdds("7.50");
		
		BetMatch match2=new BetMatch();
		match2.setCode("500220");
		match2.setPlayId("02_ZC_2");
		match2.setOdds("12.00");
		
		BetMatch match3=new BetMatch();
		match3.setCode("500310");
		match3.setPlayId("02_ZC_2");
		match3.setOdds("7.00");
		
		BetMatch match4=new BetMatch();
		match4.setCode("50053");
		match4.setPlayId("80_ZC_2");
		match4.setOdds("2.90");
		
		
		matchs.add(match1);
		matchs.add(match2);
		matchs.add(match3);
		matchs.add(match4);
		scheme.setMatchs(matchs);
		OddAndActualOdd odds = betOddServiceImpl.convert(ticketPO, scheme);
		System.out.println("odds="+odds);
		assertTrue(StringUtils.equals(odds.getOdds(), "7.50-12.00-7.00"));
		assertTrue(StringUtils.equals(odds.getActualOdds(), "5-001:[1:0=7.50]/5-002:[2:0=12.00]/5-003:[1:0=7.00]"));
	}
	
	@Test
	public void testConvert03_ZC_2(){
		BetOddsServiceImpl betOddServiceImpl=new BetOddsServiceImpl();
		TicketPO ticketPO=new TicketPO();
		ticketPO.setCode("50010-50033-50044-50055");
		ticketPO.setPassTypeId("4@1");
		ticketPO.setPlayId("03_ZC_2");
		BetScheme scheme=new BetScheme();
		scheme.setLotteryId("JCZQ");
		scheme.setPlayId("05_ZC_2");
		scheme.setPassTypeIds(",4@1,5@1,6@1,");
		List<BetMatch> matchs=new ArrayList<BetMatch>();
		
		BetMatch match1=new BetMatch();
		match1.setCode("50010");
		match1.setPlayId("03_ZC_2");
		match1.setOdds("10.00");
		
		BetMatch match2=new BetMatch();
		match2.setCode("50021");
		match2.setPlayId("03_ZC_2");
		match2.setOdds("4.20");
		
		BetMatch match3=new BetMatch();
		match3.setCode("50033");
		match3.setPlayId("03_ZC_2");
		match3.setOdds("3.20");
		
		BetMatch match4=new BetMatch();
		match4.setCode("50044");
		match4.setPlayId("03_ZC_2");
		match4.setOdds("3.20");
		
		BetMatch match5=new BetMatch();
		match5.setCode("50055");
		match5.setPlayId("03_ZC_2");
		match5.setOdds("12.00");
		
		BetMatch match6=new BetMatch();
		match6.setCode("50061");
		match6.setPlayId("80_ZC_2");
		match6.setOdds("3.65");
		
		
		matchs.add(match1);
		matchs.add(match2);
		matchs.add(match3);
		matchs.add(match4);
		matchs.add(match5);
		matchs.add(match6);
		scheme.setMatchs(matchs);
		OddAndActualOdd odds = betOddServiceImpl.convert(ticketPO, scheme);
		System.out.println("odds="+odds);
		assertTrue(StringUtils.equals(odds.getOdds(), "10.00-3.20-3.20-12.00"));
		assertTrue(StringUtils.equals(odds.getActualOdds(), "5-001:[0=10.00]/5-003:[3=3.20]/5-004:[4=3.20]/5-005:[5=12.00]"));
	}
	
	@Test
	public void testConvert04_ZC_2(){
		BetOddsServiceImpl betOddServiceImpl=new BetOddsServiceImpl();
		TicketPO ticketPO=new TicketPO();
		ticketPO.setCode("500133-500231-500330");
		ticketPO.setPassTypeId("3@1");
		ticketPO.setPlayId("04_ZC_2");
		BetScheme scheme=new BetScheme();
		scheme.setLotteryId("JCZQ");
		scheme.setPlayId("05_ZC_2");
		scheme.setPassTypeIds(",2@1,3@1,4@1,");
		List<BetMatch> matchs=new ArrayList<BetMatch>();
		
		BetMatch match1=new BetMatch();
		match1.setCode("500133");
		match1.setPlayId("04_ZC_2");
		match1.setOdds("3.30");
		
		BetMatch match2=new BetMatch();
		match2.setCode("500231");
		match2.setPlayId("04_ZC_2");
		match2.setOdds("13.00");
		
		BetMatch match3=new BetMatch();
		match3.setCode("500330");
		match3.setPlayId("04_ZC_2");
		match3.setOdds("65.00");
		
		BetMatch match5=new BetMatch();
		match5.setCode("50043");
		match5.setPlayId("80_ZC_2");
		match5.setOdds("2.02");
		
		
		
		matchs.add(match1);
		matchs.add(match2);
		matchs.add(match3);
		matchs.add(match5);
		scheme.setMatchs(matchs);
		OddAndActualOdd odds = betOddServiceImpl.convert(ticketPO, scheme);
		System.out.println("odds="+odds);
		assertTrue(StringUtils.equals(odds.getOdds(), "3.30-13.00-65.00"));
		assertTrue(StringUtils.equals(odds.getActualOdds(), "5-001:[胜胜=3.30]/5-002:[胜平=13.00]/5-003:[胜负=65.00]"));
	}
	
	@Test
	public void testConvert80_ZC_2(){
		BetOddsServiceImpl betOddServiceImpl=new BetOddsServiceImpl();
		TicketPO ticketPO=new TicketPO();
		ticketPO.setCode("50013-50021-50033-50040");
		ticketPO.setPassTypeId("4@1");
		ticketPO.setPlayId("80_ZC_2");
		BetScheme scheme=new BetScheme();
		scheme.setLotteryId("JCZQ");
		scheme.setPlayId("05_ZC_2");
		scheme.setPassTypeIds(",4@1,5@1,6@1,");
		List<BetMatch> matchs=new ArrayList<BetMatch>();
		
		BetMatch match1=new BetMatch();
		match1.setCode("50013");
		match1.setPlayId("80_ZC_2");
		match1.setOdds("2.30");
		
		BetMatch match2=new BetMatch();
		match2.setCode("50021");
		match2.setPlayId("80_ZC_2");
		match2.setOdds("3.20");
		
		BetMatch match3=new BetMatch();
		match3.setCode("50033");
		match3.setPlayId("80_ZC_2");
		match3.setOdds("1.21");
		
		BetMatch match4=new BetMatch();
		match4.setCode("50040");
		match4.setPlayId("80_ZC_2");
		match4.setOdds("3.05");
		
		BetMatch match5=new BetMatch();
		match5.setCode("50053");
		match5.setPlayId("01_ZC_2");
		match5.setOdds("1.51");
		
		BetMatch match6=new BetMatch();
		match6.setCode("50060");
		match6.setPlayId("01_ZC_2");
		match6.setOdds("2.75");
		
		
		matchs.add(match1);
		matchs.add(match2);
		matchs.add(match3);
		matchs.add(match4);
		matchs.add(match5);
		matchs.add(match6);
		scheme.setMatchs(matchs);
		OddAndActualOdd odds = betOddServiceImpl.convert(ticketPO, scheme);
		System.out.println("odds="+odds);
		assertTrue(StringUtils.equals(odds.getOdds(), "2.30-3.20-1.21-3.05"));
		assertTrue(StringUtils.equals(odds.getActualOdds(), "5-001:[胜=2.30]/5-002:[平=3.20]/5-003:[胜=1.21]/5-004:[负=3.05]"));
	}
	
	@Test
	public void testConvert06_LC_2(){
		BetOddsServiceImpl betOddServiceImpl=new BetOddsServiceImpl();
		TicketPO ticketPO=new TicketPO();
		ticketPO.setCode("73071-73082");
		ticketPO.setPassTypeId("2@1");
		ticketPO.setPlayId("06_LC_2");
		BetScheme scheme=new BetScheme();
		scheme.setLotteryId("JCLQ");
		scheme.setPlayId("10_LC_2");
		scheme.setPassTypeIds(",2@1,3@1,4@1,");
		List<BetMatch> matchs=new ArrayList<BetMatch>();
		
		BetMatch match1=new BetMatch();
		match1.setCode("73071");
		match1.setPlayId("06_LC_2");
		match1.setOdds("1.79");
		match1.setDefaultScore(-13.5f);
		
		BetMatch match2=new BetMatch();
		match2.setCode("73082");
		match2.setPlayId("06_LC_2");
		match2.setOdds("1.68");
		match2.setDefaultScore(6.50f);
		
		
		
		matchs.add(match1);
		matchs.add(match2);
		scheme.setMatchs(matchs);
		OddAndActualOdd odds = betOddServiceImpl.convert(ticketPO, scheme);
		System.out.println("odds="+odds);
		assertTrue(StringUtils.equals(odds.getOdds(), "1.79-1.68@-13.5B+6.5"));
		assertTrue(StringUtils.equals(odds.getActualOdds(), "7-307:[主胜=1.79^-13.5]/7-308:[客胜=1.68^+6.5]"));
	}
	
	@Test
	public void testConvert08_LC_2(){
		BetOddsServiceImpl betOddServiceImpl=new BetOddsServiceImpl();
		TicketPO ticketPO=new TicketPO();
		ticketPO.setCode("530513-531114");
		ticketPO.setPassTypeId("2@1");
		ticketPO.setPlayId("08_LC_2");
		BetScheme scheme=new BetScheme();
		scheme.setLotteryId("JCLQ");
		scheme.setPlayId("10_LC_2");
		scheme.setPassTypeIds(",2@1,");
		List<BetMatch> matchs=new ArrayList<BetMatch>();
		
		BetMatch match1=new BetMatch();
		match1.setCode("530513");
		match1.setPlayId("08_LC_2");
		match1.setOdds("3.65");
		
		BetMatch match2=new BetMatch();
		match2.setCode("531114");
		match2.setPlayId("08_LC_2");
		match2.setOdds("10.50");
		
		
		
		matchs.add(match1);
		matchs.add(match2);
		scheme.setMatchs(matchs);
		OddAndActualOdd odds = betOddServiceImpl.convert(ticketPO, scheme);
		System.out.println("odds="+odds);
		assertTrue(StringUtils.equals(odds.getOdds(), "3.65-10.50"));
		assertTrue(StringUtils.equals(odds.getActualOdds(), "5-305:[客胜11-15=3.65]/5-311:[客胜16-20=10.50]"));
	}
	
	@Test
	public void testConvert09_LC_2(){
		BetOddsServiceImpl betOddServiceImpl=new BetOddsServiceImpl();
		TicketPO ticketPO=new TicketPO();
		ticketPO.setCode("43012-43022");
		ticketPO.setPassTypeId("2@1");
		ticketPO.setPlayId("09_LC_2");
		BetScheme scheme=new BetScheme();
		scheme.setLotteryId("JCLQ");
		scheme.setPlayId("10_LC_2");
		scheme.setPassTypeIds(",2@1,");
		List<BetMatch> matchs=new ArrayList<BetMatch>();
		
		BetMatch match1=new BetMatch();
		match1.setCode("43012");
		match1.setPlayId("09_LC_2");
		match1.setOdds("1.75");
		match1.setDefaultScore(191.5f);
		
		BetMatch match2=new BetMatch();
		match2.setCode("43022");
		match2.setPlayId("09_LC_2");
		match2.setOdds("1.79");
		match2.setDefaultScore(176.5f);
		
		
		
		matchs.add(match1);
		matchs.add(match2);
		scheme.setMatchs(matchs);
		OddAndActualOdd odds = betOddServiceImpl.convert(ticketPO, scheme);
		System.out.println("odds="+odds);
		assertTrue(StringUtils.equals(odds.getOdds(), "1.75-1.79@191.5B176.5"));
		assertTrue(StringUtils.equals(odds.getActualOdds(), "4-301:[小=1.75^191.5]/4-302:[小=1.79^176.5]"));
	}
	
	@Test
	public void testConvert09_LC_2_DXF() {
		BetOddsServiceImpl betOddServiceImpl = new BetOddsServiceImpl();
		
		TicketPO ticketPO = new TicketPO();
		ticketPO.setCode("43011-43021-43031");
		ticketPO.setPassTypeId("3@1");
		ticketPO.setPlayId("09_LC_2");
		
		
		BetScheme scheme=new BetScheme();
		scheme.setLotteryId("JCLQ");
		scheme.setPlayId("09_LC_2");
		scheme.setPassTypeIds(",3@1,");
		
		
		List<BetMatch> matchs=new ArrayList<BetMatch>();
		
		BetMatch match1=new BetMatch();
		match1.setCode("43011");
		match1.setPlayId("09_LC_2");
		match1.setOdds("1.65");
		match1.setDefaultScore(140.50f);
		
		BetMatch match2=new BetMatch();
		match2.setCode("43021");
		match2.setPlayId("09_LC_2");
		match2.setOdds("1.70");
		match2.setDefaultScore(144.50f);
		
		BetMatch match3=new BetMatch();
		match3.setCode("43031");
		match3.setPlayId("09_LC_2");
		match3.setOdds("1.75");
		match3.setDefaultScore(127.50f);
		
		BetMatch match4=new BetMatch();
		match4.setCode("43041");
		match4.setPlayId("09_LC_2");
		match4.setOdds("1.80");
		match4.setDefaultScore(158.50f);
		
		BetMatch match5=new BetMatch();
		match5.setCode("43051");
		match5.setPlayId("09_LC_2");
		match5.setOdds("1.85");
		match5.setDefaultScore(158.50f);
		
		matchs.add(match1);
		matchs.add(match2);
		matchs.add(match3);
		matchs.add(match4);
		matchs.add(match5);
		
		scheme.setMatchs(matchs);
		OddAndActualOdd odds = betOddServiceImpl.convert(ticketPO, scheme);
		System.out.println("odds="+odds);
	}

}
