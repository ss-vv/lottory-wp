package com.xhcms.lottery.commons.client.translate;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.xhcms.lottery.commons.client.TranslateException;

public class MatchOddsTest {

	@Test
	public void testParseAnRuiMatchOdds() throws TranslateException{
		MatchOdds matchOdds = MatchOdds.parseAnRuiMatchOdds("63461:周五301(-9.50):[让分主负@1.70]","06_LC_2");
		assertTrue(StringUtils.equals(matchOdds.getDayInWeek(), "5"));
		assertTrue(StringUtils.equals(matchOdds.getExtraInfo(), "-9.50"));
		assertTrue(StringUtils.equals(matchOdds.getSequence(), "301"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("客胜"), "1.70"));
		
		matchOdds = MatchOdds.parseAnRuiMatchOdds("63462:周五302(+3.50):[让分主胜@1.63]","06_LC_2");
		assertTrue(StringUtils.equals(matchOdds.getDayInWeek(), "5"));
		assertTrue(StringUtils.equals(matchOdds.getExtraInfo(), "+3.50"));
		assertTrue(StringUtils.equals(matchOdds.getSequence(), "302"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("主胜"), "1.63"));
		
		matchOdds = MatchOdds.parseAnRuiMatchOdds("63432:周五001(0):[平@3.65+负@1.60]","05_ZC_2");
		assertTrue(StringUtils.equals(matchOdds.getDayInWeek(), "5"));
		assertTrue(StringUtils.equals(matchOdds.getExtraInfo(), ""));
		assertTrue(StringUtils.equals(matchOdds.getSequence(), "001"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("平"), "3.65"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("负"), "1.60"));
		
		matchOdds = MatchOdds.parseAnRuiMatchOdds("63433:周五002(+1):[让球负@3.90]","05_ZC_2");
		assertTrue(StringUtils.equals(matchOdds.getDayInWeek(), "5"));
		assertTrue(StringUtils.equals(matchOdds.getExtraInfo(), "+1"));
		assertTrue(StringUtils.equals(matchOdds.getSequence(), "002"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("负"), "3.90"));
		
		matchOdds = MatchOdds.parseAnRuiMatchOdds("63434:周五003(0):[胜@3.05]","05_ZC_2");
		assertTrue(StringUtils.equals(matchOdds.getDayInWeek(), "5"));
		assertTrue(StringUtils.equals(matchOdds.getExtraInfo(), ""));
		assertTrue(StringUtils.equals(matchOdds.getSequence(), "003"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("胜"), "3.05"));
		
		matchOdds = MatchOdds.parseAnRuiMatchOdds("63435:周五004(+1):[让球胜@1.56+让球负@4.80]","05_ZC_2");
		assertTrue(StringUtils.equals(matchOdds.getDayInWeek(), "5"));
		assertTrue(StringUtils.equals(matchOdds.getExtraInfo(), "+1"));
		assertTrue(StringUtils.equals(matchOdds.getSequence(), "004"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("胜"), "1.56"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("负"), "4.80"));
		
		matchOdds = MatchOdds.parseAnRuiMatchOdds("63435:周四014(+1):[3@1.56+让球负@5.80]","05_ZC_2");
		assertTrue(StringUtils.equals(matchOdds.getDayInWeek(), "4"));
		assertTrue(StringUtils.equals(matchOdds.getExtraInfo(), "+1"));
		assertTrue(StringUtils.equals(matchOdds.getSequence(), "014"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("3"), "1.56"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("负"), "5.80"));
		
		matchOdds = MatchOdds.parseAnRuiMatchOdds("63435:周日034(0):[3@1.56+21@5.80]","05_ZC_2");
		assertTrue(StringUtils.equals(matchOdds.getDayInWeek(), "7"));
		assertTrue(StringUtils.equals(matchOdds.getExtraInfo(), ""));
		assertTrue(StringUtils.equals(matchOdds.getSequence(), "034"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("3"), "1.56"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("2:1"), "5.80"));
		
		matchOdds = MatchOdds.parseAnRuiMatchOdds("63435:周日035(+127.5):[主胜6-10@2.56+大@5.80]","06_LC_2");
		assertTrue(StringUtils.equals(matchOdds.getDayInWeek(), "7"));
		assertTrue(StringUtils.equals(matchOdds.getExtraInfo(), "+127.5"));
		assertTrue(StringUtils.equals(matchOdds.getSequence(), "035"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("胜6-10分"), "2.56"));
		assertTrue(StringUtils.equals(matchOdds.getOptionOddsMap().get("大"), "5.80"));
	}

}
