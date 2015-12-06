package com.unison.lottery.weibo.common.service;

import java.util.List;

import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.xhcms.lottery.commons.persist.entity.MixMatchPlay;

public interface BetMatchesService extends BaseDao<MixMatchPlay> {
	/**
	 * 根据日期获取混合投注赛程
	 * @param time
	 * @param lotteryId TODO
	 * @return
	 */
	List<MixMatchPlay> getMixMatchPlaysByDate(String time, String lotteryId);
	
	void saveMixMatchPlaysCache(List<MixMatchPlay> mixMatchPlays,String time, String lotteryId);
}
