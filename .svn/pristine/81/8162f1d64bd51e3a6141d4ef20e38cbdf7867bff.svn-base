package com.xhcms.lottery.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

public class ResultToolTest {

	@Test
	public void testIsResultInBetCodeInTwoLetter() {
		String betCode = "102022331323";
		ResultTool tool = new ResultTool();
		assertTrue(tool.isResultInBetCodeEveryTwoLetters("10", betCode));
		assertTrue(tool.isResultInBetCodeEveryTwoLetters("20", betCode));
		assertTrue(tool.isResultInBetCodeEveryTwoLetters("22", betCode));
		assertTrue(tool.isResultInBetCodeEveryTwoLetters("33", betCode));
		assertTrue(tool.isResultInBetCodeEveryTwoLetters("13", betCode));
		assertTrue(tool.isResultInBetCodeEveryTwoLetters("23", betCode));
		
		assertFalse(tool.isResultInBetCodeEveryTwoLetters("02", betCode));
		
		betCode = "10202233132";
		assertFalse(tool.isResultInBetCodeEveryTwoLetters("10", betCode));

		betCode = "";
		assertFalse(tool.isResultInBetCodeEveryTwoLetters("10", betCode));

		betCode = null;
		assertFalse(tool.isResultInBetCodeEveryTwoLetters("10", betCode));
	}

	@Test
	public void testIsWin(){
		String betCode = "102022331323";
		String result = "23";
		String playId = Constants.PLAY_02_ZC_2;
		ResultTool tool = new ResultTool();
		PlayMatch matchContainsResult = new PlayMatch();
		BetScheme scheme = new BetScheme();
		
		matchContainsResult.setResult(result);
		matchContainsResult.setBetCode(betCode);
		scheme.setPlayId(playId);
		assertTrue(tool.isMatchWin(matchContainsResult, scheme));
		
		matchContainsResult.setResult("11");
		matchContainsResult.setBetCode(betCode);
		scheme.setPlayId(playId);
		assertFalse(tool.isMatchWin(matchContainsResult, scheme));
		
		playId = Constants.PLAY_01_ZC_1;
		matchContainsResult.setResult("3");
		matchContainsResult.setBetCode("01");
		scheme.setPlayId(playId);
		assertFalse(tool.isMatchWin(matchContainsResult, scheme));
		matchContainsResult.setResult("1");
		matchContainsResult.setBetCode("01");
		assertTrue(tool.isMatchWin(matchContainsResult, scheme));
		
		playId = Constants.PLAY_06_LC_2;
		matchContainsResult.setResult("2");
		matchContainsResult.setBetCode("1");
		scheme.setPlayId(playId);
		assertFalse(tool.isMatchWin(matchContainsResult, scheme));
		matchContainsResult.setResult("2");
		matchContainsResult.setBetCode("2");
		assertTrue(tool.isMatchWin(matchContainsResult, scheme));
		
		playId = Constants.PLAY_08_LC_1;
		matchContainsResult.setResult("01");
		matchContainsResult.setBetCode("110213041506");
		scheme.setPlayId(playId);
		assertFalse(tool.isMatchWin(matchContainsResult, scheme));
		matchContainsResult.setResult("02");
		matchContainsResult.setBetCode("110213041506");
		assertTrue(tool.isMatchWin(matchContainsResult, scheme));
	}
	
	/**
	 * 测试从投注内容转换为中文.
	 */
	@Test
	public void testCN(){
		ResultTool t = new ResultTool();
		String c = t.cn(PlayType.JCZQ_BF.getPlayIdWithPass(false), "1020", "3.1,2.5");
		System.out.println("code: " + c);
		
		// 肯定不支持。
		c = t.cn(PlayType.JCZQ_HH.getPlayIdWithPass(false), "1020", "3.1,2.5");
		System.out.println("code: " + c);
		
		c = t.cn(PlayType.JCLQ_RFSF.getPlayIdWithPass(false), "0", "2.5");
		System.out.println("code: " + c);
	}
}
