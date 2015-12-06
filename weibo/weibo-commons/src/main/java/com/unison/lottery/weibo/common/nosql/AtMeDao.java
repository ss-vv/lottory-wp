package com.unison.lottery.weibo.common.nosql;

import com.unison.lottery.weibo.common.redis.RedisException;


/**
 * 提到我的
 * @author Wang Lei
 *
 */
public interface AtMeDao extends BaseDao<String>{
	
	/**
	 * 微博中提到用户
	 * @param pid 原微博id
	 * @return 
	 */
	public long atUserByPostId(String[] uids, String pid) throws RedisException;

}
