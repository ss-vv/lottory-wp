package com.unison.lottery.weibo.common.service.impl;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.unison.lottery.weibo.common.nosql.BaseDao;
import com.unison.lottery.weibo.common.nosql.RecentWeiboDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.service.RecentWeiboService;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsg;

@Service
public class RecentWeiboServiceImpl extends BaseServiceImpl implements
		RecentWeiboService {
	
	private static final Logger log = LoggerFactory
			.getLogger(RecentWeiboService.class);
	
	@Autowired
	private RecentWeiboDao recentWeiboDao;
	
	private static final int CRITICAL_COUNT = 10;

	@Override
	public void createHotDiscussIndex(RecentDateType recentDateType) {
		log.info("开始建立‘热门讨论’索引，日期类型={}", new Object[]{recentDateType.name()});
		long start = System.currentTimeMillis();
		long max = start;
		long min = calcCreateDiscussIndex(recentDateType);
		Long count = getBaseDao().zcount(Keys.GLOBAL_TIMELINE, min, max);
		
		Set<String> postIdSet = (Set<String>) getBaseDao().zrevrangeByScoreLimt(Keys.GLOBAL_TIMELINE, 
				max+"", (min)+"", 0, count.intValue());
		if(null != postIdSet && postIdSet.size() > 0) {
			recentWeiboDao.delete(Keys.discussKey(RecentDateType.DAY));
			
			start = System.currentTimeMillis();
			for(String postId : postIdSet) {
				String postKey = Keys.postKey(String.valueOf(postId));
				WeiboMsg weiboMsg = recentWeiboDao.hashGet(postKey, WeiboMsg.class);
				if (null != weiboMsg) {
					int num = weiboMsg.getCommentCount();
					recentWeiboDao.zadd(Double.parseDouble(num + ""), Keys.discussKey(RecentDateType.DAY),
							String.valueOf(weiboMsg.getId()));
				}
			}
			long end = System.currentTimeMillis();
			log.info("创建热门讨论微博索引，耗时={}", (end-start));
		}
	}
	
	protected long calcCreateDiscussIndex(RecentDateType recentDateType) {
		long start = System.currentTimeMillis();
		long max = start;
		long min = start - recentDateType.getTime();
		Long count = getBaseDao().zcount(Keys.GLOBAL_TIMELINE, min, max);
		if(count < CRITICAL_COUNT) {
			min = start - RecentDateType.TWO_DAY.getTime();
			log.info("建立热门讨论：给定日期类型={}, 数量小于={}, 切换日期类型={}",
					new Object[]{recentDateType.getName(), CRITICAL_COUNT,
					RecentDateType.TWO_DAY.getName()});
		}
		return min;
	}
	
	@Override
	protected BaseDao<?> getBaseDao() {
		return recentWeiboDao;
	}
}
