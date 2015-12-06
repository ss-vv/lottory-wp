package com.davcai.lottery.push.common.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.push.common.model.FootballMatchMessage;
import com.davcai.lottery.push.common.model.LotteryType;
import com.davcai.lottery.push.common.model.OddsType;
import com.davcai.lottery.push.common.model.PushMessage;
import com.davcai.lottery.push.common.redis.RedisClient;
import com.davcai.lottery.push.common.redis.dao.PushMessageDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;

public class RedisClientImpl implements RedisClient {

	@Autowired
	private PushMessageDao pushMessageDao;
	
	@Override
	public PushMessage getPushMessageByTemplate(PushMessage pushMessage) {
		PushMessage result=null;
		if(null!=pushMessage){
			result=pushMessageDao.findByTemplate(pushMessage);
		}
		return result;
		
	}

	@Override
	public void savePushMessage(PushMessage pushMessage) {
		if(null!=pushMessage&&StringUtils.isNotBlank(pushMessage.getId())){
			pushMessageDao.save(pushMessage);
		}
		

	}

	public PushMessageDao getPushMessageDao() {
		return pushMessageDao;
	}

	public void setPushMessageDao(PushMessageDao pushMessageDao) {
		this.pushMessageDao = pushMessageDao;
	}

	@Override
	public void deletePushMessage(PushMessage pushMessage) {
		if(null!=pushMessage&&StringUtils.isNotBlank(pushMessage.getId())){
			pushMessageDao.delete(Keys.pushMessageKey(pushMessage.getId(),pushMessage.getClass()));
			
		}
		
	}

	@Override
	public String findFbOddsByCorpId(String matchId, String corpId, OddsType oddsType) {
		if(StringUtils.isNotBlank(matchId)&&StringUtils.isNotBlank(corpId)&&oddsType!=null){
			return pushMessageDao.findOddsByKey(matchId, corpId, oddsType,LotteryType.JCZQ);
		}
		return null;
	}

	@Override
	public void addOdds(String playingCode, String data,String corpId, OddsType oddsType,LotteryType lotteryType) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(playingCode)&&StringUtils.isNotBlank(data)&&StringUtils.isNotBlank(corpId)){
			pushMessageDao.hashAddOdds(playingCode, corpId, data,oddsType,lotteryType);
		}
	}

	public String findBbOddsByCorpId(String playingCodeId, String corpId,
			OddsType pushOddsDataType) {
		// TODO Auto-generated method stub
		if(StringUtils.isNotBlank(playingCodeId)&&StringUtils.isNotBlank(corpId)&&pushOddsDataType!=null){
			return pushMessageDao.findOddsByKey(playingCodeId, corpId, pushOddsDataType,LotteryType.JCLQ);
		}
		return null;
	}

}
