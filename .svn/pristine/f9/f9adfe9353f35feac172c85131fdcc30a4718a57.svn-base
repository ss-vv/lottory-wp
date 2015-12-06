package com.unison.lottery.weibo.msg.persist.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.data.Comment;
import com.unison.lottery.weibo.msg.persist.dao.CommentDao;

/**
 * 评论及回复DAO实现。
 * @author Yang Bo
 */
@Repository
public class CommentDaoImpl extends BaseDaoImpl<Comment> implements CommentDao {
	
	@Override
	public void addToAllComments(String ownerId, String commentId) {
		String key = Keys.allCommentsOfUidKey(ownerId);
		lpush(key, commentId);
	}

	@Override
	public void deleteFromAllComments(String commentedUserId, String commentId) {
		String key = Keys.allCommentsOfUidKey(commentedUserId);
		lrem(key, 0, commentId);
	}

	@Override
	public void update(Comment comment) {
		String key = Keys.commentKey(""+comment.getId());
		hashAdd(key, comment);
	}

	@Override
	public int totalCommentsOfPost(String postId){
		return zcard(Keys.commentsOfPostKey(postId)).intValue();
	}
	
	@Override
	public List<String> getCommentsOfPost(String postId) {
		String key = Keys.commentsOfPostKey(postId);
		return zrangeList(key, 0, -1);
	}

	@Override
	public void praise(String commentId, String uid, boolean isAdd) {
		if (isAdd) {
			sadd(Keys.praiseCommentUserKey(commentId), uid);
		}else{
			srem(Keys.praiseCommentUserKey(commentId), uid);
		}
	}

	@Override
	public List<Comment> allCommentsOfUser(String uid, int start, int length) {
		String key = Keys.allCommentsOfUidKey(uid);
		List<String> commentIds = lrange(key, start, start+length-1);
		return get(commentIds.iterator());
	}

	@Override
	public List<Comment> directRepliesOfUser(String uid, int start, int length) {
		String key = Keys.directRepliesKey(uid);
		List<String> commentIds = lrange(key, start, start+length-1);
		return get(commentIds.iterator());
	}

	@Override
	public List<Comment> followingCommentsOfUser(String uid, int start, int length) {
		String key = Keys.followingCommentsKey(uid);
		List<String> commentIds = lrange(key, start, start+length-1);
		return get(commentIds.iterator());
	}

	@Override
	public void addToCommentsOfPost(String postId, String commentId) {
		String key = Keys.commentsOfPostKey(postId);
		zadd(System.currentTimeMillis(), key, String.valueOf(commentId));
	}

	@Override
	public void deleteFromCommentsOfPost(String weiboId, String commentId) {
		String key = Keys.commentsOfPostKey(weiboId);
		zrem(key, String.valueOf(commentId));
	}
	
	@Override
	public long countAllComments(long uid) {
		String key = Keys.allCommentsOfUidKey(""+uid);
		return llen(key);
	}

	@Override
	public long countDirectReplies(long uid) {
		String key = Keys.directRepliesKey(""+uid);
		return llen(key);
	}

	@Override
	public long countFollowingComments(long uid) {
		String key = Keys.followingCommentsKey(""+uid);
		return llen(key);
	}

	@Override
	public void addToDirectReplies(long repliedUserId, long commentId) {
		String key = Keys.directRepliesKey(""+repliedUserId);
		lpush(key, ""+commentId);
	}

	@Override
	public void deleteFromDirectReplies(long commentedUserId, long commentId) {
		String key = Keys.directRepliesKey("" + commentedUserId);
		lrem(key, 0, ""+commentId);
	}

	@Override
	public void addToFollowingComments(long userId, long commentId) {
		String key = Keys.followingCommentsKey(""+userId);
		lpush(key, ""+commentId);
	}

	@Override
	public void deleteFollowingComments(long commentedUserId, long commentId) {
		String key = Keys.followingCommentsKey(""+commentedUserId);
		lrem(key, 0, ""+commentId);
	}

	@Override
	public void addToMyComments(long commentedUserId, long commentId) {
		String key = Keys.myComments(""+commentedUserId);
		lpush(key, ""+commentId);
	}

	@Override
	public void deleteFromMyComments(long userId, long commentId) {
		String key = Keys.myComments("" + userId);
		lrem(key, 0, ""+commentId);
	}
	
	@Override
	public List<Comment> myComments(String uid, int start, int length) {
		String key = Keys.myComments(uid);
		List<String> commentIds = lrange(key, start, start+length-1);
		return get(commentIds.iterator());
	}

	@Override
	public long countMyComments(long uid) {
		String key = Keys.myComments(""+uid);
		return llen(key);
	}

}
