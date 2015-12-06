package com.unison.lottery.weibo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间转换
 * @author Wang Lei
 *
 */
public class DateUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 *  输入long类型的时间，返回时间字符串
	 * @param time
	 * @return
	 */
	public static String format(long time){
		return sdf.format(new Date(time));
	}
	/**
	 *  输入Date类型的时间，返回时间字符串
	 * @param time
	 * @return
	 */
	public static String format(Date date){
		return sdf.format(date);
	}
	
	/**
	 * 返回字符串格式的当前时间
	 * @return
	 */
	public static String getCurrentTime(){
		return sdf.format(new Date());
	}
	
	/**
	 * 转换 Epoch 时间（即从1970年开始的毫秒数）为 Date 对象。
	 * 
	 * @param epoch 时间数字串，如：
	 * @return 对应的 Date 对象。
	 */
	public static Date epochToDate(String epoch){
		long epochNum = Long.parseLong(epoch);
		return new Date(epochNum);
	}
	
	public static long parseDate(String dateString){
		long time = -1;
		try {
			time = sdf.parse(dateString).getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}
}
