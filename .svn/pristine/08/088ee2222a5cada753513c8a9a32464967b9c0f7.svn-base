package com.unison.lottery.weibo.msg.service;

import com.unison.lottery.weibo.data.Favourite;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;

/**
 * @desc 微博收藏功能，对外提供的接口服务
 * @author lei.li@unison.net.cn
 * @createTime 2013-10-24
 * @version 1.0
 */
public interface FavoriteService {

	/**
	 * 增加个人微博收藏
	 * 
	 * @param userId
	 * @param postId
	 *            微博ID
	 * @return
	 */
	boolean add(long userId, long postId);

	/**
	 * 查看个人收藏列表
	 * 
	 * @param userId
	 * @return
	 */
	PageResult<Favourite> list(long userId, PageRequest pageRequest);

	/**
	 * 取消某一条微博收藏
	 * 
	 * @param userId
	 * @param postId
	 * @return
	 */
	boolean cancel(long userId, long postId);

}