package com.unison.lottery.weibo.web.service;

import java.util.List;

import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboUser;

/**
 * 微博信息服务
 * @author Wang Lei
 *
 */
public interface InfoService {

	/**
	 * 查找关注好友微博聚合
	 * @param userId
	 */
	public List<WeiboMsg> findFollowMsg(long userId);
	
	/**
	 * 根据最后微博id,查找关注好友最新微博
	 * @param userId
	 * @param lastWeiboMsgId
	 */
	public List<WeiboMsg> findFollowNewMsg(long userId, long lastWeiboMsgId);
	
	/**
	 * 热门用户排行榜
	 * @param topNumber 默认10, 不能小余0
	 * @return
	 */
	public List<WeiboUser> hotUserTop(int topNumber);
}
