package com.xhcms.lottery.alarm.task;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.lottery.alarm.service.TicketMonitorService;

public class ShiTiDianTicketMonitorTask extends Job{

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	
	
	@Autowired
	private TicketMonitorService ticketMonitorService;
	
	@Override
	protected void execute() throws Exception {
		logger.info("开始实体店出票监控任务！");
		ticketMonitorService.monitorShiTiDianTicketStatus();
	}


	
}
