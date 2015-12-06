package com.unison.lottery.weibo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.HotRecommendService;
import com.unison.lottery.weibo.data.RecentDateType;
import com.xhcms.commons.job.Job;

public class HotRecommendTask extends Job {

	private static final Logger log = LoggerFactory
			.getLogger(HotRecommendTask.class);

	@Autowired
	private HotRecommendService hotRecommendService;
	
	public HotRecommendTask() {
		log.debug("execute hot recommend task...");
	}

	@Override
	protected void execute() {
		try {
			RecentDateType recentDateType = RecentDateType.DAY;
			log.info("执行热门推荐后台服务，遍历彩种用户发布的微博创建热门推荐索引,日期类型={}", recentDateType);
			hotRecommendService.createRecommendIndexOfDate(recentDateType);
		} catch (Exception e) {
			log.error("执行热门推荐任务，根据所有彩种用户发布的微博创建热门推荐索引，error:", e);
			e.printStackTrace();
		}
	}

}