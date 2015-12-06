package com.unison.lottery.weibo.dataservice.parse;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class BBMatchInfoTransformerTest {
	
	private BBMatchInfoTransformer transformer;

	@Before
	public void setUp(){
		transformer=new BBMatchInfoTransformer();
	}
	
	@Test
	public void testMatchTimeStr(){
		String input="2014/10/10 7:30:01";
		String result = transformer.matchTimeStr(input);
		assertTrue(StringUtils.equals(result, "2014-10-10 07:30:00"));
	}
	
	@Test
	public void testMatchTimeStr2(){
		String input="2014/10/10 17:30:01";
		String result = transformer.matchTimeStr(input);
		assertTrue(StringUtils.equals(result, "2014-10-10 17:30:00"));
	}
	
	@Test
	public void testRemainTime(){
		String input="30:01";
		String result=transformer.remainTime(input);
		assertTrue(StringUtils.equals(result, "1801"));
		input="30:41";
		result=transformer.remainTime(input);
		assertTrue(StringUtils.equals(result, "1841"));
		input="030:41";
		result=transformer.remainTime(input);
		assertTrue(StringUtils.equals(result, "1841"));
		input=" 030:41 ";
		result=transformer.remainTime(input);
		assertTrue(StringUtils.equals(result, "1841"));
		
		input="29.7";
		result=transformer.remainTime(input);
		assertTrue(StringUtils.equals(result, "29"));
		
		input=" 09.7";
		result=transformer.remainTime(input);
		assertTrue(StringUtils.equals(result, "9"));
		
		input = "";
		result = transformer.remainTime(input);
		assertTrue(StringUtils.equals(result, ""));
		
		
	}
	
	@Test(expected=WrongRemainTimeFormatException.class)
	public void parseWrongRemainTimeFormat(){
		String input="3001";
		transformer.remainTime(input);
	}

}
