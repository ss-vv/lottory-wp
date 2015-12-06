package com.xhcms.lottery.dc.task.fetch.zm;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.mc.uni.client.QueryMatchesBJDCClient;
import com.unison.lottery.mc.uni.parser.QueryMatchesBJDCParserStatus;
import com.unison.lottery.mc.uni.parser.util.LCZMIDMapper;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.dc.data.BDMatch;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.lang.PlayType;

/**
 * 获取北京单场赛程任务。
 */
public class FetchMatchesBJDCTask extends ZMFetchTask {

	// 要获取的比赛类型，支持：SPF,BF,JQS,BQC,SXDS 同接口文档定义。
	private List<String> typeList;
	
	private LCZMIDMapper idMapper = new LCZMIDMapper();
	
	@Autowired
	IssueService  issueService;
	
	@Override
	@Transactional
	protected void execute() throws Exception {
		if (StringUtils.isBlank(storeDataName) || 
				null == typeList || typeList.size() == 0){
			throw new IllegalStateException("type and storeDataName can not be blank!");
		}
		for(String type : typeList) {
			postWithLottery(type);
		}
	}
	
	private void postWithLottery(String zmLotteryId) {
		logger.info("Start Fetch Matches Task for '{}'...", zmLotteryId);
		QueryMatchesBJDCClient client = (QueryMatchesBJDCClient) getZmClient();
		QueryMatchesBJDCParserStatus status = new QueryMatchesBJDCParserStatus(zmLotteryId);
		List<IssueInfo> issueInfos = null;
		PlayType playType = null;
		try {
			playType = idMapper.getLCPlayTypeFromPlatformLotteryId(zmLotteryId);
			issueInfos = issueService.findOnSalingStatusIssueListByPlayId(playType.getPlayId());
		} catch (Exception e) {
			logger.info("find onsale issueNumbers Error from LotteryId = " + zmLotteryId, e);
		}
		
		if(issueInfos == null || issueInfos.isEmpty()){
			logger.info("not find onsale issueInfo from LotteryId = " + zmLotteryId);
			return;
		}
		for(IssueInfo issueInfo:issueInfos){
			HashMap<String, Object> values = new HashMap<String, Object>();
	        values.put("type", zmLotteryId);
	        values.put("issueNumber", issueInfo.getIssueNumber());
	        status.setIssueNumber(issueInfo.getIssueNumber());

			client.postWithStatus(values, status);
			List<BDMatch> matches = status.getMatches();
			String keyName = storeDataName + "_" + zmLotteryId;
			storeData(keyName, matches);
			logger.info("Get and stored {}  matches, lotteryId={}, playType={}.", 
					new Object[]{matches.size(), playType.getLotteryId(), playType.getPlayName()});
			logger.info("End Fetch {} Matches Task.", LotteryId.BJDC);
		}
	}
	
	public List<String> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}
}
