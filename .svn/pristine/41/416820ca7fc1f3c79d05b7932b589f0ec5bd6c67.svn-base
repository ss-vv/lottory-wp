package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.data.service.store.data.FBMatchScore;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBLeagueDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.service.FBLeagueService;
import com.unison.lottery.weibo.data.service.store.persist.service.MatchDataService;

@Service
public class FBLeagueServiceImpl implements FBLeagueService {

	@Autowired
	private FBLeagueDao fbLeagueDao;
	
	@Autowired
	private MatchDataService matchDataService;

	@Transactional
	@Override
	public FBLeaguePO currMatchSeason(long leagueId) {
		return fbLeagueDao.get(leagueId);
	}

	@Transactional
	@Override
	public FBLeaguePO findLeagueBy(String lcMatchId) {
		FBMatchScore fbMatchScore = matchDataService.getFBMatch(lcMatchId);
		if(null != fbMatchScore) {
			long leagueId = fbMatchScore.getLeagueId();
			return currMatchSeason(leagueId);
		}
		return null;
	}
}
