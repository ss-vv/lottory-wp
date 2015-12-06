package com.unison.lottery.weibo.task;

import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.service.BetMatchNumService;
import com.unison.lottery.weibo.service.LotteryBetTaskService;
import com.unison.lottery.weibo.service.impl.LotteryBetTaskImpl;
import com.xhcms.commons.job.Job;

public class JCLQTask extends Job {

	@Autowired
	private LotteryBetTaskService lotteryBetTaskService;
	@Autowired
	private BetMatchNumService betMatchNumService;

	@Override
	protected void execute() throws Exception {

		Boolean jclq = LotteryBetTaskImpl.getJclq();
		if (jclq == null) {

			lotteryBetTaskService.isHaveJCLQ();
			jclq = LotteryBetTaskImpl.getJclq();
		}
		if (jclq) {
			betMatchNumService.AddJCLQBetNum();
		}

	}

}
