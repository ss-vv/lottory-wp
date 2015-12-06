package com.unison.lottery.weibo.msg;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class NoSQLTestBase {

	@Autowired
//	private JedisPool jedisPool;
	
	/**
	 * 注意：会删除所连接redis的所有数据！千万别在正式环境上使用！
	 */
    @Before
    public void startUp() {
//    	Jedis jedis = jedisPool.getResource();
//    	jedis.flushAll();
    }
}
