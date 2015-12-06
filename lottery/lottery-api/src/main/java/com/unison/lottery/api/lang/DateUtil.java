package com.unison.lottery.api.lang;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public final class DateUtil {

	public static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";
	
	public static final String SHORT_PT = "yyyy-MM-dd";
	
	public static String now() {
		SimpleDateFormat sdf = new SimpleDateFormat(defaultPattern);
		String now = sdf.format(Calendar.getInstance().getTime());
		return now;
	}
	
	public static String now(String pattern) {
		if(null == pattern || "".equals(pattern)) {
			pattern = defaultPattern;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String now = sdf.format(Calendar.getInstance().getTime());
		return now;
	}
	
	public static String format(Date date, String pattern) {
		if(StringUtils.isBlank(pattern)) {
			pattern = defaultPattern;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
}
