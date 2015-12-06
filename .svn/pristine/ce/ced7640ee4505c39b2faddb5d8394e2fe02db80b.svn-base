package com.unison.lottery.mc.uni.parser;

import static org.junit.Assert.*;

import org.junit.Test;

public class QueryMatchResultsParserTest {

	@Test
	public void testComposeZCResult() {
		String[] odds = "3.84,13.68,,2,".split(",", -1);
		assertEquals(5, odds.length);
		assertEquals("", odds[4]);
		assertEquals("2", odds[3]);
	}

}
