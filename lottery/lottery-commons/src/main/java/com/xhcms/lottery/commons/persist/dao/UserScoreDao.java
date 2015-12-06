package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.UserScorePO;
/**
 * @author yonglizhu
 */
public interface UserScoreDao extends Dao<UserScorePO> {

	/**
	 * 根据用户id，彩种id返回一个用户对应的一个彩种的战绩
	 * @param userId
	 * @param lotteryId
	 * @return
	 */
	UserScorePO getUserScoreByUserIdLottoryId(long userId,String lotteryId);
	
	/**
	 * 根据彩种取得用户该彩种的战绩榜
	 * @param lotteryId
	 * @param maxResults
	 * @return
	 */
	List<UserScorePO> findTopUserScoreOfLottery(String lotteryId, int maxResults);
	
	/**
	 * 保存用户战绩
	 * @param userScorePO
	 */
	void saveUserShowScore(UserScorePO userShowScorePO);
	
	/**
	 * 更新用户战绩
	 * @param userScorePO
	 */
	void updateUserShowScore(UserScorePO userShowScorePO);
	
	/**
	 * 清空用户晒单战绩表
	 */
	void deleteUserScore();

	List<UserScorePO> findTopGroupbuyUserScores(String lottery, String orderBy, int maxResults);
	

}
