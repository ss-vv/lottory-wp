package com.unison.lottery.weibo.data.crawler.service.store.service;

import java.util.List;

import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;

/**
 * 抓取球探网数据并存储
 * @author 彭保星
 *
 * @since 2014年10月28日上午11:33:10
 */
public interface CrawlerZqDataStoreService {
	/**
	 * 球探网资料库联赛及赛季历史数据抓取并存储
	 */
	public void crawlerFbLeagueDataAndStore();

	/**
	 * 抓取球探网的比赛的历史
	 */
	public void crawlerFbMatchDataAndStore();

	/**
	 * 抓取并存储球探赛季子联赛数据，完善联赛数据信息，为抓取比赛数据做准备
	 */
	public void crawlerFbSubLeagueSeasonMess();

	void storeService(List<SeasonModel> subLeagueOfOneSeaon);

	void crawlerAndStoreSeasonSubMesaage(List<SeasonModel> seasonModels);

	/**
	 * 抓取杯赛的数据并存储
	 */
	public void crawlerFbCupMatchDataAndStore();

	/**
	 * 抓取杯赛小组信息
	 */
	public void crawlerFbCupGroupDataAndStore();

	/**
	 * 抓取联赛积分榜数据
	 */
	public void crawlerFbLeagueScoreDataAndStore();

	/**
	 * 球探足球即时比分
	 */
	public void crawlerQtJishiBifen();
	/**
	 * 抓取对阵事件的数据并存储
	 */
	public void crawlerFbMatchEventDataAndStore();
	
	/**
	 * 抓取对阵阵容的数据并存储
	 */
	public void crawlerFbMatchLineupDataAndStore();

	/**
	 * 抓取指数公司
	 */
	public void crawlerFbOddsCompany();
	
	/**
	 * 抓取足球比赛赔率
	 */
	public void crawlerFbMatchOdds(Qt_fb_match_oddsType oddsType);

	/**
	 * 杯赛积分榜数据
	 */
	public void crawlerCupScoreDataAndStore();

	/**
	 * 竞彩足球数据抓取
	 */
	public void crawlerFbJingcaiJishi();

	/**
	 * 
	 * @param ou
	 */
	public void crawlerFbMatchOddsDetail(Qt_fb_match_oddsType ou);
	
	public void crawlerQtMatch();

}
