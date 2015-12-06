package com.unison.lottery.weibo.msg.persist.dao;

import com.unison.lottery.newsextract.lang.NewsType;

/**
 * 微博新闻模块
 * @author haoxiang.jiang@unison.com.cn
 * @date 2014-3-17 下午12:00:09
 */
public interface WeiboNewsDao {
	/**
	 * 添加彩种新闻
	 * @param newsType
	 * @param postId
	 * @param score
	 */
	void addLotteryNewsTimeline(NewsType newsType,long postId, double score);
	
	/**
	 * 添加球队新闻
	 * @param newsType
	 * @param postId
	 * @param score
	 */
	void addTeamNewsTimeline(String teamId,long postId, double score);
}
