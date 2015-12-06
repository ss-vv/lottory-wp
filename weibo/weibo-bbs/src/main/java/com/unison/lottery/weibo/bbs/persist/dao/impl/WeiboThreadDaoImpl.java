package com.unison.lottery.weibo.bbs.persist.dao.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.bbs.persist.dao.WeiboThreadDao;
import com.unison.lottery.weibo.common.nosql.impl.BaseDaoImpl;
import com.unison.lottery.weibo.common.nosql.impl.Keys;

@Repository
public class WeiboThreadDaoImpl extends BaseDaoImpl<String> implements WeiboThreadDao {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Set<String> getThreadIdByWeiboUserId(final Long key) {
		return smembers(Keys.getBbsImportThreadIdKey(key));
	}
	
	@Override
	public void saveThreadId(final Long key, final String... members) {
		String threadKey = Keys.getBbsImportThreadIdKey(key);
		Long result = sadd(threadKey, members);
		if (result == 0) {
			logger.info("插入: ‘导入到微博的BBS帖子id’ 信息 成功:key={},result={}",
					new Object[] { threadKey, members });
		} else {
			logger.info("插入: ‘导入到微博的BBS帖子id’ 信息 失败:key={},result={}",
					new Object[] { threadKey, members });
		}
	}
}
