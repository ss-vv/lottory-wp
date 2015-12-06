package com.davcai.lottery.platform.client.yuanchengchupiao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
	 * 获取当前时间字符串 格式YYYYMMDDHHMMSS
	 * @return
	 */
	public static String getDateString()
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(new Date());
	}
	
}
