package com.davcai.lottery.platform.client.anruizhiying;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davcai.lottery.platform.client.AbstractClientSupport;
import com.davcai.lottery.platform.client.ILotteryPlatformQueryMatcheOddsClient;
import com.davcai.lottery.platform.client.ILotteryPlatformQueryMatchesClient;
import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.anruizhiying.constants.AnRuiZhiYingTransCode;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchModel;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingJCMatchesResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingResponse;
import com.davcai.lottery.platform.client.anruizhiying.parser.IAnRuiZhiYingRespParser;
import com.xhcms.lottery.lang.LotteryId;

/**
 * @author haoxiang.jiang@davcai.com
 * @date 2015年1月9日 下午6:19:45
 */
@Service
public class AnRuiQueryMatchesClient extends AbstractAnRuiZhiYingClientSupport 
		implements ILotteryPlatformQueryMatchesClient,ILotteryPlatformQueryMatcheOddsClient{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IAnRuiZhiYingRespParser anRuiZhiYingJCMatchesParser;
	
	private String interfaceUrl4FootballPV;
	private String interfaceUrl4BasketballPV;
	
	public AnRuiQueryMatchesClient(){
	}
	
	@Override
	public LotteryPlatformBaseResponse getOddsByLotteryId(String lotteryId) {
		return postByMatchType(lotteryId);
	}
	/**
	 * 查询在售比赛信息。 
	 * @param matchType - JCLQ,JCZQ
	 * @return AnRuiZhiYingResponse
	 */
	@Override
	public AnRuiZhiYingResponse postByMatchType(String matchType) {
		AnRuiZhiYingJCMatchesResponse ret = new AnRuiZhiYingJCMatchesResponse();
    	if (!typeIsValid(matchType)){
    		logger.error("安瑞智赢竞彩足球赛程查询客户端不支持的赛事类型,type=" + matchType);
    		ret.setMatches(new ArrayList<AnRuiZhiYingJCMatchModel>());
    		return ret;
    	}
    	if(LotteryId.JCZQ.name().equals(matchType)){
			this.setInterfaceUrl(getInterfaceUrl4FootballPV());
    	} else if(LotteryId.JCLQ.name().equals(matchType)){
    		this.setInterfaceUrl(getInterfaceUrl4BasketballPV());
    	}
        return (AnRuiZhiYingResponse) doGetDirect(null);
	}
	
	private boolean typeIsValid(String type) {
		String[] validTypes = new String[] {"JCLQ","JCZQ"};
		for (String theType : validTypes) {
			if (theType.equals(type)){
				return true;
			}
		}
		return false;
	}

	@Override
	protected AnRuiZhiYingResponse parseResponse(String responseStr) {
		return anRuiZhiYingJCMatchesParser.parseResp(responseStr);
	}

	@Override
	protected LinkedHashSet<String> getParamsShouldSign() {
		LinkedHashSet<String> list = new LinkedHashSet<String>();
		list.add("LotteryID");
		list.add("ChannelID");
		return list;
	}

	@Override
	protected String getTransCode() {
		return AnRuiZhiYingTransCode.QUERY_MATCH;
	}


	@Override
	protected int getMaxRetryTime() {
		return 0;
	}


	@Override
	protected boolean shouldRetry(LotteryPlatformBaseResponse result) {
		return false;
	}

	public String getInterfaceUrl4FootballPV() {
		return interfaceUrl4FootballPV;
	}

	public void setInterfaceUrl4FootballPV(String interfaceUrl4FootballPV) {
		this.interfaceUrl4FootballPV = interfaceUrl4FootballPV;
	}

	public String getInterfaceUrl4BasketballPV() {
		return interfaceUrl4BasketballPV;
	}

	public void setInterfaceUrl4BasketballPV(String interfaceUrl4BasketballPV) {
		this.interfaceUrl4BasketballPV = interfaceUrl4BasketballPV;
	}
}
