package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.MatchPlatform;
import com.xhcms.lottery.commons.persist.entity.MatchPlatformPO;

public interface MatchPlatformDao extends Dao<MatchPlatformPO> {
	void save(List<MatchPlatform> matchPlatforms);
	MatchPlatformPO getMatchPlatformPO(String lotteryId,String platformId,Long matchId);
	
	List<MatchPlatformPO> findJcOfficial(String lotteryId, Collection<Long> idSet,
			String lotteryPlatformId);
}