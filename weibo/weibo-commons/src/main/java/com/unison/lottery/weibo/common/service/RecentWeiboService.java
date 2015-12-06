package com.unison.lottery.weibo.common.service;

import com.unison.lottery.weibo.data.RecentDateType;

/**
 * @desc 最近微博服务
 * @author lei.li@unison.net.cn
 * @createTime 2013-12-27
 * @version 1.0
 */
public interface RecentWeiboService {

	/**
	 * 创建指定日期类型的‘最近微博’索引,将查询的结果放到一个有序集合中
	 * 
	 * @param recentDateType
	 */
	void createHotDiscussIndex(RecentDateType recentDateType);

}
