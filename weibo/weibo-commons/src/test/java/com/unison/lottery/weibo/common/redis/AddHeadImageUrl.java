package com.unison.lottery.weibo.common.redis;


import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.Jedis;

public class AddHeadImageUrl {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//a();
	}
	
	private static void a(){
		RedisTemplate redisTemplate = new RedisTemplate("122.226.122.47", 22123);
		redisTemplate.doExecute(new RedisCallback() {
			public Object doInRedis(Jedis jedis) {
				String key = "user:";
				for (int i = 0; i < 100; i++) {
					String userKey = key + i;
					String url = jedis.hget(userKey, "headImageURL");
					if(StringUtils.isBlank(url)){
						jedis.hset(userKey, "headImageURL","images/default_face.jpg");
						System.out.println("添加用户头像，id=" + i);
					}
				}
				return null;
			}
		});
	}
}
