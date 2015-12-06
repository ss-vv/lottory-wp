package com.xhcms.lottery.commons.persist.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.dao.UserScoreDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.UserScorePO;
import com.xhcms.lottery.commons.persist.service.CacheUserScoreService;
import com.xhcms.lottery.commons.persist.service.UserScoreService;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityType;
import com.xhcms.lottery.utils.ConvertUserScore;
import com.xhcms.lottery.utils.POUtils;

/**
 * @author yonglizhu
 */
public class UserScoreServiceImpl implements UserScoreService {

	@Autowired
	private UserScoreDao userScoreDao;
	
	@Autowired
	private CacheUserScoreService cacheUserScoreService;

	@Override
	@Transactional
	public void countUserScore(BetSchemePO betSchemePO) {
		//计算方案的得分
		int showScore = countSchemeShowScore(betSchemePO);
		UserScorePO userTotalScorePO = userScoreDao
				.getUserScoreByUserIdLottoryId(betSchemePO.getSponsorId(),
						Constants.ZCZ);
		
		UserScorePO user = composeUserScorePO(betSchemePO,
				betSchemePO.getLotteryId(), showScore);
		UserScorePO userTotal = composeUserScorePO(betSchemePO, Constants.ZCZ,
				showScore);

		if (userTotalScorePO == null) {
			userScoreDao.saveUserShowScore(user);
			userScoreDao.saveUserShowScore(userTotal);
		} else {
			UserScorePO userShowScore = userScoreDao
					.getUserScoreByUserIdLottoryId(betSchemePO.getSponsorId(),
							betSchemePO.getLotteryId());
			
			if (userShowScore == null) {
				userScoreDao.saveUserShowScore(user);
			} else {
				addUserScore(userShowScore, betSchemePO, showScore);
				userScoreDao.updateUserShowScore(userShowScore);
			}
			
			addUserScore(userTotalScorePO, betSchemePO, showScore);
			userScoreDao.updateUserShowScore(userTotalScorePO);
		}
	}

	// 计算该方案的晒单战绩
	private int countSchemeShowScore(BetSchemePO betSchemePO) {
		int showScore = 0;
		int winAmount = 0;
		int returnAmount = 0;
		
		winAmount = betSchemePO.getAfterTaxBonus()
				.subtract(new BigDecimal(betSchemePO.getTotalAmount()))
				.setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
		returnAmount = betSchemePO.getAfterTaxBonus()
				.divide(new BigDecimal(betSchemePO.getTotalAmount()), 0,
						BigDecimal.ROUND_HALF_UP).intValue();

		if (winAmount >= 500000) {
			showScore += 100;
		} else if (winAmount >= 10000 && winAmount < 500000) {
			showScore += 10;
		} else if (winAmount >= 1000 && returnAmount >= 1) {
			showScore += 1;
		} else if (winAmount >= 500 && returnAmount >= 5) {
			showScore += 1;
		} else if (returnAmount >= 20) {
			showScore += 1;
		} else {
			showScore = 0;
		}

		return showScore;
	}

	private UserScorePO composeUserScorePO(BetSchemePO betSchemePO,
			String lotteryId, int showScore) {
		UserScorePO userScorePO = new UserScorePO();
		userScorePO.setUserId(betSchemePO.getSponsorId());
		userScorePO.setUsername(betSchemePO.getSponsor());
		userScorePO.setLotteryId(lotteryId);
		userScorePO.setWinAmount(betSchemePO.getAfterTaxBonus());

		if (betSchemePO.getShowScheme() == Constants.SHOW_SCHEME) {
			userScorePO.setShowScore(showScore);
			userScorePO.setTotalScore(showScore);
		} 
		if (betSchemePO.getType() == Constants.TYPE_GROUP) {
			userScorePO.setGroupScore(showScore);
			userScorePO.setTotalScore(showScore);
		}

		return userScorePO;
	}

	private void addUserScore(UserScorePO userScore, BetSchemePO betSchemePO,
			int showScore) {
		userScore.setWinAmount(userScore.getWinAmount().add(
				betSchemePO.getAfterTaxBonus()));

		if (betSchemePO.getShowScheme() == Constants.SHOW_SCHEME) {
			userScore.setShowScore(userScore.getShowScore() + showScore);
			userScore.setTotalScore(userScore.getTotalScore() + showScore);
		} 
		
		if (betSchemePO.getType() == Constants.TYPE_GROUP) {
			userScore.setGroupScore(userScore.getGroupScore() + showScore);
			userScore.setTotalScore(userScore.getTotalScore() + showScore);
		} 
	}

	@Override
	@Transactional(readOnly = true)
	public UserScore getUserScoreByUserIdLotteryId(long userId, String lotteryId) {
		UserScore userScore = cacheUserScoreService.findByUserIdAndLotteryId(userId, lotteryId);
		
		return userScore;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<UserScore> listTopUserScoreOfLottery(String lotteryId, int maxResults) {
		if (StringUtils.isBlank(lotteryId)) {
			lotteryId = Constants.ZCZ;
		}
		List<UserScorePO> scores = userScoreDao.findTopUserScoreOfLottery(lotteryId, maxResults);
		List<UserScore> userScores = POUtils.copyBeans(scores, UserScore.class);
		for(UserScore userScore:userScores) {
			String showPic = ConvertUserScore.convertScore(userScore.getShowScore(),
					EntityType.SHOW_SCORE);
			userScore.setShowPic(showPic);
		}
		
		return userScores;
	}

	@Override
	@Transactional(readOnly=true)
	public List<UserScore> topGroupbuyUserScores(String lottery, int maxResults) {
		List<UserScorePO> pos = userScoreDao.findTopGroupbuyUserScores(lottery, "groupScore", maxResults);
		List<UserScore> userScores = POUtils.copyBeans(pos, UserScore.class);
		for(UserScore userScore:userScores) {
			String groupPic = ConvertUserScore.convertScore(userScore.getGroupScore(),
					EntityType.GROUP_SCORE);
			userScore.setGroupPic(groupPic);
		}
		
		return userScores;
	}	
}