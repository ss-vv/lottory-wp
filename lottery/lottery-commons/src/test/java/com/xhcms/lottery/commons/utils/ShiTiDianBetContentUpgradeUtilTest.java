package com.xhcms.lottery.commons.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class ShiTiDianBetContentUpgradeUtilTest {
	
	@Test
	public void testShouldUpgrade(){
		ShiTiDianBetContentUpgradeUtil util=new ShiTiDianBetContentUpgradeUtil();
		String lotteryPlatformId="changchunshitidian2";
		String shouldUpgradeShiTiDians="changchun02857,changchunshitidian2";
		boolean result = util.shouldUpgrade(lotteryPlatformId, shouldUpgradeShiTiDians);
		assertTrue(result);
		
		lotteryPlatformId="changchunshitidian";
		result = util.shouldUpgrade(lotteryPlatformId, shouldUpgradeShiTiDians);
		assertFalse(result);
		
		lotteryPlatformId="changchun02857";
		result = util.shouldUpgrade(lotteryPlatformId, shouldUpgradeShiTiDians);
		assertTrue(result);
	}

}
