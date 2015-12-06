package com.unison.lottery.lang;

import static org.junit.Assert.*;
import org.junit.Test;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.PlayType;

public class PlayTypeTest {

	@Test
	public void testPlayType() {
		PlayType playType = PlayType.valueOfLcPlayId(Constants.PLAY_24_ZC_14);
		assertEquals(Constants.PLAY_24_ZC_14, playType.getPlayId());
		assertEquals(Constants.PLAY_24_ZC_14, playType.getShortPlayStr());
		assertEquals(Constants.JCZQ, PlayType.getLotteryIdByPlayId(Constants.PLAY_01_ZC_1));
		
		playType = PlayType.valueOfLcPlayId(Constants.PLAY_01_ZC_1);
		assertEquals(Constants.PLAY_01_ZC, playType.getShortPlayStr());
		assertEquals(Constants.CTZC, PlayType.getLotteryIdByPlayId(Constants.PLAY_27_ZC_JQ));
		
		PlayType zcfhPlayType = PlayType.valueOfLcPlayId(Constants.PLAY_555_ZCFH_2);
		assertEquals(Constants.PLAY_555_ZCFH, zcfhPlayType.getShortPlayStr());
		
		PlayType lcfhPlayType = PlayType.valueOfLcPlayId(Constants.PLAY_666_LCFH_2);
		assertEquals(Constants.PLAY_666_LCFH, lcfhPlayType.getShortPlayStr());
	}

	@Test
	public void testJX11PlayType() {
		
	}
}
