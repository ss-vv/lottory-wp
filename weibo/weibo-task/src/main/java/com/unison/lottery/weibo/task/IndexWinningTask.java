package com.unison.lottery.weibo.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.weibo.common.service.LotteryService;
import com.unison.lottery.weibo.service.impl.WinningServiceImpl;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.LotteryId;

public class IndexWinningTask extends Job {

	private static final Logger log = LoggerFactory
			.getLogger(IndexWinningTask.class);

	public IndexWinningTask() {
		log.debug("execute IndexWinningTask task...");
	}

	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private WinningServiceImpl winningService;
	
	
	@Override
	protected void execute() {
		try {
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.MONTH, 3);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			
			Date from = cal.getTime();
			Date to = new Date();
			List<BetSchemePO> betList = winningService.findByStatus(EntityStatus.TICKET_AWARDED, from, to);
			if(null != betList && betList.size() > 0) {
				for(BetSchemePO scheme : betList) {
					long schemeId = scheme.getId();
					String lotteryId = scheme.getLotteryId();
					if(lotteryId.equals(LotteryId.JCZQ.name()) ||
							lotteryId.equals(LotteryId.JCLQ.name())) {
						if(schemeId > 0) {
							lotteryService.autoSendWinning(schemeId);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}