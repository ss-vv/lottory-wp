package com.xhcms.lottery.pb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
	
	public static String getTimeString(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}
	
	/**
	 * 返回‘yyyyMMddHHmmss’日期 格式的 字符串
	 * @param date
	 * @return
	 */
	public static String getTimeString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(date);
	}
}
