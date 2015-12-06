package com.unison.lottery.weibo.common.service;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;

/**
 * @desc 微博热门讨论服务
 * @author lei.li@unison.net.cn
 * @createTime 2013-12-27
 * @version 1.0
 */
public interface DiscussService {

	/**
	 * 分页查询最近一段时间的热门讨论的微博（按评论数+赞：倒排）
	 * 
	 * @param recentDateType
	 * @param pageRequest
	 */
	PageResult<WeiboMsgVO> findHotDiscussByDate(long uid, RecentDateType recentDateType,
			PageRequest pageRequest);

	
}
