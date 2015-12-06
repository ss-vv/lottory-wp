package com.unison.lottery.weibo.msg.service;

import java.util.Date;
import java.util.List;

import com.unison.lottery.weibo.data.WeiboMsgVO;

/**
 * 微博平台内容运营服务。
 * 
 * @author Yang Bo
 */
public interface ContentManageService {

	/**
	 * 设置某日的“今日话题”为topics指定的微博。将会全部替换指定日期的“今日微博”列表。
	 * @param dayOfTopics 指定日期的今日话题
	 * @param topicId 话题微博id
	 */
	void addDailyTopic(Date dayOfTopics, String topicId);
	
	/**
	 * 把话题从某日的“今日话题”列表中删除。
	 * @param dayOfTopics
	 * @param topicId
	 */
	void deleteDailyTopic(Date dayOfTopics, String topicId);
	
	/**
	 * 列出今日话题。今日话题也是以微博形式存放的。
	 */
	List<WeiboMsgVO> listDailyTopics(Date dayOfTopics);
}
