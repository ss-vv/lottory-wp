package com.xhcms.lottery.dc.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;
import com.xhcms.lottery.utils.UtilityException;

public class LotteryIdUtilsTest {

	@Test
	public void whenXX_J5_XXThenJX11() throws UtilityException {
		
		String lcLotteryId=null;
		
		lcLotteryId = PlayType.getLotteryIdByPlayId("17_J5_R7");
		assertTrue(lcLotteryId.equals(Constants.JX11));
			
		lcLotteryId = PlayType.getLotteryIdByPlayId("13_J5_R3");
		assertTrue(lcLotteryId.equals(Constants.JX11));
	}

}
