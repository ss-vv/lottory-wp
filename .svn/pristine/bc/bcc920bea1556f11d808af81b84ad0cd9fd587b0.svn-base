package com.unison.lottery.weibo.data.service.store.persist.service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBCupScorePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeagueScorePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubCupPO;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubLeaguePO;
import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;
import com.unison.lottery.weibo.dataservice.parse.model.AsiaOdd;
import com.unison.lottery.weibo.dataservice.parse.model.BBBjEuropeOddsData;
import com.unison.lottery.weibo.dataservice.parse.model.BBLeagueContentData;
import com.unison.lottery.weibo.dataservice.parse.model.BBMatchInfoData;
import com.unison.lottery.weibo.dataservice.parse.model.BBOdds;
import com.unison.lottery.weibo.dataservice.parse.model.BBRangFen;
import com.unison.lottery.weibo.dataservice.parse.model.BBTeamContentData;
import com.unison.lottery.weibo.dataservice.parse.model.EuropeOdd;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFData;
import com.unison.lottery.weibo.dataservice.parse.model.FBBFResultContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBLeagueContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBMatchidContentData;
import com.unison.lottery.weibo.dataservice.parse.model.FBTeamContentData;
import com.unison.lottery.weibo.dataservice.parse.model.MatchProcess;

public interface DataStoreService {

	void storeFBLeague(List<FBLeagueContentData> leagues);

	void storeFBTeam(List<FBTeamContentData> leagues);

	void storeQTMatchid(List<FBMatchidContentData> matchidList);

	Set<Integer> findNewQTMatchIds();
	
	void storeQTMatchs(Map<Long, QTMatchPO> qtMatchMap);
	
	/**
	 * 保存足球赔率接口的赛程资料。
	 */
	void storeFBMatch(List<MatchProcess> matches);

	/**
	 * 保存足球赔率接口的亚赔（让球盘）数据。
	 */
	void storeFBAsiaOdds(List<AsiaOdd> asiaOdds);
	
	/**
	 * 保存足球赔率接口的欧赔（标准盘）数据。
	 */
	void storeFBEuroOdds(List<EuropeOdd> euroOdds);

	void storeFBBFData(FBBFData result);
	
	List<FBLeaguePO> listAllLeagues();

	void storeLeague(FBLeaguePO leaguePO);

	void updateTeamLogo(Long teamId, String teamLogo);

	void mergeMatchForCrawler(QTMatchPO matchPO);

	void storeFBMatchResult(FBBFResultContentData matchData);

	/**
	 * save or update 杯赛积分.
	 */
	void storeFBCupScore(FBCupScorePO cupScorePO);

	/**
	 * save or update 杯赛子类型记录
	 */
	void storeFBSubCups(List<FBSubCupPO> subCupPO);

	/**
	 * save or update 联赛子类型记录
	 */
	void storeSubLeague(FBSubLeaguePO subLeague);

	/**
	 * 保存联赛积分集合，如果存在则更新。
	 * @param leagueScore 联赛积分集合
	 */
	void storeLeagueScore(FBLeagueScorePO leagueScore);

	/**
	 * 保存篮球球队资料信息
	 * @param bbTeamData
	 */
	void storeBBTeam(BBTeamContentData bbTeamData);
	
	/**
	 * 保存篮球联赛信息
	 * @param bbTeamData
	 */
	void storeBBLeague(BBLeagueContentData leagues);

	/**
	 * 保存、更新篮球赛事信息。
	 */
	void storeBBMatchInfo(List<BBMatchInfoData> matches);
	
	/**
	 * 更新篮球赛事信息，更新即时比分用。
	 */
	void updateBBMatchInfo(List<BBMatchInfoData> matches);
	
	/**
	 * 篮球让分赔率入库
	 * @param bbRangFens
	 */
	void storeBBConcedeScoreOdds(List<BBRangFen> bbRangFens);

	/**
	 * 保存篮球赔率。包括：大小分、欧赔、让分赔率。
	 * @param bbOdds 篮球赔率
	 */
	void storeBBOdds(BBOdds bbOdds);

	/**
	 * 保存篮球百家欧赔.同时会保存篮球博彩公司.
	 * @param bbBjOddsEuro
	 */
	void storeBBOddsBjEuro(BBBjEuropeOddsData bbBjOddsEuro);
}
