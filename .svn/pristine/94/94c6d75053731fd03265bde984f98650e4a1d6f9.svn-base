package com.unison.lottery.weibo.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.RecentWeiboService;
import com.unison.lottery.weibo.data.RecentDateType;
import com.xhcms.commons.job.Job;

public class HotDiscussTask extends Job {

	private static final Logger log = LoggerFactory
			.getLogger(HotDiscussTask.class);

	@Autowired
	private RecentWeiboService recentWeiboService;

	public HotDiscussTask() {
		log.debug("execute hot discuss task...");
	}

	@Override
	protected void execute() {
		try {
			RecentDateType day = RecentDateType.DAY;
			log.info("执行热门讨论后台服务，创建讨论的微博索引.日期类型={}", day);
			recentWeiboService.createHotDiscussIndex(day);
		} catch (Exception e) {
			log.error("热门讨论任务error:", e);
			e.printStackTrace();
		}
	}

}