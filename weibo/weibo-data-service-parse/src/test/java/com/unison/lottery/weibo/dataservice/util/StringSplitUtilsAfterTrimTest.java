package com.unison.lottery.weibo.dataservice.util;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class StringSplitUtilsAfterTrimTest {
	@Test
	public void testSplitPreserveAllTokens(){
		String str = "197108^4^7:22 ^ 87^ 82 ^35^28^2 2^23^26^19^4^12^0^[公牛]长暂停^4^^^^^^^^True^1.44,2.79";
		String[] strArr = StringSplitUtilsAfterTrim.splitPreserveAllTokens(str, "^");
		assertNotNull(strArr);
		assertEquals(strArr.length, 25);
		assertTrue(StringUtils.equals(strArr[0], "197108"));
		assertTrue(StringUtils.equals(strArr[2], "7:22"));
		assertTrue(StringUtils.equals(strArr[3], "87"));
		assertTrue(StringUtils.equals(strArr[4], "82"));
		assertTrue(StringUtils.equals(strArr[16], ""));
		assertTrue(StringUtils.equals(strArr[7], "2 2"));
		StringSplitUtilsAfterTrim.splitPreserveAllTokens(null, null);
		strArr = StringSplitUtilsAfterTrim.splitPreserveAllTokens("23324^324",null);
		assertNotNull(strArr);
		assertEquals(strArr.length, 1);
		assertEquals("23324^324",strArr[0]);
	}
}
