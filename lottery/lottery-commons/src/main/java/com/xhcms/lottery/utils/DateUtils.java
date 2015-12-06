package com.xhcms.lottery.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日期工具
 * 
 * @author yangbo
 *
 */
public class DateUtils {
	static Logger logger = LoggerFactory.getLogger(DateUtils.class);
	public static final String DATE_SHORT = "yyyy-MM-dd";
	public static final String DATE_FORMAT = "yyyy-MM-dd hh:mm:ss";
	public static final String yyyyMMddhhmmss = "yyyyMMddhhmmss";
	public static final String yyyyMMdd = "yyyyMMdd";
	public static final String defaultPattern = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 获取date前lastDay的时间
	 * @param lastDay
	 * @return
	 */
	public static Date getLastDay(Date date,int lastDay){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, -lastDay);
		return cal.getTime();
	}
	
	/**
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static boolean isSameDay(Date dateA, Date dateB) {
		return org.apache.commons.lang.time.DateUtils.isSameDay(dateA, dateB);
	}
	
	/**
	 * 根据日期时间字符串转换成date对象
	 * @param formatString
	 * @return
	 */
	public static Date getDateByFormatString(String formatString,String pattern) {
		try {
			return new SimpleDateFormat(pattern).parse(formatString);
		} catch (ParseException e) {
			logger.error("格式化串为{}的时间转换错误",formatString);
			return null;
		}
	}
	public static int subDay(Date minuend, Date subtrahend) {
		long diff  = minuend.getTime() - subtrahend.getTime();
		int diffDay = (int) (diff / 1000 / 60 / 60 / 24);
		return diffDay;
	}
	
	public static int dayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK);
		return week;
	}
	
	public static int year(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	public static Date getLastDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, 11);
		cal.set(Calendar.DAY_OF_MONTH, 31);
		return cal.getTime();
	}
	
	public static String format(Date date) {
		return new SimpleDateFormat(defaultPattern).format(date);
	}
	public static String formatShort(Date date) {
		return new SimpleDateFormat(DATE_SHORT).format(date);
	}
	public static String formatToyyyyMMdd(Date date) {
		return new SimpleDateFormat(yyyyMMdd).format(date);
	}
	
	public static List<Date> getDayBeginAndEnd() {
		List<Date> list = new ArrayList<Date>();
		Calendar cal = Calendar.getInstance();
		
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
	
	//获取今天的开始时间
	public static Date todayBeginTime(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 00);
		cal.set(Calendar.MINUTE, 00);
		cal.set(Calendar.SECOND, 00);
		return cal.getTime();
		
	}
	/**
	 * 昨天开始时间
	 * @return
	 */
	public static Date yesterdaybeginTime(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -5);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE,00);
		cal.set(Calendar.SECOND, 00);
		return cal.getTime();
	
	}
	/**
	 * 昨天结束时间
	 * @return
	 */
	public static Date yesterdayEndTime(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
		
	}
	public static boolean isMatchStop(Date playingTime){
		if(playingTime!=null){
			if(playingTime.after(new Date())){
				return false;//未停售
			}else{
				return true;//停售
			}
		}
		return false;
	}

	/**
	 * 
	 * @return yyyy
	 */
	public static String getNowYear() {
		// TODO Auto-generated method stub
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return String.valueOf(cal.get(Calendar.YEAR));
	}
	
	public static Date converTime(String closeTime){
		if(StringUtils.isNotBlank(closeTime)){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
			try {
				return sdf.parse(closeTime);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	public static String getWeekDayWithNumber(Integer num){
		String num2=String.valueOf(num);
		switch(num2){
		  case "1":return "周日";
		  case "2":return "周一";
		  case "3":return "周二";
		  case "4":return "周三";
		  case "5":return "周四";
		  case "6":return "周五";
		  case "7":return "周六";
		  default :return "";
		}
	}
	public static String getWeekDayWithTime(Date playingTime){
		if(playingTime!=null){
			Calendar cal=Calendar.getInstance();
			cal.setTime(playingTime);
			int day=cal.get(Calendar.DAY_OF_WEEK);
			switch(day){
			  case 1:return "周日";
			  case 2:return "周一";
			  case 3:return "周二";
			  case 4:return "周三";
			  case 5:return "周四";
			  case 6:return "周五";
			  case 7:return "周六";
			}
		}
		return "";
	}

	/**
	 * 获取d小时之前的时间
	 * @param date
	 * @param d
	 * @return
	 */
	public static Date getLastHoursTime(Date date, int d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, -d);
		return cal.getTime();
	}

	/**
	 * 计算两个时间的分钟差值
	 * @param maxDate
	 * @param minDate
	 * @return
	 */
	public static Long getMinuteOfTwoDate(Date maxDate, Date minDate) {
		long millSeconds = maxDate.getTime()-minDate.getTime();
		return millSeconds/1000/60;
	}

	/**
	 * 计算两个时间的秒差值
	 * @param maxDate
	 * @param minDate
	 * @return
	 */
	public static Long getSecondOfTwoDate(Date maxDate, Date minDate) {
		long millSeconds = maxDate.getTime()-minDate.getTime();
		return millSeconds/1000;
	}
}