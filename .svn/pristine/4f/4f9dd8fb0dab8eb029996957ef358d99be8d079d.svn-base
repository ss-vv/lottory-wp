package com.xhcms.lottery.lang;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayTypeTest {

	@Test
	public void testValueOfLcPlayId() {
		PlayType pt = PlayType.valueOfLcPlayId("02_ZC_1");
		assertEquals(PlayType.JCZQ_BF, pt);
		pt = PlayType.valueOfLcPlayId("02_ZC_2");
		assertEquals(PlayType.JCZQ_BF, pt);
		pt = PlayType.valueOfLcPlayId("11_J5_R1");
		assertEquals(PlayType.JX11_R1, pt);
		
		pt = PlayType.valueOfLcPlayId(PlayType.CTZC_14.getPlayId());
		assertEquals(PlayType.CTZC_14, pt);
		
		pt = PlayType.valueOfLcPlayId("25_ZC_R9");
		assertEquals(PlayType.CTZC_R9, pt);
	}

	@Test
	public void testIsJX11(){
		String[] ids = new String[]{
				"11_J5_R1",
				"12_J5_R2",
				"13_J5_R3",
				"14_J5_R4",
				"15_J5_R5",
				"16_J5_R6",
				"17_J5_R7",
				"18_J5_R8",
				"19_J5_D2",
				"20_J5_D3",
				"21_J5_G2",
				"22_J5_G3"
		};
		for (String id : ids) {
			PlayType pt = PlayType.valueOfLcPlayId(id);
			System.out.println("test: " + id);
			assertTrue(pt.isJX11());
		}
		
		PlayType pt = PlayType.valueOfLcPlayId("02_ZC_1");
		assertFalse(pt.isJX11());
		pt = PlayType.valueOfLcPlayId("09_LC_2");
		assertFalse(pt.isJX11());
	}
}
