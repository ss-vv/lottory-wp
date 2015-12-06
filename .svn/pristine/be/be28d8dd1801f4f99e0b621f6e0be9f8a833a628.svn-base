package com.unison.lottery.weibo.web.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.common.nosql.MatchDao;
import com.unison.lottery.weibo.common.persist.QTMatchidDao;
import com.unison.lottery.weibo.data.service.store.data.MatchResultStats;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBTeamPO;
import com.unison.lottery.weibo.data.service.store.persist.service.BBMatchInfoService;
import com.unison.lottery.weibo.lang.LotteryBall;
import com.unison.lottery.weibo.web.service.BBMatchOverviewService;
import com.unison.lottery.weibo.web.service.MatchTeamPraiseService;
import com.xhcms.lottery.lang.LotteryId;

@Service
public class BBMatchOverviewServiceImpl implements BBMatchOverviewService {

	@Autowired
	private BBMatchInfoService bbMatchInfoService;
	
	@Autowired
	private MatchTeamPraiseService praiseService;
	
	@Autowired
	private BBTeamDao bbTeamDao;
	
	@Autowired
	private MatchDao matchDao;
	
	@Autowired
	private QTMatchidDao qtMatchidDao;
	
	@Transactional
	@Override
	public Map<String, Object> bbMatchInfoCollect(String matchId, long homeTeamId, 
			long guestTeamId, String lotteryId, int size) {
		Map<String, Object> m = new HashMap<String, Object>();
		if(StringUtils.isBlank(lotteryId) || !lotteryId.equals(LotteryId.JCLQ.name())) {
			return m;
		}
		//球队微博用户支持数量
		Long homeTeamPraise = praiseService.countTeamPraiseWeiboUser(matchId, 
				homeTeamId, lotteryId);
		Long guestTeamPraise = praiseService.countTeamPraiseWeiboUser(matchId, 
				guestTeamId, lotteryId);
		
		//近期赛事战绩汇总
		MatchResultStats homeRecentStatus = bbMatchInfoService.getRecentMatchsStats(homeTeamId, size);
		MatchResultStats guestRecentStatus = bbMatchInfoService.getRecentMatchsStats(guestTeamId, size);
		
		//交战历史，主队战绩
		MatchResultStats fightHistoryHomeTeamStatus = bbMatchInfoService.
				getFightHistoryMatchsStats(homeTeamId, 
				guestTeamId, null, new Date(), size);
		MatchResultStats fightHistoryGuestTeamStatus = new MatchResultStats();
		if(null != fightHistoryHomeTeamStatus) {
			int shengNum = fightHistoryHomeTeamStatus.getShengNum();
			
			int fuNum = fightHistoryHomeTeamStatus.getFuNum();
			fightHistoryGuestTeamStatus.setShengNum(fuNum);
			fightHistoryGuestTeamStatus.setFuNum(shengNum);
		}
		BBTeamPO bbTeam = bbTeamDao.findByTeamId(guestTeamId);
		if(null != bbTeam) {
			fightHistoryGuestTeamStatus.setTeamName(bbTeam.getChineseName());
		}
		m.put("supportHomeTeamNum", homeTeamPraise);
		m.put("supportGuestTeamNum", guestTeamPraise);
		
		if(null != homeRecentStatus) {
			m.put("recentHomeTeam", homeRecentStatus);
		}
		if(null != guestRecentStatus) {
			m.put("recentGuestTeam", guestRecentStatus);
		}
		if(null != fightHistoryHomeTeamStatus) {
			m.put("fightHistoryHomeTeam", fightHistoryHomeTeamStatus);
		}
		if(null != fightHistoryGuestTeamStatus) {
			m.put("fightHistoryGuestTeam", fightHistoryGuestTeamStatus);
		}
		
		//本场比赛，推荐和赛单数量
		LotteryBall ball = LotteryBall.ball(lotteryId);
		if(null != ball) {
			long qtMatchId = qtMatchidDao.findQTMatchId(matchId);
			Long recommendAndShowUsers = matchDao.getPublishRecomAndShowUsers(qtMatchId, ball.getValue());
			String recommendNum = matchDao.getMatchRecommendNumber(qtMatchId, ball.getValue());
			String showSchemeNum = matchDao.getMatchShowSchemeNumber(qtMatchId, ball.getValue());
			if(recommendAndShowUsers != null) {
				m.put("recommendAndShowUsers", recommendAndShowUsers.longValue());
			}
			if(StringUtils.isBlank(recommendNum)) {
				recommendNum = "0";
			}
			if(StringUtils.isBlank(showSchemeNum)) {
				showSchemeNum = "0";
			}
			m.put("recommend", recommendNum);
			m.put("showScheme", showSchemeNum);
		}
		return m;
	}
}
