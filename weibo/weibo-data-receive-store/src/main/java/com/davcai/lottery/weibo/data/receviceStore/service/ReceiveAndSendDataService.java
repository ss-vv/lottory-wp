package com.davcai.lottery.weibo.data.receviceStore.service;

import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;

/**
 *
 * @author baoxing.peng
 * @since 2014年12月31日下午4:06:27
 */
public interface ReceiveAndSendDataService {

	/**
	 * 
	 * @param jsonObject
	 */
	void saveFbJingcaiJishiBifen(String jsonObject);

	/**
	 * 
	 * @param jsonObject
	 */
	void saveBbJingcaiJishiBifen(String jsonObject);

	/**
	 * 
	 * @param jsonObject
	 * @param oddsType
	 */
	void saveFbOddChange(String jsonObject, Qt_fb_match_oddsType oddsType);

	String gotFbJingcaiMatchInLive();

	void saveFbJishiEvent(String jsonObject);

	void saveFbLeagueInfo(String jsonObject);

	/**
	 * 查询足球所有属于子联赛的联赛赛季信息
	 * @param source
	 * @param leagueType
	 * @return
	 */
	String gotFbSeasonMessSubLeagueMess(String source, String leagueType);

	void saveFbSubLeague(String jsonObject, String leagueType);

	String gotAllSeasonMessNotCrawler(String source);

	void saveFbMatchList(String jsonObject, int round, int seasonId);

	String receiveFbCupGroupMess(String source, String cupType);

	String receiveFbLeagueNowSeason(String leagueId);

	void saveFbCupMatchInfo(String jsonObject,String seasonId) throws Exception;

	String receiveZqMatchNotHaveLineup();

	void saveFbMatchLineup(String jsonObject);

	/**
	 * 
	 * @param leagueType
	 * @return
	 */
	String queryFbLeagueSeasonNotHaveRule(String leagueType);

	void saveFbLeagueScore(String encodeSeasonJson, String jsonObject);

	String queryAllCupSeasonNotCrawler(String leagueType, String source,
			String isSubLeague);

	void saveFbCupSocre(String encodeSeasonJson, String jsonObject);

	void updateFbAllJishiMatchMess(String jsonObject);

	void updateLqAllJishiMatchMess(String jsonObject);

	void saveBbLeagueSeason(String jsonObject);

	String queryLqSubLeague(String leagueType, String source, String isSubLeague);

	/**
	 * 
	 * @param seasonDecodeJson
	 * @param jsonObject
	 */
	void saveLqLeagueMatchMessage(String seasonDecodeJson, String jsonObject);

	String queryLqLeagueNotSubByType(String leagueType, String source);

	void saveLqCupGroup(String jsonObject, String leagueType);

	void saveLqSubLeague(String jsonObject);

	void saveLqCupMatchAndCupScore(String jsonObject, String seasonEncodeJson);

	String queryAllJingcaiBasketMathcInlive();

	void saveBasketMatchPlayerStatisticData(String jsonObject);

	void saveLqMatchTeamStatistic(String jsonObject);

	String queryAllJingcaiLqMatchNotStart();

	void saveLqJishiOdds(String jsonObject, String lqMatch,
			Qt_fb_match_oddsType oddsType);

	String queryAllZqMatchInMatchNotHaveLiveUrl();

	void saveZqMatchTelevisonUrl(String id, String jsonObject);

	String queryJingcaiZqMatchNotStart();

	void saveFbMatchOpOneCompany(String jsonObject, String companyId, Long bsId);

}
