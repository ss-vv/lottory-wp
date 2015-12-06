package com.unison.lottery.weibo.bbs.persist.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

import com.unison.lottery.weibo.bbs.persist.dao.WeiboUserDao;
import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.redis.RedisCallback;
import com.unison.lottery.weibo.common.redis.RedisTemplate;

@Repository
public class WeiboUserDaoImpl extends BaseDaoImpl<String> implements WeiboUserDao {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public String getWeiboUserIdByLotteryUserId(final Long lotteryUserId) {
		String weiboUserId = null;
		if (null != lotteryUserId) {
			weiboUserId = (String) redisTemplate.doExecute(new RedisCallback() {
				public String doInRedis(Jedis jedis) {
					String weiboUserId = jedis.get(Keys
							.getLotteryUserIdKey(lotteryUserId));
					return weiboUserId;
				}
			});
		}
		return weiboUserId;
	}
}
