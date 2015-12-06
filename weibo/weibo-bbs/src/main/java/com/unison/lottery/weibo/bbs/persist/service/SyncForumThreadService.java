package com.unison.lottery.weibo.bbs.persist.service;

/**
 * @desc 同步服务
 * @author zhuyongli
 * @createTime 2013-12-02
 * @version 1.0
 */
public interface SyncForumThreadService {
	
	/**
	 * 同步彩票有效用户所发帖子到微博
	 */
	void syncForumThread();	
	
}
