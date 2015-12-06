package com.unison.lottery.weibo.web.service.impl;

import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.unison.lottery.weibo.common.persist.QTMatchidDao;
import com.unison.lottery.weibo.data.WeiboUser;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBTeamDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBTeamPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBTeamPO;
import com.unison.lottery.weibo.lang.LotteryBall;
import com.unison.lottery.weibo.uc.data.TeamPraisedWeiboUserResult;
import com.unison.lottery.weibo.uc.persist.MatchTeamPraiseDao;
import com.unison.lottery.weibo.uc.persist.UserAccountDao;
import com.unison.lottery.weibo.utils.LotteryIdMatchData;
import com.unison.lottery.weibo.web.service.MatchTeamPraiseService;

@Service
public class MatchTeamPraiseServiceImpl implements MatchTeamPraiseService {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private MatchTeamPraiseDao teamPraiseDao;

	@Autowired
	private UserAccountDao userAccountDao;
	
	@Autowired
	private BBTeamDao bbTeamDao;
	
	@Autowired
	private FBTeamDao fbTeamDao;
	
	@Autowired
	private QTMatchidDao qtMatchidDao;
	
	@Transactional
	@Override
	public Long addPraise(String matchId, long teamId, String lotteryId,
			long weiboUserId) {
		long rs = 0;
		log.debug("增加'对球队的赞';weiboUserId={}, 比赛ID={}, 球队ID={}, 彩种ID={}",
				new Object[]{weiboUserId, matchId, teamId, lotteryId});
		LotteryBall ball = LotteryBall.ball(lotteryId);
		if(StringUtils.isBlank(matchId) || teamId <= 0 || 
				weiboUserId <= 0 || null == ball) {
			log.info("微博用户={},比赛ID={}, 球队ID={}, 彩种ID={}, '对球队的赞'请求中包含无效字段.",
					new Object[]{weiboUserId, matchId, teamId, lotteryId});
		} else {
			long qtMatchId = qtMatchidDao.findQTMatchId(matchId);
			if(qtMatchId > 0) {
				rs = teamPraiseDao.addPraise(qtMatchId, teamId, weiboUserId, ball.getValue());
			} else {
				log.info("微博用户={},比赛ID={}, 球队ID={},无法查询到对应球探赛事ID.",
					new Object[]{weiboUserId, matchId, teamId});
			}
		}
		return rs;
	}

	@Transactional
	@Override
	public TeamPraisedWeiboUserResult findTeamPraisedWeiboUser(String matchId, long teamId,
			String lotteryId, int count) {
		log.debug("查询'对球队的赞'; 比赛ID={}, 球队ID={}, 彩种ID={},的微博用户.",
				new Object[]{matchId, teamId, lotteryId});
		TeamPraisedWeiboUserResult result = new TeamPraisedWeiboUserResult();
		
		long qtMatchId = qtMatchidDao.findQTMatchId(matchId);
		LotteryBall ball = LotteryBall.ball(lotteryId);
		if(null == ball) {
			return result;
		}
		if(LotteryIdMatchData.isLC(lotteryId)) {
			BBTeamPO bbTeam = bbTeamDao.findByTeamId(teamId);
			if(bbTeam != null) {
				result.setTeamName(bbTeam.getChineseName());
				result.setLogoUrl(bbTeam.getLogoUrl());
			}
		}
		if(LotteryIdMatchData.isZC(lotteryId)) {
			FBTeamPO fbTeam = fbTeamDao.findByTeamId(teamId);
			if(fbTeam != null) {
				result.setTeamName(fbTeam.getChineseName());
				result.setLogoUrl(fbTeam.getLogoUrl());
			}
		}
		
		Set<String> weiboUserSet = teamPraiseDao.findTeamPraisedWeiboUser(qtMatchId, teamId, 
				ball.getValue(), count);
		if(null != weiboUserSet && weiboUserSet.size() > 0) {
			List<WeiboUser> users = userAccountDao.get(weiboUserSet.iterator());
			
			result.setUsers(users);
			result.setTeamId(teamId);
		} else {
			log.info("查询比赛ID={}, 球队ID={}, 彩种ID={}，'对球队赞'的微博用户为空.", 
					new Object[]{matchId, teamId, lotteryId});
		}
		return result;
	}

	@Transactional
	@Override
	public long countTeamPraiseWeiboUser(String matchId, long teamId,
			String lotteryId) {
		long qtMatchId = qtMatchidDao.findQTMatchId(matchId);
		LotteryBall ball = LotteryBall.ball(lotteryId);
		if(null != ball) {
			return teamPraiseDao.countTeamPraiseWeiboUser(qtMatchId, teamId, ball.getValue());
		} else {
			return 0;
		}
	}

	@Override
	@Transactional
	public Long delPraise(String matchId, long teamId, String lotteryType,
			long weiboUserId) {
		long rs = 0;
		LotteryBall ball = LotteryBall.ball(lotteryType);
		if(StringUtils.isBlank(matchId) || teamId <= 0 || 
				weiboUserId <= 0 || null == ball) {
			log.info("微博用户={},比赛ID={}, 球队ID={}, 彩种ID={}, '取消对球队的赞'请求中包含无效字段.",
					new Object[]{weiboUserId, matchId, teamId, lotteryType});
		} else {
			long qtMatchId = qtMatchidDao.findQTMatchId(matchId);
			if(qtMatchId > 0) {
				rs = teamPraiseDao.delPraise(qtMatchId, teamId, weiboUserId, ball.getValue());
			} else {
				log.info("微博用户={},比赛ID={}, 球队ID={},无法查询到对应球探赛事ID.",
					new Object[]{weiboUserId, matchId, teamId});
			}
		}
		return rs;
	}
}