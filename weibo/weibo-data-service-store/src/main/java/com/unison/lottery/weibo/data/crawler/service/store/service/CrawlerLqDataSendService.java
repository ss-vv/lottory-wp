package com.unison.lottery.weibo.data.crawler.service.store.service;

/**
 * 
 * @author baoxing.peng
 *
 * @since 2015年1月12日下午4:42:54
 */
public interface CrawlerLqDataSendService {

	/**
	 * 抓取篮球即时比分数据并发送至服务器
	 */
	void crawlerLqJishiBifenAndSend();

	void crawlerLqLeagueNowSeasonMessAndSend();

	void crawlerHistoryMatchAndSend();

	/**
	 * 杯赛分组赛
	 */
	void crawlerHistorySubCupAndSend();

	void crawlerHistorySubLeagueAndSend();

	void crawlerHistoryCupMatchAndSend();
	
	

}
