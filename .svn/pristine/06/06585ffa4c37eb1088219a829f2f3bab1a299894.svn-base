package com.unison.lottery.weibo.common.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.unison.lottery.weibo.common.nosql.CommonDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.BetRecordCache;
import com.unison.lottery.weibo.common.service.SchemeService;
import com.unison.lottery.weibo.lang.BetRecordConstant;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.service.BetPartnerService;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.lang.LotteryId;
import com.xhcms.lottery.utils.CombineBetMatchUtil;

@Service
public class BetRecordCacheImpl implements BetRecordCache {

	private static final Logger logger = LoggerFactory
			.getLogger(BetRecordCache.class);

	@Autowired
	CommonDao commonDao;
	@Autowired
	private SchemeService schemeService;
	@Autowired
	private BetSchemeService betSchemeService;
	@Autowired
	private BetPartnerService betPartnerService;
	
	@Override
	public List<String> loadRecord(String lotteryId, long userId) {
		List<String> list = new ArrayList<String>();
		String betRecordKey = Keys.userBetRecord(userId + "", lotteryId + "");
		
		LinkedHashSet<String> schemeIdList = commonDao.zrevrange(betRecordKey, 0, 4);
		if(null == schemeIdList || schemeIdList.size() <= 0) {
			logger.info("用户ID={}，彩种ID={}：找不到投注记录.", userId, lotteryId);
		} else {
			list.addAll(schemeIdList);
		}
		return list;
	}
	
	protected List<BetScheme> loadScheme(String lotteryId, long userId) {
		List<BetScheme> list = new ArrayList<BetScheme>();
		
		List<String> schemeIds = loadRecord(lotteryId, userId);
		if(null != schemeIds && schemeIds.size() > 0) {
			list = splitLoad(schemeIds, userId);
		}
		
		return list;
	}
	
	protected List<BetScheme> splitLoad(List<String> schemeIds, long userId) {
		List<BetScheme> list = new ArrayList<BetScheme>();
		if(null != schemeIds && schemeIds.size() > 0) {
			for(String schemeIdKey : schemeIds) {
				String[] idMode = Keys.splitSchemeIDCacheValue(schemeIdKey);
				
				long schemeId = Long.valueOf(idMode[0]);
				int displayMode = Integer.valueOf(idMode[1]);
				
				BetScheme scheme = schemeService.viewSchemeCache(schemeId, userId, displayMode);
				if(null != scheme && scheme.getId() > 0) {
					if(3 == idMode.length) {
						long betRecordId = Integer.valueOf(idMode[2]);
						scheme.setBetRecordId(betRecordId);
						BetPartner betPartner = betPartnerService.findById(betRecordId);
						if(null != betPartner && betPartner.getId() != null) {
							scheme.setGroupBetMount(betPartner.getBetAmount());
							scheme.setGroupWinAmount(betPartner.getWinAmount());
						}
					}
					//将同一赛事不同玩法合并
					scheme = CombineBetMatchUtil.combine(scheme);
					list.add(scheme);
				}
			}
		}
		return list;
	}

	@Override
	public List<BetScheme> loadSchemeList(String lotteryId, long userId) {
		List<BetScheme> list = new ArrayList<BetScheme>();
		if(StringUtils.isBlank(lotteryId)) {
			String[] lotteryIds = new String[]{LotteryId.JCZQ.name(),
					LotteryId.JCLQ.name(),LotteryId.CTZC.name(),
					LotteryId.BJDC.name(),LotteryId.SSQ.name()}; 
			
			List<String> schemeIdList = new ArrayList<String>();
			for(String id : lotteryIds) {
				List<String> schemeIds = loadRecord(id, userId);
				schemeIdList.addAll(schemeIds);
			}
			if(schemeIdList.size() > 0) {
				Collections.sort(schemeIdList, new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						String s1 = o1.split(BetRecordConstant.SCHEME_CUT)[0];
						String s2 = o2.split(BetRecordConstant.SCHEME_CUT)[0];
						Long r1 = Long.valueOf(s1);
						Long r2 = Long.valueOf(s2);
						return - r1.compareTo(r2);
					}
				});
				list = splitLoad(schemeIdList, userId);
			}
	    } else {
	    	list = loadScheme(lotteryId, userId);
	    }
		return list;
	}

	/**
	 * 将投注记录ID缓存到集合，value格式：方案ID+投注记录ID
	 * 默认只保存5条记录
	 */
	@Override
	public long cacheBetRecord(long userId, String lotteryId, String value, long score) {
		String key = Keys.userBetRecord(userId + "", lotteryId + "");
		
		long size = commonDao.zcard(key);
		int limit = 5;
		if(size > limit) {
			commonDao.zremrangeByRank(key, 0, -limit);
		}
		
		return commonDao.zadd(score, key, value);
	}
}