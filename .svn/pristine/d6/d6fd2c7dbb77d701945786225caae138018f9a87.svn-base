package com.unison.lottery.weibo.task;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Tuple;

import com.unison.lottery.weibo.common.cache.SchemeCache;
import com.unison.lottery.weibo.common.nosql.CommonDao;
import com.unison.lottery.weibo.common.nosql.impl.Keys;
import com.unison.lottery.weibo.common.redis.RedisConstant;
import com.xhcms.commons.job.Job;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.service.BetSchemeService;
import com.xhcms.lottery.commons.util.SchemeStatusTools;
import com.xhcms.lottery.lang.SchemeType;

public class AsyncUpdateSchemeCacheTask extends Job {

	private static final Logger log = LoggerFactory
			.getLogger(AsyncUpdateSchemeCacheTask.class);

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private BetSchemeService betSchemeService;

	@Autowired
	private SchemeCache schemeCache;

	@Override
	protected void execute() {
		log.info("开始执行更新方案缓存的task...");
		
		try {
			Set<Tuple> schemeSetIndex = commonDao.zrangeByScoreWithScores(
					Keys.REAL_SCHEME_CACHE_NOT_FINAL, RedisConstant.N_INFINIT,
					RedisConstant.INFINIT);
			if (null != schemeSetIndex && schemeSetIndex.size() > 0) {
				log.info("\n发现需要更新未达到最终状态的缓存方案个数：{}", schemeSetIndex.size());
				printScheme(schemeSetIndex);
				
				Iterator<Tuple> iter = schemeSetIndex.iterator();
				while (iter.hasNext()) {
					Tuple schemeIndex = iter.next();
					long schemeId = Long.valueOf(schemeIndex.getElement());
					int score = (int) schemeIndex.getScore();
					if(schemeId <= 0) {
						continue;
					}
					BetScheme betScheme;
					try {
						betScheme = betSchemeService.viewScheme(schemeId, score);
						if (null != betScheme && betScheme.getId() > 0) {
							int schemeStatus = betScheme.getStatus();
							boolean isFinalStatus = SchemeStatusTools.isFinalStatus(schemeStatus);
							String schemeCacheKey = Keys.realSchemeCacheKey(schemeId, score);
							List<BetMatch> matchs = betScheme.getMatchs();
							if (isFinalStatus && SchemeStatusTools.isGetAllMatchResult(matchs)) {
								commonDao.zrem(Keys.REAL_SCHEME_CACHE_NOT_FINAL, schemeId + "");
								log.info("方案ID={},方案类型={},达到最终状态，移除待更新列表!", 
										schemeId, SchemeType.REAL.getName());
							}
							schemeCache.updateSchemeCache(schemeCacheKey,betScheme);
						}
					} catch (Exception e) {
						log.error("更新方案缓存error，方案ID={}，缓存的key={}, error={}", 
								new Object[]{schemeId, Keys.realSchemeCacheKey(schemeId, score), e});
						e.printStackTrace();
					}
				}
			} else {
				log.info("未发现需要更新的‘未达到最终状态方案’列表.");
			}
		} catch (Exception e) {
			log.error("AsyncUpdateSchemeCacheTask error:", e);
		}
	}
	
	private void printScheme(Set<Tuple> schemeSetIndex) {
		if(null != schemeSetIndex) {
			Iterator<Tuple> iter = schemeSetIndex.iterator();
			log.info("***查询到的需要更新状态的方案Id集合,size={}***", 
					schemeSetIndex.size());
			while(iter.hasNext()) {
				Tuple schemeIndex = iter.next();
				long schemeId = Long.valueOf(schemeIndex.getElement());
				int score = (int) schemeIndex.getScore();
				log.info("{}:{}", schemeId, score);
			}
			log.info("************************************");
		}
	}
}