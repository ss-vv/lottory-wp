package com.davcai.lottery.push.common.redis.dao;

import com.davcai.lottery.push.common.model.LotteryType;
import com.davcai.lottery.push.common.model.OddsType;
import com.davcai.lottery.push.common.model.PushMessage;
import com.unison.lottery.weibo.common.nosql.BaseDao;

public interface PushMessageDao extends BaseDao<PushMessage>{

	/**
	 * 根据参数pushMessage的若干信息从redis中获取PushMessage对象
	 * @param pushMessage
	 * @return
	 */
	PushMessage findByTemplate(PushMessage pushMessage);

	void save(PushMessage pushMessage);
	
	String findFieldValueByTemplate(String key,String field);

	String findOddsByKey(String matchId, String field, OddsType oddsType,LotteryType lotteryType);

	/**
	 * 
	 * @param playingCode比赛标示
	 * @param corpId 公司id
	 * @param data 数据
	 * @param oddsType赔率类型
	 * @param lotteryType 彩种类型
	 */
	void hashAddOdds(String playingCode, String corpId, String data, OddsType oddsType,LotteryType lotteryType);

}
