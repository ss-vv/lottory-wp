package com.unison.lottery.weibo.task;

import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.service.LotteryBetTaskService;
import com.xhcms.commons.job.Job;

public class InitMatchTask extends Job {

	@Autowired
	private LotteryBetTaskService lotteryBetTaskService;

	@Override
	protected void execute() throws Exception {

		lotteryBetTaskService.initBetNum();

	}

}
