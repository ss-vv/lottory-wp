package com.unison.lottery.weibo.web.service;

import java.util.List;

import com.unison.lottery.weibo.data.MyHome;
import com.unison.lottery.weibo.data.PrivateMessage;
import com.unison.lottery.weibo.data.WeiboMsg;

/**
 * 微博个人页服务
 * @author Wang Lei
 *
 */
public interface MyWeiboService {
	
	/**
	 * 我的微博主页
	 * @param userId
	 * @return
	 */
	public MyHome home(long userId);

	/**
	 * 评论我的列表
	 * @param userId
	 */
	public List<WeiboMsg> comment(long userId);
	
	/**
	 * 我的收藏
	 * @param userId
	 * @return
	 */
	public List<WeiboMsg> favorite(long userId);
	
	/**
	 * 提到我的
	 * @param userId
	 * @return
	 */
	public List<WeiboMsg> mention(long userId);
	
	/**
	 * 我的私信
	 * @param userId
	 * @return
	 */
	public List<PrivateMessage> myPrivate(long userId);
}
