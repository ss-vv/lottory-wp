package com.unison.lottery.weibo.web.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List; 
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davcai.lottery.constant.FbLeagueRankDataType;
import com.davcai.lottery.dao.JCZQAnalyseDao;
import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;
import com.googlecode.aviator.asm.tree.IntInsnNode;
import com.unison.lottery.weibo.common.nosql.JCZQAnlyseJedisDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchOpOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueScorePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueScoreRulePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.JsonConvertUtil;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.web.service.JCZQAnalyseService;
import com.xhcms.lottery.utils.ServiceUtils;

/**
 *
 * @author baoxing.peng@davcai.com
 *
 * @since 2015年1月27日 下午12:42:33
 */
@Service
public class JCZQAnalyseServiceImpl implements JCZQAnalyseService {

	private static final String CORP_ID_PREFIX = "corpId";

	private static final String MATCH_ID_PREFIX = "matchId";

	private static final String ASIAN_KEY = "asian";

	private static final String BIGSMALL_KEY = "bigsmall";

	private static final String EUROPE_KEY = "europe";

	@Autowired
	private JCZQAnalyseDao jCZQAnalyseDao;

	@Autowired
	private JCZQAnlyseJedisDao jCZQAnlyseJedisDao;

	private Logger logger = LoggerFactory
			.getLogger(JCZQAnalyseServiceImpl.class);

	private int maxNum = 5;
	private static Map<String, String> fbOpCorpId = new HashMap<>(13);
	static {
		// 数据库足球欧赔公司id与即时欧赔公司id对照
		fbOpCorpId.put("1", "80");
		fbOpCorpId.put("3", "545");
		fbOpCorpId.put("4", "82");
		fbOpCorpId.put("8", "281");
		fbOpCorpId.put("12", "90");
		fbOpCorpId.put("14", "81");
		fbOpCorpId.put("17", "517");
		fbOpCorpId.put("22", "16");
		fbOpCorpId.put("23", "499");
		fbOpCorpId.put("24", "18");
		fbOpCorpId.put("31", "474");
		fbOpCorpId.put("35", "659");
		fbOpCorpId.put("80", "1");
		fbOpCorpId.put("545", "3");
		fbOpCorpId.put("82", "4");
		fbOpCorpId.put("281", "8");
		fbOpCorpId.put("90", "12");
		fbOpCorpId.put("81", "14");
		fbOpCorpId.put("517", "17");
		fbOpCorpId.put("16", "22");
		fbOpCorpId.put("499", "23");
		fbOpCorpId.put("18", "24");
		fbOpCorpId.put("474", "31");
		fbOpCorpId.put("659", "35");
	}

	@Override
	public Map<String, Object> findFbLeagueRankData(String daVCaiMatchId) {
		Map<String, Object> fbMap = new HashMap<String, Object>(18);
		try {
			if (StringUtils.isNotBlank(daVCaiMatchId)) {
				Object[] objects = jCZQAnalyseDao
						.findFbMatchInfoByDavcaiMatchId(daVCaiMatchId);
				if (objects != null) {
					FbMatchBaseInfoPO fbMatchBaseInfoPO = (FbMatchBaseInfoPO) objects[0];

					if (fbMatchBaseInfoPO.getMatchStatus() != null
							&& fbMatchBaseInfoPO.getMatchStatus() == -1) {
						fbMap.put("leaveTime", "已完赛");
					} else {
						// 计算距离开赛剩余时间
						fbMap.put("leaveTime", "距离开赛还剩"
								+ makeLeaveTime(fbMatchBaseInfoPO));
					}
					if (fbMatchBaseInfoPO != null
							&& fbMatchBaseInfoPO.getSeason() != null) {
						String leagueId = fbMatchBaseInfoPO.getLeagueId();
						String season = fbMatchBaseInfoPO.getSeason();
						String homeTeamId = fbMatchBaseInfoPO.getHomeTeamId();
						String guestTeamId = fbMatchBaseInfoPO.getGuestTeamId();

						queryLeagueScoreRank(fbMap, leagueId, season,
								homeTeamId, guestTeamId);
						queryLeagueHalfScoreRank(fbMap, leagueId, season,
								homeTeamId, guestTeamId);

					}
					fbMap.put("homeTeamId", objects[1]);
					fbMap.put("guestTeamId", objects[2]);
					fbMap.put(FbLeagueRankDataType.matchMess.toString(),
							fbMatchBaseInfoPO);
				}

			}
		} catch (Throwable e) {
			logger.error("查询竞彩足球联赛排名情况数据时出错:{}", e);
		}
		return fbMap;
	}

