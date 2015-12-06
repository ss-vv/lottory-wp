package com.unison.lottery.pm.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public final class DateUtil {

	public static final String defaultPattern = "yyyy-MM-dd";
	
	public static String now() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(Calendar.getInstance().getTime());
		return now;
	}
	
	public static String now(String pattern) {
		if(StringUtils.isBlank(pattern)) {
			pattern = defaultPattern;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String now = sdf.format(Calendar.getInstance().getTime());
		return now;
	}
	
	public static String format(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(defaultPattern);
		return sdf.format(date); 
	}
	
	public static List<Date> getDayBeginAndEnd() {
		List<Date> list = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
		
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		Date start = cal.getTime();
		list.add(start);
		
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		list.add(cal.getTime());
		return list;
	}
	
	public static long surplusSecondToDayEnd() {
		Calendar c = Calendar.getInstance();
		long curr = c.getTimeInMillis();
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		
		long end = c.getTimeInMillis();
		return (end - curr)/1000;
	}
}
