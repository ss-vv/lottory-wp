package com.unison.lottery.mc.uni.client;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.mc.uni.ZMClient;
import com.unison.lottery.mc.uni.parser.QueryMatchesParserStatus;

/**
 * 查询在售比赛接口（016）。
 * @author Yang Bo
 * @version 1.0.0
 */
public class QueryMatchesClient extends ZMClient {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public QueryMatchesClient(){
		setTranscode("016");
	}
	
	/**
	 * 查询在售比赛信息。 
	 * @param type - jczq,jclq,jcgj,jcgy,jcsjbgj 同接口文档定义。
	 * @return true 成功; false 如果参数不正确。
	 */
    public boolean postWithStatus(String type, QueryMatchesParserStatus matchParserStatus){
    	if (!typeIsValid(type) || !typeIsValid(matchParserStatus.getType())){
    		logger.error("Unsupported type: " + type);
    		return false;
    	}
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("type", type);
        postWithStatus(values, matchParserStatus);
        return true;
    }

	private boolean typeIsValid(String type) {
		String[] validTypes = new String[] {"jczq","jclq","jcgj","jcgy", "jcsjbgj"};
		for (String theType : validTypes) {
			if (theType.equals(type)){
				return true;
			}
		}
		return false;
	}
}
