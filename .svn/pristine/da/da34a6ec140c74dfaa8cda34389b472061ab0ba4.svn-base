package com.unison.lottery.weibo.common.nosql.impl;

import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.common.nosql.NotificationDao;
import com.unison.lottery.weibo.common.redis.RedisConstant;
import com.unison.lottery.weibo.data.WeiboMsg;

@Repository
public class NotificationDaoImpl extends BaseDaoImpl<WeiboMsg> implements
		NotificationDao {

	@Override
	public void haveNewFollowers(Long uid, String... followingUid) {
		for (String fid : followingUid) {
			double score = new Long(System.currentTimeMillis()).doubleValue();
			zadd(score, Keys.haveNewFollowers(Long.parseLong(fid)), String.valueOf(uid));
		}
	}

	@Override
	public void haveUnreadMetions(Long uid, Long postId) {
		zadd(System.currentTimeMillis(), Keys.haveUnreadMentions(uid),
				String.valueOf(postId));
	}

	@Override
	public void haveUnreadCommentions(Long uid, Long commenttId) {
		zadd(System.currentTimeMillis(), Keys.haveUnreadComments(uid),
				String.valueOf(commenttId));
	}
	
	@Override
	public void haveUnreadPrivateMsgs(Long uid, Long privateMsgId) {
		zadd(System.currentTimeMillis(), Keys.haveUnreadPrivateMsgs(uid),
				String.valueOf(privateMsgId));
	}

	@Override
	public Long clearNewFollowersTimeline(Long uid) {
		return zremrangeByScore(Keys.haveNewFollowers(uid),
				RedisConstant.N_INFINIT, RedisConstant.INFINIT);
	}

	@Override
	public Long clearUnreadMetionsTimeline(Long uid) {
		return zremrangeByScore(Keys.haveUnreadMentions(uid),
				RedisConstant.N_INFINIT, RedisConstant.INFINIT);
	}

	@Override
	public Long clearUnreadCommentionsTimeline(Long uid) {
		return zremrangeByScore(Keys.haveUnreadComments(uid),
				RedisConstant.N_INFINIT, RedisConstant.INFINIT);
	}
	
	@Override
	public Long clearUnreadPrivateMsgsTimeline(Long uid) {
		return zremrangeByScore(Keys.haveUnreadPrivateMsgs(uid),
				RedisConstant.N_INFINIT, RedisConstant.INFINIT);
	}

	@Override
	public void removeFollowerOfNewFollowers(Long uid, Long followedId) {
		zrem(Keys.haveNewFollowers(followedId), String.valueOf(uid));
	}

	@Override
	public Long countNewFollowers(Long uid) {
		return zcard(Keys.haveNewFollowers(uid));
	}

	@Override
	public Long countUnreadMetions(Long uid) {
		return zcard(Keys.haveUnreadMentions(uid));
	}

	@Override
	public Long countUnreadComments(Long uid) {
		return zcard(Keys.haveUnreadComments(uid));
	}

	@Override
	public Long countUnreadPrivateMsgs(Long uid) {
		return zcard(Keys.haveUnreadPrivateMsgs(uid));
	}
}
