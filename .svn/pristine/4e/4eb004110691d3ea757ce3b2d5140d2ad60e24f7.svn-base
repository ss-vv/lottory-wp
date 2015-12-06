package com.unison.lottery.weibo.common.service;

import com.unison.lottery.weibo.common.data.TimeLineType;
import com.unison.lottery.weibo.data.WeiboMsg;

/**
 * 异步服务。
 * 
 * @author Yang Bo
 */
public interface AsyncService {

	/**
	 * 异步投递新微博到微博作者粉丝的指定时间线中。
	 * @param weibo 要通知的微博
	 * @param 时间线类型
	 */
	void notifyFollowers(WeiboMsg weibo, TimeLineType timeline);

	/**
	 * 关注彩种用户
	 * @param followerUid 彩种用户昵称
	 * @param followingUid TODO
	 */
	void followLotteryUser(long followerUid, long followingUid);
	
	/**
	 * 取消对彩种用户的关注
	 * @param followerUid 彩种用户昵称
	 * @param followingUid TODO
	 */
	void unFollowLotteryUser(long followerUid, long followingUid);
}
