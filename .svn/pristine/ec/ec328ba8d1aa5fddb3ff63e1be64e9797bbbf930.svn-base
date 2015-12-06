package com.unison.lottery.weibo.web.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map; 

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.davcai.lottery.constant.BbLeagueRankDataType;
import com.davcai.lottery.dao.JCLQAnalyseDao;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueScorePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallMatchPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchAsiaOuOddsInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketMatchOpOddsInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.util.JsonConvertUtil;
import com.unison.lottery.weibo.web.service.JCLQAnalyseService;
import com.xhcms.lottery.utils.ServiceUtils;

/**
 * @author baoxing.peng@davcai.com
 *
 * @since 2015年3月2日下午5:12:39
 */
@Service
public class JCLQAnalyseServiceImpl implements JCLQAnalyseService {

	private static final String MATCH_ID = "matchId";
	private static final String CORP_ID = "corpId";
	private static final String EUROPE_TIME_KEY = "europe_time";
	private static final String EUROPE_KEY = "europe";
	private static final String ASIAN_TIME_KEY = "asian_time";
	private static final String ASIAN_KEY = "asian";
	private static final String BIGSMALL_KEY = "bigsmall";
	private static final String BIGSMALL_TIME_KEY = "bigsmall_time";
	private static final int maxNum = 5;
	@Autowired
	private JCLQAnalyseDao jCLQAnalyseDao;
	private Logger logger = LoggerFactory
			.getLogger(JCLQAnalyseServiceImpl.class);
	private static Map<String, String> corpIdsArray = new HashMap<String, String>();
	private static Map<String, String> corpNamesArray = new HashMap<String, String>();
	static { 
		// asian or bigsmall or euro corp
		corpIdsArray.put("1", "1");
		corpIdsArray.put("2", "2");
		corpIdsArray.put("3", "3");
		corpIdsArray.put("8", "8");
		corpIdsArray.put("9", "9");

		corpNamesArray.put("澳门", "1");
		corpNamesArray.put("易胜博", "2");
		corpNamesArray.put("皇冠", "3");
		corpNamesArray.put("bet365", "8");
		corpNamesArray.put("韦德", "9");
		corpNamesArray.put("Macauslot", "4");
		corpNamesArray.put("BWin", "12");
		corpNamesArray.put("Coral", "15");
		corpNamesArray.put("easybets", "16");
		corpNamesArray.put("Expekt", "17");
		corpNamesArray.put("Bet365", "8");
	}

	@Override
	public String findAgainstHistory_latest_20(String homeTeamId,
			String guestTeamId) {
		List<BasketBallMatchPO> matchBaseInfoPOs = jCLQAnalyseDao
				.queryBbMatchAgainstLatest_20(homeTeamId, guestTeamId, 10);
		String jsonMatch = "";
		if (matchBaseInfoPOs != null && matchBaseInfoPOs.size() > 0) {
			jsonMatch = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(matchBaseInfoPOs);

		}
		return jsonMatch;
	}

	@Override
	public Map<String, Object> findMatchMessageById(String daVCaiMatchId) {
		Map<String, Object> fbMap = new HashMap<String, Object>(18);
		try {
			if (StringUtils.isNotBlank(daVCaiMatchId)) {
				Object[] objects = jCLQAnalyseDao
						.findBbMatchInfoByDavcaiMatchId(daVCaiMatchId);
				if (objects != null) {
					BasketBallMatchPO bbMatchBaseInfoPO = (BasketBallMatchPO) objects[0];

					if (bbMatchBaseInfoPO.getMatchState() != null
							&& bbMatchBaseInfoPO.getMatchState() == -1) {
						fbMap.put("leaveTime", "已完赛");
					} else {
						// 计算距离开赛剩余时间
						fbMap.put("leaveTime", "距离开赛还剩"
								+ makeLeaveTime(bbMatchBaseInfoPO));
					}
					if (bbMatchBaseInfoPO != null
							&& bbMatchBaseInfoPO.getSeason() != null) {
						String leagueId = bbMatchBaseInfoPO.getLeagueId();
						String season = bbMatchBaseInfoPO.getSeason();
						String homeTeamId = bbMatchBaseInfoPO.getHomeTeam();
						String guestTeamId = bbMatchBaseInfoPO.getGuestTeam();
						fbMap.put("homeTeamId", homeTeamId);
						fbMap.put("guestTeamId", guestTeamId);
						fbMap.put("leagueId", leagueId);

					}
					bbMatchBaseInfoPO.setHomeTeamId((String) objects[1]);
					bbMatchBaseInfoPO.setGuestTeamId((String) objects[2]);
					fbMap.put(BbLeagueRankDataType.matchMess.toString(),
							bbMatchBaseInfoPO);
				}

			}
		} catch (Throwable e) {
			logger.error("查询竞彩篮球联赛排名情况数据时出错:{}", e);
		}
		return fbMap;
	}

