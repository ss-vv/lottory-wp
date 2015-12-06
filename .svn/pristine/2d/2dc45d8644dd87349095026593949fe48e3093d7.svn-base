package com.unison.lottery.weibo.common.service;

import java.util.List;

import com.xhcms.lottery.commons.data.BetScheme;

/**
 * 投注记录缓存管理：提供缓存，查询等服务
 * @author lei.li@davcai.com
 */
public interface BetRecordCache {

	List<String> loadRecord(String lotteryId, long userId);
	
	List<BetScheme> loadSchemeList(String lotteryId, long userId);
	
	long cacheBetRecord(long userId, String lotteryId, String value, long score);
}
