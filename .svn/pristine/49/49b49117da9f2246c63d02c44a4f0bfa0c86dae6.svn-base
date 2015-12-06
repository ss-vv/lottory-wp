package com.unison.lottery.api.lang;

import java.util.Calendar;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.entity.IssueInfoPO;

/**
 * @desc
 * @createTime 2013-1-15
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public final class IssueInfoUtil {

	/**
	 * 获得指定“期信息”的倒计时秒数
	 * @param issueInfo
	 * @return
	 */
	public static Long getCurrentIssueCountDown(IssueInfo issueInfo) {
		long countDownTime = issueInfo.getStopTime().getTime()- Calendar.getInstance().getTimeInMillis();
		return countDownTime/1000;
	}
	
	public static Long getCurrentIssueCountDown(IssueInfoPO issueInfo) {
		long countDownTime = issueInfo.getStopTime().getTime()- Calendar.getInstance().getTimeInMillis();
		return countDownTime/1000;
	}
	
	/**
	 * 获得指定“期信息”的大V彩停止接票倒计时秒数
	 * @param issueInfo
	 * @return
	 */
	public static Long getCurrentIssueStopTimeForUserCountDown(IssueInfo issueInfo) {
		long countDownTime = issueInfo.getStopTimeForUser().getTime()- Calendar.getInstance().getTimeInMillis();
		return countDownTime/1000;
	}
	
}