package com.xhcms.lottery.account.service;

import com.xhcms.lottery.commons.data.BetScheme;

/**
 * 实单方案缓存更新：更新的操作
 * 1.代购/晒单
 * 2.跟单
 * 3.发起合买
 * 4.发起合买
 * @author lei.li@davcai.com
 */
public interface RealSchemeCacheUpdate {

	/**
	 * 投注成功之后更新方案缓存：
	 * 注意：***该方法仅提供给方案购买成功之后的更新***
	 * 1.如果是代购只更新原方案
	 * 2.如果是跟单会更新被跟单方案和跟单方案缓存
	 * 3.如果是合买会更新发起合买的方案缓存
	 * @param scheme
	 * @param isFollowScheme 是否是跟单操作
	 */
	void betSuccessUpdateSchemeCache(BetScheme scheme, boolean isFollowScheme);
	
}
