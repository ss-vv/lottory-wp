package com.unison.lottery.weibo.msg.service.johm;

import java.util.List;

import org.springframework.stereotype.Component;

import redis.clients.johm.JOhm;

import com.unison.lottery.weibo.msg.model.Comment;
import com.unison.lottery.weibo.msg.model.Post;
import com.unison.lottery.weibo.msg.model.PostComments;
import com.unison.lottery.weibo.msg.model.UserComments;
import com.unison.lottery.weibo.msg.model.UserPosts;
import com.unison.lottery.weibo.msg.model.UserRelationship;
import com.unison.lottery.weibo.msg.model.WeiboUser;
import com.unison.lottery.weibo.msg.service.johm.exception.WeiboRuntimeException;

@Component
public class WeiboServiceImpl implements WeiboService {

	@Override
	public void createWeiboUser(WeiboUser weiboUser) {
		JOhm.save(weiboUser);
	}

	@Override
	public void updateWeiboUser(WeiboUser weiboUser) {
		JOhm.save(weiboUser);
	}

	@Override
	public WeiboUser findWeiboUser(long weiboUserId) {
		return JOhm.get(WeiboUser.class, weiboUserId);
	}

	@Override
	public UserRelationship findRelationship(WeiboUser weiboUser) {
		List<UserRelationship> relation = JOhm.find(UserRelationship.class, "host", weiboUser.getId());
		if (relation == null || relation.size() != 1) {
			throw new WeiboRuntimeException("User has more than 1 relationship!");
		}
		return relation.get(0);
	}

	@Override
	public void following(WeiboUser me, WeiboUser followed) {
		List<UserRelationship> meRelation = JOhm.find(UserRelationship.class, "host", me.getWeiboUid());
	}

	@Override
	public void unFollowing(WeiboUser me, WeiboUser following) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createPost(Post post) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePost(Post post) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePost(long postId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Post findPost(long postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Post forwardPost(WeiboUser user, long postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserPosts findUserPosts(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment createComment(long authorId, long commentedPostId,
			String comment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Comment createReply(long authorId, long commentId, String reply) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateComment(Comment commentOrReply) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteComment(long commentOrReplyId) {
		// TODO Auto-generated method stub

	}

	@Override
	public PostComments findPostComments(long postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserComments findUserComments(long weiboUserId) {
		// TODO Auto-generated method stub
		return null;
	}

}
