package com.unison.lottery.weibo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.service.StatisticUserDataService;
import com.xhcms.commons.job.Job;

public class StatisticWinTask extends Job{
	private static final Logger logger = LoggerFactory
			.getLogger(StatisticWinTask.class);
	@Autowired
	private StatisticUserDataService statisticUserDataService;
	
	public StatisticWinTask() {
		logger.debug("execute task : StatisticWinTask ...");
	}
	
	@Override
	protected void execute() {
		statisticUserDataService.statisticAllUserLast7DaysWin();
	}
}
