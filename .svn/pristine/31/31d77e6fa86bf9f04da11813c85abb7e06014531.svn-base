package com.davcai.lottery.weibo.analyse.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.weaver.ast.Var;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.weibo.analyse.dao.MatchAnalyseDao;
import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;
import com.davcai.lottery.weibo.analyse.redis.RedisClient;
import com.davcai.lottery.weibo.analyse.service.MatchAnalyseService;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.xhcms.lottery.utils.DateUtils;

/**
 * 赛事分析业务
 * 
 * @author baoxing.peng
 *
 */
public class MatchAnalyseServiceImpl implements MatchAnalyseService {
	private static final int QT_SOURCE = 1;

	private Logger logger = LoggerFactory
			.getLogger(MatchAnalyseServiceImpl.class);
	@Autowired
	private MatchAnalyseDao matchAnalyseDao;

	private static final long LATEST_COUNT = 6; // 统计最近LATEST_COUNT场的排名情况

	private static final long LQ_LATEST_COUNT = 10; // 篮球最近10场
	@Autowired
	private ComparatorFbLeagueScore comparatorFbLeagueScore;

	@Autowired
	private ComparatorBbLeagueScore comparatorBbLeagueScore;
	@Autowired
	private RedisClient redisClient;

	private Set<String> leagueIdList;
	
	private Set<String> bbLeagueIdList;

	@Override
	public void analyseFbLeaguseScoreRank() {
		try {
			String year = DateUtils.getNowYear();
			for (String leagueId : leagueIdList) {
				int count = matchAnalyseDao.queryNowSeasonByLeagueIdAndYear(StringUtils.substring(year,year.length()-2),leagueId);
				if(count>0){
					// 获取主场联赛排名情况
					List<LeagueScoreRandPO> homeLeagueScoreRandPOs = matchAnalyseDao
							.queryQtFbHomeScoreNowSeasonByLeagueId(leagueId,
									QT_SOURCE);
	
					// 获取客场联赛排名情况
					List<LeagueScoreRandPO> guestLeagueScoreRandPOs = matchAnalyseDao
							.queryQtFbGuestScoreNowSeasonByLeagueId(leagueId,
									QT_SOURCE);
	
					// 总排名
					List<LeagueScoreRandPO> totalLeagueScoreRandPOs = anlyseTotalMatchScoreRand(
							homeLeagueScoreRandPOs, guestLeagueScoreRandPOs);
					// 按照积分排序
					Collections.sort(totalLeagueScoreRandPOs,
							comparatorFbLeagueScore);
					Collections.sort(homeLeagueScoreRandPOs,
							comparatorFbLeagueScore);
					Collections.sort(guestLeagueScoreRandPOs,
							comparatorFbLeagueScore);
					// 排名赋值
					makeRankNumToRandPO(totalLeagueScoreRandPOs,
							homeLeagueScoreRandPOs, guestLeagueScoreRandPOs);
					// 近6场
					List<LeagueScoreRandPO> latestLeagueScoreRandPOs = new ArrayList<>();
					statisticLatestMatchScore(homeLeagueScoreRandPOs,
							latestLeagueScoreRandPOs);
					// 获取联赛当前赛季
					String seasonName = matchAnalyseDao
							.getLeagueNowSeason(leagueId);
	
					redisClient.saveFbMatchLeagueScorRank(totalLeagueScoreRandPOs,
							homeLeagueScoreRandPOs, guestLeagueScoreRandPOs,
							latestLeagueScoreRandPOs, leagueId, seasonName);
					logger.info("足球联赛id为{}的积分统计数据完成!", leagueId);
				}
			}
		} catch (Throwable e) {
			logger.error("统计足球联赛积分数据出错:{}", e);
		}
	}

