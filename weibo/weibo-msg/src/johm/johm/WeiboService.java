package com.unison.lottery.weibo.msg.service.johm;

import com.unison.lottery.weibo.msg.model.Comment;
import com.unison.lottery.weibo.msg.model.Post;
import com.unison.lottery.weibo.msg.model.PostComments;
import com.unison.lottery.weibo.msg.model.UserComments;
import com.unison.lottery.weibo.msg.model.UserPosts;
import com.unison.lottery.weibo.msg.model.UserRelationship;
import com.unison.lottery.weibo.msg.model.WeiboUser;
import com.unison.lottery.weibo.msg.service.johm.exception.WeiboServiceException;

/**
 * 微博相关服务
 * 
 * @author Yang Bo
 */
public interface WeiboService {

	// ---------- 微博用户 -----------
	void createWeiboUser(WeiboUser weiboUser);
	void updateWeiboUser(WeiboUser weiboUser);
	
	/**
	 * @return null if not found.
	 */
	WeiboUser findWeiboUser(long weiboUserId);
	
	UserRelationship findRelationship(WeiboUser weiboUser);
	
	/**
	 * 关注某人
	 * @param me 我
	 * @param following 要关注的人
	 * @throws WeiboServiceException 操作失败
	 */
	void following(WeiboUser me, WeiboUser following);
	
	/** 取消关注 */
	void unFollowing(WeiboUser me, WeiboUser following);
	
	// ---------- 微博 -----------
	
	/** 创建一条微博 */
	void createPost(Post post);
	void updatePost(Post post);
	void deletePost(long postId);
	Post findPost(long postId);
	
	/** 转发微博 */
	Post forwardPost(WeiboUser user, long postId);
	
	/** TODO: 翻页 */
	UserPosts findUserPosts(long userId);
	
	// ---------- 评论及回复 -----------
	Comment createComment(long authorId, long commentedPostId, String comment);
	Comment createReply(long authorId, long commentId, String reply);
	void updateComment(Comment commentOrReply);
	void deleteComment(long commentOrReplyId);
	
	/** TODO: 翻页 */
	PostComments findPostComments(long postId);
	
	/** TODO: 翻页 */
	UserComments findUserComments(long weiboUserId);
}
