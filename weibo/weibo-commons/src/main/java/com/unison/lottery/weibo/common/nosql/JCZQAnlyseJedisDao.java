package com.unison.lottery.weibo.common.nosql;

import java.util.List;

import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;

/**
 * 竞彩足球分析jedis
 *
 * @author baoxing.peng@davcai.com
 *
 * @since 2015年1月27日 下午2:58:48
 */

public interface JCZQAnlyseJedisDao {

	/**
	 * 根据key值获取球队排名情况
	 * @param hashSetKeys
	 * @return
	 */
	LeagueScoreRandPO findFbLeagueRank(
			String hashSetKeys);

	/**
	 * 根据key数组查询
	 * @param keys 多个key
	 * @return
	 */
	List<LeagueScoreRandPO> findFbLeagueRankList(String[] keys);

}