	private void makeRankNumToRandPO(
			List<LeagueScoreRandPO> totalLeagueScoreRandPOs,
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueScoreRandPOs) {
		if (!totalLeagueScoreRandPOs.isEmpty()&&totalLeagueScoreRandPOs.size()==homeLeagueScoreRandPOs.size()) {
			for (int i = 0; i < homeLeagueScoreRandPOs.size(); i++) {
				homeLeagueScoreRandPOs.get(i).setRank(String.valueOf(i + 1));
				totalLeagueScoreRandPOs.get(i).setRank(String.valueOf(i + 1));
				guestLeagueScoreRandPOs.get(i).setRank(String.valueOf(i + 1));
			}
		} else {
			totalLeagueScoreRandPOs.clear();
			for (int i = 0; i < homeLeagueScoreRandPOs.size(); i++) {
				homeLeagueScoreRandPOs.get(i).setRank(String.valueOf(i + 1));
			}
			for(int i=0;i<guestLeagueScoreRandPOs.size();i++){
				guestLeagueScoreRandPOs.get(i).setRank(String.valueOf(i + 1));
			}
			int maxSize = homeLeagueScoreRandPOs.size();

			if (guestLeagueScoreRandPOs.size() > homeLeagueScoreRandPOs.size()) {
				maxSize = guestLeagueScoreRandPOs.size();
			}
			Map<String, LeagueScoreRandPO> homeMap = new HashMap<String, LeagueScoreRandPO>(
					maxSize);
			Map<String, LeagueScoreRandPO> guestMap = new HashMap<String, LeagueScoreRandPO>(
					maxSize);

			//初始化map
			for (LeagueScoreRandPO leagueScoreRandPO : homeLeagueScoreRandPOs) {
				homeMap.put(leagueScoreRandPO.getTeamId(), leagueScoreRandPO);
			}
			for (LeagueScoreRandPO leagueScoreRandPO : guestLeagueScoreRandPOs) {
				guestMap.put(leagueScoreRandPO.getTeamId(), leagueScoreRandPO);
			}
			//寻找主场与客场均存在的球队，并对totalRank进行排名
			for (Entry<String, LeagueScoreRandPO> entry : homeMap.entrySet()) {
				LeagueScoreRandPO guestleagueScoreRandPO = guestMap.get(entry
						.getKey());
				if (guestleagueScoreRandPO != null) {
					LeagueScoreRandPO homeFbLeagueScoreRandPO = entry
							.getValue();
					addToTotalLeagueRankScore(totalLeagueScoreRandPOs,
							homeFbLeagueScoreRandPO, guestleagueScoreRandPO);

				}else{
					totalLeagueScoreRandPOs.add(entry.getValue());
				}
			}
			for(Entry<String, LeagueScoreRandPO> entry : guestMap.entrySet()){
				LeagueScoreRandPO leagueScoreRandPO1 = homeMap.get(entry
						.getKey());
				if(leagueScoreRandPO1==null){
					totalLeagueScoreRandPOs.add(entry.getValue());
				}
			}
			
			// 按照积分排序
			Collections.sort(totalLeagueScoreRandPOs,
					comparatorBbLeagueScore);
			for (int i = 0; i < homeLeagueScoreRandPOs.size(); i++) {
				totalLeagueScoreRandPOs.get(i).setRank(String.valueOf(i + 1));
			}
		}
	}

	private void addToTotalLeagueRankScore(
			List<LeagueScoreRandPO> totalLeagueScoreRandPOs,
			LeagueScoreRandPO guestFbLeagueScoreRandPO,
			LeagueScoreRandPO homeFbLeagueScoreRandPO) {
		LeagueScoreRandPO leagueScoreRandPO = new LeagueScoreRandPO();
		leagueScoreRandPO.setTotalMatches(homeFbLeagueScoreRandPO
				.getTotalMatches()
				+ guestFbLeagueScoreRandPO.getTotalMatches());
		leagueScoreRandPO.setWinMatches(homeFbLeagueScoreRandPO
				.getWinMatches()
				+ guestFbLeagueScoreRandPO.getWinMatches());
		if (homeFbLeagueScoreRandPO.getDrawMatches() != null) {
			leagueScoreRandPO
					.setDrawMatches(homeFbLeagueScoreRandPO
							.getDrawMatches()
							+ guestFbLeagueScoreRandPO
									.getDrawMatches());
		}
		leagueScoreRandPO.setLoseMatches(homeFbLeagueScoreRandPO
				.getLoseMatches()
				+ guestFbLeagueScoreRandPO.getLoseMatches());
		leagueScoreRandPO.setGoal(homeFbLeagueScoreRandPO.getGoal()
				+ guestFbLeagueScoreRandPO.getGoal());
		leagueScoreRandPO.setLose(homeFbLeagueScoreRandPO.getLose()
				+ guestFbLeagueScoreRandPO.getGoal());
		leagueScoreRandPO.setTeamId(guestFbLeagueScoreRandPO
				.getTeamId());
		if (guestFbLeagueScoreRandPO.getLeageScore() != null) {
			leagueScoreRandPO
					.setLeageScore(guestFbLeagueScoreRandPO
							.getLeageScore()
							+ homeFbLeagueScoreRandPO
									.getLeageScore());
		}
		totalLeagueScoreRandPOs.add(leagueScoreRandPO);
	}

