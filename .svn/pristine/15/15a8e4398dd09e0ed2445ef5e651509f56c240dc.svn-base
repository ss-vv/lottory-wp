package com.unison.lottery.weibo.data.crawler.service.store.service;

import com.unison.lottery.weibo.dataservice.commons.crawler.constants.Qt_fb_match_oddsType;

/**
 * @author 彭保星
 *
 * @since 2014年12月1日下午6:06:22
 */
public interface JishiBifenDataStoreService {
	/**
	 * 足球竞彩即时比分
	 */
	public void crawlerQtJishiBifen();

	/**
	 * 篮球竞彩即时
	 */
	public void crawlerQtLqJishiBifen();

	/**
	 * 竞彩足球即时赔率
	 * @param euro
	 */
	public void crawlerFbMatchOdds(Qt_fb_match_oddsType euro);

	/*
	 * 竞彩篮球即时赔率
	 */
	public void crawlerLqMatchOdds(Qt_fb_match_oddsType euro);

	/**
	 * 即时事件
	 */
	public void crawlerFbMatchEventDataAndStore();

	/**
	 * 竞彩篮球即时球员统计
	 */
	public void crawlerBasketMatchPlayerStatistic();

	/**
	 * 竞彩篮球即时球队统计
	 */
	public void crawlerBasketMatchTeamStatistic();

	/**
	 * 变化赔率
	 * @param euro
	 */
	public void crawlerOddsChange(Qt_fb_match_oddsType oddsType);

	/**
	 * 抓取足球竞彩即时比分并发送至服务器
	 */
	public void crawlerQtFbJishiBifenAndSend();

	/**
	 * 抓取篮球竞彩即时比分并发送至服务器
	 */
	public void crawlerQtLqJingcaiJishiBifenAndSend();

	/**
	 * 抓取赔率变化并发送至服务器
	 * @param oddsType
	 */
	public void crawlerOddsChangeAndSend(Qt_fb_match_oddsType oddsType);

	/**
	 * 抓取足球竞彩比赛事件
	 */
	public void crawlerFbJishiEventsAndSend();

	/**
	 * 
	 */
	public void crawlerBasketMatchPlayerStatisticAndSend();

	/**
	 * 
	 */
	public void crawlerBasketMatchTeamStatisticAndSend();

	public void crawlerLqMatchOddsAndSend(Qt_fb_match_oddsType oddsType);

	public void crawlerZqMatchLiveAndSend();

	public void crawlerZqMatchLiveAndStore();
}
