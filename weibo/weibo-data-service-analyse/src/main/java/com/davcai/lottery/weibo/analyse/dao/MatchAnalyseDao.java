package com.davcai.lottery.weibo.analyse.dao;

import java.util.List;

import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;

/**
 * @author baoxing.peng
 *
 */
public interface MatchAnalyseDao {

	/**
	 * 查询竞彩联赛id
	 * @param source 联赛来源
	 * @param leagueType 赛事类型 1联赛,2杯赛 
	 * @return
	 */
	List<String> queryAllJingcaiLeagueId(int source, int leagueType);

	/**
	 * 统计球队客场联赛排名
	 * @param leagueId
	 * @return
	 */
	List<LeagueScoreRandPO> queryQtFbGuestScoreNowSeasonByLeagueId(String leagueId, int source);

	/**
	 * 统计球队主场联赛排名情况
	 * @param leagueId
	 * @return
	 */
	List<LeagueScoreRandPO> queryQtFbHomeScoreNowSeasonByLeagueId(
			String leagueId, int source);

	/**
	 * 查询最近的比赛
	 * @param teamId 球队id
	 * @param latestCount 最近的场数
	 * @param qtSource 来源
	 * @return
	 */
	List<FbMatchBaseInfoPO> queryFbLastestMatch(String teamId, long latestCount,
			int qtSource);

	/**
	 * 获取联赛当前赛季
	 * @param leagueId 
	 * @return
	 */
	String getLeagueNowSeason(String leagueId);

	/**
	 * 
	 * @param leagueType
	 * @param source
	 * @return
	 */
	List<String> queryAllJingcaiLqLeagueId(int leagueType, int source);

	/**
	 * 主场联赛排名
	 * @param leagueId
	 * @param qtSource
	 * @return
	 */
	List<LeagueScoreRandPO> queryQtBbHomeScoreNowSeasonByLeagueId(
			String leagueId, int qtSource);

	/**
	 * 客场联赛排名
	 * @param leagueId
	 * @param qtSource
	 * @return
	 */
	List<LeagueScoreRandPO> queryQtBbGuestScoreNowSeasonByLeagueId(
			String leagueId, int qtSource);

	/**
	 * 
	 * @param teamId
	 * @param lqLatestCount 近几场
	 * @param qtSource 
	 * @return
	 */
	List<FbMatchBaseInfoPO> queryBbLastestMatch(String teamId,
			long lqLatestCount, int qtSource);

	/**
	 * 获取篮球联赛当前赛季
	 * @param leagueId
	 * @return
	 */
	String getBbLeagueNowSeason(String leagueId);

	List<LeagueScoreRandPO> queryQtFbHomeHalfScoreNowSeasonByLeagueId(
			String leagueId, int qtSource);

	List<LeagueScoreRandPO> queryQtFbGuestHalfScoreNowSeasonByLeagueId(
			String leagueId, int qtSource);

	int queryNowSeasonByLeagueIdAndYear(String nowYear, String leagueId);

	int countBbLeagueSeasonNowYear(String leagueId, String nowYear);
}
