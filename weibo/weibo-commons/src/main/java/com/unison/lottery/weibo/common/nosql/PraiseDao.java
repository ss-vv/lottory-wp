package com.unison.lottery.weibo.common.nosql;

import java.util.Set;


/**
 * 微博采纳
 * @author Wang Lei
 *
 */
public interface PraiseDao extends BaseDao<String>{
	
	/**
	 * 采纳微博
	 * @param uid
	 * @param pid
	 * @return
	 */
	public long praise(Long uid, String pid);
	
	/**
	 * 取消采纳微博
	 * @param uid
	 * @param pid
	 * @return
	 */
	public long unPraise(Long uid, String pid);

	/**
	 * 列出用户采纳的微博id集合
	 * @param uid
	 * @return
	 */
	public Set<String> list(Long uid);
}
