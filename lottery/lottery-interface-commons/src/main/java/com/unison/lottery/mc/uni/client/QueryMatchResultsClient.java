package com.unison.lottery.mc.uni.client;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.mc.uni.ZMClient;
import com.unison.lottery.mc.uni.parser.QueryMatchResultsParserStatus;

/**
 * 查询比赛结果接口（009）。
 * @author Yang Bo
 * @version 1.0.0
 */
public class QueryMatchResultsClient extends ZMClient {
    
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public QueryMatchResultsClient(){
		setTranscode("009");
	}
	
	/**
	 * 查询在售比赛信息。 会返回前4天的比赛结果数据。
	 * @param type - jczq,jclq,bd 同接口文档定义。
	 * @return true 成功; false 如果参数不正确。
	 */
    public boolean postWithStatus(String type, String issue, QueryMatchResultsParserStatus matchResultParserStatus){
    	if (!typeIsValid(type) || !typeIsValid(matchResultParserStatus.getType())){
    		logger.error("Unsupported type: " + type);
    		return false;
    	}
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("lotteryType", type);
        values.put("issueNumber", issue);
        postWithStatus(values, matchResultParserStatus);
        return true;
    }

	private boolean typeIsValid(String type) {
		String[] validTypes = new String[] {"jczq","jclq","SPF","BF","SXDS","JQS","BQC","SF"};//添加bjdc的胜负bdsf
		for (String theType : validTypes) {
			if (theType.equals(type)){
				return true;
			}
		}
		return false;
	}
}
