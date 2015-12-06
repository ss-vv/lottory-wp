package com.unison.lottery.weibo.msg.persist.dao.impl;

import org.springframework.stereotype.Repository;

import com.unison.lottery.newsextract.data.ExtractNews;
import com.unison.lottery.newsextract.lang.NewsType;
import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.msg.persist.dao.WeiboNewsDao;

@Repository
public class WeiboNewsDaoImpl extends BaseDaoImpl<ExtractNews> implements WeiboNewsDao{

	@Override
	public void addLotteryNewsTimeline(NewsType newsType, long postId,
			double score) {
		zadd(score, 
				Keys.getLotteryNewsTimelineKey(newsType.name()), 
				postId + "");
	}
	@Override
	public void addTeamNewsTimeline(String teamId, long postId, double score) {
		zadd(score, 
				Keys.getTeamNewsTimelineKey(teamId), 
				postId + "");
	}

}
