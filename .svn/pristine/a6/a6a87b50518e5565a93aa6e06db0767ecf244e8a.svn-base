package com.davcai.lottery.weibo.analyse.dao.impl;

import java.lang.reflect.Method;
import java.util.List;

import com.davcai.lottery.weibo.analyse.dao.FbLeagueScoreRandRedisDao;
import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;
import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.common.nosql.impl.Keys;

/**
 * @author baoxing.peng
 *
 */
public class FbLeagueScoreRandRedisDaoImpl extends BaseDaoImpl<LeagueScoreRandPO> implements FbLeagueScoreRandRedisDao{

	@Override
	public void saveLeagueRank(List<LeagueScoreRandPO> totalLeagueScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueScoreRandPOs,
			String leagueId, String seasonName) {
		if(totalLeagueScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : totalLeagueScoreRandPOs) {
				try {
					Method method = leagueScoreRandPO.getClass().getDeclaredMethod("getTeamId",null);
					method.setAccessible(false);
				} catch (NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				
				hashAddIncludeInheriteProperty(
						Keys.getFbLeagueTeamScoreRankAnalyseKeyTotal(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
		if(homeLeagueScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : homeLeagueScoreRandPOs) {
				hashAddIncludeInheriteProperty(
						Keys.getFbLeagueTeamScoreRankAnalyseKeyZC(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
		if(guestLeagueScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : guestLeagueScoreRandPOs) {
				hashAddIncludeInheriteProperty(
						Keys.getFbLeagueTeamScoreRankAnalyseKeyKC(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
		if(latestLeagueScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : latestLeagueScoreRandPOs) {
				hashAddIncludeInheriteProperty(
						Keys.getFbLeagueTeamScoreRankAnalyseKeyLatest_6(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
				
		
	}

	@Override
	public void saveBbLeagueRank(
			List<LeagueScoreRandPO> totalLeagueScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueScoreRandPOs,
			String seasonName, String leagueId) {
		if(totalLeagueScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : totalLeagueScoreRandPOs) {
				hashAddIncludeInheriteProperty(
						Keys.getBbLeagueTeamScoreRankAnalyseKeyTotal(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
		if(homeLeagueScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : homeLeagueScoreRandPOs) {
				hashAddIncludeInheriteProperty(
						Keys.getBbLeagueTeamScoreRankAnalyseKeyZC(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
		if(guestLeagueScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : guestLeagueScoreRandPOs) {
				hashAddIncludeInheriteProperty(
						Keys.getBbLeagueTeamScoreRankAnalyseKeyKC(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
		if(latestLeagueScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : latestLeagueScoreRandPOs) {
				hashAddIncludeInheriteProperty(
						Keys.getBbLeagueTeamScoreRankAnalyseKeyLatest_10(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
	}

	@Override
	public void saveLeagueHalfRank(
			List<LeagueScoreRandPO> totalLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueHalfScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueHalfScoreRandPOs,
			String leagueId, String seasonName) {
		if(totalLeagueHalfScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : totalLeagueHalfScoreRandPOs) {
				hashAddIncludeInheriteProperty(
						Keys.getFbLeagueTeamHalfScoreRankAnalyseKeyTotal(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
		if(homeLeagueHalfScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : homeLeagueHalfScoreRandPOs) {
				hashAddIncludeInheriteProperty(
						Keys.getFbLeagueTeamHalfScoreRankAnalyseKeyZC(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
		if(guestLeagueHalfScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : guestLeagueHalfScoreRandPOs) {
				hashAddIncludeInheriteProperty(
						Keys.getFbLeagueTeamHalfScoreRankAnalyseKeyKC(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
		if(latestLeagueHalfScoreRandPOs!=null){
			for (LeagueScoreRandPO leagueScoreRandPO : latestLeagueHalfScoreRandPOs) {
				hashAddIncludeInheriteProperty(
						Keys.getFbLeagueTeamHalfScoreRankAnalyseKeyLatest_6(leagueId,
								seasonName, leagueScoreRandPO.getTeamId()),
						leagueScoreRandPO);
			}
		}
	}

} 
