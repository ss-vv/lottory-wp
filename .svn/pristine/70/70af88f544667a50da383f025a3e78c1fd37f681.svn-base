package com.unison.lottery.weibo.common.nosql.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.Tuple;

import com.unison.lottery.weibo.common.nosql.FavouriteDao;
import com.unison.lottery.weibo.data.Favourite;

@Repository
public class FavouriteDaoImpl extends BaseDaoImpl<Favourite> implements
		FavouriteDao {

	@Override
	public Long save(final long userId, final long postId) {
		return zadd(System.currentTimeMillis(), Keys.favorites(userId),
				String.valueOf(postId));
	}

	@Override
	public Set<Tuple> load(final long userId, final long startIndex,
			final long endIndex) {
		return zrangeWithScores(Keys.favorites(userId), startIndex, endIndex);
	}
	
	@Override
	public LinkedHashSet<String> loadAll(final long userId, final long startIndex,
			final long endIndex) {
		return zrange(Keys.favorites(userId), startIndex, endIndex);
	}

	@Override
	public Long delete(final long userId, final long postId) {
		return zrem(Keys.favorites(userId), String.valueOf(postId));
	}

	@Override
	public Long favouriteCount(final long userId) {
		return zcard(Keys.favorites(userId));
	}
}
