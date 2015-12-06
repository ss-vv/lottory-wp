package com.unison.lottery.weibo.web.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.weibo.common.nosql.MessageDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.persist.QTMatchidDao;
import com.unison.lottery.weibo.data.MatchFloatCard;
import com.unison.lottery.weibo.lang.LotteryBall;
import com.unison.lottery.weibo.lang.WeiboType;
import com.unison.lottery.weibo.uc.persist.MatchTeamPraiseDao;
import com.unison.lottery.weibo.web.service.MatchFloatCardService;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.dao.FBMatchPlayDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.LotteryId;

@Service
public class MatchFloatCardServiceImpl implements MatchFloatCardService {
	
	@Autowired
	private FBMatchPlayDao fbMatchPlayDao;
	@Autowired
	private BBMatchPlayDao bbMatchPlayDao;
	@Autowired
	private MatchTeamPraiseDao matchTeamPraiseDao;
	@Autowired
	private QTMatchidDao qtMatchidDao;
	
	@Autowired
	private MessageDao messageDao;
	
	@Override
	@Transactional
	public MatchFloatCard addCardOddInfo(MatchFloatCard matchFloatCard) {
		if(LotteryId.JCZQ.name().equals(matchFloatCard.getLotteryType())){
			HashSet<Long> matchIdSet = new HashSet<Long>();
			matchIdSet.add(matchFloatCard.getMatchId());
			List<FBMatchPlayPO> fbMatchPlayPOs =  fbMatchPlayDao.find(Constants.PLAY_80_ZC_2, matchIdSet);
			if(fbMatchPlayPOs.size() > 0){
				FBMatchPlayPO f = fbMatchPlayPOs.get(0);
				matchFloatCard.setOdds(f.getOdds());
			}
		} else if(LotteryId.JCLQ.name().equals(matchFloatCard.getLotteryType())){
			HashSet<Long> matchIdSet = new HashSet<Long>();
			matchIdSet.add(matchFloatCard.getMatchId());
			List<BBMatchPlayPO> bbMatchPlayPOs =  bbMatchPlayDao.find(Constants.PLAY_07_LC_2, matchIdSet);
			if(bbMatchPlayPOs.size() > 0){
				BBMatchPlayPO b = bbMatchPlayPOs.get(0);
				matchFloatCard.setOdds(b.getOdds());
			}
		} else if(LotteryId.CTZC.name().equals(matchFloatCard.getLotteryType())){
			
		}
		return matchFloatCard;
	}

	@Override
	@Transactional
	public MatchFloatCard addCardRealAndRecCountInfo(
			MatchFloatCard matchFloatCard) {
		String key = Keys.matchRealDataKey(makeRealMatchId(matchFloatCard));
		LinkedHashSet<String>  weiboIds = messageDao.zrange(key, 0, -1);
		int realCount = 0;
		int recCount = 0;
		for (String weiboId : weiboIds) {
			String weiboType = messageDao.hget(Keys.postKey(weiboId), "type");
			if(WeiboType.RECOMMEND.getType().equals(weiboType)){
				recCount ++;
			} else {
				realCount ++;
			}
		}
		matchFloatCard.setRealCount(realCount);
		matchFloatCard.setRecCount(recCount);
		return matchFloatCard;
	}
	
	private String makeRealMatchId(MatchFloatCard matchFloatCard){
		String matchId = matchFloatCard.getMatchId()+"";
		matchId = matchId.substring(2);
		if(LotteryId.JCZQ.name().equals(matchFloatCard.getLotteryType())){
			matchId = "JZ" + matchId;
		} else if(LotteryId.JCLQ.name().equals(matchFloatCard.getLotteryType())){
			matchId = "JL" + matchId;
		}
		return matchId;
	}
	
	@Override
	@Transactional
	public MatchFloatCard addCardFavorCountInfo(MatchFloatCard matchFloatCard,String curWeiboUserId) {
		long qtMatchId = qtMatchidDao.findQTMatchId(matchFloatCard.getMatchId()+"");
		LotteryBall ball = LotteryBall.ball(matchFloatCard.getLotteryType());
		Set<String> homeWeiboUserIds = matchTeamPraiseDao.findTeamPraisedWeiboUser(qtMatchId, matchFloatCard.getHomeTeamId(), ball.getValue(), "0", new Date().getTime()+"");
		Set<String> guestWeiboUserIds = matchTeamPraiseDao.findTeamPraisedWeiboUser(qtMatchId, matchFloatCard.getGuestTeamId(), ball.getValue(), "0", new Date().getTime()+"");
		matchFloatCard.setFavorHomeTeam(homeWeiboUserIds.contains(curWeiboUserId));
		matchFloatCard.setFavorGuestTeam(guestWeiboUserIds.contains(curWeiboUserId));
		int fHomeTeamCount = homeWeiboUserIds.size();
		int fGuestTeamCount = guestWeiboUserIds.size();
		matchFloatCard.setFavorHomeTeamCount(fHomeTeamCount);
		matchFloatCard.setFavorGuestTeamCount(fGuestTeamCount);
		return matchFloatCard;
	}

}
