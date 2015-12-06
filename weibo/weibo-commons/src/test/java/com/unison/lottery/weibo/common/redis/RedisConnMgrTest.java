package com.unison.lottery.weibo.common.redis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

/**
 * @desc RedisConnMgr的简单测试类
 * @author lei.li@unison.net.cn
 * @createTime 2013-10-22
 * @version 1.0
 */
public class RedisConnMgrTest {

	private static Logger logger = LoggerFactory
			.getLogger(RedisConnMgrTest.class);

	@Test
	public void getToProxy() {
		String host = "122.226.122.47";
		String password = null;
		int port = 22123;
		int timeout = 5000;
		RedisConnMgr redisConnMgr = new RedisConnMgr();
		redisConnMgr.initConnPool(host, port, password, timeout);
		Jedis jedis = redisConnMgr.getResource();
		logger.info("通过twemproxy代理连接redis实例获取数据...");
		load(jedis);
	}

	@Test
	public void setToProxy() {
		String host = "122.226.122.47";
		String password = null;
		int port = 22123;
		int timeout = 5000;
		RedisConnMgr redisConnMgr = new RedisConnMgr();
		redisConnMgr.initConnPool(host, port, password, timeout);
		Jedis jedis = redisConnMgr.getResource();
		logger.info("通过twemproxy代理连接redis实例存数据...");
		insert(jedis);
	}

	private static void insert(Jedis jedis) {
		long start = 0;
		int interval = 1;
		for (int i = 0; i < 10; i++) {
			jedis.set("test:redis:ccid:" + i, "" + i);
			if (i == 0) {
				start = System.currentTimeMillis();
			}
			if (i >= interval && i % interval == 0) {
				logger.info("总插入了{}条数据.", new Object[] { i });
				long end = System.currentTimeMillis();
				logger.info("\t\t每{}条耗时:{}", new Object[] { interval,
						(end - start) });
				start = System.currentTimeMillis();
			}
		}
	}

	private static void load(Jedis jedis) {
		long start = 0;
		int interval = 1;
		for (int i = 0; i < 10; i++) {
			jedis.get("test:redis:ccid:" + i);
			if (i == 0) {
				start = System.currentTimeMillis();
			}
			if (i >= interval && i % interval == 0) {
				logger.info("总加载了{}条数据.", new Object[] { i });
				long end = System.currentTimeMillis();
				logger.info("\t\t每{}条耗时:{}", new Object[] { interval,
						(end - start) });
				start = System.currentTimeMillis();
			}
		}
	}

}