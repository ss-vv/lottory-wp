package com.xhcms.lottery.dc.platform.task;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.ILotteryPlatformClient;
import com.davcai.lottery.platform.client.ILotteryPlatformQueryMatcheOddsClient;
import com.davcai.lottery.platform.client.ILotteryPlatformQueryMatchesClient;
import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.constants.LotteryInterfaceName;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.dc.data.OddsBase;
import com.xhcms.lottery.dc.platform.parser.IMatchDataStoreParser;
import com.xhcms.lottery.dc.platform.parser.IMatchOddsStoreParser;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.LotteryPlatformId;

public class FetchMatcheOddsWithMultiPlatformTask extends BaseTaskWithMultiPlatform{

	private ILotteryPlatformQueryMatcheOddsClient matcheOddsClient;
	@Autowired
	private IMatchOddsStoreParser matchOddsStoreParser;
	// 要获取的比赛类型，支持："JCLQ"，"JCZQ"
	private String lotteryId;
	private String anRuiStoreDataName;
	
	@Override
	protected void execute() {
		try {
			logger.info("开始获取竞彩赛程赔率 lotteryId='{}'...", lotteryId);
			ILotteryPlatformClient client = getClient(LotteryInterfaceName.queryMatchOdds, lotteryId);
			if(client instanceof ILotteryPlatformQueryMatcheOddsClient){
				matcheOddsClient = (ILotteryPlatformQueryMatcheOddsClient)client;
				LotteryPlatformBaseResponse resp = matcheOddsClient.getOddsByLotteryId(lotteryId);
				List<OddsBase> odds = matchOddsStoreParser.parseMatcheOdds(resp, getCurrentPlatformId(), LotteryId.get(lotteryId));
				storeData(storeDataName, odds);
				logger.info("获取到lotteryId={}的彩种的{}种玩法赔率", lotteryId,odds.size());
			} else {
				logger.error("matcheOddsClient错误！client不是ILotteryPlatformQueryMatcheOddsClient的实现！");
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	/**
	 * 异步保存数据。
	 * @param name 数据名称
	 * @param data 被保存的数据对象列表
	 */
	protected void storeData(String name, List<?> data) {
		if(StringUtils.equals(super.getCurrentPlatformId(), LotteryPlatformId.AN_RUI_ZHI_YING)
				|| StringUtils.equals(super.getCurrentPlatformId(), LotteryPlatformId.QIU_TAN)){
			super.storeData(anRuiStoreDataName, data);
		} else if(StringUtils.equals(super.getCurrentPlatformId(), LotteryPlatformId.ZUN_AO)){
			super.storeData(super.getStoreDataName(), data);
		}
	}

	public String getLotteryId() {
		return lotteryId;
	}

	public void setLotteryId(String lotteryId) {
		this.lotteryId = lotteryId; 
	}

	public String getAnRuiStoreDataName() {
		return anRuiStoreDataName;
	}

	public void setAnRuiStoreDataName(String anRuiStoreDataName) {
		this.anRuiStoreDataName = anRuiStoreDataName;
	}
}