	/**
	 * 根据主客场排名情况计算出总排名
	 * 
	 * @param homeLeagueScoreRandPOs
	 * @param guestLeagueScoreRandPOs
	 * @return
	 */
	private List<LeagueScoreRandPO> anlyseTotalMatchScoreRand(
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> guestLeagueScoreRandPOs) {
		List<LeagueScoreRandPO> totalLeagueScoreRandPOs = new ArrayList<>();
		if (!homeLeagueScoreRandPOs.isEmpty()
				&& homeLeagueScoreRandPOs.size() == guestLeagueScoreRandPOs
						.size()) {
			int size = homeLeagueScoreRandPOs.size();
			for (int i = 0; i < size; i++) {
				LeagueScoreRandPO leagueScoreRandPO = new LeagueScoreRandPO();
				LeagueScoreRandPO homelFbLeagueScoreRandPO = homeLeagueScoreRandPOs
						.get(i);
				LeagueScoreRandPO guestFbLeagueScoreRandPO = guestLeagueScoreRandPOs
						.get(i);
				if(!StringUtils.equals(homelFbLeagueScoreRandPO.getTeamId(), guestFbLeagueScoreRandPO.getTeamId())){
					break;
				}
				leagueScoreRandPO.setTotalMatches(homelFbLeagueScoreRandPO
						.getTotalMatches()
						+ guestFbLeagueScoreRandPO.getTotalMatches());
				leagueScoreRandPO.setWinMatches(homelFbLeagueScoreRandPO
						.getWinMatches()
						+ guestFbLeagueScoreRandPO.getWinMatches());
				if (homelFbLeagueScoreRandPO.getDrawMatches() != null) {
					leagueScoreRandPO.setDrawMatches(homelFbLeagueScoreRandPO
							.getDrawMatches()
							+ guestFbLeagueScoreRandPO.getDrawMatches());
				}
				leagueScoreRandPO.setLoseMatches(homelFbLeagueScoreRandPO
						.getLoseMatches()
						+ guestFbLeagueScoreRandPO.getLoseMatches());
				leagueScoreRandPO.setGoal(homelFbLeagueScoreRandPO.getGoal()
						+ guestFbLeagueScoreRandPO.getGoal());
				leagueScoreRandPO.setLose(homelFbLeagueScoreRandPO.getLose()
						+ guestFbLeagueScoreRandPO.getGoal());
				leagueScoreRandPO.setTeamId(guestFbLeagueScoreRandPO
						.getTeamId());
				if (guestFbLeagueScoreRandPO.getLeageScore() != null) {
					leagueScoreRandPO.setLeageScore(guestFbLeagueScoreRandPO
							.getLeageScore()
							+ homelFbLeagueScoreRandPO.getLeageScore());
				}
				totalLeagueScoreRandPOs.add(leagueScoreRandPO);
			}
		}
		return totalLeagueScoreRandPOs;
	}

