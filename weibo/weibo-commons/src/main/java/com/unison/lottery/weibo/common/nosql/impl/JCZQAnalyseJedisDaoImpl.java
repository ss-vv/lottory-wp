package com.unison.lottery.weibo.common.nosql.impl;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;
import com.unison.lottery.weibo.common.nosql.JCZQAnlyseJedisDao;
import com.unison.lottery.weibo.common.redis.RedisCallback;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;

/**
 *
 * @author baoxing.peng@davcai.com
 *
 * @since 2015年1月27日 下午3:10:16
 */
@Repository
public class JCZQAnalyseJedisDaoImpl extends BaseDaoImpl<LeagueScoreRandPO> implements JCZQAnlyseJedisDao {

	@Override
	public LeagueScoreRandPO findFbLeagueRank(String hashSetKeys) {
		return hashGet(hashSetKeys);
	}
	@Override
	public List<LeagueScoreRandPO> findFbLeagueRankList(String[] keys) {
		
		return hashList(keys);
	}
}
