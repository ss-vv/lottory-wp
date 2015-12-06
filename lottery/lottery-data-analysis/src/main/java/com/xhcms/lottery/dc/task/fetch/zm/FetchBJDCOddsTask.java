package com.xhcms.lottery.dc.task.fetch.zm;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import com.unison.lottery.mc.uni.client.QueryBJDCOddsClient;
import com.unison.lottery.mc.uni.parser.QueryBJDCOddsParserStatus;
import com.unison.lottery.mc.uni.parser.util.ZMInterfaceConstants;
import com.xhcms.lottery.dc.data.BDOdds;

/**
 * 获取北京单场赔率
 */
public class FetchBJDCOddsTask extends ZMFetchTask {

	//支持：SPF,BF,JQS,BQC,SXDS
	private List<String> playType;
	
	@Override
	protected void execute() throws Exception {
		if (StringUtils.isBlank(storeDataName) || 
				null == playType || playType.size() == 0) {
			throw new IllegalStateException(
					"type and storeDataName can not be blank!");
		}
		for(String type : playType) {
			logger.info("Start Fetch BJDC Odds Task for '{}'...", type);
			QueryBJDCOddsClient client = (QueryBJDCOddsClient) getZmClient();
			QueryBJDCOddsParserStatus status = new QueryBJDCOddsParserStatus(type);
			
			String queryPlayType = ZMInterfaceConstants.SPF;
			if(type.equals(ZMInterfaceConstants.SF)) {
				queryPlayType = ZMInterfaceConstants.SF;
			}
			
			client.postWithStatusAndType(type, queryPlayType, status);
			List<BDOdds> odds = status.getOdds();
			String name = storeDataName + "_" + type;
			storeData(name, odds);
			logger.info("Get and stored {} ,BJDC {} odds.", odds.size(), type);
			logger.info("End Fetch Matches Task.");
		}
	}

	public List<String> getPlayType() {
		return playType;
	}

	public void setPlayType(List<String> playType) {
		this.playType = playType;
	}
}