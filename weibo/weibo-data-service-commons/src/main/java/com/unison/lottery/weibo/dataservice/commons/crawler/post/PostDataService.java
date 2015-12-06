package com.unison.lottery.weibo.dataservice.commons.crawler.post;

import java.io.IOException;
import java.util.List;

import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.mq.CrawlerFbZqCompanyOddsHandle;

/**
 * 发送数据
 * @author baoxing.peng
 *
 */
public interface PostDataService {

	/**
	 * 发送足球即时比分数据
	 * @param encryptedData
	 * @throws IOException 
	 */
	void sendZqJishiBifenDataToServer(String encryptedData) throws IOException;

	/**
	 * 发送篮球竞彩即时比分数据
	 * @throws IOException 
	 */
	void sendBbJingcaiJishiBifenDataToServer(String encodeData) throws IOException;

	/**
	 * 
	 * @param encodeString
	 * @param oddsType
	 * @throws IOException 
	 */
	void sendZqChangeOddsToServer(String encodeString,
			Qt_fb_match_oddsType oddsType) throws IOException;

	/**
	 * 
	 * @return
	 * @throws IOException 
	 */
	String receiveAllJingcaiZqMatchInLive() throws IOException;

	void sendZqJishiEventToServer(String encodeData) throws IOException;

	void sendFbLeagueToServer(String encodeData) throws IOException;

	String receiveAllSeasonMessageSubLeague(Integer source, int leagueType) throws IOException;

	void sendFbSubLeagueToServer(Integer leagueType, String jsonData) throws IOException;

	String receiveAllSeasonMessageNotCrawler(Integer source) throws IOException;

	void sendFbMatchBaseMessToServer(int round, String encode, Integer seasonId) throws IOException;

	String receiveAllCupGroupMessToCraw(Integer source, Integer cupType) throws IOException;

	String receiveNowSeasonNameByLeagueId(String leagueId) throws IOException;

	void sendCupMatchToServer(String decodeData, Integer seasonId)throws IOException;

	String receiveAllZqMatchNotHaveLineup() throws IOException;

	void sendMatchLineupDataToServer(String decodeData) throws IOException;

	String receiveAllSeasonNotHaveRule(Integer source, int leagueType) throws IOException;

	void sendLeagueScoreDataToServer(String scoreRuleJson, String seasonJson) throws IOException;

	String receiveAllCupSeasonNotCrawler(int leagueType, int isSubLeague,
			Integer source) throws IOException;

	void sendCupScoreDataToserver(String encodeData, String sesonEncode) throws IOException;

	void sendFbMatchBaseMessToServer(String encode) throws IOException;

	void sendLqJishiMatchBaseMessToServer(String encode) throws IOException;

	void sendBbLeagueToServer(String encode) throws IOException;

	String receiveLqSubLeague(int leagueType, int isSubLeague, int source) throws IOException;

	void sendLqHistoryMatchToServer(String ballMatchEncodeData,
			String seasonEncodeData) throws IOException;

	String receiveLqLeagueNotSub(Integer leagueType, Integer source) throws IOException;

	void sendLqCupGroupToServer(String jsonData, Integer cupType) throws IOException;

	void sendLqSubLeagueToServer(String jsonData) throws IOException;

	void sendCupMatchToServer(String encodeLeaugeScoreAndMatch,
			String encodeSeason) throws IOException;

	String receiveAllBasketMathcInlive() throws IOException;

	void sendBasketMatchPlayerStatisticDataToServer(String encodeString) throws IOException;

	void sendMatchTeamStatisticDataToServer(String encodeString) throws IOException;

	String gotAllJingcaiLqMatchNotStart() throws IOException;

	void sendLqJishiOddsToServer(String matchBaseData, String oddsData,
			Qt_fb_match_oddsType oddsType) throws IOException;

	String receiveAllZqMatchInMatchNotHaveLiveUrl() throws IOException;

	void sendZqLiveUrlToServer(String encodeData, long id) throws IOException;

	String receiveJingcaiZqMatchNotStart() throws IOException;

	void sendFbMatchOpOneCompanyOddsToServer(String fbMatchData,
			CrawlerFbZqCompanyOddsHandle zqCompanyOddsHandle) throws IOException;


}
