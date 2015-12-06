package com.unison.lottery.weibo.common.nosql.impl;

import java.util.Set;

import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.common.nosql.PraiseDao;

@Repository
public class PraiseDaoImpl extends BaseDaoImpl<String> implements PraiseDao {

	@Override
	public long praise(Long uid, String pid) {
		return sadd(Keys.praise(uid), pid);
	}

	@Override
	public long unPraise(Long uid, String pid) {
		return srem(Keys.praise(uid), pid);
	}

	@Override
	public Set<String> list(Long uid) {
		return smembers(Keys.praise(uid));
	}
}