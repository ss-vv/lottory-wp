package com.unison.lottery.weibo.msg.service;

import java.util.List;

import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsgVO;
import com.unison.lottery.weibo.data.WeiboUser;

/**
 * @desc 推荐服务
 * @author lei.li@unison.net.cn
 * @createTime 2014-01-22
 * @version 1.0
 */
public interface RecommendService {

	/**
	 * 查询指定彩种的推荐列表
	 * 	获取的是彩种用户发的推荐微博
	 * @param lotteryId
	 *            彩种枚举值
	 */
	PageResult<WeiboMsgVO> queryLotteryUserRecommendList(String lotteryId,
			PageRequest pageRequest);

	List<WeiboUser> findAnalyzeTalent();
}