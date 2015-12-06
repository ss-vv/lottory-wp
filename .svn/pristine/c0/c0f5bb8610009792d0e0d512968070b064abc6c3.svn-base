package com.davcai.lottery.weibo.analyse.redis;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.weibo.analyse.dao.FbLeagueScoreRandRedisDao;
import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;


public class RedisClientImpl implements RedisClient {
	@Autowired
	private FbLeagueScoreRandRedisDao fbLeagueSocreRandRedisDao;
	@Override
	public void saveFbMatchLeagueScorRank(
			List<LeagueScoreRandPO> totalLeagueScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueScoreRandPOs, String leagueId, String seasonName) throws NoSuchMethodException, SecurityException {
		if(null!=leagueId&&seasonName!=null){
			fbLeagueSocreRandRedisDao.saveLeagueRank(totalLeagueScoreRandPOs, homeLeagueScoreRandPOs, guestLeagueScoreRandPOs, latestLeagueScoreRandPOs, leagueId, seasonName);
		}
	}
	@Override
	public void saveBbMatchLeagueScorRank(
			List<LeagueScoreRandPO> totalLeagueScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueScoreRandPOs,
			String leagueId, String seasonName) {
		if(leagueId!=null&&seasonName!=null){
			fbLeagueSocreRandRedisDao.saveBbLeagueRank(totalLeagueScoreRandPOs,homeLeagueScoreRandPOs,guestLeagueScoreRandPOs,latestLeagueScoreRandPOs,seasonName,leagueId);
		}
	}
	@Override
	public void saveFbMatchLeagueHalfScorRank(
			List<LeagueScoreRandPO> totalLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueHalfScoreRandPOs,
			String leagueId, String seasonName) {
		if(null!=leagueId&&seasonName!=null){
			fbLeagueSocreRandRedisDao.saveLeagueHalfRank(totalLeagueHalfScoreRandPOs, homeLeagueHalfScoreRandPOs, guestLeagueHalfScoreRandPOs, latestLeagueHalfScoreRandPOs, leagueId, seasonName);
		}
	}

}
