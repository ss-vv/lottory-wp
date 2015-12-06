package com.unison.lottery.mc.uni.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.mc.uni.ZMClient;
import com.unison.lottery.mc.uni.parser.QueryJCOddsParserStatus;

/**
 * 查询竞彩赔率接口（013）。
 * @author Yang Bo
 * @version 1.0.0
 */
public class QueryJCOddsClient extends ZMClient {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	
	public QueryJCOddsClient(){
		setTranscode("013");
	}
	
	/**
	 * 查询指定日期及4天后的竞彩赔率。 
	 * @param type - jczqdg,jczqgg,jclqdg,jclqgg 同接口文档定义。
	 * @param startTime - 指定的查询时间
	 * @return true 成功; false 如果参数不正确。
	 */
	public boolean postWithStatus(String type, Date startTime, 
			QueryJCOddsParserStatus oddsParserStatus){

		if (!typeIsValid(type) || !typeIsValid(oddsParserStatus.getType())){
    		logger.error("Unsupported type: " + type);
    		return false;
    	}
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("type", type);
		values.put("matchDate", format.format(startTime));
        return postWithStatus(values, oddsParserStatus);
	}
	
	/**
	 * 查询当天及4天后的竞彩赔率。 
	 * @param type - jczqdg,jczqgg,jclqdg,jclqgg 同接口文档定义。
	 * @return true 成功; false 如果参数不正确。
	 */
    public boolean postWithStatus(String type, QueryJCOddsParserStatus oddsParserStatus){
    	return postWithStatus(type, new Date(), oddsParserStatus);
    }

	private boolean typeIsValid(String type) {
		String[] validTypes = new String[] {"jczqdg","jczqgg","jclqdg","jclqgg"};
		for (String theType : validTypes) {
			if (theType.equals(type)){
				return true;
			}
		}
		return false;
	}
}
