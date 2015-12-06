package com.unison.lottery.weibo.bot.service;

import java.util.List;

import com.unison.lottery.weibo.bot.model.SinaWeiboUser;
import com.unison.lottery.weibo.bot.model.WeiboContent;

public interface RobotService {
	/**
	 * 保存新浪微博授权帐号
	 */
	void saveSinaWeiboUser(SinaWeiboUser sinaWeiboUser);
	
	/**
	 * 获取所有新浪微博帐号
	 * @return
	 */
	List<SinaWeiboUser> getAllSinaWeiboUser();
	
	/**
	 * 保存微博内容
	 * @param weiboContent
	 */
	void saveWeiboContent(WeiboContent weiboContent);
	
	/**
	 * 批量保存微博内容
	 * @param weiboContents
	 */
	void saveWeiboContent(List<WeiboContent> weiboContents);
}
