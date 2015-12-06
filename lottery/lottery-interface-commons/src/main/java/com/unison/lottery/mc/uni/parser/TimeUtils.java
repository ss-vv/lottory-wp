package com.unison.lottery.mc.uni.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * 接口时间工具类。
 * 
 * @author yangbo
 *
 */
public class TimeUtils {

	public static Date parseOfftimeFromMatchStartTime(String matchStartTime) throws ParseException{
		SimpleDateFormat matchTimeFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date playTime = matchTimeFormater.parse(matchStartTime);
		Date offtime = DateUtils.addMinutes(playTime, -1);
		return offtime;
	}
	
	public static Date parseOfftimeFromMatchStartTime(String matchStartTime, String pattern) 
			throws ParseException{
		SimpleDateFormat matchTimeFormater = new SimpleDateFormat(pattern);
		Date playTime = matchTimeFormater.parse(matchStartTime);
		Date offtime = DateUtils.addMinutes(playTime, -1);
		return offtime;
	}
	
}
