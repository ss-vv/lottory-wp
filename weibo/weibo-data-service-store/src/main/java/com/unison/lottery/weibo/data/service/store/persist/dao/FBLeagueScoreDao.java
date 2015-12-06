package com.unison.lottery.weibo.data.service.store.persist.dao;

import java.util.List;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeagueScorePO;
import com.xhcms.commons.persist.Dao;

public interface FBLeagueScoreDao extends Dao<FBLeagueScorePO> {

	/**
	 * 根据子联赛ID获取联赛积分
	 * @param subLeagueId
	 * @param scoreType 积分榜类型
	 * @return
	 */
	List<FBLeagueScorePO> getLeagueScore(long subLeagueId, int scoreType);

	/**
	 * @param subLeagueId 联赛子类型id
	 * @param teamId 队伍id
	 * @param scoreType TODO
	 */
	FBLeagueScorePO findLeagueScoreBy(long subLeagueId, long teamId, int scoreType);
}
