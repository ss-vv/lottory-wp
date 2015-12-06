package com.xhcms.lottery.commons.client.translate;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

public class OddsTest {

	@Test
	public void testHHToLCOdds() throws TranslateException {
		String zmOdds = "JQS@5-027:[0=11]";
		Odds o = Odds.parseZMOdds(zmOdds);
		System.out.println(o);
	}
	
	@Test
	public void testToLCOdds() throws TranslateException {
		String zmOdds = "1-001:[胜=1.3,负=1.1]/1-003:[胜=1.4,平=1.2]";
		Odds odds = Odds.parseZMOdds(zmOdds);
		String lcOdds = odds.toLCOdds("100130-100331", Constants.PLAY_01_ZC_2);
		System.out.println("lcOdds: " + lcOdds);
		assertEquals("1.3A1.1-1.4A1.2", lcOdds);
		
		zmOdds = "1-001:[胜=1.3,负=1.1]/1-003:[胜=1.4,平=1.2]";
		odds = Odds.parseZMOdds(zmOdds);
		lcOdds = odds.toLCOdds("100331-100130", Constants.PLAY_01_ZC_2);
		System.out.println("lcOdds: " + lcOdds);
		assertEquals("1.4A1.2-1.3A1.1", lcOdds);
		
		zmOdds = "1-001:[胜=1.3,负=1.1]/1-003:[胜=1.4,平=1.2]";
		odds = Odds.parseZMOdds(zmOdds);
		lcOdds = odds.toLCOdds("100313-100103", Constants.PLAY_01_ZC_2);
		System.out.println("lcOdds: " + lcOdds);
		assertEquals("1.2A1.4-1.1A1.3", lcOdds);
		
		zmOdds = "4-001:[胜胜=5.4,胜平=15,胜负=23,平胜=8,平平=5.4,平负=4.9,负胜=34,负平=15]/4-002:[胜胜=2.7,胜平=16,胜负=40,平胜=4.5,平平=4.8,平负=9,负胜=25,负平=16]";
		odds = Odds.parseZMOdds(zmOdds);
		lcOdds = odds.toLCOdds("40013331301311100301-40023331301311100301", Constants.PLAY_04_ZC_2);
		System.out.println("lcOdds: " + lcOdds);
		assertEquals("5.4A15A23A8A5.4A4.9A34A15-2.7A16A40A4.5A4.8A9A25A16", lcOdds);
		
		zmOdds = "4-302:[胜6-10分=5.15,胜16-20分=25,胜26分以上=60,负1-5分=3.65,负11-15分=5.7,负21-25分=20]";
		odds = Odds.parseZMOdds(zmOdds);
		lcOdds = odds.toLCOdds("4302110213041506", Constants.PLAY_08_LC_1);
		System.out.println("lcOdds: " + lcOdds);
		assertEquals("3.65A5.15A5.7A25A20A60", lcOdds);
	}

	@Test
	public void testToLCOddsWithExtraInfo() throws TranslateException {
		String zmOdds = "1-001:[主胜=1.3^+2.6]";
		Odds odds = Odds.parseZMOdds(zmOdds);
		String lcOdds = odds.toLCOdds("10011", Constants.PLAY_06_LC_2);
		System.out.println("lcOdds: " + lcOdds);
		assertEquals("1.3@+2.6", lcOdds);
	}
	
	@Test
	public void testTransZMBuyOptToOddsOpt(){
		String oopt = Odds.transZMBuyOptToOddsOpt(PlayType.JCZQ_BQC, "负-胜");
		assertEquals("负胜", oopt);
		oopt = Odds.transZMBuyOptToOddsOpt(PlayType.JCLQ_SFC, "胜1-5");
		assertEquals("胜1-5分", oopt);
	}
}
