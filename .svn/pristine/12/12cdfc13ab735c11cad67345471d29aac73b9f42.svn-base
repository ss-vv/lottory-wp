package com.unison.lottery.mc.uni.parser;

import static org.junit.Assert.*;

import org.junit.Test;

import com.unison.lottery.mc.uni.parser.QueryMatchesParserStatus.Type;

public class QueryMatchesParserStatusTest {

	@Test
	public void testGetEnumType() {
		QueryMatchesParserStatus status = new QueryMatchesParserStatus("jclq");
		assertEquals(Type.jclq, status.getEnumType());
		assertTrue(Type.jclq==status.getEnumType());
		status = new QueryMatchesParserStatus("jczq");
		assertNotSame(Type.jclq, status.getEnumType());
	}

}
