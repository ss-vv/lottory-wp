package com.unison.lottery.weibo.task;

import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.service.BetMatchNumService;
import com.unison.lottery.weibo.service.LotteryBetTaskService;
import com.unison.lottery.weibo.service.impl.LotteryBetTaskImpl;
import com.xhcms.commons.job.Job;

public class JCZQTask extends Job{

	
	@Autowired
	private LotteryBetTaskService lotteryBetTaskService;
	@Autowired
	private BetMatchNumService betMatchNumService;
	@Override
	protected void execute() throws Exception {
		
		Boolean jczq=LotteryBetTaskImpl.getJczq();
		if(jczq==null){
			
			lotteryBetTaskService.isHaveJCZQ();
			jczq=LotteryBetTaskImpl.getJczq();
		}
		if(jczq){
			betMatchNumService.AddJCZQBetNum();
		}
	}

}
