package com.unison.lottery.weibo.service;

import java.math.BigDecimal;

public interface ShareOrderTemplateService {
	
	/**
	 * 获取晒单方案模版
	 * @param lotteryName
	 * @param totalAmount
	 * @param maxBonus
	 * @param returnRatio
	 * @param schemeType TODO
	 * @return
	 */
	String getShowSchemeTpl(String lotteryName, int totalAmount, BigDecimal maxBonus, BigDecimal returnRatio, int schemeType);
	
	/**
	 * 转发方案微博 
	 * @return
	 */
	String forwardWeiboTpl();
	
	/**
	 * 根据方案状态，给出不同的晒单文字
	 * @param status TODO
	 * @return
	 */
	String schemeStatus(int status);
}