package com.unison.lottery.weibo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.service.StatisticUserDataService;
import com.xhcms.commons.job.Job;

/**
 * 统计微博用户相关数据（实单统计、推荐统计等）
 * @author haoxiang.jiang@unison.net.cn
 * @time 2014-5-27 下午5:20:05
 */
public class StatisticUserDataTask extends Job{
	private static final Logger logger = LoggerFactory
			.getLogger(StatisticUserDataTask.class);
	
	@Autowired
	private StatisticUserDataService statisticUserDataService;
	
	public StatisticUserDataTask() {
		logger.debug("execute task : statistic user data ...");
	}
	
	@Override
	protected void execute() {
		statisticUserDataService.statisticRealWeiboData();
		statisticUserDataService.statisticRecWeiboData();
		statisticUserDataService.statisticGuoDanLvTop10();
		statisticUserDataService.statisticBonusTop10();
		statisticUserDataService.statisticActiveUser();
	}
}
