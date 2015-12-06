package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.persist.entity.RecommendUserPO;

/**
 * 跟单
 * @author Yang Bo
 *
 */
public interface FollowService {
	
	// ------------ 推荐用户相关 --------------
	
	/**
     * 查询"自动推荐跟单方案的用户"列表
     * @param paging [in/out]翻页对象。
     */
    List<RecommendUserPO> listRecUser(Paging paging);
    
    /**
     * 记录推荐用户
     * @param id 用户id
     * @param lotteries 彩种列表，同表 lt_lottery 中的id值.
     */
    void recommendUser(long userId, List<String>lotteries, long creatorId);
    
    void cancelRecommendUser(long recId);

	public RecommendUserPO findRecommendUser(long userId, String lotteryId);
	
}
