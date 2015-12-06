package com.unison.lottery.weibo;

import java.util.Date;
import java.util.Set;

import redis.clients.jedis.Jedis;

import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.redis.RedisCallback;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.data.Relationship;

/**
 * @Description:修改了 关注关系（粉丝列表和关注列表的数据结构），
 * 		将旧的（set）数据导入新的（sorted set）集合类型中 
 * @author 江浩翔   
 * @date 2014-1-2 下午12:17:02 
 * @version V1.0
 */
public class LoadOldRLSP2New {
	
	public static void main(String[] args ){
//		RedisTemplate redisTemplate = new RedisTemplate("122.226.122.47", 22123);
//		
//		for (int i = 0; i < 100; i++) {
//			final long iL = i;
//			Relationship r = (Relationship)(redisTemplate.doExecute(new RedisCallback() {
//				public Relationship doInRedis(Jedis jedis) {
//					System.out.println("查询key：" + Keys.getFollowingKey(iL));
//					Set<String> followingsSet = jedis.smembers(Keys.getFollowingKey(iL));
//					if(followingsSet.size() < 1){
//						return null;
//					}
//					Relationship relationship = new Relationship();
//					relationship.setFollowings(followingsSet.toArray(new String[followingsSet.size()]));
//					//删除关注key
//					jedis.del(Keys.getFollowingKey(iL));
//					return relationship;
//				}
//			}));
//			if(null == r){
//				continue;
//			}
//			final String[] beFollowUids=r.getFollowings();
//			final long followerUid = i; 
//			redisTemplate.doExecute(new RedisCallback() {
//				public Long doInRedis(Jedis jedis) {
//					String key = Keys.getFollowingKey(followerUid);
//					String[] value = beFollowUids;
//					double score = new Date().getTime();
//					for (String val : value) {
//						jedis.zadd(key, score, val);
//						System.err.println("weiboUid=" + followerUid + ";关注了uid=" + val);
//					}
//					for (int i = 0; i < beFollowUids.length; i++) {
//						key = Keys.getFollowerKey(Long.valueOf(beFollowUids[i]));
//						jedis.zadd(key, score, "" + followerUid);
//					}
//					return 1L;
//				}
//			});
//		}
	}
}
