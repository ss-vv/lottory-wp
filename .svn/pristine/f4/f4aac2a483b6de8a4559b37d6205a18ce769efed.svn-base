package com.xhcms.lottery.admin.persist.service;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.admin.persist.service.exception.BetSchemeServiceException;
import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;

public interface BetSchemeService {
    /**
     * 按条件查询投注方案
     * @param paging
     * @param begin 查询开始日期
     * @param end 查询结束日期
     * @param status 状态
     * @param lotteryId 彩种，为null时忽略该条件
     * @param schemeId 方案ID，为null时忽略该条件
     * @param sponsor 发起人登录名，为null时忽略该条件
     * @param playId 玩法ID，为null时忽略该条件
     * @param passType 过关方式ID，为null时忽略该条件
     */
	void listBetScheme(Paging paging, Date begin, Date end, int status, String lotteryId, Long schemeId, String sponsor, String playId, String passType);

	/**
	 * 按条件列出在售的晒单方案。即可以目前还可以跟单购买的方案，它们的停售时间晚于当前时间。按照方案创建时间倒排。
	 * @param paging
     * @param status 状态
     * @param lotteryId 彩种，为null时忽略该条件
     * @param schemeId 方案ID，为null时忽略该条件
     * @param sponsor 发起人登录名，为null时忽略该条件
     * @param playId 玩法ID，为null时忽略该条件
     * @param passType 过关方式ID，为null时忽略该条件
	 */
	void listOnSaleShowingBetScheme(Paging paging, int status, String lotteryId, Long schemeId, String sponsor, String playId, String passType);
	
	/**
	 * 
	 * @param paging
	 * @param status 状态
	 * @param lotteryId
	 * @param schemeId
	 * @param sponsor
	 * @param playId
	 * @param passType
	 */
	void listOnSaleGroupbuyBetScheme(Paging paging, int status, String lotteryId, Long schemeId, String sponsor, String playId, String passType);
	
	/**
	 * 列出推荐方案，按照方案创建时间倒排。
	 * @param paging 返回对象.
	 * @param isOnSale true 只列出在售方案，false - 列出所有方案，包括在售和停售的方案。
	 */
	void listRecommendedBetScheme(Paging paging, boolean isOnSale);
	
	/**
	 * 
	 * @param paging
	 * @param isOnSale
	 * @param type 方案类型
	 */
	void listRecommendedBetScheme(Paging paging, boolean isOnSale, int type);

	/**
	 * 查询投注方案的拆票情况
	 * @param schemeId
	 * @return
	 */
	List<Ticket> listTicket(long schemeId);
	
	/**
	 * 派奖
	 * 1. 检查方案状态(中奖未派奖)
	 * 2. 派奖操作
	 * 3. 修改方案状态(已派奖), 修改投注方案彩票表状态(已派奖)
	 * 
	 * @param operator
	 * @param id
	 */
	void award(int operator, List<Long> id);
	
	/**
	 * 撤单
	 * @param operator
	 * @param schemeId
	 */
	void cancel(int operator, long schemeId);
	
	/**
	 * 查询投注方案
	 * @param id
	 * @return
	 */
	BetScheme getScheme(Long id);
	
	/**
	 * 查询投注赛事
	 * @param schemeId
	 * @return
	 */
	List<BetMatch> listMatch(Long schemeId);
	
	/**
	 * 查询投注合买人
	 * @param schemeId
	 * @return
	 */
	List<BetPartner> listPartner(Long schemeId);
	
	/**
	 * 设置方案为推荐方案。
	 * @param schemeId
	 * @throws BetSchemeServiceException 如果该方案用户并未晒单或没有该id方案。
	 */
	void recommendScheme(Long schemeId) throws BetSchemeServiceException;
	
	/**
	 * 取消推荐该方案。
	 * @param schemeId
	 * @throws BetSchemeServiceException 如果没有该id方案。
	 */
	void cancelRecommendScheme(Long schemeId) throws BetSchemeServiceException;
	
	/**
	 * 列出跟单.
	 * @param showingSchemeId 晒单id
	 */
	List<BetScheme> listFollowingScheme(Long showingSchemeId);

	/**
	 * 重置方案为可出票状态
	 * @param operatorId 操作员id
	 * @param sid 方案id
	 */
	void setToAllowBuy(int operatorId, long sid);
	
	/**
	 * 设置方案为出票成功(方案当前状态必须是"可出票状态")
	 * @param operatorId 操作员id
	 * @param sid 方案id
	 */
	void setToSchemeBuySuccess(int operatorId, long schemeId);
	
}
