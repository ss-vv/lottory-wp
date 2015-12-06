package com.unison.lottery.pm.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.pm.service.UserShowScoreService;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.dao.UserScoreDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.UserScorePO;

public class UserShowScoreServiceImpl implements UserShowScoreService {
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BetSchemeDao betSchemeDao;

	@Autowired
	private UserScoreDao userScoreDao;

	@Override
	@Transactional
	public void countUserShowScore() {
		userScoreDao.deleteUserScore();
		List<BetSchemePO> betSchemeList = betSchemeDao
				.getWinSchemeList();
		log.info("get scheme size : betSchemeList={}",betSchemeList.size());		
		int countBetScheme = 0;
		for (BetSchemePO betSchemePO : betSchemeList) {
			++countBetScheme;
			int showScore = countSchemeShowScore(betSchemePO);			
			UserScorePO userTotalScorePO = userScoreDao
					.getUserScoreByUserIdLottoryId(
							betSchemePO.getSponsorId(), "-1");
			UserScorePO user = composeUserScorePO(betSchemePO,betSchemePO.getLotteryId(),showScore);
			UserScorePO userTotal = composeUserScorePO(betSchemePO,"-1",showScore);

			if (userTotalScorePO == null) {
				userScoreDao.saveUserShowScore(user);
				userScoreDao.saveUserShowScore(userTotal);
			} else {
				UserScorePO userShowScore = userScoreDao
						.getUserScoreByUserIdLottoryId(
								betSchemePO.getSponsorId(),
								betSchemePO.getLotteryId());
				if (userShowScore == null) {
					userScoreDao.saveUserShowScore(user);
				} else {
					addUserScore(userShowScore,betSchemePO,showScore);
					userScoreDao.updateUserShowScore(userShowScore);
				}
				addUserScore(userTotalScorePO,betSchemePO,showScore);
				userScoreDao.updateUserShowScore(userTotalScorePO);
			}
			
		}
		log.info("have score scheme size ：countBetScheme={}",countBetScheme);
	}

	//计算该方案的晒单战绩
	private int countSchemeShowScore(BetSchemePO betSchemePO){
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
		}
		return showScore;
	}
	
	private UserScorePO composeUserScorePO(BetSchemePO betSchemePO,String lotteryId,int showScore){
		UserScorePO userScorePO = new UserScorePO();
		userScorePO.setUserId(betSchemePO.getSponsorId());
		userScorePO.setUsername(betSchemePO.getSponsor());
		userScorePO.setLotteryId(lotteryId);
		userScorePO.setWinAmount(betSchemePO.getAfterTaxBonus());
		if(betSchemePO.getType()==0){
			userScorePO.setShowScore(showScore);
			userScorePO.setTotalScore(showScore);	
		}else if(betSchemePO.getType()==1){
			userScorePO.setGroupScore(showScore);
			userScorePO.setTotalScore(showScore);	
		}
		return userScorePO;
	}
	
	private void addUserScore(UserScorePO userScore,BetSchemePO betSchemePO,int showScore){
		userScore.setWinAmount(userScore.getWinAmount().add(betSchemePO.getAfterTaxBonus()));
		if(betSchemePO.getType()==0){
			userScore.setShowScore(userScore.getShowScore()+showScore);
			userScore.setTotalScore(userScore.getTotalScore()+showScore);
		}else if(betSchemePO.getType()==1){
			userScore.setGroupScore(userScore.getGroupScore()+showScore);
			userScore.setTotalScore(userScore.getTotalScore()+showScore);
		}
	}
	
}