	/**
	 * 统计近6场比赛的得失情况
	 * 
	 * @param homeLeagueScoreRandPOs
	 * @param latestLeagueScoreRandPOs
	 */
	private void statisticLatestMatchScore(
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueScoreRandPOs) {
		if (!homeLeagueScoreRandPOs.isEmpty()) {
			//
			long winMatches = 0, drawMatches = 0, loseMatches = 0, goal = 0, lose = 0;
			// 获取近6场比赛的积分情况
			for (LeagueScoreRandPO leagueScoreRandPO : homeLeagueScoreRandPOs) {
				LeagueScoreRandPO fbLeagueScoreRandPO = new LeagueScoreRandPO();
				winMatches = 0;
				drawMatches = 0;
				loseMatches = 0;
				goal = 0;
				lose = 0;
				String teamId = leagueScoreRandPO.getTeamId();
				List<FbMatchBaseInfoPO> fMatchBaseInfoPOs = matchAnalyseDao
						.queryFbLastestMatch(teamId, LATEST_COUNT, QT_SOURCE);
				for (FbMatchBaseInfoPO matchBaseInfoPO : fMatchBaseInfoPOs) {
					if (StringUtils.equals(matchBaseInfoPO.getHomeTeamId(),
							teamId)) {
						goal += matchBaseInfoPO.getHomeTeamScore();
						lose += matchBaseInfoPO.getGuestTeamScore();
						if (matchBaseInfoPO.getHomeTeamScore() > matchBaseInfoPO
								.getGuestTeamScore()) {
							winMatches++;
						} else if (matchBaseInfoPO.getHomeTeamScore() == matchBaseInfoPO
								.getGuestTeamScore()) {
							drawMatches++;
						} else {
							loseMatches++;
						}
					} else {
						lose += matchBaseInfoPO.getHomeTeamScore();
						goal += matchBaseInfoPO.getGuestTeamScore();
						if (matchBaseInfoPO.getHomeTeamScore() < matchBaseInfoPO
								.getGuestTeamScore()) {
							winMatches++;
						} else if (matchBaseInfoPO.getHomeTeamScore() == matchBaseInfoPO
								.getGuestTeamScore()) {
							drawMatches++;
						} else {
							loseMatches++;
						}
					}
				}
				fbLeagueScoreRandPO.setWinMatches(winMatches);
				fbLeagueScoreRandPO.setLose(Double.valueOf(lose));
				fbLeagueScoreRandPO.setLoseMatches(loseMatches);
				fbLeagueScoreRandPO.setGoal(Double.valueOf(goal));
				fbLeagueScoreRandPO.setTeamId(teamId);
				fbLeagueScoreRandPO.setDrawMatches(drawMatches);
				fbLeagueScoreRandPO.setTotalMatches(LATEST_COUNT);
				fbLeagueScoreRandPO.setLeageScore(winMatches * 3 + drawMatches);
				latestLeagueScoreRandPOs.add(fbLeagueScoreRandPO);
			}
		}
	}

	@Override
	public void analyseBbLeagueScoreRank() {
		try {
			String nowYear = DateUtils.getNowYear();
			nowYear = StringUtils.substring(nowYear,nowYear.length()-2);
			for (String leagueId : bbLeagueIdList) {
				//检测联赛当前年是否有赛季
				int count = matchAnalyseDao.countBbLeagueSeasonNowYear(leagueId,nowYear);
				if(count>0){
					// 获取主场联赛排名情况
					List<LeagueScoreRandPO> homeLeagueScoreRandPOs = matchAnalyseDao
							.queryQtBbHomeScoreNowSeasonByLeagueId(leagueId,
									QT_SOURCE);
	
					// 获取客场联赛排名情况
					List<LeagueScoreRandPO> guestLeagueScoreRandPOs = matchAnalyseDao
							.queryQtBbGuestScoreNowSeasonByLeagueId(leagueId,
									QT_SOURCE);
					// 总排名
					List<LeagueScoreRandPO> totalLeagueScoreRandPOs = anlyseTotalMatchScoreRand(
							homeLeagueScoreRandPOs, guestLeagueScoreRandPOs);
					// // 按照积分排序
					Collections.sort(totalLeagueScoreRandPOs,comparatorBbLeagueScore);
					 Collections.sort(homeLeagueScoreRandPOs,comparatorBbLeagueScore);
					 Collections.sort(guestLeagueScoreRandPOs,comparatorBbLeagueScore);
					// 排名赋值
					 makeRankNumToRandPO(totalLeagueScoreRandPOs, homeLeagueScoreRandPOs, guestLeagueScoreRandPOs);
					// 近10场
					List<LeagueScoreRandPO> latestLeagueScoreRandPOs = new ArrayList<>();
					statisticLqLatestMatchScore(homeLeagueScoreRandPOs,
							latestLeagueScoreRandPOs);
					// 获取联赛当前赛季
					String seasonName = matchAnalyseDao
							.getBbLeagueNowSeason(leagueId);
	
					redisClient.saveBbMatchLeagueScorRank(totalLeagueScoreRandPOs,
							homeLeagueScoreRandPOs, guestLeagueScoreRandPOs,
							latestLeagueScoreRandPOs, leagueId, seasonName);
					logger.info("联赛id为{}的统计完成",leagueId);
				}
			}
			logger.info("统计篮球联赛积分完成!");
		} catch (Throwable e) {
			logger.error("统计篮球联赛积分数据出错:{}", e);
		}
	}

