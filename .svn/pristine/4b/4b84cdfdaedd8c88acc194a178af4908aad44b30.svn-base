package com.xhcms.lottery.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class TextUtilsTest {

	@Test
	public void testRemoveFirstBlankLine() {
		String content = "\nabc\ndef";
		String removed = TextUtils.removeFirstBlankLine(content);
		assertEquals("abc\ndef", removed);
		
		content = "\r\nabc\ndef";
		removed = TextUtils.removeFirstBlankLine(content);
		assertEquals("abc\ndef", removed);
	}

}
