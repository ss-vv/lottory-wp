package com.xhcms.lottery.commons.utils.internal.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import com.xhcms.lottery.commons.utils.internal.IssueNumberStrategy;

public class SSQIssueNumberStrategyImpl implements IssueNumberStrategy {
	
	private static final int ISSUE_INCREASE_FACTOR = 1;
	
	private static final Date lastIssueDate; 
	
	private int lastIssueIndex;
	
	private String firstIssueOfYear;
	
	static {
		lastIssueDate = returnLastIssueDateOfYear();
	}
	
	@Override
	public String nextIssueNumber(String currOnSaleIssue, Date startTime, int index) {
		int currYear = com.xhcms.lottery.utils.DateUtils.year(startTime);
		int week = com.xhcms.lottery.utils.DateUtils.dayOfWeek(startTime);
		Date nextIssueStartTime = null;
		int issueCnt = index + 1;
		int count = issueCnt / 3;
		int mode = issueCnt % 3;
		int addDays = -1;
		//根据当前期的开始时间计算下一期的开始时间
		switch (week) {
		case 1://周日
			addDays = count * 7 + mode * 2;
			nextIssueStartTime = DateUtils.addDays(startTime, addDays);
			break;
		case 3://周二
			addDays = count * 7;
			if(1 == mode) {
				addDays = addDays + 2;
			} else if(2 == mode) {
				addDays = addDays + 5;
			}
			nextIssueStartTime = DateUtils.addDays(startTime, addDays);
			break;
		case 5://周四
			addDays = count * 7;
			if(1 == mode) {
				addDays = addDays + 3;
			} else if(2 == mode) {
				addDays = addDays + 5;
			}
			nextIssueStartTime = DateUtils.addDays(startTime, addDays);
			break;
		default:
			new RuntimeException("当前期:" + currOnSaleIssue + 
					", 的开始时间：" + startTime + 
					"，无效[双色球开始时间不在周二、周四、周日日期内]");
			break;
		}
		
		int year = com.xhcms.lottery.utils.DateUtils.year(nextIssueStartTime);
		boolean isSameDay = com.xhcms.lottery.utils.DateUtils.isSameDay(nextIssueStartTime, lastIssueDate);
		String issueNumber = "";
		if(isSameDay) {
			lastIssueIndex = index;
			firstIssueOfYear = (year+1) + "000";//跨年期的基数
		}
		if(year > currYear) {//期号跨年
			issueNumber = nextIssue(firstIssueOfYear, (index - lastIssueIndex - 1));
		} else {
			issueNumber = nextIssue(currOnSaleIssue, index);
		}
		return issueNumber;
	}

	@Override
	public List<String> moreIssueNumbers(String currOnSaleIssue, 
			int postponeIssueSize, Date startTime) {
		List<String> issueList = new ArrayList<String>();
		for(int i = 0; i < postponeIssueSize; i++) {
			String issueNumber = nextIssueNumber(currOnSaleIssue, startTime, i);
			issueList.add(issueNumber);
		}
		return issueList;
	}

	/**
	 * 在指定的期号上累加1，注意对于双色球来说给定的期号必须已经做了跨年处理
	 * @param issueNumber
	 * @param index
	 * @return
	 */
	private String nextIssue(String issueNumber, int index) {
		long issueNum = Long.parseLong(issueNumber);
		long issueNo = issueNum + (index + ISSUE_INCREASE_FACTOR);
		return String.valueOf(issueNo);
	}
	
	/**
	 * 返回一年中最后一期的投注日期
	 * @return
	 */
	private static Date returnLastIssueDateOfYear() {
		Date lastDay = com.xhcms.lottery.utils.DateUtils.getLastDayOfYear();
		int lastDayOfWeek = com.xhcms.lottery.utils.DateUtils.dayOfWeek(lastDay);
		switch (lastDayOfWeek) {
			case 1:
			case 3:
			case 5:
				break;
			case 2:
			case 4:
			case 6:
				lastDay = DateUtils.addDays(lastDay, -1);
			case 7:
				lastDay = DateUtils.addDays(lastDay, -2);
				break;
		}
		return lastDay;
	}
}
