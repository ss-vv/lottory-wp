package com.unison.lottery.mc.uni.client;

import java.util.HashMap;
import java.util.List;

import com.unison.lottery.mc.uni.ZMClient;
import com.unison.lottery.mc.uni.parser.QueryPrizeParserStatus;

/**
 * 中奖结果查询接口（004）。
 * @author Yang Bo
 * @version 1.0.0
 */
public class QueryPrizeClient extends ZMClient {
    
	private static final int EXPECTED_TRANSCODE = 104;
	public QueryPrizeClient(){
		setTranscode("004");
	}
	
	/**
	 * 查询票的中奖结果。
	 * @param zmLotteryId 彩种，在《中民接口文档中的附录5.1》定义
	 * @param issueNumber 要查询的期数，竞彩是日期。
	 */
    public boolean postWithStatus(
    		String zmLotteryId, 
    		String issueNumber, 
    		QueryPrizeParserStatus parserStatus,
    		IPrizeProcessor prizeProcessor){
        HashMap<String, Object> values = new HashMap<String, Object>();
        
        do {
	        values.put("lotteryId", zmLotteryId);
	        values.put("issueNumber", issueNumber);
	        values.put("prevTicketId", parserStatus.getPrevPlatformTicketId());
	        values.put("status", 0);	// 查询全部票
	        
	        parserStatus.clearTickets();
	        postWithStatus(values, parserStatus);
	        prizeProcessor.process(parserStatus);
        } while (!parserStatus.isReachLastPage() && 
        		parserStatus.getErrorCode()==EXPECTED_TRANSCODE);
        return true;
    }
    /**
     * 只查询中奖结果，不更新票在数据库中的状态
     * @param zmLotteryId
     * @param issue
     * @param parserStatuses
     * @return
     */

	public boolean postWithStatus(String zmLotteryId, String issueNumber,
			QueryPrizeParserStatus parserStatus) {
		if(null!=parserStatus){
			HashMap<String, Object> values = new HashMap<String, Object>();
			do {
				values.put("lotteryId", zmLotteryId);
				values.put("issueNumber", issueNumber);
				values.put("prevTicketId",
						parserStatus.getPrevPlatformTicketId());
				values.put("status", 0); // 查询全部票

//				parserStatus.clearTickets();
				postWithStatus(values, parserStatus);
			} while (!parserStatus.isReachLastPage()
					&& parserStatus.getErrorCode() == EXPECTED_TRANSCODE);
			return true;
		}
		return false;
	}

}
