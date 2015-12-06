package com.xhcms.lottery.lang;

import static org.junit.Assert.*;

import org.junit.Test;

public class MixPlayTypeTest {

	@Test
	public void testValueOfLC() {
		assertEquals(MixPlayType.SF, MixPlayType.valueOfPlayName("sf"));
		assertEquals(MixPlayType.SPF, MixPlayType.valueOfPlayName("spf"));
		assertEquals(MixPlayType.BQC, MixPlayType.valueOfPlayName("bqc"));
	}
	
	@Test
	public void testGetPlayId() {
		System.out.println(MixPlayType.SF.getPlayId());
		assertEquals("07_LC_2", MixPlayType.SF.getPlayId());
	}
	
	@Test
	public void testValuesOfPlayType(){
		assertEquals(MixPlayType.SPF, MixPlayType.valueOfPlayType(PlayType.JCZQ_SPF));
		assertEquals(MixPlayType.SF, MixPlayType.valueOfPlayType(PlayType.JCLQ_SF));
		assertEquals(MixPlayType.BQC, MixPlayType.valueOfPlayType(PlayType.JCZQ_BQC));
	}
}
