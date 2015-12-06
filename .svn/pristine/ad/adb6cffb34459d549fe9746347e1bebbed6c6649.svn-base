package com.unison.lottery.mc.uni.client;

import java.util.HashMap;

import com.unison.lottery.mc.uni.ZMClient;
import com.unison.lottery.mc.uni.parser.QueryIssueResponseParserStatus;

public class QueryIssueClient extends ZMClient{

	public QueryIssueClient() {
		super();
		setTranscode("001");
	}

	/**
	 * 期查询接口（001）
	 * @param lotteryId 彩种id
	 * @param issueNumber 期数，如为空，则查询当天所有期信息
	 * @param queryIssueResponseParserStatus
	 * @return
	 */
	public boolean postWithStatus(String lotteryId, String issueNumber,
			QueryIssueResponseParserStatus queryIssueResponseParserStatus) {
		
		HashMap<String, Object> values = new HashMap<String, Object>();
        
        values.put("lotteryId", lotteryId);
        values.put("issueNumber", issueNumber);
        return this.postWithStatus(values, queryIssueResponseParserStatus);
		
	}

}
