package com.unison.lottery.weibo.task;

import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.nosql.LotteryBetDao;
import com.unison.lottery.weibo.service.BetMatchNumService;
import com.unison.lottery.weibo.service.LotteryBetTaskService;
import com.unison.lottery.weibo.service.impl.LotteryBetTaskImpl;
import com.xhcms.commons.job.Job;

public class BJDCTask extends Job {

	@Autowired
	private LotteryBetTaskService lotteryBetTaskService;
	@Autowired
	private LotteryBetDao lotteryBetDao;
	@Autowired
	private BetMatchNumService betMatchNumService;

	@Override
	protected void execute() throws Exception {

		Boolean bjdc = LotteryBetTaskImpl.getBjdc();
		if (bjdc == null) {

			lotteryBetTaskService.isHaveBJDC();
			bjdc = LotteryBetTaskImpl.getBjdc();
		}
		if (bjdc) {
			betMatchNumService.AddBJDCBetNum();

		}

	}

}
