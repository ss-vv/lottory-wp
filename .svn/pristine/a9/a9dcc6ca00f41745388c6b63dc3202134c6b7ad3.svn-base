package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

/**
 * 战绩服务
 * @author yonglizhu
 */
public interface UserScoreService {
	
	/**
	 * 计算用户战绩：代购晒单战绩，合买晒单战绩
	 */
	void countUserScore(BetSchemePO betSchemePO);
	
	/**
	 * 根据用户id和彩种id取得用户某个彩种的战绩
	 * @return
	 */
	UserScore getUserScoreByUserIdLotteryId(long userId,
			String lotteryId);
	
	/**
	 * 取得某个彩种的战绩榜
	 * @param lotteryId
	 * @param maxResults
	 * @return
	 */
	List<UserScore> listTopUserScoreOfLottery(String lotteryId, int maxResults);
	
	/**
	 * 合买战绩榜
	 * @param lottery
	 * @param maxResults
	 * @return
	 */
	List<UserScore> topGroupbuyUserScores(String lottery, int maxResults);
}
