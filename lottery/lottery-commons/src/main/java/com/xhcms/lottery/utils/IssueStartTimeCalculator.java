package com.xhcms.lottery.utils;

import java.util.Calendar;
import java.util.Date;
import com.xhcms.lottery.lang.Constants;

/**
 * 计算彩种指定期次的开始时间(startTime),计算的开始时间并非官方开始时间，
 * 而是根据官方截止时间和彩种ID推算出来的期开始时间
 * @author lei.li@unison.net.cn
 */
public final class IssueStartTimeCalculator {

	public static void main(String[] args) {
		getStartTime(new Date(), Constants.SSQ);
	}
	
	public static Date getStartTime(Date closeTime, String lotteryId) {
		if(null != closeTime) {
			int dayForWeek = getDayForWeek(closeTime);
			Calendar c = Calendar.getInstance();
			//双色球每周二,四,日开奖
			if(Constants.SSQ.equals(lotteryId)) {
				c.setTime(closeTime);
				if(2 == dayForWeek || 4 == dayForWeek) {
					c.add(Calendar.DATE, -2);
				} else if(7 == dayForWeek) {
					c.add(Calendar.DATE, -3);
				}
			}
			return c.getTime();
		}
		throw new RuntimeException("无法计算期次的开始时间,lotteryId:" + lotteryId);
	}
	
	//获取指定日期对应是星期几
	public static int getDayForWeek(Date closeTime) {
		Calendar c = Calendar.getInstance();
		c.setTime(closeTime);
		int dayForWeek = 0;
		if(c.get(Calendar.DAY_OF_WEEK) == 1){
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
}