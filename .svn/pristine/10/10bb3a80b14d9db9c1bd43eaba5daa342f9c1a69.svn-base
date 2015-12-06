package com.unison.lottery.wap.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @desc
 * @createTime 2012-9-27
 * @author lei.li
 * @version 1.0
 */
public class WeekDateListGenerator {

	/**
	 * 提供用于获取从当前时间开始往前的一周日期字符串集合,日期字符串格式:"yyyy-MM-dd"
	 * @return
	 */
	public static List<String> getWeekDateList() {
		List<String> weekDateList = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		String now = sdf.format(cal.getTime());
		weekDateList.add(now);
		
		for(int index = 0; index < 6; index++) {
			cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
			weekDateList.add(sdf.format(cal.getTime()));
		}
		return weekDateList;
	}
	
}
