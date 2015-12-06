package com.xhcms.lottery.account.web.action.bet;

import java.util.Calendar;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @desc 截止投注
 * @author lei.li@unison.net.cn
 * @createTime 2014-4-14
 * @version 1.0
 */
public class StopBet {

	private Date beginDeadBetDate;

	private Date endDeadBetDate;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public StopBet() {
		Calendar cal = Calendar.getInstance();
		cal.set(2014, 3, 14, 20, 50, 0);
		beginDeadBetDate = cal.getTime();
		
		cal.set(2014, 3, 15, 8, 30, 0);
		endDeadBetDate = cal.getTime();
		
		logger.info("beginDeadBetDate=" + beginDeadBetDate);
		logger.info("endDeadBetDate=" + endDeadBetDate);
	}
	
	public static void main(String[] args) {
		new StopBet();
	}

	public Date getBeginDeadBetDate() {
		return beginDeadBetDate;
	}

	public Date getEndDeadBetDate() {
		return endDeadBetDate;
	}
	
	public boolean isStop() {
		boolean result = false;
		long now = Calendar.getInstance().getTime().getTime();
		long beginDeadBet = beginDeadBetDate.getTime();
		long endDeadBet = endDeadBetDate.getTime();
		if(now >= beginDeadBet && now <= endDeadBet) {
			result = true;
		}
		return result;
	}
}