package com.unison.lottery.weibo.task;

import java.math.BigInteger;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.BetRecordCache;
import com.unison.lottery.weibo.lang.BetRecordConstant;
import com.unison.lottery.weibo.utils.DateUtil;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.persist.service.AccountQueryService;
import com.xhcms.lottery.commons.persist.service.BetPartnerService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.persist.service.PhantomService;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.SchemeDisplayMode;

public class FillUserBetCacheTask extends Job {

	private static final Logger log = LoggerFactory
			.getLogger(FillUserBetCacheTask.class);
	
	@Autowired
	AccountQueryService accountQueryService;
	@Autowired
	BetSchemeService betSchemeService;
	@Autowired
	BetRecordCache betRecordCache;
	@Autowired
	PhantomService phantomService;
	@Autowired
	BetPartnerService betPartnerService;
	
	public FillUserBetCacheTask() {
		
	}

	@Override
	protected void execute() {
		try {
			List<BigInteger> userIds = accountQueryService.findRechargeUserId();
			List<BigInteger> listUser = phantomService.listUser();
			for(BigInteger userId : listUser) {
				boolean result = userIds.contains(userId);
				if(!result) {
					userIds.add(userId);
				}
			}
			
//			if(null != userIds && userIds.size() > 0) {
//				for(BigInteger userId : userIds) {
//					String[] lotteryIds = new String[]{LotteryId.JCZQ.name(),
//							LotteryId.JCLQ.name(),LotteryId.CTZC.name(),
//							LotteryId.BJDC.name(),LotteryId.SSQ.name()};
//					for(String lotteryId : lotteryIds) {
//						List<Object[]> betRecords = betSchemeService.findBetRecord(lotteryId, userId.longValue(), 
//								BetRecordConstant.LOAD_RECORD_COUNT);
//						for(Object[] scheme : betRecords) {
//							long schemeId = Long.valueOf(String.valueOf(scheme[0]));
//							int schemeType = Integer.parseInt(String.valueOf(scheme[1]));
//							int isShowScheme = Integer.parseInt(String.valueOf(scheme[2]));
//							int isPublishShow = Integer.parseInt(String.valueOf(scheme[3]));
//							long createdTime = DateUtil.parseDate(String.valueOf(scheme[4]));
//							
//							int displayMode = SchemeDisplayMode.getDisplayMode(schemeType, isShowScheme, isPublishShow);
//							
//							String value = Keys.schemeIdCacheValue(schemeId, displayMode, null);
//							
//							betRecordCache.cacheBetRecord(userId.longValue(), lotteryId, value, createdTime);
//							
//							log.info("缓存投注记录用户ID={}，彩种={}, 方案ID={}, value={}, createdTime={}：",
//									new Object[]{userId, lotteryId, scheme, value, createdTime});
//						}
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}