	/**
	 * @param fbMap
	 * @param leagueId
	 * @param season
	 * @param homeTeamId
	 * @param guestTeamId
	 */
	private void queryLeagueScoreRank(Map<String, Object> fbMap,
			String leagueId, String season, String homeTeamId,
			String guestTeamId) {
		// 主队总排名
		LeagueScoreRandPO fbLeagueScoreRandPO = jCZQAnlyseJedisDao
				.findFbLeagueRank(Keys.getFbLeagueTeamScoreRankAnalyseKeyTotal(
						leagueId, season, homeTeamId));

		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.homeTeamTotalRank.toString(),
				fbLeagueScoreRandPO);
		// 主队主场排名
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamScoreRankAnalyseKeyZC(leagueId, season,
						homeTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.homeTeamZCRank.toString(),
				fbLeagueScoreRandPO);

		// 主队客场排名
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamScoreRankAnalyseKeyKC(leagueId, season,
						homeTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.homeTeamKCRank.toString(),
				fbLeagueScoreRandPO);
		// 主队近六场
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamScoreRankAnalyseKeyLatest_6(leagueId, season,
						homeTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.homeTeamRankLatest_6.toString(),
				fbLeagueScoreRandPO);
		// 客队总排名
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamScoreRankAnalyseKeyTotal(leagueId, season,
						guestTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.guestTeamTotalRank.toString(),
				fbLeagueScoreRandPO);
		// 客队主场排名
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamScoreRankAnalyseKeyZC(leagueId, season,
						guestTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.guestTeamZCRank.toString(),
				fbLeagueScoreRandPO);
		// 客队客场排名
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamScoreRankAnalyseKeyKC(leagueId, season,
						guestTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.guestTeamKCRank.toString(),
				fbLeagueScoreRandPO);
		// 客队近六场
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamScoreRankAnalyseKeyLatest_6(leagueId, season,
						guestTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.guestTeamRankLatest_6.toString(),
				fbLeagueScoreRandPO);
	}

	/**
	 * @param fbMap
	 * @param leagueId
	 * @param season
	 * @param homeTeamId
	 * @param guestTeamId
	 */
	private void queryLeagueHalfScoreRank(Map<String, Object> fbMap,
			String leagueId, String season, String homeTeamId,
			String guestTeamId) {
		LeagueScoreRandPO fbLeagueScoreRandPO = null;
		// 主队半场总排名
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamHalfScoreRankAnalyseKeyTotal(leagueId, season,
						homeTeamId));

		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.homeTeamHalfTotalRank.toString(),
				fbLeagueScoreRandPO);
		// 主队半场主场排名
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamHalfScoreRankAnalyseKeyZC(leagueId, season,
						homeTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.homeTeamHalfZCRank.toString(),
				fbLeagueScoreRandPO);

		// 主队半场客场排名
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamHalfScoreRankAnalyseKeyKC(leagueId, season,
						homeTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(
				FbLeagueRankDataType.homeTeamHalfKCRank.toString().toString(),
				fbLeagueScoreRandPO);
		// 主队半场近六场
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamHalfScoreRankAnalyseKeyLatest_6(leagueId,
						season, homeTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.homeTeamHalfRankLatest_6.toString(),
				fbLeagueScoreRandPO);
		// 客队半场总排名
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamHalfScoreRankAnalyseKeyTotal(leagueId, season,
						guestTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.guestTeamHalfTotalRank.toString(),
				fbLeagueScoreRandPO);
		// 客队半场主场排名
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamHalfScoreRankAnalyseKeyZC(leagueId, season,
						guestTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.guestTeamHalfZCRank.toString(),
				fbLeagueScoreRandPO);
		// 客队半场客场排名
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamHalfScoreRankAnalyseKeyKC(leagueId, season,
						guestTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.guestTeamHalfKCRank.toString(),
				fbLeagueScoreRandPO);
		// 客队半场近六场
		fbLeagueScoreRandPO = jCZQAnlyseJedisDao.findFbLeagueRank(Keys
				.getFbLeagueTeamHalfScoreRankAnalyseKeyLatest_6(leagueId,
						season, guestTeamId));
		if (fbLeagueScoreRandPO != null) {
			fbLeagueScoreRandPO.setWinRate(makeWinPercent(fbLeagueScoreRandPO));
		}
		fbMap.put(FbLeagueRankDataType.guestTeamHalfRankLatest_6.toString(),
				fbLeagueScoreRandPO);
	}

	private String makeLeaveTime(FbMatchBaseInfoPO fbMatchBaseInfoPO) {
		// TODO Auto-generated method stub
		if (fbMatchBaseInfoPO != null
				&& fbMatchBaseInfoPO.getMatchStatus() == 0) {
			Date date = new Date();
			long time = fbMatchBaseInfoPO.getMatchTime().getTime()
					- date.getTime();
			if (time > 0) {
				long day = time / (24 * 3600 * 1000);
				long hour = time % (24 * 3600 * 1000) / (3600 * 1000);
				long minute = time % (24 * 3600 * 1000) % (3600 * 1000)
						/ (60 * 1000);
				long second = time % (24 * 3600 * 1000) % (3600 * 1000)
						% (60 * 1000) / 1000;
				StringBuilder sb = new StringBuilder();
				if (day > 0) {
					sb.append(day + "天");
				}
				if (hour > 0) {
					sb.append(hour + "小时");
				}
				if (minute > 0) {
					sb.append(minute + "分");
				}
				if (second > 0) {
					sb.append(second + "秒");
				}
				return sb.toString();
			}
		}
		return null;
	}

	/**
	 * 计算胜率
	 * 
	 * @param fbLeagueScoreRandPO
	 * @return
	 */
	private String makeWinPercent(LeagueScoreRandPO fbLeagueScoreRandPO) {
		if (fbLeagueScoreRandPO != null) {
			NumberFormat numberFormat = NumberFormat.getPercentInstance();
			numberFormat.setMinimumFractionDigits(2);
			return numberFormat.format(fbLeagueScoreRandPO.getWinMatches()
					* 1.0 / fbLeagueScoreRandPO.getTotalMatches());
		}
		return null;
	}

	@Override
	public String findAgainstHistory_latest_20(String homeTeamId,
			String guestTeamId) {
		List<FbMatchBaseInfoPO> matchBaseInfoPOs = jCZQAnalyseDao
				.queryFbMatchAgainstLatest_20(homeTeamId, guestTeamId, 20);
		String jsonMatch = "";
		if (matchBaseInfoPOs != null && matchBaseInfoPOs.size() > 0) {
			jsonMatch = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(matchBaseInfoPOs);
		}
		return jsonMatch;
	}

	@Override
	public String findTeamRecentRecord_latest_20(String teamId) {
		List<FbMatchBaseInfoPO> matchBaseInfoPOs = jCZQAnalyseDao
				.queryFbTeamMatchRecord_latest(teamId, 20);
		String jsonMatch = "";
		if (matchBaseInfoPOs != null && matchBaseInfoPOs.size() > 0) {
			jsonMatch = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(matchBaseInfoPOs);
		}
		return jsonMatch;
	}

	@Override
	public String findLeagueScoreRankByLeagueId(String leagueId, String rankType) {
		String data = "";
		if (StringUtils.isNotBlank(leagueId)
				&& StringUtils.isNotBlank(rankType)) {
			Map<String, Object> leagueScoreAndRuleMap = new HashMap<>();
			// 总积分榜
			if (StringUtils.equals("total_rank", rankType)) {
				List<FbLeagueScorePO> leagueScorePOs = jCZQAnalyseDao
						.queryLeagueOverallStandings(leagueId);
				List<FbLeagueScoreRulePO> leagueScoreRulePOs = jCZQAnalyseDao
						.queryLeagueOverallStandingsRule(leagueId);

				leagueScoreAndRuleMap.put("leagueScoreOverall", leagueScorePOs);
				leagueScoreAndRuleMap
						.put("leagueScoreRule", leagueScoreRulePOs);
			} else {
				String seasonName = jCZQAnalyseDao
						.queryNowSeasonByLeagueId(leagueId);
				Set<String> teamIds = jCZQAnalyseDao.queryQtFbTeamByLeagueId(
						leagueId, 1, seasonName);
				String[] keys = new String[teamIds.size()];
				List<LeagueScoreRandPO> leagueScorePOs;
				makeKeys(leagueId, rankType, seasonName, teamIds, keys);
				leagueScorePOs = jCZQAnlyseJedisDao.findFbLeagueRankList(keys);
				// logger.debug("ranktype:" + rankType);
				leagueScoreAndRuleMap.put("leagueScoreOverall", leagueScorePOs);

			}
			data = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(leagueScoreAndRuleMap);

		}

		return data;
	}

	/**
	 * @param leagueId
	 * @param rankType
	 * @param seasonName
	 * @param teamIds
	 * @param keys
	 */
	private void makeKeys(String leagueId, String rankType, String seasonName,
			Set<String> teamIds, String[] keys) {
		Iterator<String> iterator = teamIds.iterator();
		int i = 0;
		if (StringUtils.equals("zc_rank", rankType)) {
			while (iterator.hasNext()) {
				String teamId = iterator.next();
				if (StringUtils.isNotBlank(teamId)) {
					keys[i++] = Keys.getFbLeagueTeamScoreRankAnalyseKeyZC(
							leagueId, seasonName, teamId);
				}
			}
		} else if (StringUtils.equals("kc_rank", rankType)) {
			// 客场积分榜
			while (iterator.hasNext()) {
				String teamId = iterator.next();
				if (StringUtils.isNotBlank(teamId)) {
					keys[i++] = Keys.getFbLeagueTeamScoreRankAnalyseKeyKC(
							leagueId, seasonName, teamId);
				}
			}

		} else if (StringUtils.equals("half_total_rank", rankType)) {
			// 半场总积分榜
			while (iterator.hasNext()) {
				String teamId = iterator.next();
				if (StringUtils.isNotBlank(teamId)) {
					keys[i++] = Keys
							.getFbLeagueTeamHalfScoreRankAnalyseKeyTotal(
									leagueId, seasonName, teamId);
				}
			}

		} else if (StringUtils.equals("half_kc_rank", rankType)) {
			// 半场客场积分榜
			while (iterator.hasNext()) {
				String teamId = iterator.next();
				if (StringUtils.isNotBlank(teamId)) {
					keys[i++] = Keys.getFbLeagueTeamHalfScoreRankAnalyseKeyKC(
							leagueId, seasonName, teamId);
				}
			}
		} else if (StringUtils.equals("half_zc_rank", rankType)) {
			// 半场主场积分榜
			while (iterator.hasNext()) {
				String teamId = iterator.next();
				if (StringUtils.isNotBlank(teamId)) {
					keys[i++] = Keys.getFbLeagueTeamHalfScoreRankAnalyseKeyZC(
							leagueId, seasonName, teamId);
				}
			}
		}
	}

	@Override
	public String findFbMatchEuroOddsById(String daVCaiMatchId) {
		String jsonData = "";
		List<QtMatchOpOddsModel> opOddsModels = new ArrayList<>();
		if (StringUtils.isNotBlank(daVCaiMatchId)) {
			Long matchId = jCZQAnalyseDao
					.queryFbMatchIdByDavcaiId(daVCaiMatchId);
			if (matchId != null) {
				List<Object[]> oddsInfoPOs = jCZQAnalyseDao
						.queryFbMatchEuroOddsByMatchId(matchId,null);
				if (oddsInfoPOs != null && !oddsInfoPOs.isEmpty()) {
					for (Object[] oddsInfoPO : oddsInfoPOs) {
						FbMatchOpOddsInfoPO oddsInfoPO2 = (FbMatchOpOddsInfoPO) oddsInfoPO[0];
						QtMatchOpOddsModel opOddsModel = new QtMatchOpOddsModel();
						opOddsModel.setCorpId(oddsInfoPO2.getCorpId());
						opOddsModel.setCorpName((String) oddsInfoPO[1]);
						opOddsModel.setEuroOdds(oddsInfoPO2.getEuroOdds());
						opOddsModel.setChangeTime(oddsInfoPO2.getChangeTime());
						opOddsModel.setKellyIndex(oddsInfoPO2.getKellyIndex());
						opOddsModels.add(opOddsModel);
					}

				}

			}

		}
		jsonData = JsonConvertUtil
				.convertObjectNotNullPropertiesToJsonString(opOddsModels);
		return jsonData;
	}

	@Override
	public List<Map<String, Object>> findFbMatchAsianOddsById(
			String daVCaiMatchId) {
		List<Map<String, Object>> asiaMapList = new ArrayList<>();
		if (StringUtils.isNotBlank(daVCaiMatchId)) {
			Map<String, Object> asiaMap = new HashMap<>();
			Map<String, Object> jishiMap = new HashMap<>();
			Map<String, Object> matchMap = new HashMap<>();
			Object[] objectsMatch = jCZQAnalyseDao
					.findFbMatchInfoByDavcaiMatchId(daVCaiMatchId);
			if (objectsMatch != null) {
				FbMatchBaseInfoPO matchBaseInfoPO = (FbMatchBaseInfoPO) objectsMatch[0];
				if (matchBaseInfoPO != null) {
					Long matchId = matchBaseInfoPO.getId();

					matchMap.put("fbMatch", matchBaseInfoPO);
					// 获取初盘
					List<Object[]> asiaOuOddsInfoPOs = jCZQAnalyseDao
							.queryFbMatchAsianOddsById(matchId, "min");

					// 获取即时盘
					List<Object[]> jishInfoPOs = jCZQAnalyseDao
							.queryFbMatchAsianOddsById(matchId, "max");
					if (asiaOuOddsInfoPOs != null
							&& !asiaOuOddsInfoPOs.isEmpty()) {
						for (int i = 0; i < asiaOuOddsInfoPOs.size(); i++) {
							Object[] objects = asiaOuOddsInfoPOs.get(i);
							FbMatchAsiaOuOddsInfoPO ouOddsInfoPO = (FbMatchAsiaOuOddsInfoPO) objects[0];
							String corpName = (String) objects[1];
							ouOddsInfoPO.setCorpName(corpName);
							asiaMap.put(ouOddsInfoPO.getCorpId() + "",
									ouOddsInfoPO);
							Object[] objects1 = jishInfoPOs.get(i);
							FbMatchAsiaOuOddsInfoPO jishInfoPO = (FbMatchAsiaOuOddsInfoPO) objects1[0];
							jishiMap.put(jishInfoPO.getCorpId() + "",
									jishInfoPO);
						}

					}

				}
				matchBaseInfoPO.setHomeTeamId((String) objectsMatch[1]);
				matchBaseInfoPO.setGuestTeamId((String) objectsMatch[2]);
				asiaMapList.add(asiaMap);
				asiaMapList.add(jishiMap);
				asiaMapList.add(matchMap);
			}
		}
		return asiaMapList;
	}

	@Override
	public List<FbMatchAsiaOuOddsInfoPO> findFbAsianOddsOneCompany(
			Long matchId, Integer corpId) {
		// 获取初盘
		List<FbMatchAsiaOuOddsInfoPO> asiaOuOddsInfoPOs = jCZQAnalyseDao
				.queryFbMatchAsianOddsByIdAndCorpId(matchId, corpId);
		return asiaOuOddsInfoPOs;
	}

	@Override
	public Map<String, Map<String, Map>> findFbOddsPushInit(String corpIds,
			String time) {
		if (StringUtils.isNotBlank(corpIds)) {

			// 按时间范围查询足球赛程
			ArrayList<Date> fromTo = ServiceUtils.getFromAndTo(time);
			List<Object[]> bsId_cnCode_matchTime = jCZQAnalyseDao
					.queryFbMatchByPlayingTime(fromTo.get(0), fromTo.get(1));
			if (bsId_cnCode_matchTime != null
					&& !bsId_cnCode_matchTime.isEmpty()) {
				String[] corpIdArray = corpIds.split(",");
				String bsIds = "";
				String euroCorpId = "";
				String asianOuCorpId = ""; // 亚赔或大小的公司id
				Map<Long, String> bsMessage = new HashMap<Long, String>();
				for (Object[] obj : bsId_cnCode_matchTime) {
					bsMessage.put(
							(Long) obj[0],
							obj[1]
									+ "-"
									+ DateFormatUtils.format((Date) obj[2],
											"yyyyMMddHHmmss"));
					bsIds += obj[0] + ",";
				}
				for (String corpId : corpIdArray) {
					if(fbOpCorpId.get(corpId)!=null){
						euroCorpId += "'" + fbOpCorpId.get(corpId) + "',";
					}
					asianOuCorpId += "'" + corpId + "',";
				}
				euroCorpId = StringUtils.removeEnd(euroCorpId, ",");
				asianOuCorpId = StringUtils.removeEnd(asianOuCorpId, ",");
				bsIds = StringUtils.removeEnd(bsIds, ",");
				List<FbMatchAsiaOuOddsInfoPO> fbMatchInitAsianOddsInfoPOs = jCZQAnalyseDao
						.queryFbMatchInitAsianOrOuOddsByBsIdsAndCorpIds(bsIds,asianOuCorpId,1);
				List<FbMatchAsiaOuOddsInfoPO> fbMatchInitOuOddsInfoPOs = jCZQAnalyseDao
						.queryFbMatchInitAsianOrOuOddsByBsIdsAndCorpIds(bsIds, asianOuCorpId, 2);
				fbMatchInitAsianOddsInfoPOs.addAll(fbMatchInitOuOddsInfoPOs);
				
				List<FbMatchOpOddsInfoPO> fbMatchOpOddsInfoPOs = jCZQAnalyseDao
						.queryFbOpOddsByBsIdsAndCorpIds(euroCorpId, bsIds);
				List<FbMatchAsiaOuOddsInfoPO> fbMatchAsiaOddsInfoPOs = jCZQAnalyseDao
						.queryFbMatchAsianOrOuOddsByBsIdsAndCorpIds(
								asianOuCorpId, bsIds, 1, maxNum);
				List<FbMatchAsiaOuOddsInfoPO> fbMatchOuOddsInfoPOs = jCZQAnalyseDao
						.queryFbMatchAsianOrOuOddsByBsIdsAndCorpIds(
								asianOuCorpId, bsIds, 2, maxNum);
				List<Object> oddsObjects = new ArrayList<Object>();
				oddsObjects.addAll(fbMatchOpOddsInfoPOs);
				oddsObjects.addAll(fbMatchAsiaOddsInfoPOs);
				oddsObjects.addAll(fbMatchOuOddsInfoPOs);
				Map<String, Map<String, Map>> oddsMap = new HashMap<String, Map<String, Map>>();
				Map<String, Map> corpIdMap = null;
				Map<String, String> odds = null;
				
				makeOddsData(bsMessage, oddsObjects, oddsMap);
				for(FbMatchAsiaOuOddsInfoPO fbMatchAsiaOuOddsInfoPO : fbMatchInitAsianOddsInfoPOs){
					String playingCode = MATCH_ID_PREFIX+bsMessage.get(Long.valueOf(fbMatchAsiaOuOddsInfoPO.getBsId()));
					if(oddsMap.get(playingCode)!=null){
						if(oddsMap.get(playingCode).get(CORP_ID_PREFIX+fbMatchAsiaOuOddsInfoPO.getCorpId())!=null){
							String init_key = (fbMatchAsiaOuOddsInfoPO.getOddsType()==1?ASIAN_KEY:BIGSMALL_KEY)+"_init";
							oddsMap.get(playingCode).get(CORP_ID_PREFIX+fbMatchAsiaOuOddsInfoPO.getCorpId()).put(init_key, 
									fbMatchAsiaOuOddsInfoPO.getHomeWinOdds()+","+
									fbMatchAsiaOuOddsInfoPO.getHandicap()+","+
									fbMatchAsiaOuOddsInfoPO.getGuestWinOdds());
						}
					}
				}
				return oddsMap;
			}
		}
		return null;
	}

	private void makeOddsData(Map<Long, String> bsMessage,
			List<Object> oddsObjects, Map<String, Map<String, Map>> oddsMap) {
		String corpId = "";
		String playCode = "";
		String key = "";
		String time_key = "";
		Map<String, Map> corpIdMap;
		Map<String, String> odds;
		try {
			for (Object oddsObject : oddsObjects) {
				String initOddsKey = "";
				String initOddsData = "";
				String oddsData = "";
				String timestamp = "";
				if (oddsObject instanceof FbMatchOpOddsInfoPO) {
					FbMatchOpOddsInfoPO fbMatchOpOddsInfoPO = (FbMatchOpOddsInfoPO) oddsObject;
					String[] euroOdds = fbMatchOpOddsInfoPO
							.getEuroOdds().split("!");
					String[] changeTimes = fbMatchOpOddsInfoPO
							.getChangeTime().split(",");
					corpId = CORP_ID_PREFIX+fbOpCorpId.get(fbMatchOpOddsInfoPO.getCorpId());
					playCode = MATCH_ID_PREFIX+bsMessage.get(fbMatchOpOddsInfoPO.getBsId());
					initOddsData = euroOdds[euroOdds.length - 1];
					initOddsData = StringUtils.substring(initOddsData,0,initOddsData.lastIndexOf(","));
					for (int i = 0; i < euroOdds.length; i++) {
						oddsData += StringUtils.substring(euroOdds[i],0,euroOdds[i].lastIndexOf(",")) + ",";
						timestamp += String.valueOf(DateUtils.parseDate(
												changeTimes[i],new String[] { "yyyyMMddHHmmss" })
										.getTime())+ ",";
						if (i >= 4) {
							break;
						}
					}
					oddsData=StringUtils.removeEnd(oddsData, ",");
					timestamp = StringUtils.removeEnd(timestamp, ",");
					key = EUROPE_KEY;
					time_key = EUROPE_KEY+"_time"; 
					initOddsKey = EUROPE_KEY+"_init";
				} else {
					FbMatchAsiaOuOddsInfoPO fbMatchAsiaOuOddsInfoPO = (FbMatchAsiaOuOddsInfoPO) oddsObject;
					Integer oddsType = fbMatchAsiaOuOddsInfoPO
							.getOddsType();
					Integer bsId = fbMatchAsiaOuOddsInfoPO.getBsId();
					corpId = CORP_ID_PREFIX+fbMatchAsiaOuOddsInfoPO.getCorpId();
					playCode = MATCH_ID_PREFIX + bsMessage.get(Long.valueOf(bsId));
					oddsData = fbMatchAsiaOuOddsInfoPO.getHomeWinOdds()
							+ ","
							+ fbMatchAsiaOuOddsInfoPO.getHandicap()
							+ ","
							+ fbMatchAsiaOuOddsInfoPO.getGuestWinOdds();
					timestamp += String.valueOf(fbMatchAsiaOuOddsInfoPO
							.getTimestamp().getTime());
					if (oddsType == 1) {
						key = ASIAN_KEY;
						time_key = ASIAN_KEY+"_time";
						initOddsKey = ASIAN_KEY+"_init";
					} else if (oddsType == 2) {
						key = BIGSMALL_KEY;
						time_key = BIGSMALL_KEY+"_time";
					}
				}
				if (oddsMap.get(playCode) != null) {
					corpIdMap = oddsMap.get(playCode);
					if (corpIdMap.get(corpId) != null) {
						odds = corpIdMap.get(corpId);
						if(odds.get(key)!=null){
							odds.put(key,odds.get(key)+","+oddsData);
							odds.put(time_key, odds.get(time_key)+","+timestamp);
						}else{
							odds.put(key,oddsData);
							odds.put(time_key, timestamp);
							if(StringUtils.isNotBlank(initOddsData)){
								odds.put(initOddsKey, initOddsData);
							}
						}
					} else {
						odds = new HashMap<String, String>();
						odds.put(key,oddsData);
						odds.put(time_key, timestamp);
						if(StringUtils.isNotBlank(initOddsData)){
							odds.put(initOddsKey, initOddsData);
						}
					}
				} else {
					corpIdMap = new HashMap<String, Map>();
					odds = new HashMap<String, String>();
					odds.put(key,oddsData);
					odds.put(time_key, timestamp);
					if(StringUtils.isNotBlank(initOddsData)){
						odds.put(initOddsKey, initOddsData);
					}
				}
				corpIdMap.put(corpId, odds);
				oddsMap.put(playCode, corpIdMap);
			}
		} catch (Exception e) {
			logger.error("解析赔率数据时出错:{}",e);
		}
	}

	@Override
	public Map<String, Map> findJczqMatchTeamPosition(String time) {
		// 按时间范围查询足球赛程
		ArrayList<Date> fromTo = ServiceUtils.getFromAndTo(time);
		List<Object[]> bsId_cnCode_matchTime = jCZQAnalyseDao
				.queryFbMatchByPlayingTime(fromTo.get(0), fromTo.get(1));
		if(bsId_cnCode_matchTime!=null&&!bsId_cnCode_matchTime.isEmpty()){
			Map<String, Map> dataMap = new HashMap<String, Map>();
			for(Object[] obj:bsId_cnCode_matchTime){
				String playCode = MATCH_ID_PREFIX+obj[1]+ "-"
						+ DateFormatUtils.format((Date) obj[2],
								"yyyyMMddHHmmss");
				Map<String, Integer> postionData = new HashMap<String, Integer>();
				postionData.put("homeTeamPosition", (Integer) obj[3]);
				postionData.put("guestTeamPosition", (Integer) obj[4]);
				dataMap.put(playCode, postionData);
			}
			return dataMap;
		}
		return null;
	}

}