	private void statisticLqLatestMatchScore(
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueScoreRandPOs) {
		if (!homeLeagueScoreRandPOs.isEmpty()) {
			//
			long winMatches = 0, loseMatches = 0, goal = 0, lose = 0;
			// 获取近10场比赛的积分情况
			for (LeagueScoreRandPO leagueScoreRandPO : homeLeagueScoreRandPOs) {
				LeagueScoreRandPO fbLeagueScoreRandPO = new LeagueScoreRandPO();
				winMatches = 0;
				loseMatches = 0;
				goal = 0;
				lose = 0;
				String teamId = leagueScoreRandPO.getTeamId();
				List<FbMatchBaseInfoPO> fMatchBaseInfoPOs = matchAnalyseDao
						.queryBbLastestMatch(teamId, LQ_LATEST_COUNT, QT_SOURCE);
				for (FbMatchBaseInfoPO matchBaseInfoPO : fMatchBaseInfoPOs) {
					if (StringUtils.equals(matchBaseInfoPO.getHomeTeamId(),
							teamId)) {
						goal += matchBaseInfoPO.getHomeTeamScore();
						lose += matchBaseInfoPO.getGuestTeamScore();
						if (matchBaseInfoPO.getHomeTeamScore() > matchBaseInfoPO
								.getGuestTeamScore()) {
							winMatches++;
						} else {
							loseMatches++;
						}
					} else {
						lose += matchBaseInfoPO.getHomeTeamScore();
						goal += matchBaseInfoPO.getGuestTeamScore();
						if (matchBaseInfoPO.getHomeTeamScore() < matchBaseInfoPO
								.getGuestTeamScore()) {
							winMatches++;
						} else {
							loseMatches++;
						}
					}
				}
				fbLeagueScoreRandPO.setWinMatches(winMatches);
				fbLeagueScoreRandPO.setLose(lose * 1.0 / LQ_LATEST_COUNT);
				fbLeagueScoreRandPO.setLoseMatches(loseMatches);
				fbLeagueScoreRandPO.setGoal(goal * 1.0 / LQ_LATEST_COUNT);
				fbLeagueScoreRandPO.setTeamId(teamId);
				fbLeagueScoreRandPO.setTotalMatches(LQ_LATEST_COUNT);
				latestLeagueScoreRandPOs.add(fbLeagueScoreRandPO);
			}
		}
	}

	@Override
	public void analyseFbHalfLeagueScoreRank() {
		try {
			// List<String> leagueIdList = matchAnalyseDao
			// .queryAllJingcaiLeagueId(1, 1);
			String year = DateUtils.getNowYear();
			for (String leagueId : leagueIdList) {
				//检测联赛当前是否有赛季
				int count = matchAnalyseDao.queryNowSeasonByLeagueIdAndYear(StringUtils.substring(year,year.length()-2),leagueId);
				if(count>0){
					// // 获取主场联赛排名情况
					List<LeagueScoreRandPO> homeLeagueHalfScoreRandPOs = matchAnalyseDao
							.queryQtFbHomeHalfScoreNowSeasonByLeagueId(leagueId,
									QT_SOURCE);
	
					// 获取客场联赛排名情况
					List<LeagueScoreRandPO> guestLeagueHalfScoreRandPOs = matchAnalyseDao
							.queryQtFbGuestHalfScoreNowSeasonByLeagueId(leagueId,
									QT_SOURCE);
	
					// 总排名
					List<LeagueScoreRandPO> totalLeagueHalfScoreRandPOs = anlyseTotalMatchScoreRand(
							homeLeagueHalfScoreRandPOs, guestLeagueHalfScoreRandPOs);
					// 按照积分排序
					Collections.sort(totalLeagueHalfScoreRandPOs,
							comparatorFbLeagueScore);
					Collections.sort(homeLeagueHalfScoreRandPOs,
							comparatorFbLeagueScore);
					Collections.sort(guestLeagueHalfScoreRandPOs,
							comparatorFbLeagueScore);
					// 排名赋值
					makeRankNumToRandPO(totalLeagueHalfScoreRandPOs,
							homeLeagueHalfScoreRandPOs, guestLeagueHalfScoreRandPOs);
					// 近6场
					List<LeagueScoreRandPO> latestLeagueHalfScoreRandPOs = new ArrayList<>();
					statisticLatestMatchHalfScore(homeLeagueHalfScoreRandPOs,
							latestLeagueHalfScoreRandPOs);
					// 获取联赛当前赛季
					String seasonName = matchAnalyseDao
							.getLeagueNowSeason(leagueId);
	
					redisClient.saveFbMatchLeagueHalfScorRank(
							totalLeagueHalfScoreRandPOs,
							homeLeagueHalfScoreRandPOs,
							guestLeagueHalfScoreRandPOs,
							latestLeagueHalfScoreRandPOs, leagueId, seasonName);
					logger.info("足球联赛id为{}的半场积分统计数据完成!", leagueId);
				}
			}
		} catch (Throwable e) {
			logger.error("统计足球联赛半场积分数据出错:{}", e);
		}
	}

