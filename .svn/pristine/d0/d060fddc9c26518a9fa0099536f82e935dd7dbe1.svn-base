package com.xhcms.lottery.dc.platform.task;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.ILotteryPlatformClient;
import com.davcai.lottery.platform.client.ILotteryPlatformQueryMatchesClient;
import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.xhcms.lottery.commons.persist.service.MatchPlatformService;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.platform.parser.IMatchDataStoreParser;
import com.xhcms.lottery.lang.LotteryId;

public class FetchMatchesWithMultiPlatformTask extends BaseTaskWithMultiPlatform{

	private ILotteryPlatformQueryMatchesClient matchesClient;
	@Autowired
	MatchPlatformService matchPlatformService;
	@Autowired
	private IMatchDataStoreParser matchDataStoreParser;
	// 要获取的比赛类型，支持："JCLQ"，"JCZQ"
	private String lotteryId;
	
	@Override
	protected void execute() {
		try {
			logger.info("开始获取竞彩赛程 lotteryId='{}'...", lotteryId);
			ILotteryPlatformClient client = getClient(LotteryInterfaceName.queryMatch, lotteryId);
			if(client instanceof ILotteryPlatformQueryMatchesClient){
				matchesClient = (ILotteryPlatformQueryMatchesClient)client;
				LotteryPlatformBaseResponse resp = matchesClient.postByMatchType(lotteryId);
				List<Match> matches = matchDataStoreParser.parseMatches(resp, getCurrentPlatformId(), LotteryId.get(lotteryId));
				storeData(storeDataName, matches);
				//保存赛事平台ID关联表信息
				matchPlatformService.save(matches, getCurrentPlatformId(), LotteryId.get(lotteryId));
				logger.info("获取到{}场type={}的赛程,使用{}平台", new Object[]{matches.size(), lotteryId,getCurrentPlatformId()});
			} else {
				logger.error("matchesClient错误！client不是ILotteryPlatformQueryMatchesClient的实现！");
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId;
	}
}
