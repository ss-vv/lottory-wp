/**
 * 
 */
package com.xhcms.lottery.dc.feed.data;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.velocity.tools.ConversionUtils;

/**
 * @author Bean.Long
 *
 */
public class DateTools {
	
	public String formatDayOrTime(Object obj, Object minutes) {
		Date date = ConversionUtils.toDate(obj);
		if(date == null)
			return StringUtils.EMPTY;
		
		if(minutes instanceof Number) {
			date = DateUtils.addMinutes(date, ((Number)minutes).intValue());
		}
		
		Date currDate = Calendar.getInstance().getTime();
	
		if(DateUtils.isSameDay(date, currDate)) {
			return DateFormatUtils.format(date, "HH:mm");
		} else {
			return DateFormatUtils.format(date, "MM-dd");
		}
	}
}