	private void statisticLatestMatchHalfScore(
			List<LeagueScoreRandPO> homeLeagueScoreRandPOs,
			List<LeagueScoreRandPO> latestLeagueScoreRandPOs) {
		// TODO Auto-generated method stub
		if (!homeLeagueScoreRandPOs.isEmpty()) {
			//
			long winMatches = 0, drawMatches = 0, loseMatches = 0, goal = 0, lose = 0;
			// 获取近6场比赛的积分情况
			for (LeagueScoreRandPO leagueScoreRandPO : homeLeagueScoreRandPOs) {
				LeagueScoreRandPO fbLeagueScoreRandPO = new LeagueScoreRandPO();
				winMatches = 0;
				drawMatches = 0;
				loseMatches = 0;
				goal = 0;
				lose = 0;
				String teamId = leagueScoreRandPO.getTeamId();
				List<FbMatchBaseInfoPO> fMatchBaseInfoPOs = matchAnalyseDao
						.queryFbLastestMatch(teamId, LATEST_COUNT, QT_SOURCE);
				for (FbMatchBaseInfoPO matchBaseInfoPO : fMatchBaseInfoPOs) {
					if (StringUtils.equals(matchBaseInfoPO.getHomeTeamId(),
							teamId)) {
						goal += matchBaseInfoPO.getHomeTeamHalfScore();
						lose += matchBaseInfoPO.getGuestTeamHalfScore();
						if (matchBaseInfoPO.getHomeTeamHalfScore() > matchBaseInfoPO
								.getGuestTeamHalfScore()) {
							winMatches++;
						} else if (matchBaseInfoPO.getHomeTeamHalfScore() == matchBaseInfoPO
								.getGuestTeamHalfScore()) {
							drawMatches++;
						} else {
							loseMatches++;
						}
					} else {
						lose += matchBaseInfoPO.getHomeTeamHalfScore();
						goal += matchBaseInfoPO.getGuestTeamHalfScore();
						if (matchBaseInfoPO.getHomeTeamHalfScore() < matchBaseInfoPO
								.getGuestTeamHalfScore()) {
							winMatches++;
						} else if (matchBaseInfoPO.getHomeTeamHalfScore() == matchBaseInfoPO
								.getGuestTeamHalfScore()) {
							drawMatches++;
						} else {
							loseMatches++;
						}
					}
				}
				fbLeagueScoreRandPO.setWinMatches(winMatches);
				fbLeagueScoreRandPO.setLose(Double.valueOf(lose));
				fbLeagueScoreRandPO.setLoseMatches(loseMatches);
				fbLeagueScoreRandPO.setGoal(Double.valueOf(goal));
				fbLeagueScoreRandPO.setTeamId(teamId);
				fbLeagueScoreRandPO.setDrawMatches(drawMatches);
				fbLeagueScoreRandPO.setTotalMatches(LATEST_COUNT);
				fbLeagueScoreRandPO.setLeageScore(winMatches * 3 + drawMatches);
				latestLeagueScoreRandPOs.add(fbLeagueScoreRandPO);
			}
		}
	}
	public Set<String> getLeagueIdList() {
		return leagueIdList;
	}

	public void setLeagueIdList(Set<String> leagueIdList) {
		this.leagueIdList = leagueIdList;
	}

	public Set<String> getBbLeagueIdList() {
		return bbLeagueIdList;
	}

	public void setBbLeagueIdList(Set<String> bbLeagueIdList) {
		this.bbLeagueIdList = bbLeagueIdList;
	}
}