package com.xhcms.lottery.dc.utils;

import static org.junit.Assert.*;

import org.junit.Test;

import com.xhcms.lottery.commons.util.OptionUtils;
import com.xhcms.lottery.lang.Constants;

public class OptionUtilsTest {

	@Test
	public void testLcWinOption() {
		
		int playId = Constants.PLAY_08;
		float letScore = 0.0f;
		float guessScore = 0.0f;
		String score = "61:81";
		String option  = OptionUtils.lcWinOption(playId , letScore, guessScore , score);
		assertEquals("04", option);
		
		score = "1:2";
		option  = OptionUtils.lcWinOption(playId , letScore, guessScore , score);
		assertEquals("01", option);

		score = "2:1";
		option  = OptionUtils.lcWinOption(playId , letScore, guessScore , score);
		assertEquals("11", option);

		score = "1:6";
		option  = OptionUtils.lcWinOption(playId , letScore, guessScore , score);
		assertEquals("01", option);

		score = "1:7";
		option  = OptionUtils.lcWinOption(playId , letScore, guessScore , score);
		assertEquals("02", option);
		
		score = "1:11";
		option  = OptionUtils.lcWinOption(playId , letScore, guessScore , score);
		assertEquals("02", option);

		score = "1:12";
		option  = OptionUtils.lcWinOption(playId , letScore, guessScore , score);
		assertEquals("03", option);

		score = "1:16";
		option  = OptionUtils.lcWinOption(playId , letScore, guessScore , score);
		assertEquals("03", option);

		score = "1:26";
		option  = OptionUtils.lcWinOption(playId , letScore, guessScore , score);
		assertEquals("05", option);

		score = "1:27";
		option  = OptionUtils.lcWinOption(playId , letScore, guessScore , score);
		assertEquals("06", option);

		score = "1:28";
		option  = OptionUtils.lcWinOption(playId , letScore, guessScore , score);
		assertEquals("06", option);
	}

}
