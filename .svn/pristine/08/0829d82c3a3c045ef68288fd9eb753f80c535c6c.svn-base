package com.davcai.lottery.push.common.redis;

import com.davcai.lottery.push.common.model.LotteryType;
import com.davcai.lottery.push.common.model.OddsType;
import com.davcai.lottery.push.common.model.PushMessage;


public interface RedisClient {

	/**
	 * 根据参数pushMessage的若干信息从redis中获取PushMessage对象
	 * @param pushMessage
	 * @return
	 */
	PushMessage getPushMessageByTemplate(PushMessage pushMessage);

	void savePushMessage(PushMessage pushMessage);

	public abstract void deletePushMessage(PushMessage pushMessage);
	
	String findFbOddsByCorpId(String matchId,String corpId,OddsType oddsType);

	void addOdds(String playingCode, String data,String corpId,OddsType oddsType,LotteryType lotteryType);
	

}
