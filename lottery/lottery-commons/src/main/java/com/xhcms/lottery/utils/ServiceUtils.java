package com.xhcms.lottery.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author baoxing.peng@davcai.com
 *
 */
public class ServiceUtils {
	private static Logger logger  = LoggerFactory.getLogger(ServiceUtils.class);
	/**
	 * 按时间范围查询竞彩赛程时，时间转换工具类
	 * @param time
	 * @return
	 */
	public static ArrayList<Date> getFromAndTo(String time){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(null == time){
			time = simpleDateFormat.format(new Date());
		}
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	Date today = c.getTime();
    	c.set(Calendar.HOUR_OF_DAY, 12);
		Date from;
		Date to;
		try {
			Date date = simpleDateFormat.parse(time);
			if(date.after(today) || date.equals(today)){
				date = new Date();
				c.setTime(date);
		    	c.set(Calendar.HOUR_OF_DAY, 12);
		    	c.set(Calendar.MINUTE, 0);
		    	c.set(Calendar.SECOND, 0);
		    	c.set(Calendar.MILLISECOND, 0);
		    	c.add(Calendar.DATE, -1);
		    	from = c.getTime();
		    	to = null;
			} else {
				c.setTime(date);
				c.set(Calendar.HOUR_OF_DAY, 12);
		    	c.set(Calendar.MINUTE, 0);
		    	c.set(Calendar.SECOND, 0);
		    	c.set(Calendar.MILLISECOND, 0);
		    	from = c.getTime();
		    	c.add(Calendar.DATE, 1);
		    	to = c.getTime();
			}
		} catch (ParseException e) {
			logger.info("查询投注赛程错误，非法的事件格式：{},要求的时间格式：yyyy-MM-dd",time);
			logger.info("使用当前时间作为默认查询时间");
			c.add(Calendar.DATE, -1);
			from = c.getTime();
			to = null;
		}
		ArrayList<Date> d = new ArrayList<Date>();
		d.add(from);
		d.add(to);
		return d;
	}
	/**
	 * 获取当前日期
	 * @return
	 */
	public static List<Date> getDateOfToDay() {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = simpleDateFormat.format(new Date());
		Calendar c = Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
    	c.set(Calendar.MINUTE, 0);
    	c.set(Calendar.SECOND, 0);
    	c.set(Calendar.MILLISECOND, 0);
    	Date today = c.getTime();
    	c.set(Calendar.HOUR_OF_DAY, 12);
		Date from;
		Date to;
		try {
			Date date = simpleDateFormat.parse(time);
			if(date.after(today) || date.equals(today)){
				date = new Date();
				c.setTime(date);
		    	c.set(Calendar.HOUR_OF_DAY, 0);
		    	c.set(Calendar.MINUTE, 0);
		    	c.set(Calendar.SECOND, 0);
		    	c.set(Calendar.MILLISECOND, 0);
		    	c.add(Calendar.DATE, 0);
		    	from = c.getTime();
		    	to = null;
			} else {
				c.setTime(date);
				c.set(Calendar.HOUR_OF_DAY, 0);
		    	c.set(Calendar.MINUTE, 0);
		    	c.set(Calendar.SECOND, 0);
		    	c.set(Calendar.MILLISECOND, 0);
		    	from = c.getTime();
		    	c.add(Calendar.DATE, 1);
		    	to = c.getTime();
			}
		} catch (ParseException e) {
			logger.info("查询投注赛程错误，非法的事件格式：{},要求的时间格式：yyyy-MM-dd",time);
			logger.info("使用当前时间作为默认查询时间");
			c.add(Calendar.DATE, -1);
			from = c.getTime();
			to = null;
		}
		ArrayList<Date> d = new ArrayList<Date>();
		d.add(from);
		d.add(to);
		return d;
	}
}
