package com.xhcms.lottery.dc.task.fetch.zm;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.mc.uni.client.QueryMatchesCTZCClient;
import com.unison.lottery.mc.uni.parser.QueryMatchesCTZCParserStatus;
import com.unison.lottery.mc.uni.parser.util.LCZMIDMapper;
import com.xhcms.lottery.commons.data.CTFBMatch;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.PlayType;

/**
 * 获取传统足彩赛程。
 * @author Wang Lei
 */
public class FetchMatchesCTZCTask extends ZMFetchTask {

	// 要获取的比赛类型，支持：14CSF,SFR9,6CBQ,4CJQ 同接口文档定义。
	private String type;
	private LCZMIDMapper idMapper = new LCZMIDMapper();
	
	@Autowired
	IssueService  issueService;
	
	@Override
	@Transactional
	protected void execute() throws Exception {
		if (StringUtils.isBlank(storeDataName) || StringUtils.isBlank(type)){
			throw new IllegalStateException("type and storeDataName can not be blank!");
		}
		logger.info("Start Fetch Matches Task for '{}'...", type);
		QueryMatchesCTZCClient client = (QueryMatchesCTZCClient) getZmClient();
		QueryMatchesCTZCParserStatus status = new QueryMatchesCTZCParserStatus(type);
		List<IssueInfo> issueInfos = null;
		try {
			PlayType playType = idMapper.getLCPlayTypeFromPlatformLotteryId(type);
			issueInfos = issueService.findOnSalingStatusIssueListByPlayId(playType.getPlayId());
		} catch (Exception e) {
			logger.info("find onsale issueNumbers Error from LotteryId = " + type, e);
		}
		
		if(issueInfos == null || issueInfos.isEmpty()){
			logger.info("not find onsale issueInfo from LotteryId = " + type);
			return;
		}
		for(IssueInfo issueInfo:issueInfos){
			HashMap<String, Object> values = new HashMap<String, Object>();
	        values.put("type", type);
	        values.put("issueNumber", issueInfo.getIssueNumber());
	        status.setIssueNumber(issueInfo.getIssueNumber());

			client.postWithStatus(values, status);
			List<CTFBMatch> matches = status.getMatches();
			storeData(storeDataName , matches);
			logger.debug("Get and stored {} {} matches.", matches.size(), type);
			logger.info("End Fetch Matches Task.");
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
