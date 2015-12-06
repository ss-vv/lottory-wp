package com.unison.lottery.weibo.dataservice.crawler.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueSeasonPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchOpOddsInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.Proxy;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.AwdataStore;
import com.unison.lottery.weibo.dataservice.crawler.parse.index.yapeiModel.enuma;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallLeagueSeasonModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BbLeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.FbLeagueScoreModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.LeagueInfoModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.OddsBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchLineupModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtOddsCompanyModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;
import com.unison.lottery.weibo.mq.CrawlerFbZqCompanyOddsHandle;

/**
 * @author 彭保星
 *
 * @since 2014年10月28日下午3:24:44
 */
public interface CrawlerDataParseService {
	/**
	 * 抓取联赛及赛季信息
	 * @param proxy
	 * @param header
	 * @return
	 */
	public List<LeagueInfoModel> getLeagueInfo(Proxy proxy,Map<String, String> header);

	/**
	 * 抓取资料库赛程信息
	 * @param proxy
	 * @param header
	 * @param seasonModel
	 * @param roundNow 当前第几轮
	 * @return
	 */
	public List<QtMatchBaseModel> getHistoryMatchProcess(Proxy proxy,
			Map<String, String> header, SeasonModel seasonModel,Integer roundNow);
	/**
	 * 抓取子联赛信息
	 * @param proxy
	 * @param header
	 * @param seasonModel
	 * @return
	 */
	public List<SeasonModel> getHistorySubLeagueMess(Proxy proxy,
			Map<String, String> header, SeasonModel seasonModel);

	/**
	 * 抓取球探资料库杯赛分组赛历史记录
	 * @param header
	 * @param proxy
	 * @param seasonModel
	 * @return
	 */
	public List<SeasonModel> getFbCupHistoryGroupMess(
			Map<String, String> header, Proxy proxy, SeasonModel seasonModel);

	/**
	 * 抓取球探资料库杯赛赛程历史记录
	 * @param proxy 
	 * @param header 
	 * @param seasonModel
	 * @return
	 */
	public List<QtMatchBaseModel> crawlerCupHistoryMatchData(
			Map<String, String> header, Proxy proxy, SeasonModel seasonModel);
	
	
	
	/**
	 * 抓取赛事分析事件数据
	 * @param header
	 * @param proxy
	 * @param matchBaseModel
	 * @return
	 */
	public List<AwdataStore> crawlerMatchEventHasFinishedData(
			Map<String, String> header, Proxy proxy, String matchId,long Id);
	
	
	/**
	 * 抓取赛事分析阵容数据
	 * @param header
	 * @param proxy
	 * @param matchBaseModel
	 * @return
	 */
	public QtMatchLineupModel crawlerMatchLineupHasFinishedData(
			Map<String, String> header, Proxy proxy, String matchId,long Id);
	

	/**
	 * 抓取联赛积分信息
	 * @param header
	 * @param proxy
	 * @param seasonModel
	 * @return
	 */
	public Map<String, Object> getLeagueScoreAndRule(
			Map<String, String> header, Proxy proxy, SeasonModel seasonModel);

	/**
	 * 抓取球探即时比分全部数据
	 * @param header
	 * @param proxy
	 * @return
	 */
	public List<QtMatchBaseModel> getQtJishiBifenData(
			Map<String, String> header, Proxy proxy);

	/**
	 * 抓取球探赔率公司数据
	 * @param header
	 * @param proxy
	 */
	public List<QtOddsCompanyModel> crawlerOddsCompanyData(Map<String, String> header, Proxy proxy);

	/**
	 * 抓取球探足球欧赔数据
	 * @param header
	 * @param proxy
	 * @param bsId
	 * @param id
	 * @param companyId
	 * @return
	 */
	public List<QtMatchOpOddsModel> crawlerMatchOpOddFinishedData(
			Map<String, String> header, Proxy proxy, String bsId, long id,
			String companyId,Qt_fb_match_oddsType itemType);
	
	

	/**
	 * 抓取篮球即时比分数据
	 * @param proxy
	 * @param header
	 * @return
	 */
	public List<BasketBallMatchModel> crawlerBaskerBallJishiBifen(Proxy proxy,
			Map<String, String> header);

