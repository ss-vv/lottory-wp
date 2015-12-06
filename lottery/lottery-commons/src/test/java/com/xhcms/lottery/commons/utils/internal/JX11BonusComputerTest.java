package com.xhcms.lottery.commons.utils.internal;

import static org.junit.Assert.*;

import org.junit.Test;

import com.xhcms.lottery.lang.PlayType;

public class JX11BonusComputerTest {

	@Test
	public void testComputeExpectBonus() {
		int bonus = JX11BonusComputer.computeExpectBonus(PlayType.JX11_R1, 3);
		assertEquals(13*3, bonus);
		
		bonus = JX11BonusComputer.computeExpectBonus(PlayType.JX11_D3, 5);
		assertEquals(1170*5, bonus);
	}

}
