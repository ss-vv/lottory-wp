package com.unison.lottery.weibo.dataservice.crawler.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;
import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.commons.crawler.model.Proxy;
import com.unison.lottery.weibo.dataservice.crawler.parse.fenxi.AwdataStore;
import com.unison.lottery.weibo.dataservice.crawler.service.model.BasketBallMatchModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.OddsBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchOddsModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchPlayerStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtBasketMatchTeamStatisticModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchBaseModel;
import com.unison.lottery.weibo.dataservice.crawler.service.model.QtMatchOpOddsModel;

/**
 * @author 彭保星
 *
 * @since 2014年12月1日下午6:46:25
 */
public interface JishiDataParseService {

	List<QtMatchBaseModel> getQtFbJishiBifenData(Map<String, String> header);

	List<BasketBallMatchModel> crawlerBaskerBallJingCaiBifen(
			Map<String, String> header);
	/**
	 * 抓取竞彩即时赔率
	 * @param header
	 * @param proxy
	 * @param bsId
	 * @param oddsType 
	 * @return
	 */
	public List<OddsBaseModel> crawlerZqJingcaiMatchJishiOdds( Map<String, String> header,String bsId, Qt_fb_match_oddsType oddsType);

	/**
	 * 根据抓取到赔率id获取公司的赔率变化信息
	 * @param header
	 * @param oddsId
	 * @param oddsType 
	 * @return
	 */
	List<QtMatchOpOddsModel> crawlerJingcaiMatchHistoryData(
			Map<String, String> header, String oddsId, Qt_fb_match_oddsType oddsType);

	/**
	 * 
	 * @param header
	 * @param bsId
	 * @param id
	 * @return
	 */
	List crawlerMatchEventHasFinishedData(Map<String, String> header,
			String bsId, long id);

	/**
	 * 
	 * @param header
	 * @param bsId
	 * @param id
	 * @return
	 */
	List<QtBasketMatchPlayerStatisticModel> crawlerBasketMatchPlayerStatisticHasFinishedData(
			Map<String, String> header, String bsId, Integer id);

	/**
	 * 篮球球队统计竞彩即时数据
	 * @param header
	 * @param bsId
	 * @param id
	 * @return
	 */
	List<QtBasketMatchTeamStatisticModel> crawlerBasketMatchTeamStatisticData(
			Map<String, String> header, String bsId, Integer id);

	/**
	 * 即时赔率获取
	 * @param header
	 * @param oddsType
	 * @return
	 */
	List<QtMatchOpOddsModel> crawlerChangeOdds(Map<String, String> header,
			Qt_fb_match_oddsType oddsType);

	/**
	 * 篮球即时赔率抓取
	 * @param header
	 * @param bsId
	 * @param oddsType
	 * @param lastReponse 
	 * @return
	 */
	List<QtBasketMatchOddsModel> crawlerLqJingcaiMatchJishiOdds(
			Map<String, String> header, String bsId,
			Qt_fb_match_oddsType oddsType, String lastReponse);

	/**
	 * 发送抓取的数据到指定服务器
	 * @param matchBaseInfos
	 * @throws IOException 
	 */
	void sendZqJishiBifenData(List<QtMatchBaseModel> matchBaseInfos) throws IOException;

	/**
	 * 
	 * @param ballMatchModels
	 * @throws IOException 
	 */
	void sendLqjingcaiJishi(List<BasketBallMatchModel> ballMatchModels) throws IOException;

	/**
	 * 
	 * @param oddsModels
	 * @param oddsType
	 * @throws IOException 
	 */
	void sendZqJishiOdds(List<QtMatchOpOddsModel> oddsModels,
			Qt_fb_match_oddsType oddsType) throws IOException;

	/**
	 * 从server获取当前正在比赛的数据
	 * @return
	 * @throws IOException 
	 */
	List<FbMatchBaseInfoPO> gotAllJingcaiZqMatchInLive() throws IOException;

	/**
	 * 发送足球比赛事件
	 * @param qtMatchEventStatistics
	 * @throws IOException 
	 */
	void sendZqJishiEvent(List<AwdataStore> qtMatchEventStatistics) throws IOException;

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	List<BasketBallMatchModel> gotAllBasketMathcInlive() throws IOException;

	/**
	 * 
	 * @param qtMatchEventStatistics
	 * @throws IOException
	 */
	void sendBasketMatchPlayerStatisticData(
			List<QtBasketMatchPlayerStatisticModel> qtMatchEventStatistics) throws IOException;

	/**
	 * 
	 * @param qtMatchEventStatistics
	 * @throws IOException
	 */
	void sendMatchTeamStatisticData(
			List<QtBasketMatchTeamStatisticModel> qtMatchEventStatistics) throws IOException;
	
	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	List<BasketBallMatchModel> gotAllJingcaiLqMatchNotStart() throws IOException;

	/**
	 * 
	 * @param oddsModels
	 * @param oddsType
	 * @param matchBaseInfoPO
	 * @throws IOException
	 */
	void sendLqJishiOdds(List<QtBasketMatchOddsModel> oddsModels,
			Qt_fb_match_oddsType oddsType, BasketBallMatchModel matchBaseInfoPO) throws IOException;

	/**
	 * 抓取足球比赛直播地址
	 * @param bsId
	 * @param header 
	 * @return
	 */
	String crawlerZqLiveUrl(String bsId, Map<String, String> header);

	/**
	 * 
	 * @return
	 * @throws IOException
	 */
	List<FbMatchBaseInfoPO> gotAllZqMatchInMatchNotHaveLiveUrl() throws IOException;

	/**
	 * 
	 * @param televisonUrl
	 * @param id
	 * @throws IOException
	 */
	void sendZqLiveUrl(String televisonUrl, long id) throws IOException;

	/**
	 * 获取为开赛的竞彩赛程
	 * @return
	 * @throws IOException 
	 */
	List<FbMatchBaseInfoPO> gotAllJingcaiZqMatchNotStart() throws IOException;

	/**
	 * 篮球即时指数抓取
	 * @param header
	 * @param lastReponse
	 * @param corpId
	 * @return
	 */
	List<QtBasketMatchOddsModel> crawlerLqJingcaiMatchJishiOdds(
			Map<String, String> header, String lastReponse, String corpId);
}
