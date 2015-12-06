package com.unison.lottery.weibo.common.service;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsgVO;

/**
 * @desc 热门推荐
 * 将所有“彩种用户”的推荐时间线，创建按“评论数”的索引列表
 * @author lei.li@unison.net.cn
 * @createTime 2014-01-06
 * @version 1.0
 */
public interface HotRecommendService {

	/**
	 * 查询热门推荐列表
	 * 
	 * @param pageRequest
	 */
	PageResult<WeiboMsgVO> query(long uid, PageRequest pageRequest);
	
	/**
	 * 遍历所有彩种用户，创建热门推荐索引
	 */
	void createRecommendIndexOfDate(RecentDateType recentDateType);

}
