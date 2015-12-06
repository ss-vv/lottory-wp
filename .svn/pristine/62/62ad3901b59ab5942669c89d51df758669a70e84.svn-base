package com.unison.lottery.mc.uni.client;

import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.unison.lottery.mc.uni.ZMClient;
import com.unison.lottery.mc.uni.parser.QueryBJDCOddsParserStatus;
import com.unison.lottery.mc.uni.parser.util.LCZMIDMapper;
import com.unison.lottery.mc.uni.parser.util.PlayTypeValidation;
import com.unison.lottery.mc.uni.parser.util.ZMInterfaceConstants;
import com.xhcms.lottery.commons.data.IssueInfo;
import com.xhcms.lottery.commons.persist.service.IssueService;
import com.xhcms.lottery.lang.PlayType;

/**
 * 查询北单赔率接口（008）
 */
public class QueryBJDCOddsClient extends ZMClient {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	private LCZMIDMapper idMapper = new LCZMIDMapper();
	@Autowired
	IssueService  issueService;
	
	public QueryBJDCOddsClient(){
		setTranscode("008");
	}
	
	/**
	 * 查询当前期的北单赔率。 
	 * @param lotteryId -  SPF,BF,JQS,BQC,SXDS 同接口文档定义。
	 * @param startTime - 指定的查询时间
	 * @return true 成功; false 如果参数不正确。
	 */
	public boolean postWithStatus(String lotteryId, String issueNumber, 
			QueryBJDCOddsParserStatus oddsParserStatus){
		boolean result = !PlayTypeValidation.typeIsValid(lotteryId, ZMInterfaceConstants.getBjdcArray()) ||
    			!PlayTypeValidation.typeIsValid(oddsParserStatus.getType(), 
    					ZMInterfaceConstants.getBjdcArray());
		if (result){
    		logger.error("Unsupported lotteryId: " + lotteryId);
    		return false;
    	}
        HashMap<String, Object> values = new HashMap<String, Object>();
        values.put("lotteryId", lotteryId);
		values.put("issueNumber", issueNumber);
        return postWithStatus(values, oddsParserStatus);
	}
	
	/**
	 * 查询当前期的北单赔率。 
	 * @param lotteryId -  SPF,BF,JQS,BQC,SXDS 同接口文档定义。
	 * @param startTime - 指定的查询时间
	 * @return true 成功; false 如果参数不正确。
	 */
    public boolean postWithStatus(String type, QueryBJDCOddsParserStatus oddsParserStatus){
    	List<IssueInfo> issueInfos = null;
		try {
			PlayType playType = idMapper.getLCPlayTypeFromPlatformLotteryId(type);
			issueInfos = issueService.findOnSalingStatusIssueListByPlayId(playType.getPlayId());
		} catch (Exception e) {
			logger.info("find onsale issueNumbers Error from LotteryId = " + type, e);
			e.printStackTrace();
		}
		
		if(issueInfos == null || issueInfos.isEmpty()){
			logger.info("not find onsale issueInfo from LotteryId = " + type);
			return false;
		}
		String issueNumber = issueInfos.get(0).getIssueNumber();
		logger.info("query bjdc odds: type={}, issueNumber={}", type, issueNumber);
    	return postWithStatus(type, issueNumber, oddsParserStatus);
    }
    
    public boolean postWithStatusAndType(String type, String queryIssueType, 
    		QueryBJDCOddsParserStatus oddsParserStatus){
    	List<IssueInfo> issueInfos = null;
		try {
			PlayType playType = idMapper.getLCPlayTypeFromPlatformLotteryId(queryIssueType);
			issueInfos = issueService.findOnSalingStatusIssueListByPlayId(playType.getPlayId());
		} catch (Exception e) {
			logger.info("find onsale issueNumbers Error from LotteryId = " + type, e);
			e.printStackTrace();
		}
		
		if(issueInfos == null || issueInfos.isEmpty()){
			logger.info("not find onsale issueInfo from LotteryId = " + type);
			return false;
		}
		
		String issueNumber = issueInfos.get(0).getIssueNumber();
		logger.info("query bjdc odds: type={}, issueNumber={}", type, issueNumber);
    	return postWithStatus(type, issueNumber, oddsParserStatus);
    }
}