package com.unison.lottery.pm.jms.task;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.persist.service.PMWeekBonusService;
import com.xhcms.lottery.lang.LotteryId;

/**
 * “中奖加奖”活动任务调度
 * @author lei.li@davcai.com
 */
public class BonusTask extends Job {

	protected Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private PMWeekBonusService pMWeekBonusService;

	private String[] lotteryIds = new String[] { LotteryId.JCZQ.name()};
	
	/**参与“中奖加奖”活动的方案时间**/
	private Date beginTime;
	private Date endTime;

	@Override
	protected void execute() throws Exception {
		log.info("开始查找满足活动（奖上加奖）条件的投注方案。");
		int count = 0;
		
		if(null == beginTime || null == endTime) {
			beginTime = getPromotionBeginTime();
			endTime = new Date();
		}
		
		try {
			Set<Long> schemeIds = pMWeekBonusService.findWinSchemeIds(beginTime, endTime, lotteryIds);
			for (Long schemeId : schemeIds) {
				pMWeekBonusService.insertWinRecord(schemeId);
				count++;
			}
		} catch (Exception e) {
			log.error("记录用户加奖记录时异常:", e);
		}
		log.info("奖上加奖活动，开始时间={}, 结束时间={}：满足条件的投注方案共成功插入{}条。", 
				new Object[]{beginTime, endTime, count});
		log.info("结束处理奖上加奖活动。");
	}

	/**
	 * 默认活动开始时间从当前时间减30天
	 * @return
	 */
	private Date getPromotionBeginTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 30);
		return cal.getTime();
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
