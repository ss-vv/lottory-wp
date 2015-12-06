package com.unison.lottery.mc.uni.client;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.unison.lottery.mc.uni.ZMClient;
import com.unison.lottery.mc.uni.parser.QueryMatchesCTZCParserStatus;
import com.unison.lottery.mc.uni.parser.util.ZMInterfaceConstants;

/**
 * 查询在售传统足彩比赛接口（015）。
 * @author Wang Lei
 * @version 1.0.0
 */
public class QueryMatchesCTZCClient extends ZMClient {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public QueryMatchesCTZCClient(){
		setTranscode("015");
	}
	
	/**
	 * 查询在售比赛信息。 
	 * @param type - 14CSF,SFR9,6CBQ,4CJQ 同接口文档定义。
	 * @return true 成功; false 如果参数不正确。
	 */
    public boolean postWithStatus(Map<String, Object> values, QueryMatchesCTZCParserStatus matchParserStatus){
    	String type = (String) values.get("type");
    	if (!typeIsValid(type) || !typeIsValid(matchParserStatus.getType())){
    		logger.error("Unsupported type: " + type);
    		return false;
    	}
    	super.postWithStatus(values, matchParserStatus);
        return true;
    }

	private boolean typeIsValid(String type) {
		String[] validTypes = new String[] {ZMInterfaceConstants.SF14,ZMInterfaceConstants.JQ4C,ZMInterfaceConstants.SFR9,ZMInterfaceConstants.BQ6C};
		for (String theType : validTypes) {
			if (theType.equals(type)){
				return true;
			}
		}
		return false;
	}
}
