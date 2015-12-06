package com.unison.lottery.weibo.web.service;

import com.unison.lottery.weibo.data.CTZCInfo;
import com.unison.lottery.weibo.data.JCMatch;
import com.unison.lottery.weibo.data.JX11X5;
import com.unison.lottery.weibo.data.SSQIssue;

/**
 * 微博彩票信息服务
 * @author Wang Lei
 *
 */
public interface LotteryInfoSerice {
	
	/**
	 * 查询竞彩足球信息
	 * @param lotteryId
	 */
	public JCMatch findJCZQInfo(String lotteryId);
	
	/**
	 * 查询竞彩篮球信息
	 * @param lotteryId
	 */
	public JCMatch findJCLQInfo(String lotteryId);
	
	/**
	 * 查询双色球信息
	 * @param issue
	 */
	public SSQIssue findSSQInfo(String issue);
	
	/**
	 * 查询传统足彩信息
	 * @param issue
	 */
	public CTZCInfo findCTZCInfo(String issue);
	
	/**
	 * 查询江西十一选五信息
	 * @param issue
	 */
	public JX11X5 findJX11X5Info(String issue);
}
