package com.xhcms.lottery.dc.persist.dao;

import java.math.BigInteger;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.MatchSupportPlay;
import com.xhcms.lottery.commons.persist.entity.MatchSupportPlayPO;
import com.xhcms.lottery.lang.LotteryId;

public interface MatchSupportPlayDao extends Dao<MatchSupportPlayPO> {
	
	void save(List<MatchSupportPlay> supportPlays);
	
	/**
	 * 查询当前支持的单关赛事
	 * @param lotteryId
	 * @return
	 */
	List<BigInteger> findCurrSupportMatchId(LotteryId lotteryId);
	
	/**
	 * 查询当前支持的单关赛事玩法
	 * @return
	 */
	List<String> findCurrSupportMatchPlay(LotteryId lotteryId);

	void removeNotSupportPlay(final long matchId,final String playId);
}