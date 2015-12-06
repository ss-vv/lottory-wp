package com.xhcms.lottery.dc.task.fetch.zm;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.unison.lottery.mc.uni.client.QueryJCOddsClient;
import com.unison.lottery.mc.uni.parser.QueryJCOddsParserStatus;
import com.xhcms.lottery.dc.data.OddsBase;

/**
 * 获取竞彩赔率。
 * @author Yang Bo
 */
public class FetchJCOddsTask extends ZMFetchTask {

	// 要获取的比赛类型，支持：jczqdg,jczqgg,jclqdg,jclqgg
	private String type;
	
	@Override
	protected void execute() throws Exception {
		if (StringUtils.isBlank(storeDataName) || StringUtils.isBlank(type)){
			throw new IllegalStateException("type and storeDataName can not be blank!");
		}
		logger.info("Start Fetch JC Odds Task for '{}'...", type);
		QueryJCOddsClient client = (QueryJCOddsClient) getZmClient();
		QueryJCOddsParserStatus status = new QueryJCOddsParserStatus(type);
		client.postWithStatus(type, status);
		List<OddsBase> odds = status.getOdds();
		storeData(storeDataName , odds);
		logger.info("Get and stored {} {} odds.", odds.size(), type);
		logger.info("End Fetch Matches Task.");
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
