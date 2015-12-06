package com.unison.lottery.weibo.msg.persist.dao;

import java.util.List;

import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.data.Comment;



/**
 * 评论及回复的 DAO
 * 
 * @author Yang Bo
 */
public interface CommentDao extends BaseDao<Comment>{

	/**
	 * 添加评论id到"我的评论"列表。
	 * @param userId 我的评论列表属于的用户id
	 * @param commentId 评论id
	 */
	void addToAllComments(String userId, String commentId);

	/**
	 * 添加评论id到用户的“直接回复”列表。
	 * @param repliedUserId 被直接回复的用户id
	 * @param commentId 评论id
	 */
	void addToDirectReplies(long repliedUserId, long commentId);
	
	/**
	 * 添加评论id到用户的“我关注的人”列表。
	 * @param userId 用户id
	 * @param commentId 评论id，是user关注的人给我的评论
	 */
	void addToFollowingComments(long userId, long commentId);
	
	/**
	 * 更新评论内容
	 * @param comment
	 */
	void update(Comment comment);

	/**
	 * 获取微博的评论列表
	 */
	List<String> getCommentsOfPost(String postId);

	/**
	 * 赞评论.
	 * @param commentId 评论id
	 * @param uid 用户id
	 * @param isAdd true 加一；false 减一
	 */
	void praise(String commentId, String uid, boolean isAdd);

	/**
	 * 列出用户的所有评论.
	 * @param uid
	 * @param start
	 * @param length
	 * @return
	 */
	List<Comment> allCommentsOfUser(String uid, int start, int length);

	/**
	 * 列出用户的直接回复评论列表。
	 * @param uid
	 * @param start
	 * @param length
	 * @return
	 */
	List<Comment> directRepliesOfUser(String uid, int start, int length);

	/**
	 * 列出用户的所有关注用户给的评论
	 * @param uid
	 * @param start
	 * @param length
	 * @return
	 */
	List<Comment> followingCommentsOfUser(String uid, int start, int length);

	/**
	 * 添加到微博的评论列表。
	 * @param postId 微博id
	 * @param commentId 评论id
	 */
	void addToCommentsOfPost(String postId, String commentId);

	/**
	 * 微博的总评论数。
	 * @param postId 微博id
	 * @return 总评论数
	 */
	int totalCommentsOfPost(String postId);

	/**
	 * 评论某用户的所有“评论或回复”。
	 * @param uid 大V彩微博用户id
	 * @return 总数
	 */
	long countAllComments(long uid);

	/** 直接回复总数 */
	long countDirectReplies(long uid);

	/** 我关注的人的回复总数 */
	long countFollowingComments(long uid);

	/**
	 * 添加到我的评论列表。
	 * @param commentedUserId 用户id
	 * @param commentId 评论id
	 */
	void addToMyComments(long commentedUserId, long commentId);

	/**
	 * 分页列出“我的评论”。
	 * @param uid 用户id
	 * @param start 开始位置，从0开始
	 * @param length 长度
	 * @return 评论列表
	 */
	List<Comment> myComments(String uid, int start, int length);

	/**
	 * “我的评论”总数。
	 * @param uid 用户id
	 * @return 总数
	 */
	long countMyComments(long uid);

	/**
	 * 从微博的评论列表中删除
	 * @param weiboId
	 * @param commentId
	 */
	void deleteFromCommentsOfPost(String weiboId, String commentId);

	/**
	 * 从被评论者的"评论我的"列表中删除
	 * @param commentedUserId 被评论的用户id
	 * @param commentId 评论id
	 */
	void deleteFromAllComments(String commentedUserId, String commentId);

	/**
	 * 从“直接回复”列表删除
	 * @param commentedUserId 被直接回复的用户id
	 * @param commentId 评论id
	 */
	void deleteFromDirectReplies(long commentedUserId, long commentId);

	/**
	 * 从“我关注的人”列表删除
	 * @param commentedUserId 被评论的人id，此人是我关注的人。
	 * @param commentId 评论id
	 */
	void deleteFollowingComments(long commentedUserId, long commentId);

	/**
	 * 从“我的评论”列表删除
	 * @param userId 评论作者的用户id 
	 * @param commentId 评论id
	 */
	void deleteFromMyComments(long userId, long commentId);
}
