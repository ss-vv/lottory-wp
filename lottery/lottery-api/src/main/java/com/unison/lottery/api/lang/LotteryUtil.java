package com.unison.lottery.api.lang;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.unison.lottery.api.protocol.common.Constants;
import com.xhcms.lottery.commons.data.IssueInfo;

/**
 * @desc
 * @createTime 2013-1-15
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public final class LotteryUtil {

	//根据彩种判断是否需要解析赛事
	public static boolean isUsedParseMatch(String lotteryId) {
		if(!com.xhcms.lottery.lang.Constants.JX11.equals(lotteryId) &&
				!com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId)) {
			return true;
		}
		return false;
	}
	
	//根据彩种判断是否需要解析赔率
	public static boolean isUsedParseOdd(String lotteryId) {
		if(!com.xhcms.lottery.lang.Constants.JX11.equals(lotteryId) &&
				!com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId)) {
			return true;
		}
		return false;
	}
	
	//根据彩种判断是否需要解析过关方式
	public static boolean isUsedParsePassType(String lotteryId) {
		if(!com.xhcms.lottery.lang.Constants.JX11.equals(lotteryId) &&
				!com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId) &&
				!com.xhcms.lottery.lang.Constants.CTZC.equals(lotteryId) &&
				!com.xhcms.lottery.lang.Constants.SSQ.equals(lotteryId)) {
			return true;
		}
		return false;
	}
	
	//时时彩和高频彩，根据彩种获得当前期与下一期的间隔秒数
	public static int getTimeIntervalByLotteryId(IssueInfo issueInfo, String lotteryId) {
		if(com.xhcms.lottery.lang.Constants.CQSS.equals(lotteryId)) {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			Date startTime = issueInfo.getStartTime();
			String time = sdf.format(startTime);
			String[] timeArr = time.split(":");
			int hour = Integer.parseInt(timeArr[0]);
			int minute = Integer.parseInt(timeArr[1]);
			int second = Integer.parseInt(timeArr[2]);
			if(hour >= Constants.TIME_10 
					&& hour < Constants.TIME_22) {
				return Constants.CQSS_TIME_INTERVAL_TEN_MINUTE;
			} else if(hour == Constants.TIME_22) {
				if(minute == 0 && second == 0) {
					return Constants.CQSS_TIME_INTERVAL_TEN_MINUTE;
				} else {
					return Constants.CQSS_TIME_INTERVAL_FIVE_MINUTE;
				}
			} else if(hour > Constants.TIME_22 || (hour >= 0 && hour <= Constants.TIME_2)) {
				return Constants.CQSS_TIME_INTERVAL_FIVE_MINUTE;
			}
		} else if(com.xhcms.lottery.lang.Constants.JX11.equals(lotteryId)) {
			return Constants.JX11_TIME_INTERVAL_TEN_MINUTE;
		}
		throw new RuntimeException("Unsupport lotteryId：" + lotteryId);
	}
}