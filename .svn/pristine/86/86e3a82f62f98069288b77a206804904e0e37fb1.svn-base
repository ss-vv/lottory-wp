package com.unison.lottery.weibo.common.service;

import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboNotice;

/**
 * @desc 微博系统通知服务
 * @author lei.li@unison.net.cn
 * @createTime 2014-3-5
 * @version 1.0
 */
public interface NotificationService {

	/**
	 * 将关注者的ID加入到用户的新增粉丝时间线
	 * @param uid
	 * @param beFollowedUids 被关注者ID
	 */
	void addNewFollowers(Long uid, String ... beFollowedUids);
	
	/**
	 * 将postId微博中提到的所有用户都增加“未读提到我的”通知
	 * @param uids
	 * @param postId
	 */
	void addUnreadMetions(String[] uids, Long postId);
	
	/**
	 * 通过微博找到微博作者，并将微博的评论通知加入到未读‘评论我的’的消息时间线上
	 * @param weiboOwnerId 微博ID
	 * @param commentId	评论ID
	 */
	void addUnreadCommentions(Long weiboOwnerId, Long commentId);
	
	/**
	 * 加入到未读‘私信我的’的消息时间线上
	 * @param weiboOwnerId 微博ID
	 * @param privateMsgId	私信ID
	 */
	void addUnreadPrivateMsgs(Long weiboOwnerId, Long privateMsgId);
	
	/**
	 * 将postId对应微博的“未读评论我的”中删除评论commentId
	 * @param postId
	 * @param commentId
	 */
	void deleteUnreadComment(Long postId, Long commentId);
	
	/**
	 * 从给定用户集合的微博未读的“提到我的”时间线中删除对应的postId
	 * @param users
	 * @param postId
	 */
	void deleteUnreadMetion(String[] users, Long postId);
	
	/**
	 * 清除新粉丝时间线上的数据
	 * @param uid 微博用户ID
	 */
	void clearNewFollowersTimeline(Long uid);
	
	/**
	 * 清除未读“提到我的”时间线数据
	 * @param uid 微博用户ID
	 */
	void clearUnreadMetionsTimeline(Long uid);
	
	/**
	 * 清除未读“评论我的”时间线数据
	 * @param uid 微博用户ID
	 */
	void clearUnreadCommentionsTimeline(Long uid);
	
	/**
	 * 清除未读“私信”时间线数据
	 * @param uid 微博用户ID
	 */
	void clearUnreadPrivateMsgsTimeline(Long uid);
	
	/**
	 * 用户取消关注时从新增粉丝时间线中移除
	 * @param uid
	 * @param followedIds 被关注者id
	 */
	void cancelFollow(Long uid, String ... followedIds);
	
	/**
	 * 判断当前微博是否提自己
	 * @param weiboMsg
	 */
	void isMetionSelf(WeiboMsg weiboMsg);
	
	/**
	 * 指定用户id的未读新粉丝数
	 * @param id
	 * @return
	 */
	Long countNewFollowers(Long id);
	
	/**
	 * 指定用户id的未读提到我的消息数
	 * @param id
	 * @return
	 */
	Long countUnreadMetions(Long id);
	
	/**
	 * 指定用户id的未读评论我的消息数
	 * @param id
	 * @return
	 */
	Long countUnreadComments(Long id);
	
	/**
	 * 查看给定id用户的微博通知状态信息
	 * @param id
	 * @return
	 */
	WeiboNotice lookStatus(Long id);
}