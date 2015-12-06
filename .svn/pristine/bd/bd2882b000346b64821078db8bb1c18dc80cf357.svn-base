package com.unison.lottery.weibo.task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.service.BetMatchesService;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.persist.entity.MixMatchPlay;
import com.xhcms.lottery.commons.persist.service.MatchService;
import com.xhcms.lottery.lang.LotteryId;

public class UpdateBetMatchesCacheTask extends Job {
	private static final Logger logger = LoggerFactory.getLogger(UpdateBetMatchesCacheTask.class);
	
	@Autowired
	private BetMatchesService betMatchesService;
	@Autowired
	private MatchService matchService;
	
	@Override
	protected void execute() throws Exception {
		List<MixMatchPlay> jczqAllMatches = matchService.listFBInLast2Days();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		betMatchesService.saveMixMatchPlaysCache(jczqAllMatches, s.format(new Date()), LotteryId.JCZQ.name());
		logger.info("更新投注赛程数据完成..............");
	}
}
