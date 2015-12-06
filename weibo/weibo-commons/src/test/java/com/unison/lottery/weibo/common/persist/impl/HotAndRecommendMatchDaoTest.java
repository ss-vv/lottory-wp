package com.unison.lottery.weibo.common.persist.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.unison.lottery.weibo.common.nosql.HotAndRecommendMatchDao;
import com.unison.lottery.weibo.common.nosql.Top5RecommendDao;
import com.unison.lottery.weibo.common.nosql.impl.HotAndRecommendMatchDaoImpl;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.nosql.impl.Top5RecommendDaoImpl;
import com.unison.lottery.weibo.common.redis.RedisTemplate;
import com.unison.lottery.weibo.data.Top5Recommend;
import com.xhcms.lottery.commons.data.HotAndRecommendMatch;

public class HotAndRecommendMatchDaoTest {
	
	private HotAndRecommendMatchDao hotAndRecommendMatchDao;
	private RedisTemplate redisTemplate;
	@Before
	public void setUp() {
		
		hotAndRecommendMatchDao=new HotAndRecommendMatchDaoImpl();
		String host="182.92.191.193";
		int port=22122;
		redisTemplate=new RedisTemplate(host, port);
		((HotAndRecommendMatchDaoImpl)hotAndRecommendMatchDao).setRedisTemplate(redisTemplate);
		
	}
	
	@Test
	public void testGetList(){
		listTargetKeyList(Keys.HOT_AND_RECOMMEND_MATCH);
	}

	private void listTargetKeyList(String keyName) {
		List<String> keys = hotAndRecommendMatchDao.lrange(keyName, 0, -1);
		if(null!=keys&&!keys.isEmpty()){
			for(String key:keys){
				HotAndRecommendMatch hotAndRecommendMatch = hotAndRecommendMatchDao.hashGet(key);
				System.out.println("key="+key+",value="+hotAndRecommendMatch);
			}
			
		}
		else{
			System.out.println("keyName:"+keyName+"对应的列表为空");
		}
	}

}
