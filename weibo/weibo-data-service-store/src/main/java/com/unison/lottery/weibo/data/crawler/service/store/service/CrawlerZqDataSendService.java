package com.unison.lottery.weibo.data.crawler.service.store.service;

import java.io.IOException;
import java.util.List;

import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;
import com.unison.lottery.weibo.dataservice.crawler.service.model.SeasonModel;

/**
 * 
 * @author baoxing.peng
 *
 * @since 2015年1月8日下午4:32:34
 */
public interface CrawlerZqDataSendService {

	void crawlerFbLeagueDataAndSend();

	void crawlerFbSubLeagueSeasonMessAndSend();

	void crawlerAndStoreSeasonSubMesaageAndSend(List<SeasonModel> seasonModels) throws IOException;

	void crawlerFbMatchDataAndSend();

	void crawlerFbCupGroupDataAndSend();

	void crawlerFbCupMatchDataAndSend();

	void crawlerFbMatchLineupDataAndSend();

	void crawlerFbLeagueScoreDataAndSend();

	void crawlerCupScoreDataAndSend();

	void crawlerQtJishiBifenAndSend();
	
	/**
	 * 抓取新接口足球欧赔
	 * @param euro
	 */
	public void crawlerFbEuroOddsNewAndSend(Qt_fb_match_oddsType euro);

}
