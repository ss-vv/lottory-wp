package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.data.service.store.data.MatchTypeEnum;
import com.unison.lottery.weibo.data.service.store.data.ScoreTypeEnum;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBLeagueDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBLeagueScoreDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBSubLeagueDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeagueScorePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;
import com.unison.lottery.weibo.data.service.store.persist.service.FBLeagueScoreService;

@Service
public class FBLeagueScoreServiceImpl implements FBLeagueScoreService {

	@Autowired
	private FBLeagueDao fbLeagueDao;
	@Autowired
	private FBSubLeagueDao fbSubLeagueDao;
	@Autowired
	private FBLeagueScoreDao fbLeagueScoreDao;
	
	@Autowired
	private QTMatchDao qtMatchDao;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Transactional
	@Override
	public List<FBLeagueScorePO> getLeagueScoreByLeagueId(long leagueId, ScoreTypeEnum scoreType) {
		FBLeaguePO fbLeaguePO = fbLeagueDao.get(leagueId);
		if(null != fbLeaguePO) {
			String season = fbLeaguePO.getCurrMatchSeason();
			if(fbLeaguePO.getType() == MatchTypeEnum.LEAGUE.getType()) {
				FBSubLeaguePO fbSubLeaguePO = fbSubLeagueDao.findFBSubLeagueBy(
						leagueId, season);
				if(null != fbSubLeaguePO) {
					return fbLeagueScoreDao.getLeagueScore(fbSubLeaguePO.getId(), scoreType.getType());
				} else {
					log.info("通过联赛ID={}, 赛季={}, 去查询足球子联赛类型，结果为空.", 
							leagueId, season);
				}
			}
		}
		return null;
	}

	@Transactional
	@Override
	public List<FBLeagueScorePO> getLeagueScoreByLcMatchId(String lcMatchId, ScoreTypeEnum scoreType) {
		int qtMatchId = qtMatchDao.queryQTMatchId(lcMatchId);
		QTMatchPO qtMatchPO = qtMatchDao.queryQTMatchInfo(qtMatchId);
		if(null != qtMatchPO && qtMatchPO.getLeagueId() > 0) {
			return getLeagueScoreByLeagueId(qtMatchPO.getLeagueId(), scoreType);
		}
		return null;
	}
}