	/**
	 * 篮球联赛
	 * @param proxy
	 * @param header
	 * @return
	 */
	public List<BbLeagueInfoModel> getBasketLeagueInfo(Proxy proxy,
			Map<String, String> header);
	
	
	/**
	 * 抓取球探 篮球 赔率数据
	 * @param header
	 * @param proxy
	 * @param bsId
	 * @param id
	 * @param companyId
	 * @param itemType 赔率类型
	 * @return
	 */
	public List<QtBasketMatchOddsModel> crawlerBasketMatchOpOddFinishedData(
			Map<String, String> header, Proxy proxy, String bsId, long id,
			String companyId,Qt_fb_match_oddsType itemType);

	/**
	 * 杯赛积分历史
	 * @param header
	 * @param proxy
	 * @param seasonModel
	 * @return
	 */
	public List<FbLeagueScoreModel> getCupScore(Map<String, String> header,
			Proxy proxy, SeasonModel seasonModel);

	
	/**
	 * 抓取球探 篮球  球队统计数据
	 * @param header
	 * @param proxy
	 * @param bsId
	 * @param id
	 * @return
	 */
	public List<QtBasketMatchTeamStatisticModel> crawlerBasketMatchTeamStatisticHasFinishedData(
			Map<String, String> header, Proxy proxy, String bsId, long id);

	/**
	 * 抓取球探 篮球 球队队员技术统计数据
	 * @param header
	 * @param proxy
	 * @param bsId
	 * @param id
	 * @return
	 */
	public List<QtBasketMatchPlayerStatisticModel> crawlerBasketMatchPlayerStatisticHasFinishedData(
			Map<String, String> header, Proxy proxy, String bsId, long id);

	/**
	 * 竞彩即时比分
	 * @param header
	 * @param proxy
	 * @return
	 */
	public List<QtMatchBaseModel> getQtJingcaiJishiBifenData(
			Map<String, String> header, Proxy proxy);

	/**
	 * 竞彩比分
	 * @param proxy
	 * @param header
	 * @return
	 */
	public List<BasketBallMatchModel> crawlerBaskerBallJingCaiBifen(
			Proxy proxy, Map<String, String> header);

	/**
	 * 篮球资料库子联赛解析
	 * @param ballLeagueSeasonModel
	 * @param header
	 * @param proxy
	 * @param type 1解析子联赛，2解析轮数
	 * @return
	 */
	public List<BasketBallLeagueSeasonModel> parseLqSubLeagueSeason(
			BasketBallLeagueSeasonModel ballLeagueSeasonModel,
			Map<String, String> header, Proxy proxy,int type);

	/**
	 * 篮球联赛历史赛程记录
	 * @param proxy
	 * @param header
	 * @param basketBallLeagueSeasonModel
	 * @return
	 */
	public List<BasketBallMatchModel> crawlerBasketBallLeagueHistoryMatch(
			Proxy proxy, Map<String, String> header,
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel);

	/**
	 * 杯赛历史分组赛
	 * @param header
	 * @param proxy
	 * @param seasonModel
	 * @return
	 */
	public List<BasketBallLeagueSeasonModel> getBasketCupHistoryGroupMess(
			Map<String, String> header, Proxy proxy, BasketBallLeagueSeasonModel seasonModel);

	/**
	 * 杯赛历史赛程
	 * @param proxy
	 * @param header
	 * @param basketBallLeagueSeasonModel
	 * @return
	 */
	public Map<String, Object> parseLqCupMatch(Proxy proxy,
			Map<String, String> header,
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel);

	/**
	 * 联赛积分
	 * @param proxy
	 * @param header
	 * @param basketBallLeagueSeasonModel
	 * @return
	 */
	public List<BasketBallLeagueScoreModel> parseLqLeagueScore(Proxy proxy,
			Map<String, String> header,
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel);

	/**
	 * 发送联赛数据到服务器
	 * @param leagueInfoModels
	 * @throws IOException 
	 */
	public void sendFbLeague(List<LeagueInfoModel> leagueInfoModels) throws IOException;

	/**
	 * 
	 * @param source
	 * @param leagueType
	 * @return
	 * @throws IOException 
	 */
	public List<FbLeagueSeasonBasePO> queryAllSeasonMessageSubLeague(
			Integer source, int leagueType) throws IOException;

	public void sendFbSubLeague(List<SeasonModel> subLeagueOfOneSeaon,
			Integer leagueType) throws IOException;

	/**
	 * 获取所有未抓取过的联赛赛程数据
	 * @param source
	 * @return
	 * @throws IOException 
	 */
	public List<FbLeagueSeasonBasePO> getAllSeasonMessageNotCrawler(
			Integer source) throws IOException;

