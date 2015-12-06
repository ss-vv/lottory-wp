package com.unison.lottery.weibo.bbs.persist.dao;

import java.util.Set;

import com.unison.lottery.weibo.common.nosql.BaseDao;

/**
 * 帖子 DAO
 * 
 * @author zhuyongli
 */
public interface WeiboThreadDao extends BaseDao<String>{
	
	Set<String> getThreadIdByWeiboUserId(final Long key);
	
	void saveThreadId(final Long key, final String... members);
}
