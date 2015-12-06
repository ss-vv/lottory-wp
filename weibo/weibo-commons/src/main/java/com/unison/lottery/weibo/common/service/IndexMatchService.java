package com.unison.lottery.weibo.common.service;

/**
 * @Description:索引赛事服务 
 * @author 江浩翔   
 * @date 2014-1-21 下午3:52:30 
 * @version V1.0
 */
public interface IndexMatchService {
	/**
	 * 建立竞彩足球赛事索引
	 */
	public void buildJCZQMatchIndex();
	/**
	 * 建立竞彩篮球赛事索引
	 */
	public void buildJCLQMatchIndex();
	/**
	 * 建立传统足彩赛事索引
	 */
	public void buildCTZCMatchIndex();
	public void buildBJDCMatchIndex();
}
