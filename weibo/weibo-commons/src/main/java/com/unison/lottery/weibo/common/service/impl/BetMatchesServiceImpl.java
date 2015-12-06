package com.unison.lottery.weibo.common.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.common.service.BetMatchesService;
import com.xhcms.lottery.commons.persist.entity.MixMatchPlay;

@Service
public class BetMatchesServiceImpl extends BaseDaoImpl<MixMatchPlay> implements BetMatchesService {
	
	private String getMixMatchCacheKey(String time, String lotteryId){
		return String.format("MixMatchCacheKey:%s:%s",lotteryId,time);
	}
	private String getMixMatchKey(String matchId, String lotteryId){
		return String.format("MixMatchPlays:%s:%s",lotteryId,matchId);
	}
	
	private static Set<String> dateTypeField = new HashSet<String>();
	static {
		dateTypeField.add("playingTime");
		dateTypeField.add("offtime");
		dateTypeField.add("entrustDeadline");
	}
	@Override
	public List<MixMatchPlay> getMixMatchPlaysByDate(String time, String lotteryId) {
		time = getValidTime(time);
		String mixMatchCacheKey = getMixMatchCacheKey(time, lotteryId);
		List<MixMatchPlay> retVal = new ArrayList<MixMatchPlay>();
		LinkedHashSet<String> matchIds = zrange(mixMatchCacheKey, 0, -1);
		
		for (String k : matchIds) {
			retVal.add(hashGetIncludeDateProperty(getMixMatchKey(k,lotteryId),
					MixMatchPlay.class, dateTypeField));
		}
		return retVal;
	}
	
	@Override
	public void saveMixMatchPlaysCache(List<MixMatchPlay> mixMatchPlays,
			String time, String lotteryId) {
		double i=1;
		String key=getMixMatchCacheKey(time, lotteryId);
		delete(key);
		for (MixMatchPlay mixMatchPlay : mixMatchPlays) {
			zadd(i, key, mixMatchPlay.getId()+"");
			hashAddIncludeInheriteProperty(getMixMatchKey(mixMatchPlay.getId()+"",lotteryId), mixMatchPlay);
			i++;
		}
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
		if(time.equals(s.format(new Date()))){//当前时间和缓存的对象是同一天
			expire(key, 60*15);//15分钟
		} else {
			expire(key, 60*60*24*30);//30天
		}
	}
	
	private String getValidTime(String time){
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(null == time){
				time = simpleDateFormat.format(new Date());
				return time;
			}
			simpleDateFormat.parse(time);
			return time;
		} catch (ParseException e) {
			time = simpleDateFormat.format(new Date());
			return time;
		}
	}
}