	private String makeLeaveTime(BasketBallMatchPO bbMatchBaseInfoPO) {
		// TODO Auto-generated method stub
		return null;
	}

	public JCLQAnalyseDao getjCLQAnalyseDao() {
		return jCLQAnalyseDao;
	}

	public void setjCLQAnalyseDao(JCLQAnalyseDao jCLQAnalyseDao) {
		this.jCLQAnalyseDao = jCLQAnalyseDao;
	}

	@Override
	public String findLeagueScoreRankByLeagueId(String leagueId,
			String rankType, String subLeagueId) {
		String data = "";
		if (StringUtils.isNotBlank(leagueId)
				&& StringUtils.isNotBlank(rankType)) {
			Map<String, Object> leagueScoreAndRuleMap = new HashMap<>();
			// 总积分榜
			if (StringUtils.equals("total_rank", rankType)) {
				List<BasketBallLeagueScorePO> leagueScorePOs = jCLQAnalyseDao
						.queryLeagueOverallStandings(leagueId, subLeagueId);
				leagueScoreAndRuleMap.put("leagueScoreOverall", leagueScorePOs);
			} else {
				// String seasonName = jCZQAnalyseDao
				// .queryNowSeasonByLeagueId(leagueId);
				// Set<String> teamIds =
				// jCZQAnalyseDao.queryQtFbTeamByLeagueId(leagueId,
				// 1, seasonName);
				// String[] keys = new String[teamIds.size()];
				// List<FbLeagueScoreRandPO> leagueScorePOs;
				// makeKeys(leagueId, rankType, seasonName, teamIds, keys);
				// leagueScorePOs =
				// jCZQAnlyseJedisDao.findFbLeagueRankList(keys);
				// // logger.debug("ranktype:" + rankType);
				// leagueScoreAndRuleMap.put("leagueScoreOverall",
				// leagueScorePOs);

			}
			data = JsonConvertUtil
					.convertObjectNotNullPropertiesToJsonString(leagueScoreAndRuleMap);

		}

		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Map<String, Map>> findBbOddsPushInit(String corpIds,String time) {
		if (StringUtils.isNotBlank(corpIds)) {

			// 按时间范围查询篮球赛程
			ArrayList<Date> fromTo = ServiceUtils.getFromAndTo(time);
			List<Object[]> bsId_cnCode_matchTime = jCLQAnalyseDao
					.queryBbMatchByPlayingTime(fromTo.get(0),fromTo.get(1));
			if (bsId_cnCode_matchTime != null
					&& !bsId_cnCode_matchTime.isEmpty()) {
				String[] corpIdArray = corpIds.split(",");
				StringBuilder corpNameSBuilder = new StringBuilder("");
				Map<Integer, String> bsMessage = new HashMap<Integer, String>();
				for (int i = 0; i < corpIdArray.length; i++) {
					String corpName = corpIdsArray.get(corpIdArray[i]);
					if (corpName != null) {
						corpNameSBuilder.append("'" + corpName + "',");
					}
					if("8".equals(corpIdArray[i])){
						corpNameSBuilder.append("'Bet365'");
					}
				}
				String bsIds = "";
				String corpNames = StringUtils.substring(
						corpNameSBuilder.toString(), 0,
						corpNameSBuilder.length() - 1);
				for (Object[] obj : bsId_cnCode_matchTime) {
					bsMessage.put(((Long) obj[0]).intValue(), obj[1] + "-" + DateFormatUtils.format((Date)obj[2], "yyyyMMddHHmmss"));
					bsIds += obj[0] + ",";
				}
				bsIds = StringUtils.substring(bsIds, 0, bsIds.length() - 1);
				List<BasketMatchOpOddsInfoPO> basketMatchOpOddsInfoPOs = jCLQAnalyseDao
						.queryBbOpOddsByBsIdsAndCorpIds(corpNames, bsIds,maxNum);
				List<BasketMatchAsiaOuOddsInfoPO> basketMatchAsiaOddsInfoPOs = jCLQAnalyseDao
						.queryBbMatchAsianOrOuOddsByBsIdsAndCorpIds(corpNames,
								bsIds, 1,maxNum);
				List<BasketMatchAsiaOuOddsInfoPO> basketMatchOuOddsInfoPOs = jCLQAnalyseDao
						.queryBbMatchAsianOrOuOddsByBsIdsAndCorpIds(corpNames,
								bsIds, 2,maxNum);
				Map<String, Map<String, Map>> oddsMap = new HashMap<String, Map<String, Map>>();
				List<Object> oddsObjects = new ArrayList<Object>();
				oddsObjects.addAll(basketMatchOpOddsInfoPOs);
				oddsObjects.addAll(basketMatchAsiaOddsInfoPOs);
				oddsObjects.addAll(basketMatchOuOddsInfoPOs);
				Map<String, Map> corpIdMap = null;
				Map<String, String> odds = null;
				String corpId = "";
				String playCode = "";
				String oddsData = "";
				String key = "";
				String timestamp = "";
				String time_key = "";
				for (int i = 0; i < oddsObjects.size(); i++) {
					if (oddsObjects.get(i) instanceof BasketMatchOpOddsInfoPO) {
						BasketMatchOpOddsInfoPO basketMatchOpOddsInfoPO = (BasketMatchOpOddsInfoPO) oddsObjects.get(i);
						Integer bsId = basketMatchOpOddsInfoPO.getBsId();
						corpId = CORP_ID+corpNamesArray
								.get(basketMatchOpOddsInfoPO.getCorpName());
						playCode = MATCH_ID+bsMessage.get(bsId);
						oddsData = basketMatchOpOddsInfoPO.getHomeWinOdds()
								+ ","
								+ basketMatchOpOddsInfoPO
										.getGuestWinOdds();
						timestamp = String.valueOf(basketMatchOpOddsInfoPO.getTimestamp().getTime());
						key = EUROPE_KEY;
						time_key = EUROPE_TIME_KEY; 
						
					}else{
						BasketMatchAsiaOuOddsInfoPO basketMatchAsianOuOddsInfoPO = (BasketMatchAsiaOuOddsInfoPO) oddsObjects.get(i);
						Integer oddsType = basketMatchAsianOuOddsInfoPO.getOddsType();
						Integer bsId = basketMatchAsianOuOddsInfoPO.getBsId();
						corpId = CORP_ID+corpNamesArray
								.get(basketMatchAsianOuOddsInfoPO.getCorpName());
						playCode = MATCH_ID+bsMessage.get(bsId);
						oddsData = basketMatchAsianOuOddsInfoPO.getHomeWinOdds()
						+ "," + basketMatchAsianOuOddsInfoPO.getHandicapOrScore() + ","
						+ basketMatchAsianOuOddsInfoPO
								.getGuestWinOdds();
						timestamp = String.valueOf(basketMatchAsianOuOddsInfoPO.getTimestamp().getTime());
						if(oddsType == 1){
							key = ASIAN_KEY;
							time_key = ASIAN_TIME_KEY;
						}else if(oddsType == 2){
							key = BIGSMALL_KEY;
							time_key = BIGSMALL_TIME_KEY;
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
							}
						} else {
							odds = new HashMap<String, String>();
							odds.put(key,oddsData);
							odds.put(time_key, timestamp);
						}
					} else {
						corpIdMap = new HashMap<String, Map>();
						odds = new HashMap<String, String>();
						odds.put(key,oddsData);
						odds.put(time_key, timestamp);
					}
					corpIdMap.put(corpId, odds);
					oddsMap.put(playCode, corpIdMap);
				}
				return oddsMap;
			}
		}
		return null;
	}
}
