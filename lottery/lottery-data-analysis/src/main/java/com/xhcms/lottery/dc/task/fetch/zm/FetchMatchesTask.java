package com.xhcms.lottery.dc.task.fetch.zm;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.unison.lottery.mc.uni.client.QueryMatchesClient;
import com.unison.lottery.mc.uni.parser.QueryMatchesParserStatus;
import com.xhcms.lottery.dc.data.Match;

/**
 * 获取赛程。
 * @author Yang Bo
 */
public class FetchMatchesTask extends ZMFetchTask {

	// 要获取的比赛类型，支持：jczq,jclq,jcgj,jcgy
	private String type;
	
	@Override
	protected void execute() throws Exception {
		if (StringUtils.isBlank(storeDataName) || StringUtils.isBlank(type)){
			throw new IllegalStateException("type and storeDataName can not be blank!");
		}
		logger.info("Start Fetch Matches Task for '{}'...", type);
		QueryMatchesClient client = (QueryMatchesClient) getZmClient();
		QueryMatchesParserStatus status = new QueryMatchesParserStatus(type);
		client.postWithStatus(type, status);
		List<Match> matches = status.getMatches();
		storeData(storeDataName , matches);
		logger.debug("Get and stored {} {} matches.", matches.size(), type);
		logger.info("End Fetch Matches Task.");
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
