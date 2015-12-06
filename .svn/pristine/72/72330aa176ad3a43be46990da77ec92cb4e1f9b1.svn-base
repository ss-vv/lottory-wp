package com.davcai.lottery.weibo.analyse.dao;

import java.util.List;

import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;

/**
 * @author baoxing.peng
 *
 */
public interface FbLeagueScoreRandRedisDao {

	/**
	 * 
	 * @param totalLeagueScoreRandPOs
	 * @param homeLeagueScoreRandPOs
	 * @param guestLeagueScoreRandPOs
	 * @param latestLeagueScoreRandPOs
	 * @param leagueId
	 * @param seasonName
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	void saveLeagueRank(List<LeagueScoreRandPO> totalLeagueScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueScoreRandPOs,
			String leagueId, String seasonName) throws NoSuchMethodException, SecurityException;

	/**
	 * 
	 * @param totalLeagueScoreRandPOs
	 * @param homeLeagueScoreRandPOs
	 * @param guestLeagueScoreRandPOs
	 * @param latestLeagueScoreRandPOs
	 * @param seasonName
	 * @param leagueId
	 */
	void saveBbLeagueRank(List<LeagueScoreRandPO> totalLeagueScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueScoreRandPOs,
			String seasonName, String leagueId);

	void saveLeagueHalfRank(
			List<LeagueScoreRandPO> totalLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueHalfScoreRandPOs,
			String leagueId, String seasonName);

}
