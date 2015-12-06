package com.unison.lottery.weibo.uc.persist;

import java.util.Set;
import com.unison.lottery.weibo.common.nosql.BaseDao;

/**
 * @desc 提供球队“赞”数据服务
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-20
 * @version 1.0
 */
public interface MatchTeamPraiseDao extends BaseDao<Object> {

	/**
	 * 增加对于某只球队的赞
	 * @param qtMatchId
	 * @param teamId
	 * @param lotteryId <pre>对应LotteryBall的value</pre>
	 * @param weiboUserId
	 * @return
	 */
	Long addPraise(long qtMatchId, long teamId, long weiboUserId, String lottery);
	
	/**
	 * 查询对某支球队“赞”的微博用户
	 * @param qtMatchId
	 * @param teamId
	 * @param lotteryId <pre>对应LotteryBall的value</pre>
	 * @param min
	 * @param max
	 * @return
	 */
	Set<String> findTeamPraisedWeiboUser(long qtMatchId, long teamId, String lotteryId, 
			String min, String max);
	
	/**
	 * 按score倒排获取count条数据
	 * @param qtMatchId
	 * @param teamId
	 * @param lotteryId <pre>对应LotteryBall的value</pre>
	 * @param count
	 * @return
	 */
	Set<String> findTeamPraisedWeiboUser(long qtMatchId, long teamId, String lotteryId, int count);
	
	/**
	 * 查看指定比赛中的某只球队被多少微博用户“赞”过
	 * @param qtMatchId
	 * @param teamId
	 * @param lotteryId <pre>对应LotteryBall的value</pre>
	 * @return
	 */
	long countTeamPraiseWeiboUser(long qtMatchId, long teamId, String lotteryId);

	long delPraise(long qtMatchId, long teamId, long weiboUserId, String lottery);
}