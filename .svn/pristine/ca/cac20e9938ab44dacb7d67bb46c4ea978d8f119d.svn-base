package com.unison.lottery.weibo.dataservice.crawler.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



/**
 * @author 彭保星
 *
 * @since 2014年10月31日下午5:51:32
 */
public class DateFormateUtil {
	/**
	 * 转换字符串为日期
	 * @param regex 转换规则
	 * @param dateStr 待转换日期字符串
	 * @return
	 */
	public static Date toDate(String regex,String dateStr){
		Date date = null;
		if(dateStr!=null){
			DateFormat format = new SimpleDateFormat(regex);
			try {
				date = format.parse(dateStr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}
		return date;
	}

	public static String getNowYear() {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		return String.valueOf(calendar.get(Calendar.YEAR));
	}

	/**
	 * 获取当前时间的前几天或后几天的时间
	 * @param day 正数表示day天后，负数表示day天前
	 * @return
	 */
	public static Date getDateOfBefore(int day) {
		// TODO Auto-generated method stub
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, day);
		return calendar.getTime();
	}
	/**
	 * 从指定时间中获取日期(精确到日)
	 * @param date 
	 * @return
	 */
	public static Object getNowDate(Date date) {
		DateFormat dFormat = new SimpleDateFormat("yyyyMMdd");
		return dFormat.format(date);
	}

	/**
	 * 转换日期为指定格式的字符串
	 * @param regex
	 * @param timestamp
	 * @return
	 */
	public static String getStringOfDate(String regex, Date timestamp) {
		if(timestamp!=null){
			if(regex!=null){
				try {
					DateFormat format = new SimpleDateFormat(regex);
					return format.format(timestamp);
				} catch (IllegalArgumentException e) {
					// TODO: handle exception
				}
			}
		}
		return null;
	}
}