	/**
	 * 发送抓取的足球赛程数据到Server
	 * @param qtMatchBaseModels
	 * @param round 当前第几轮
	 * @param seasonId 
	 * @throws IOException 
	 */
	public void sendMatchBaseMessage(List<QtMatchBaseModel> qtMatchBaseModels,
			int round, Integer seasonId) throws IOException;

	public List<FbLeagueSeasonBasePO> gotAllCupGroupMessToCraw(Integer source,
			Integer cupType) throws IOException;

	public String gotNowSeasonNameByLeagueId(String leagueId) throws IOException;

	/**
	 * 发送杯赛赛程数据
	 * @param qtMatchBaseModels
	 * @param seasonId 
	 * @throws IOException 
	 */
	public void sendCupMatchInfo(List<QtMatchBaseModel> qtMatchBaseModels, Integer seasonId) throws IOException;

	public List<FbMatchBaseInfoPO> gotAllMatchDataHasFinish() throws IOException;

	
	public void sendMatchLineupData(QtMatchLineupModel qtMatchLineupModel) throws IOException;

	public List<FbLeagueSeasonBasePO> gotAllSeasonNotHaveRule(int leagueType,
			Integer source) throws IOException;

	public void sendLeagueScoreData(Map<String, Object> scoreRuleMap,
			SeasonModel seasonModel) throws IOException;

	public List<FbLeagueSeasonBasePO> gotAllCupSeasonNotCrawler(int i, int j,
			Integer source) throws IOException;

	public void sendCupScoreData(List<FbLeagueScoreModel> fbLeagueScoreModels,
			SeasonModel seasonModel) throws IOException;

	public void sendFbAllMatchData(List<QtMatchBaseModel> qtMatchBaseModels) throws IOException;

	/**
	 * 
	 * @param ballMatchModels
	 * @param isJingcai 是否竞彩比分
	 * @throws IOException 
	 */
	public void sendLqJishiMatchInfo(
			List<BasketBallMatchModel> ballMatchModels, boolean isJingcai) throws IOException;

	public void sendBbLeague(List<BbLeagueInfoModel> leagueInfoModels) throws IOException;

	/**
	 * 
	 * @param source
	 * @param isSubLeague 是否子联赛 0是，1否
	 * @param leagueType 联赛类型  1联赛 2杯赛
	 * @return
	 * @throws IOException 
	 */
	public List<BasketBallLeagueSeasonPO> gotAllLqSubLeagueNotCrawler(int source,
			int isSubLeague, int leagueType) throws IOException;

	public void sendLqHistoryMatch(
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel,
			List<BasketBallMatchModel> ballMatchModels) throws IOException;

	public List<BasketBallLeagueSeasonPO> gotAllLqLeagueSeasonNotSub(
			Integer source, Integer leagueType) throws IOException;

	public void sendLqCupGroup(
			List<BasketBallLeagueSeasonModel> seasonModels, Integer cupType) throws IOException;

	public void sendLqSubLeague(
			List<BasketBallLeagueSeasonModel> basketBallLeagueSeasonModels) throws IOException;

	public void sendCupMatch(
			BasketBallLeagueSeasonModel basketBallLeagueSeasonModel,
			Map<String, Object> basketBallMatchAndLeagueScore) throws IOException;
	
	/**
	 * 获取为开赛的竞彩赛程
	 * @return
	 * @throws IOException 
	 */
	List<FbMatchBaseInfoPO> gotAllJingcaiZqMatchNotStart() throws IOException;

	/**
	 * 
	 * @param zqCompanyOddsHandle
	 * @param header 
	 * @return
	 */
	public FbMatchOpOddsInfoPO crawlerFbMatchOpOddsOneCompany(
			CrawlerFbZqCompanyOddsHandle zqCompanyOddsHandle, Map<String, String> header);

	public void sendFbMatchOpOneCompanyOddsToServer(
			FbMatchOpOddsInfoPO fbMatchOpOddsInfoPOs,
			CrawlerFbZqCompanyOddsHandle zqCompanyOddsHandle) throws IOException;

	public List<QtMatchOpOddsModel> getQtFbJishizhishuOddsDetails(
			Map<String, String> header, Qt_fb_match_oddsType ou, String corpId,
			FbMatchBaseInfoPO fbMatchBaseInfoPO); 

	public List<QtMatchBaseModel> getQtMatchData(Map<String, String> header,Proxy proxy);

	public List<QtMatchBaseModel> getCaikeMatchData(Map<String, String> header,
			Object object, String jingcaiId) throws NoSuchAlgorithmException, UnsupportedEncodingException;
}
