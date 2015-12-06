package com.unison.lottery.weibo.common.redis;

import redis.clients.jedis.Jedis;

/**
 * @desc 由客户端实现其中的回调方法，获取redis实例对象从而进行相关的数据操作
 * @author lei.li@unison.net.cn
 * @createTime 2013-10-23
 * @version 1.0
 */
public interface RedisCallback {

	Object doInRedis(Jedis jedis);

}