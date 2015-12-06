package com.unison.lottery.weibo.msg.service;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.WeiboMsgVO;

/**
 * @desc 中奖喜报微博服务
 * @author lei.li@unison.net.cn
 * @createTime 2014-5-13
 * @version 1.0
 */
public interface WinningWeiboService {
	
	/**
	 * 查询中奖喜报对应微博
	 * @param pageRequest
	 * @param recentDateType
	 * @return
	 */
	PageResult<WeiboMsgVO> queryWinWeibo(PageRequest pageRequest, 
			RecentDateType recentDateType);
	
	/**
	 * 查询我关注的人的中奖
	 * @param weiboUserId
	 * @param pageRequest
	 * @return
	 */
	PageResult<WeiboMsgVO> queryMyFollowingWinnings(long weiboUserId, PageRequest pageRequest);
}