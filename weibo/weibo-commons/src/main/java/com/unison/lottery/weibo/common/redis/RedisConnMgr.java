package com.unison.lottery.weibo.common.redis;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @desc redis连接池管理类，一般不直接使用；
 * 更好的做法是通过@Autowired注入<pre>RedisTemplate</pre>对象进行操作
 * 
 * @author lei.li@unison.net.cn
 * @createTime 2013-10-23
 * @version 1.0
 */
public class RedisConnMgr {

	private JedisPool pool;

	private JedisPoolConfig jedisConfig = new JedisPoolConfig();

	public JedisPool initConnPool(String host, int port, String password,
			int timeout) {
		if (null == pool) {
			if(StringUtils.isNotBlank(password)) {
				pool = new JedisPool(jedisConfig, host, port, timeout, password);
			} else {
				pool = new JedisPool(jedisConfig, host, port, timeout);
			}
		}
		return pool;
	}

	public Jedis getResource() {
		return null == pool ? null : pool.getResource();
	}

	public void returnResource(Jedis jedis) {
		if (null != pool) {
			pool.returnResource(jedis);
		}
	}

	protected void closePool() {
		if (null != pool) {
			pool.destroy();
		}
	}

	public JedisPoolConfig getJedisConfig() {
		return jedisConfig;
	}

	public void setJedisConfig(JedisPoolConfig jedisConfig) {
		this.jedisConfig = jedisConfig;
	}
}
