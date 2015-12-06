package com.unison.lottery.weibo.common.nosql;

import com.unison.lottery.weibo.data.WeiboMsg;

/**
 * @desc 微博通知的数据库操作
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-5
 * @version 1.0
 */
public interface NotificationDao extends BaseDao<WeiboMsg>{
	
	/**
	 * 将新粉丝的ID加入到用户的新粉丝时间线
	 * @param uid 微博用户ID
	 * @param beFollowedUids 被关注者ID
	 */
	void haveNewFollowers(Long uid, String ... beFollowedUids);
	
	/**
	 * 有未读提到我的@信息
	 * @param uid 微博提到的用户ID
	 * @param postId 提到我的微博ID
	 */
	void haveUnreadMetions(Long uid, Long postId);
	
	/**
	 * 有未读私信我的
	 * @param uid
	 * @param privateMsgId 私信ID
	 */
	void haveUnreadPrivateMsgs(Long uid, Long privateMsgId);
	
	/**
	 * 有未读评论我的微博信息
	 * @param uid	用户ID
	 * @param commenttId 评论ID
	 */
	void haveUnreadCommentions(Long uid, Long commenttId);
	
	/**
	 * 清除新粉丝时间线上的数据
	 * @param uid 微博用户ID
	 * @return
	 */
	Long clearNewFollowersTimeline(Long uid);
	
	/**
	 * 清除未读“提到我的”时间线数据
	 * @param uid 微博用户ID
	 * @return
	 */
	Long clearUnreadMetionsTimeline(Long uid);
	
	/**
	 * 清除未读“评论我的”时间线数据
	 * @param uid 微博用户ID
	 */
	Long clearUnreadCommentionsTimeline(Long uid);
	
	/**
	 * 清除未读“私信我的”时间线数据
	 * @param uid 微博用户ID
	 */
	Long clearUnreadPrivateMsgsTimeline(Long uid);
	
	/**
	 * 用户取消关注时从新增粉丝时间线中移除
	 * @param uid
	 * @param followedId
	 */
	void removeFollowerOfNewFollowers(Long uid, Long followedId);

	/**
	 * 用户未读新增粉丝数
	 * @param id
	 * @return
	 */
	Long countNewFollowers(Long id);

	/**
	 * 用户未读提到我的消息数量
	 * @param id
	 * @return
	 */
	Long countUnreadMetions(Long id);

	/**
	 * 用户未读评论我的消息数量
	 * @param id
	 * @return
	 */
	Long countUnreadComments(Long id);
	
	/**
	 * 用户未读私信数量
	 * @param id
	 * @return
	 */
	Long countUnreadPrivateMsgs(Long id);
}