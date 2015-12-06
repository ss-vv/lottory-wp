package com.unison.lottery.weibo.common.redis;

import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

/**
 * @desc 用于提供redis的连接，方便redis连接获取和资源回收管理；方便进行数据操作，默认情况下只需指定"主机名"；
 * 若有参数变更可参考JedisPool修改此类的无参构造方法即可实现扩展
 * 实际开发中可通过Spring管理该对象，代码中直接<pre>@Autowired</pre>注入即可使用
 * 具体测试用例可以看这里：<pre>com.unison.lottery.weibo.common.redis.RedisTemplateTest</pre>
 * 
 * @author lei.li@unison.net.cn
 * @createTime 2013-10-23
 * @version 1.0
 */
public class RedisTemplate {

	private String host;

	private int port = Protocol.DEFAULT_PORT;

	private String password;

	//private int timeout = Protocol.DEFAULT_TIMEOUT;
	private int timeout = 10000;

	private JedisPoolConfig jedisConfig;

	private RedisConnMgr redisConnMgr;

	private JedisPool jedisPool;

	public RedisTemplate() throws RedisException {
		init();
	}

	public RedisTemplate(String host, int port) throws RedisException {
		this.host = host;
		this.port = port;
		init();
	}
	
	public void init() throws RedisException {
		if(StringUtils.isBlank(host)) {
			throw new RedisException("目标主机不能为空...");
		}
		if(null == jedisConfig) {
			jedisConfig = new JedisPoolConfig();
		}
		redisConnMgr = new RedisConnMgr();
		redisConnMgr.setJedisConfig(jedisConfig);
		jedisPool = redisConnMgr.initConnPool(host, port, password, timeout);
	}

	public Object doExecute(RedisCallback callback) throws RedisException {
		Jedis jedis = jedisPool.getResource();	//从连接池获取连接
		Object obj = callback.doInRedis(jedis);	//回调客户端具体实现
		redisConnMgr.returnResource(jedis);		//将资源放回连接池
		return obj;
	}

	public JedisPoolConfig getJedisConfig() {
		return jedisConfig;
	}

	public void setJedisConfig(JedisPoolConfig jedisConfig) {
		this.jedisConfig = jedisConfig;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	@SuppressWarnings({ "rawtypes"})
	public RedisDao dao(String key) {
		RedisDao dao = new RedisDao(key);
		dao.setJedisPool(jedisPool);
		return dao;
	}

}