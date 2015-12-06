package com.xhcms.lottery.commons.persist.service;

import com.xhcms.lottery.commons.data.UserScore;

/**
 * 用户战绩缓存服务
 * @author yonglizhu
 */
public interface CacheUserScoreService {
	UserScore findByUserIdAndLotteryId(long userId, String lotteryId);
}
