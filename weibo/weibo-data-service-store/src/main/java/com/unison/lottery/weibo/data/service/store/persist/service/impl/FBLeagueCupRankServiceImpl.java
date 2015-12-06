package com.unison.lottery.weibo.data.service.store.persist.service.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.data.service.store.data.LeagueScoreRank;
import com.unison.lottery.weibo.data.service.store.data.MatchTypeEnum;
import com.unison.lottery.weibo.data.service.store.data.ScoreTypeEnum;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeagueScorePO;
import com.unison.lottery.weibo.data.service.store.persist.service.FBCupScoreService;
import com.unison.lottery.weibo.data.service.store.persist.service.FBLeagueCupRankService;
import com.unison.lottery.weibo.data.service.store.persist.service.FBLeagueScoreService;
import com.unison.lottery.weibo.data.service.store.persist.service.FBLeagueService;

@Service
public class FBLeagueCupRankServiceImpl implements FBLeagueCupRankService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FBLeagueService fbLeagueService;

	@Autowired
	private FBLeagueScoreService fbLeagueScoreService;

	@Autowired
	private FBCupScoreService fbCupScoreService;
	
	@Transactional
	@Override
	public LeagueScoreRank findFBLeagueRankBy(String lcMatchId) {
		LeagueScoreRank scoreRank = new LeagueScoreRank();
		FBLeaguePO fbLeaguePO = fbLeagueService.findLeagueBy(lcMatchId);
		log.info("查询联赛积分排行，通过大V彩赛事ID={},获取联赛对象={}", 
				new Object[]{lcMatchId, fbLeaguePO});
		if (null != fbLeaguePO) {
			scoreRank.setLeagueName(fbLeaguePO.getChineseNameAll());
			if (fbLeaguePO.getType() == MatchTypeEnum.LEAGUE.getType()) {
				scoreRank.setMatchTypeEnum(MatchTypeEnum.LEAGUE);
				List<FBLeagueScorePO> leagueScoreList = fbLeagueScoreService
						.getLeagueScoreByLcMatchId(lcMatchId,ScoreTypeEnum.TOTAL_SCORE);
				scoreRank.setLeagueScoreList(leagueScoreList);
			} else {
				scoreRank.setMatchTypeEnum(MatchTypeEnum.CUP);
				List<Object[]> cupScoreList = fbCupScoreService.findCupScoreBy(fbLeaguePO.getLeagueId(),
								fbLeaguePO.getCurrMatchSeason());
				scoreRank.setCupScoreList(cupScoreList);
			}
		}
		return scoreRank;
	}
}
