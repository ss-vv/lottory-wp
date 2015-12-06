package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.data.service.store.data.LeagueScoreRank;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBLeagueDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBMatchInfoDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBMatchInfoPO;
import com.unison.lottery.weibo.data.service.store.persist.service.BBLeagueRankService;

@Service
public class BBLeagueRankServiceImpl implements BBLeagueRankService {

	@Autowired
	private BBMatchInfoDao bbMatchInfoDao;
	
	@Autowired
	private QTMatchDao qtMatchDao;
	
	@Autowired
	private BBLeagueDao bbLeagueDao;
	
	@Transactional
	@Override
	public LeagueScoreRank findBBLeagueRankBy(String lcMatchId) {
//		int qtMatchId = qtMatchDao.queryQTMatchId(lcMatchId);
//		BBMatchInfoPO bbMatchInfo = bbMatchInfoDao.queryBBMatchInfo(qtMatchId);
//		if(null == bbMatchInfo) {
//			return null; 
//		}
//		LeagueScoreRank rank = new LeagueScoreRank();
//		String leagueName = bbMatchInfo.getName();
//		BBLeaguePO bbLeague = bbLeagueDao.findByShortName(leagueName);
//		String currMatchSeason = (null == bbLeague ? null : bbLeague.getCurrMatchSeason());
		
		
		
		return null;
	}
	
	//由于从球探结果抓过来的赛事数据中，没有对应的联赛ID，查询当前赛季只能通过联赛名称进行匹配
	protected String[] doSeason(String season) {
		if("13-14".equals(season)) {
			return new String[]{"13-14", "13-14赛季"};
		}
		return null;
	}
}