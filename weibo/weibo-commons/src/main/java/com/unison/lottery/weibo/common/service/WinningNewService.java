package com.unison.lottery.weibo.common.service;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.RecentDateType;
import com.unison.lottery.weibo.data.vo.WinningNewVO;

/**
 * @desc 中奖喜报服务
 * @author lei.li@unison.net.cn
 * @createTime 2014-01-06
 * @version 1.0
 */
public interface WinningNewService {

	/**
	 * 查询喜报列表
	 * 
	 * @param recentDateType
	 * @param pageRequest
	 */
	PageResult<WinningNewVO> query(PageRequest pageRequest);

	/**
	 * 查询最近一段时间的喜报列表
	 * @param pageRequest
	 * @param recentDateType	最近时间的枚举类型
	 * @return
	 */
	PageResult<WinningNewVO> queryByTime(PageRequest pageRequest,
			RecentDateType recentDateType);
}
