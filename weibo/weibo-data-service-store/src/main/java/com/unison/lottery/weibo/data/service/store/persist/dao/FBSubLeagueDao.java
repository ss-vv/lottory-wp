package com.unison.lottery.weibo.data.service.store.persist.dao;

import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubLeaguePO;
import com.xhcms.commons.persist.Dao;

public interface FBSubLeagueDao extends Dao<FBSubLeaguePO> {

	/**
	 * 通过联赛ID和赛季获取子联赛类型
	 * @param leagueId
	 * @param season
	 * @return
	 */
	FBSubLeaguePO findFBSubLeagueBy(long leagueId, String season);

}
