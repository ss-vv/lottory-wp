package com.unison.lottery.mc.uni.client;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.unison.lottery.mc.uni.ZMClient;
import com.unison.lottery.mc.uni.parser.QueryMatchesBJDCParserStatus;
import com.unison.lottery.mc.uni.parser.util.PlayTypeValidation;
import com.unison.lottery.mc.uni.parser.util.ZMInterfaceConstants;

/**
 * 查询北京单场比赛接口（006）。
 */
public class QueryMatchesBJDCClient extends ZMClient {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public QueryMatchesBJDCClient(){
		setTranscode("006");
	}
	
	/**
	 * 查询在售比赛信息。 
	 * @param type - SPF,BF,JQS,BQC,SXDS 同接口文档定义。
	 * @return true 成功; false 如果参数不正确。
	 */
    public boolean postWithStatus(Map<String, Object> values, QueryMatchesBJDCParserStatus matchParserStatus){
    	String type = (String) values.get("type");
    	
    	boolean result = !PlayTypeValidation.typeIsValid(type, ZMInterfaceConstants.getBjdcArray()) ||
    			!PlayTypeValidation.typeIsValid(matchParserStatus.getType(), 
    					ZMInterfaceConstants.getBjdcArray());
    	if (result){
    		logger.error("Unsupported type: " + type);
    		return false;
    	}
    	super.postWithStatus(values, matchParserStatus);
        return true;
    }
}