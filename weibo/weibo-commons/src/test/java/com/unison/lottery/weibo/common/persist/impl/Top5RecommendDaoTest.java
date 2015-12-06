package com.unison.lottery.weibo.common.persist.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.unison.lottery.weibo.common.nosql.Top5RecommendDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.nosql.impl.Top5RecommendDaoImpl;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.data.Top5Recommend;

public class Top5RecommendDaoTest {
	
	private Top5RecommendDao top5RecommendDao;
	private RedisTemplate redisTemplate;
	@Before
	public void setUp() {
		
		top5RecommendDao=new Top5RecommendDaoImpl();
		String host="182.92.191.193";
		int port=22122;
		redisTemplate=new RedisTemplate(host, port);
		((Top5RecommendDaoImpl)top5RecommendDao).setRedisTemplate(redisTemplate);
		
	}
	
	@Test
	public void testGetList(){
		listTargetKeyList(Keys.DAY_7_SHENGLV);
		listTargetKeyList(Keys.DAY_7_YINGLV);
		listTargetKeyList(Keys.MATCH_50_SHENGLV);
		listTargetKeyList(Keys.MATCH_50_YINGLV);
		listTargetKeyList(Keys.MATCH_50_FOLLOW_SCHEME_BONUS);
		listTargetKeyList(Keys.DAY_7_FOLLOW_SCHEME_BONUS);
		listTargetKeyList(Keys.MATCH_50_FOLLOW_SCHEME_SHENGLV);
		listTargetKeyList(Keys.DAY_7_FOLLOW_SCHEME_SHENGLV);
		listTargetKeyList(Keys.DAY_7_SHOW_SCHEME_SHENGLV);
		listTargetKeyList(Keys.DAY_7_SHOW_SCHEME_HELP_BONUS);
		listTargetKeyList(Keys.MATCH_50_SHOW_SCHEME_HELP_BONUS);
		listTargetKeyList(Keys.MATCH_50_SHOW_SCHEME_SHENGLV);
	}

	private void listTargetKeyList(String keyName) {
		List<String> keys = top5RecommendDao.lrange(keyName, 0, -1);
		if(null!=keys&&!keys.isEmpty()){
			for(String key:keys){
				Top5Recommend top5Recommend = top5RecommendDao.hashGet(key);
				System.out.println("key="+key+",value="+top5Recommend);
			}
			
		}
		else{
			System.out.println("keyName:"+keyName+"对应的列表为空");
		}
	}

}
