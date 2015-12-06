package com.xhcms.lottery.dc.task.ticket;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.persist.DbUnitTestIssueBase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-issue-spring.xml")
public class OpenSaleIntercessorTest extends DbUnitTestIssueBase {

	@Autowired
	OpenSaleIntercessor openSaleIntercessor;
	
	@Test
	public void whenTargetDateInSalingTimeOfHighFrequenceLotteryThenReturnTrue() {
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 10, 5, 10);//2012-09-13 10:05:10
		Date targetDate=calendar.getTime();
		assertTrue(openSaleIntercessor.isAllowable("JX11", targetDate));
	}
	
	@Test
	public void whenTargetDateOutOfSalingTimeOfHighFrequenceLotteryThenReturnFalse() {
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 13, 8, 5, 10);//2012-09-13 08:05:10
		Date targetDate=calendar.getTime();
		assertFalse(openSaleIntercessor.isAllowable("JX11", targetDate));
	}
	
	// 可接票
	@Test
	public void testLQ() throws ParseException {
		String date = "2012-11-21 07:32:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date targetDate = sdf.parse(date);
		assertTrue(openSaleIntercessor.isAllowable("JCLQ", targetDate));
	}

	// 不可接票
	@Test
	public void testLQLate() throws ParseException {
		String date = "2012-11-21 07:31:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date targetDate = sdf.parse(date);
		assertFalse(openSaleIntercessor.isAllowable("JCLQ", targetDate));
	}

	// 可接票
	@Test
	public void testZQ() throws ParseException {
		String date = "2012-11-25 00:54:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date targetDate = sdf.parse(date);
		assertTrue(openSaleIntercessor.isAllowable("JCZQ", targetDate));
	}

	@Test
	public void testZQLate() throws ParseException {
		String date = "2012-11-25 00:55:00";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date targetDate = sdf.parse(date);
		assertFalse(openSaleIntercessor.isAllowable("JCZQ", targetDate));
	}

}
