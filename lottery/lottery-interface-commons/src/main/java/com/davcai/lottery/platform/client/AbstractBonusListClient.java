package com.davcai.lottery.platform.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.davcai.lottery.platform.client.model.QueryPrizeResponse;
import com.davcai.lottery.platform.client.model.QueryPrizeResponse4OneLoop;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.GetBonusListResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.GetBonusListResponse4OneLoop;

public abstract class AbstractBonusListClient implements ILotteryPlatformGetBonusListClient{
	
	protected Logger logger=LoggerFactory.getLogger(getClass());
	

	@Override
	public GetBonusListResponse queryPrize(int startTime, int stopTime) {
		GetBonusListResponse getBonusListResponse = null;
		logger.debug("快速出票，查询金额开始时间{},结束时间{}",startTime,stopTime);
		
		if(0 != startTime && 0!= stopTime && startTime < stopTime)
		{
			getBonusListResponse = new GetBonusListResponse();
			GetBonusListResponse4OneLoop getBonusOneLoop = new GetBonusListResponse4OneLoop();
			
			handleOneLoop(getBonusOneLoop,startTime,stopTime);
			
			getBonusListResponse.setGetBonusListResponse4OneLoop(getBonusOneLoop);
		}
		return getBonusListResponse;
	}

	//一次请求
	private void handleOneLoop(GetBonusListResponse4OneLoop getBonusOneLoop,int startTime,int stopTime)
	{
		getBonusOneLoop = queryPrizeForOneLoop(startTime, stopTime);
		logger.debug("getBonusOneLoop={}",getBonusOneLoop);
	}
	
	
	@Override
	public  abstract GetBonusListResponse4OneLoop queryPrizeForOneLoop(int startTime, int stopTime);
	
	
	
}
