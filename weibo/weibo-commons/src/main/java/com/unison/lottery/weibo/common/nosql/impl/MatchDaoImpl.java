package com.unison.lottery.weibo.common.nosql.impl;

import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.common.nosql.MatchDao;
import com.xhcms.lottery.lang.LotteryId;

@Repository
public class MatchDaoImpl extends BaseDaoImpl<String> implements MatchDao {

	@Override
	public long addToMatchTimeline(String matchIdStr, long postId, double score) {
		return zadd(score, Keys.matchDataKey(matchIdStr),
				String.valueOf(postId));
	}
	@Override
	public long addToMatchDiscussTimeline(String matchIdStr, long postId, double score) {
		return zadd(score, Keys.matchDiscussKey(matchIdStr),
				String.valueOf(postId));
	}
	
	@Override
	public long addToMatchRealTimeline(String matchIdStr, long postId, double score) {
		return zadd(score, Keys.matchRealDataKey(matchIdStr),
				String.valueOf(postId));
	}
	
	@Override
	public long addToRecomRealTimeline(long userId, long postId,
			double score) {
		Long rs = zadd(score, Keys.userSelfRecomRealsKey(userId+""),
				String.valueOf(postId));
		
		rs += zadd(score, Keys.userRecomRealsTimelineKey(userId+""),
				String.valueOf(postId));
		return rs;
	}

	@Override
	public long addToMatchRecommendLotteryTimeline(LotteryId lotteryId,
			long postId, double score) {
		return zadd(score, Keys.matchRecommendLottery(lotteryId.name()), postId+"");
	}
	@Override
	public long addToLotteryRealFollowTimeline(LotteryId lotteryId,
			long postId, double score) {
		String name = "";
		if(lotteryId == LotteryId.UNKNOWN){
			name = "ALL";
		} else {
			name = lotteryId.name();
		}
		return zadd(score, Keys.getLotteryRealFollowTimeline(name), postId+"");
	}
	@Override
	public long addToLotteryRecommendTimeline(LotteryId lotteryId,
			long postId, double score) {
		String name = "";
		if(lotteryId == LotteryId.UNKNOWN){
			name = "ALL";
		} else {
			name = lotteryId.name();
		}
		return zadd(score, Keys.getLotteryRecommendTimeline(name), postId+"");
	}
	
	@Override
	public long addToMatchGlobalTimeline(String matchIdStr, long postId,
			double score) {
		return zadd(score, Keys.matchAllContentKey(matchIdStr),
				String.valueOf(postId));
	}

	@Override
	public long addToMatchRecommendNumber(long qtMatchId, String lotteryId) {
		return incr(Keys.matchRecommend(qtMatchId, lotteryId));
	}

	@Override
	public long addToMatchShowSchemeNumber(long qtMatchId, String lotteryId) {
		return incr(Keys.matchShowScheme(qtMatchId, lotteryId));
	}
	
	@Override
	public long addToPublishRecomAndShowUsers(long qtMatchId, String lotteryId, long weiboUserId) {
		String key = Keys.matchShowRecommendUsers(qtMatchId, lotteryId);
		return zadd(Double.valueOf(System.currentTimeMillis()), key, weiboUserId+"");
	}

	@Override
	public String getMatchRecommendNumber(long qtMatchId, String lotteryId) {
		return getString(Keys.matchRecommend(qtMatchId, lotteryId));
	}

	@Override
	public String getMatchShowSchemeNumber(long qtMatchId, String lotteryId) {
		return getString(Keys.matchShowScheme(qtMatchId, lotteryId));
	}

	@Override
	public Long getPublishRecomAndShowUsers(long qtMatchId, String lotteryId) {
		return zcard(Keys.matchShowRecommendUsers(qtMatchId, lotteryId));
	}
}