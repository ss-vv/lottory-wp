package com.davcai.lottery.weibo.analyse.redis;

import java.util.List;

import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;



public interface RedisClient {

	/**
	 * 
	 * @param totalLeagueScoreRandPOs总排名
	 * @param homeLeagueScoreRandPOs主场排名
	 * @param guestLeagueScoreRandPOs客场排名
	 * @param latestLeagueScoreRandPOs最近6场积分情况
	 * @param seasonName 
	 * @param leagueId 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	void saveFbMatchLeagueScorRank(
			List<LeagueScoreRandPO> totalLeagueScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueScoreRandPOs, String leagueId, String seasonName) throws NoSuchMethodException, SecurityException;

	void saveBbMatchLeagueScorRank(
			List<LeagueScoreRandPO> totalLeagueScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueScoreRandPOs,
			String leagueId, String seasonName);

	void saveFbMatchLeagueHalfScorRank(
			List<LeagueScoreRandPO> totalLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueHalfScoreRandPOs,
			String leagueId, String seasonName);	

}
