package com.xhcms.lottery.account.service;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;

/**
 * @author jiajiancheng
 * 
 */
public interface BetSchemeService extends BetSchemeBaseService {
	
	/**
	 * 更新实单方案缓存
	 * @param schemeId
	 * @param displayMode
	 * @return
	 */
	boolean updateRealSchemeCache(long schemeId, int displayMode);
	
    /**	
     * 确认投注内容是否有效，不会修改数据库内容。会设置预设让分值。
     * @param schemeId
     * @param userId
     */
    void confirmScheme(BetScheme scheme);
    
	/**
	 * 查询投注方案的拆票情况
	 * @param schemeId
	 * @param userId
	 * @return
	 */
	List<Ticket> listTicket(long schemeId, long userId,int displayMode);
	
	/**
	 * 判断此方案的用户是否为推荐用户
	 * @param scheme
	 * @return
	 */
	boolean isRecommendUser(BetScheme scheme);

	
	/**
	 * 查询我参加的跟单列表
	 * @param paging
	 * @param status
	 * @param lotteryId
	 * @param schemeId
	 * @param sponsorid
	 * @param playId
	 * @param passType
	 * @param from
	 * @param to
	 */
	void findMyJoinFollowList(Paging paging, int status, String lotteryId, Long schemeId, long sponsorid, String playId, String passType,Date from,Date to);
	
	/**
	 * 查询我发起的跟单列表
	 * @param paging
	 * @param status
	 * @param lotteryId
	 * @param schemeId
	 * @param sponsorid
	 * @param playId
	 * @param passType
	 * @param from
	 * @param to
	 */
	void findMyLaunchFollowList(Paging paging, int status, String lotteryId, Long schemeId, long sponsorid, String playId, String passType,Date from,Date to);
	
	/**
	 * 查询我参加的合买列表
	 * @param paging
	 * @param status
	 * @param lotteryId
	 * @param sponsorId
	 * @param playId
	 * @param passType
	 * @param from
	 * @param to
	 */
	void findMyJoinGroupbuyList(Paging paging,	int status, String lotteryId, Long sponsorId, String playId,String passType, Date from, Date to);
	
	/**
	 * 查询我发起的合买列表
	 * @param paging
	 * @param status
	 * @param lotteryId
	 * @param sponsorid
	 * @param playId
	 * @param passType
	 * @param from
	 * @param to
	 */
	void findMyLaunchGroupbuyList(Paging paging, int status, String lotteryId, long sponsorid, String playId, String passType,Date from,Date to);
	
	/**
     * 根据用户id获取佣金支出列表
     * @param userId
     * @param startDate
     * @param endDate
     * @param paging
     * @return
     */
	void listCommissionOutByUserId(Long userId,Date startDate, Date endDate, Paging paging);
	
	/**
     * 根据用户id获取佣金收入列表
     * @param userId
     * @param startDate
     * @param endDate
     * @param paging
     * @return
     */
	void listCommissionInByUserId(Long userId,Date startDate, Date endDate, Paging paging);

	void computeMinAndMaxBonus(BetScheme scheme, Bet bet);
}
