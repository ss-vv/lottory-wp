package com.xhcms.lottery.commons.persist.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.dc.data.Match;
import com.xhcms.lottery.lang.LotteryId;

public interface MatchPlatformService{
	void save(List<Match> matches,String platformId,LotteryId lotteryId);
	
	Map<Long, Long> findJcOfficial(String lotteryId, Set<Long> idSet, String lotteryPlatformId);
	
	Map<Long, Long> jcOfficialMatchIdList(String playId, List<BetMatch> matchs);
}