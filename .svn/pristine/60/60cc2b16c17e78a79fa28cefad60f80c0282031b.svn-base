package com.unison.lottery.weibo.common.nosql;

import com.xhcms.lottery.lang.LotteryId;

/**
 * @desc 比赛数据
 * @author lei.li@unison.net.cn
 * @createTime 2014-2-18
 * @version 1.0
 */
public interface MatchDao extends BaseDao<String>{
	
	/**
	 * 将微博加入到对应赛事时间线
	 * @param matchIdStr
	 * @param postId
	 * @param score
	 */
	long addToMatchTimeline(String matchIdStr, long postId, double score);
	
	/**
	 * 将微博加入到对应实单赛事时间线
	 * @param matchIdStr
	 * @param postId
	 * @param score
	 */
	long addToMatchRealTimeline(String matchIdStr, long postId, double score);
	
	/**
	* 将微博加入到个人“推荐/实单时间线”
	* @param matchIdStr
	* @param postId
	* @param score
	*/
	long addToRecomRealTimeline(long userId, long postId, double score);
	
	/**
	 * 将微博加入到分彩种的推荐时间线
	 * @param lotteryId	彩种枚举值
	 * @param postId	微博ID
	 * @param score
	 * @return
	 */
	long addToMatchRecommendLotteryTimeline(LotteryId lotteryId, long postId, double score);
	
	/**
	 * 将微博加入到对应比赛的‘赛事全部内容’时间线
	 * @param matchIdStr
	 * @param postId
	 * @param score
	 * @return
	 */
	long addToMatchGlobalTimeline(String matchIdStr, long postId, double score);
	
	/**
	 * 获取本场比赛发布推荐和晒单的人数
	 * @param qtMatchId
	 * @param lotteryId	<pre>对应LotteryBall的value</pre>
	 * @return
	 */
	Long getPublishRecomAndShowUsers(long qtMatchId, String lotteryId);

	/**
	 * 获取指定赛事推荐微博数
	 * @param qtMatchId
	 * @param lotteryId	<pre>对应LotteryBall的value</pre>
	 * @return
	 */
	String getMatchRecommendNumber(long qtMatchId, String lotteryId);
	
	/**
	 * 获取指定赛事晒单微博数
	 * @param qtMatchId
	 * @param lotteryId	<pre>对应LotteryBall的value</pre>
	 * @return
	 */
	String getMatchShowSchemeNumber(long qtMatchId, String lotteryId);
	
	/**
	 * 加入到比赛发布推荐和晒单的人列表
	 * @param qtMatchId
	 * @param lotteryId	<pre>对应LotteryBall的value</pre>
	 * @param weiboUserId
	 * @return
	 */
	long addToPublishRecomAndShowUsers(long qtMatchId, String lotteryId, long weiboUserId);
	
	/**
	 * 本场比赛推荐微博数+1
	 * @param qtMatchId
	 * @param lotteryId	<pre>对应LotteryBall的value</pre>
	 * @return
	 */
	long addToMatchRecommendNumber(long qtMatchId, String lotteryId);
	
	/**
	 * 本场比赛晒单微博数+1
	 * @param qtMatchId
	 * @param lotteryId	<pre>对应LotteryBall的value</pre>
	 * @return
	 */
	long addToMatchShowSchemeNumber(long qtMatchId, String lotteryId);

	long addToMatchDiscussTimeline(String mId, long postId, double score);

	long addToLotteryRealFollowTimeline(LotteryId lotteryId, long postId,
			double score);

	long addToLotteryRecommendTimeline(LotteryId lotteryId, long postId,
			double score);
}