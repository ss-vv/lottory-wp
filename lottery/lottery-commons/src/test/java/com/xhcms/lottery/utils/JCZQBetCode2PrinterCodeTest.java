package com.xhcms.lottery.utils;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.lottery.commons.data.Ticket;

public class JCZQBetCode2PrinterCodeTest {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Ticket ticket;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ticket = new Ticket();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * 竞彩足球进球数玩法
	 */
	@Test
	public void testConvertJQSBetCode() {
		ticket.setPlayId("03_ZC_2");
		ticket.setCode("70326-70335-70347");
		ticket.setPassTypeId("3@1");
		ticket.setMultiple(1);
		String result = Ticket2ShiTiDianCodeTool.convert(ticket);
		String expected = "53333,竞彩足球胜平负,T,(53)(7032>6)(7033>5)(7034>7),3串1,1,2";
		
		logger.info("====================================");
		logger.info("ticket={}", ticket);
		logger.info("result={}", result);
		logger.info("====================================");
		
		Assert.assertEquals(expected, result);
	}
	
	/**
	 * 竞彩足球半全场玩法
	 */
	@Test
	public void testConvertBQCBetCode() {
		ticket.setPlayId("04_ZC_2");
		ticket.setCode("701510-70161110-70181110");
		ticket.setPassTypeId("3@1");
		ticket.setMultiple(1);
		String result = Ticket2ShiTiDianCodeTool.convert(ticket);
		String expected = "54444,竞彩足球胜平负,T,(54)(7015>10)(7016>1110)(7018>1110),3串1,1,2";
		
		logger.info("====================================");
		logger.info("ticket={}", ticket);
		logger.info("result={}", result);
		logger.info("====================================");
		
		Assert.assertEquals(expected, result);
	}
	
	/**
	 * 竞彩足球混合比分玩法
	 */
	@Test
	public void testConvertBFBetCode() {
		ticket.setPlayId("05_ZC_2");
		ticket.setCode("71083:SPF-60264221:BF");
		ticket.setPassTypeId("2@1");
		ticket.setMultiple(1);
		String result = Ticket2ShiTiDianCodeTool.convert(ticket);
		String expected = "59999,竞彩足球胜平负,T,(59)(7108>56-3)(6026>52-422-1),2串1,1,2";
		
		logger.info("====================================");
		logger.info("ticket={}", ticket);
		logger.info("result={}", result);
		logger.info("====================================");
		
		Assert.assertEquals(expected, result);
	}
	
	/**
	 * 竞彩足球m@n过关
	 */
	@Test
	public void testConvertBetCodeWith() {
		ticket.setPlayId("04_ZC_1");
		ticket.setCode("50010330");
		ticket.setPassTypeId("1@1");
		ticket.setMultiple(1);
		String result = Ticket2ShiTiDianCodeTool.convert(ticket);
		String expected = "54444,竞彩足球胜平负,T,(54)(5001>0330),单关,1,2";
		
		logger.info("====================================");
		logger.info("ticket={}", ticket);
		logger.info("result={}", result);
		logger.info("====================================");
		
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testConvertJCZQHH() {
		ticket.setPlayId("05_ZC_2");
		ticket.setCode("71123030:SPF-71294:JQS-71303:JQS");
		ticket.setPassTypeId("3@1");
		ticket.setMultiple(1);
		String result = Ticket2ShiTiDianCodeTool.convert(ticket);
		String expected = "59999,竞彩足球胜平负,T,(59)(7112>56-3030)(7129>53-4)(7130>53-3),3串1,1,2";
		
		logger.info("====================================");
		logger.info("ticket={}", ticket);
		logger.info("result={}", result);
		logger.info("====================================");
		
		Assert.assertEquals(expected, result);
	}
	
	@Test
	public void testConvertJCZQHHData() {
		ticket.setPlayId("05_ZC_2");
		ticket.setCode("50013:BRQSPF-50023:SPF");
		ticket.setPassTypeId("2@1");
		ticket.setMultiple(15);
		String result = Ticket2ShiTiDianCodeTool.convert(ticket);
		String expected = "59999,竞彩足球胜平负,T,(59)(5001>51-3)(5002>56-3),2串1,15,2";
		
		logger.info("====================================");
		logger.info("ticket={}", ticket);
		logger.info("result={}", result);
		logger.info("====================================");
		
		Assert.assertEquals(expected, result);
	}
}
