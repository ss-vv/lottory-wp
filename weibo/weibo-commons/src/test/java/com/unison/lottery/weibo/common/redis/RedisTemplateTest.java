package com.unison.lottery.weibo.common.redis;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;

/**
 * @desc RedisTemplate的doExecute方法测试，当前不存在主机配置，这里期望抛出
 * 
 *       <pre>
 * RedisException
 * </pre>
 * 
 *       异常
 * @author lei.li@unison.net.cn
 * @createTime 2013-10-23
 * @version 1.0
 */
public class RedisTemplateTest {
	//@Test(expected = RedisException.class)
	@Test
	public void testDoExecute() throws RedisException {
		UserDao userDao = new UserDao();
		setupRedisTemplate(userDao);
		userDao.saveUser();
	}

	private void setupRedisTemplate(UserDao userDao) throws RedisException {
		// 实际开发中通过Spring自动注入template
		RedisTemplate redisTemplate = new RedisTemplate("122.226.122.47", 22123);
		userDao.setRedisTemplate(redisTemplate);
	}
}

// 模拟DAO
class UserDao {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private RedisTemplate redisTemplate;

	public void saveUser() throws RedisException {
		redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				String key = "TestCase:tpl";
				String value = "redisTemplate+Callback";
				Object ret = jedis.set(key, value);
				if ("OK".equals(ret)) {
					logger.info("成功插入数据:key={},value={}", new Object[] { key,
							value });
				} else {
					logger.error("插入数据失败");
				}
				return ret;
			}
		});
	}

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}
