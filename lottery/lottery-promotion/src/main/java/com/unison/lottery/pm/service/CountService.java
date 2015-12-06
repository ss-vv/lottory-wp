package com.unison.lottery.pm.service;

/**
 * @author yongli zhu
 */
public interface CountService {
	
	/**
	 * 统计数据
	 */
	void count();
	
	/**
	 * 更新跟单总金额数据
	 */
	void updateAmount(String month);
}
