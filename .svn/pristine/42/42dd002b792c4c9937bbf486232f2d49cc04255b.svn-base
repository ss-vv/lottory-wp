package com.unison.lottery.weibo.uc.persist.impl;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.redis.RedisCallback;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.data.Relationship;
import com.unison.lottery.weibo.uc.persist.RelationshipDao;

@Repository
public class RelationshipDaoImpl extends BaseDaoImpl<Relationship> implements
		RelationshipDao {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RedisTemplate redisTemplate;

	@Override
	public String[] findFollowersByUid(final Long uid) {
		Relationship r = (Relationship)(redisTemplate.doExecute(new RedisCallback() {
			public Relationship doInRedis(Jedis jedis) {
				Set<String> followersSet = jedis.zrevrange((Keys.getFollowerKey(uid)),0,-1);
				Relationship relationship = new Relationship();
				relationship.setFollowers(followersSet.toArray(new String[followersSet.size()]));
				return relationship;
			}
		}));
		return r.getFollowers();
	}

	@Override
	public String[] findFollowingByUid(final Long uid) {
		Relationship r = (Relationship)(redisTemplate.doExecute(new RedisCallback() {
			public Relationship doInRedis(Jedis jedis) {
				Set<String> followingsSet = jedis.zrevrange(Keys.getFollowingKey(uid),0,-1);
				Relationship relationship = new Relationship();
				relationship.setFollowings(followingsSet.toArray(new String[followingsSet.size()]));
				return relationship;
			}
		}));
		return r.getFollowings();
	}

	@Override
	public void saveFollower(final Long followerUid,
			final String... beFollowUids) {
		redisTemplate.doExecute(new RedisCallback() {
			public Long doInRedis(Jedis jedis) {
				String key = Keys.getFollowingKey(followerUid);
				String[] value = beFollowUids;
				long score = new Date().getTime();
				for (String val : value) {
					jedis.zadd(key, score, val);
				}
				logger.info("关注关系：插入:key={},value={}", new Object[] { key,
						ToStringBuilder.reflectionToString(value) });
				for (int i = 0; i < beFollowUids.length; i++) {
					key = Keys.getFollowerKey(Long.valueOf(beFollowUids[i]));
					jedis.zadd(key, score, followerUid.toString());
					logger.info("关注关系：插入:key={},value={}", new Object[] { key,
							ToStringBuilder.reflectionToString(value) });
				}
				return 1L;
			}
		});
	}

	@Override
	public void deleteFollower(final Long followerUid,
			final String... beFollowUids) {
		redisTemplate.doExecute(new RedisCallback() {
			public Long doInRedis(Jedis jedis) {
				jedis.zrem(Keys.getFollowingKey(followerUid), beFollowUids);
				for (int i = 0; i < beFollowUids.length; i++) {
					jedis.zrem(
							Keys.getFollowerKey(Long.valueOf(beFollowUids[i])),
							followerUid.toString());
				}
				return 1L;
			}
		});
	}

	@Override
	public boolean isFollowing(final String weiboUserId, final String followingId) {
		Object o = redisTemplate.doExecute(new RedisCallback() {
			public Boolean doInRedis(Jedis jedis) {
				if(null != jedis.zrank((Keys.getFollowingKey(Long.parseLong(weiboUserId))), followingId)){
					return new Boolean(true);
				} else {
					return new Boolean(false);
				}
			}
		});
		if(null == o){
			return false;
		}
		return ((Boolean)o).booleanValue();
	}

}
