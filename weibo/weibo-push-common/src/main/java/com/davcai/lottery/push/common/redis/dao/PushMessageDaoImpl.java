package com.davcai.lottery.push.common.redis.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.push.common.model.LotteryType;
import com.davcai.lottery.push.common.model.OddsType;
import com.davcai.lottery.push.common.model.PushMessage;
import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.common.nosql.impl.Keys;

public class PushMessageDaoImpl extends BaseDaoImpl<PushMessage> implements
		PushMessageDao {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private Set<String> propertyNamesOfDate = new HashSet<String>();

	@Override
	public com.davcai.lottery.push.common.model.PushMessage findByTemplate(
			PushMessage pushMessage) {
		PushMessage result = null;
		if (null != pushMessage && StringUtils.isNotBlank(pushMessage.getId())) {

			result = hashGetIncludeDateProperty(
					Keys.pushMessageKey(pushMessage.getId(),
							pushMessage.getClass()), pushMessage.getClass(),
					propertyNamesOfDate);
		}
		return result;
	}

	@Override
	public void save(
			com.davcai.lottery.push.common.model.PushMessage pushMessage) {
		if (null != pushMessage && StringUtils.isNotBlank(pushMessage.getId())) {
			hashAddIncludeInheriteProperty(
					Keys.pushMessageKey(pushMessage.getId(),
							pushMessage.getClass()), pushMessage);
		}

	}

	public Set<String> getPropertyNamesOfDate() {
		return propertyNamesOfDate;
	}

	public void setPropertyNamesOfDate(Set<String> propertyNamesOfDate) {
		this.propertyNamesOfDate = propertyNamesOfDate;
	}

	@Override
	public String findOddsByKey(String matchId, String field,
			OddsType oddsType, LotteryType lotteryType) {
		String key = makeOddsKey(matchId, oddsType, lotteryType);
		return findFieldValueByTemplate(key, field);
	}

	private String makeOddsKey(String matchId, OddsType oddsType,
			LotteryType lotteryType) {
		String key = "";
		switch (lotteryType) {
		case JCZQ: {
			switch (oddsType) {
			case europe:
				key = Keys.getFbMatchEuropeodds(matchId);
				break;
			case asian:
				key = Keys.getFbMatchAsiaOdds(matchId);
				break;
			case bigsmall:
				key = Keys.getFbMatchBigOrSmallOdds(matchId);
				break;
			}
		};break;
		case JCLQ:{
			switch (oddsType) {
			case europe:
				key = Keys.getBbMatchEuropeodds(matchId);
				break;
			case asian:
				key = Keys.getBbMatchAsiaOdds(matchId);
				break;
			case bigsmall:
				key = Keys.getBbMatchBigOrSmallOdds(matchId);
				break;
			}
		};break;
		default:
			break;
		}
		return key;
	}

	@Override
	public String findFieldValueByTemplate(String key, String field) {
		// TODO Auto-generated method stub
		return hget(key, field);
	}

	@Override
	public void hashAddOdds(String playingCode, String corpId, String data,
			OddsType oddsType,LotteryType lotteryType) {
		// TODO Auto-generated method stub
		String key = makeOddsKey(playingCode, oddsType,lotteryType);
		hashAdd(key, corpId, data);
	}

}
