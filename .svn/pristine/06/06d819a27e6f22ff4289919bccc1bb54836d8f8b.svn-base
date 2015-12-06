package com.xhcms.lottery.alarm.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.alarm.service.TicketMonitorService;

public class TicketMonitorTask extends Job{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private int thresholdValue; //报警阀值，用于计算查询的截至时间 （to）
	private int intervalValue; //间隔时间，用于计算从哪个时间开始查 （from）
	
	@Autowired
	private TicketMonitorService ticketMonitorService;
	
	@Override
	protected void execute() throws Exception {
		logger.info("开始出票监控任务！");
		if(thresholdValue == 0){
			thresholdValue = 480; // 默认8分钟
		}
		if(intervalValue == 0){
			intervalValue = 300; // 默认5分钟
		}
		ticketMonitorService.monitorTicketStatus(thresholdValue,intervalValue);
	}

	public int getThresholdValue() {
		return thresholdValue;
	}

	public void setThresholdValue(int thresholdValue) {
		this.thresholdValue = thresholdValue;
	}

	public int getIntervalValue() {
		return intervalValue;
	}

	public void setIntervalValue(int intervalValue) {
		this.intervalValue = intervalValue;
	}
}
