package com.unison.lottery.weibo.service;

import com.unison.thrift.scheme.service.gen.BetSchemeData;
import com.xhcms.lottery.commons.data.weibo.SchemeHandle;

/**
 * @desc 和方案有关的微博服务
 * @author lei.li@unison.net.cn
 * @createTime 2014-4-23
 * @version 1.0
 */
public interface WeiboSchemeService {
	
	/**
	 * 对给定的方案ID，晒出微博
	 * @param schemeId	方案ID
	 * @param isBetRecordShowScheme 是否是投注记录晒单
	 * @param loginUserId
	 */
	void publish(SchemeHandle schemeHandle);
	
	/**
	 * 代购晒单方案发微博
	 * @param schemeId 晒单方案ID
	 * @param slogan 晒单内容
	 * @param po
	 */
	void publishWeiboWithAloneScheme(long schemeId, String slogan);
	
	/**
	 * 跟单方案发微博
	 * @param po
	 */
	void publishWeiboWithFollowScheme(BetSchemeData po, String followWeiboContent);
	
	/**
	 * 合买方案发微博
	 * @param po
	 */
	void publishWeiboWithPartnerScheme(SchemeHandle schemeHandle);
	
	void forwardSchemeWeibo(long schemeId, 
			long sponsorId, String forwardWeiboContent, String lottery);
}