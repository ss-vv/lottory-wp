package com.xhcms.lottery.utils;

import java.util.HashMap;
import java.util.Map;

public class WeekUtil {
	
	private static Map<String,String> weekMap = new HashMap<String, String>();
	static {
		weekMap.put("周一", "1");
		weekMap.put("周二", "2");
		weekMap.put("周三", "3");
		weekMap.put("周四", "4");
		weekMap.put("周五", "5");
		weekMap.put("周六", "6");
		weekMap.put("周日", "7");
	}
	/**
	 * 
	 * @param cnName eg：周一001 返回1001
	 * @return 
	 */
	public static String getWeekNumByCnName(String cnName) {
		return weekMap.get(cnName);
	}
}
