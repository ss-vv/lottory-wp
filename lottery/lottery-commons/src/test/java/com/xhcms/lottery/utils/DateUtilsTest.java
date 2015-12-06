package com.xhcms.lottery.utils;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

public class DateUtilsTest {

	private static final long HOUR = 1000*60*60;

	@Test
	public void oneHourDiffIsSameDay() {
		Date dA = new Date(1348019411308L);
		Date dB = new Date(1348019411308L-HOUR);
		Assert.assertTrue(DateUtils.isSameDay(dA, dB));
	}

	@Test
	public void twentyFourHourDiffIsSameDay() {
		Date dA = new Date(1348019411308L);
		Date dB = new Date(1348019411308L-24*HOUR);
		Assert.assertFalse(DateUtils.isSameDay(dA, dB));
	}
	
	@Test
	public void testParse(){
		Date date = DateUtils.getDateByFormatString("2015-03-21 20:15:00", DateUtils.DATE_FORMAT);
		System.out.println("date="+date);
		
		date = DateUtils.getDateByFormatString("2015-03-22 04:00:00", DateUtils.DATE_FORMAT);
		System.out.println("date="+date);
		
		date = DateUtils.getDateByFormatString("2015-03-22 12:00:00", DateUtils.DATE_FORMAT);
		System.out.println("date="+date);
		
		date = DateUtils.getDateByFormatString("2015-03-21 20:15:00", DateUtils.defaultPattern);
		System.out.println("date="+date);
		
		date = DateUtils.getDateByFormatString("2015-03-22 04:00:00", DateUtils.defaultPattern);
		System.out.println("date="+date);
		
		date = DateUtils.getDateByFormatString("2015-03-22 12:00:00", DateUtils.defaultPattern);
		System.out.println("date="+date);
	}
}
