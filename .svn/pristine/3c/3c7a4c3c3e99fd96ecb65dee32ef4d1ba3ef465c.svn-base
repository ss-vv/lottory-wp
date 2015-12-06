package com.unison.lottery.weibo.web.service;

import com.unison.lottery.weibo.uc.data.TeamPraisedWeiboUserResult;

/**
 * @desc 对于比赛中对某只球队“赞”的服务
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-20
 * @version 1.0
 */
public interface MatchTeamPraiseService {

	/**
	 * 增加微博用户对于一场比赛中指定球队的“赞”
	 * @param matchId
	 * @param teamId
	 * @param lotteryId <pre>对应LotteryBall的value</pre>
	 * @param weiboUserId
	 * @return
	 */
	Long addPraise(String matchId, long teamId, String lotteryId, long weiboUserId);
	
	/**
	 * 查询球队被赞的微博用户集合，按点赞时间倒排，获取count条
	 * @param matchId
	 * @param teamId
	 * @param lotteryId <pre>对应LotteryBall的value</pre>
	 * @return
	 */
	TeamPraisedWeiboUserResult findTeamPraisedWeiboUser(String matchId, long teamId, String lotteryId, int count);
	
	/**
	 * 查询球队微博支持者的数量
	 * @param matchId
	 * @param teamId
	 * @param lotteryId <pre>对应LotteryBall的value</pre>
	 * @return
	 */
	long countTeamPraiseWeiboUser(String matchId, long teamId, String lotteryId);

	Long delPraise(String matchId, long teamId, String lotteryType,
			long weiboUserId);
}