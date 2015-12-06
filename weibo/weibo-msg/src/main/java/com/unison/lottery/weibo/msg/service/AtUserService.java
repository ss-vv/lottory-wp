package com.unison.lottery.weibo.msg.service;

import java.util.Set;
import com.unison.lottery.weibo.data.PageRequest;
import com.unison.lottery.weibo.data.PageResult;
import com.unison.lottery.weibo.data.WeiboMsg;
import com.unison.lottery.weibo.data.WeiboMsgVO;

/**
 * 发布微博同时@用户
 * @author Wang Lei
 *
 */
public interface AtUserService {
	
	/**
	 * 添加提到我的微博
	 * @param pid
	 * @return 
	 */
	public long atUserByPostId(final long pid);
	
	/**
	 * 查询提到我的
	 * @param uid
	 * @param pageRequest
	 */
	public PageResult<WeiboMsgVO> findAtMeList(long uid, PageRequest pageRequest);

	/**
	 * 添加提到我的微博
	 * @param weiboMsg
	 * @return 
	 */
	public long atUserByPost(WeiboMsg weiboMsg);
	
	/**
	 * 查看给定内容中都@了那些微博用户
	 * @param content
	 * @return
	 */
	public Set<String> findAtUsers(String content);
}
