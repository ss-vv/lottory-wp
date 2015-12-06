package com.unison.lottery.weibo.common.nosql;

import java.util.LinkedHashSet;
import java.util.Set;

import redis.clients.jedis.Tuple;

import com.unison.lottery.weibo.data.Favourite;

/**
 * @desc 收藏信息数据管理
 * @author lei.li@unison.net.cn
 * @createTime 2013-10-29
 * @version 1.0
 */
public interface FavouriteDao  extends BaseDao<Favourite>{

	/**
	 * 保存一条收藏信息
	 * 
	 * @param userId
	 * @param postId
	 * @return
	 */
	Long save(long userId, long postId);

	/**
	 * 加载指定用户收藏信息
	 * 
	 * @param userId
	 * @param startIndex
	 *            开始索引
	 * @param endIndex
	 *            结束索引
	 * @return
	 */
	Set<Tuple> load(final long userId, final long startIndex,
			final long endIndex);

	/**
	 * 计算用户收藏数量
	 * 
	 * @param userId
	 * @return
	 */
	Long favouriteCount(final long userId);

	/**
	 * 取消执行用户的一条收藏信息
	 * 
	 * @param userId
	 * @param postId
	 * @return
	 */
	Long delete(long userId, long postId);

	LinkedHashSet<String> loadAll(long userId, long startIndex, long endIndex);
}