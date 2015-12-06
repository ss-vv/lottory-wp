package com.unison.lottery.weibo.bbs.persist.dao;

import com.unison.lottery.weibo.common.nosql.BaseDao;

/**
 * 彩票用户和微博用户关系 DAO
 * 
 * @author zhuyongli
 */
public interface WeiboUserDao extends BaseDao<String>{
	
	String getWeiboUserIdByLotteryUserId(final Long lotteryUserId);

}
