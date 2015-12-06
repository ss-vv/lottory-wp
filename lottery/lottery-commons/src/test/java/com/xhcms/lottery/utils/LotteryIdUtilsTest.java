package com.xhcms.lottery.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.LotteryIdException;
import com.xhcms.lottery.lang.PlayType;

public class LotteryIdUtilsTest {

	@Test
	public void testGetLotteryIdFromPlayId() throws UtilityException, LotteryIdException {
		String lotteryId = PlayType.getLotteryIdByPlayId(Constants.PLAY_02_ZC_1);
		System.out.println("lotteryId: " + lotteryId);
		assertEquals("JCZQ", lotteryId);
		
		lotteryId = PlayType.getLotteryIdByPlayId(Constants.PLAY_13_J5_R3);
		System.out.println("lotteryId: " + lotteryId);
		assertEquals(Constants.JX11, lotteryId);
	}

	@Test
	public void testGetShortPlayId() throws UtilityException {
		String shortPlayId = PlayType.valueOfLcPlayId(Constants.PLAY_07_LC_1).getShortPlayStr(); // LotteryIdUtils.getShortPlayId(Constants.PLAY_07_LC_1);
		System.out.println("shortPlayId: " + shortPlayId);
		assertEquals("07_LC", shortPlayId);
		
		shortPlayId = PlayType.valueOfLcPlayId(Constants.PLAY_13_J5_R3).getShortPlayStr(); // LotteryIdUtils.getShortPlayId(Constants.PLAY_13_J5_R3);
		System.out.println("shortPlayId: " + shortPlayId);
		assertEquals(Constants.PLAY_13_J5_R3, shortPlayId);
	}

}
