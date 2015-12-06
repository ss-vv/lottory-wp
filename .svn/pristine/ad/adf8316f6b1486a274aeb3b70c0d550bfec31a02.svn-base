package com.xhcms.lottery.commons.persist.service;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

/**
 * @author jiajiancheng, Bob Yang
 *  
 */
public interface BetSchemeService extends BetSchemeBaseService {

	/**
	 * 设置投注内容的隐私
	 * @param betScheme
	 * @param userId
	 * @param displayMode
	 */
	BetScheme setBetCodeWithPrivacy(BetScheme betScheme, long userId, int displayMode);
	
	/**
     * 查看投注详情
     * @param schemeId
     * @param displayMode -1为代购不晒(跟单)模式,0为晒单模式,1为合买模式
     * @return
     */
    BetScheme viewScheme(Long schemeId, int displayMode);
    
    /**
     * 确认投注内容是否有效，不会修改数据库内容。
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
	
	/**
	 * 派奖
	 * @param operatorId 不能为空
	 * @param betSchemeIdList
	 */
	void award(int operatorId, List<Long> betSchemeIdList);
	
	/**
	 * 取得最新开奖的5个方案
	 * @param lotteryId
	 * @param winStatus
	 * @return
	 */
	List<BetScheme> findNewestWinScheme(String lotteryId, int winStatus);

	List<BetSchemePO>  queryShowingScheme(String schemeType,Integer firstResult,Integer pageMaxResult);
	
	/**
	 * 查询所有的赛单跟单
	 * @param schemeType
	 * @param firstResult
	 * @param pageMaxResult
	 * @return
	 */
	List<BetSchemePO>  queryShowingSchemeAll(String schemeType,Integer firstResult,Integer pageMaxResult);
	
	/**
	 * 用户最近投注记录
	 * @param lotteryId
	 * @param userId
	 * @param count	需要获取的条数
	 * @return
	 */
	List<Object[]> findBetRecord(String lotteryId, long userId, int count);
	
	List<Long>  queryShowingSchemeBy(String fromDate, String followCountSort, String buyAmountSort,
			String rateOfReturnSort, String timeSort,String lottery);

	/**
	 * 查询合买记录
	 * @param schemeType
	 * @param parseInt
	 * @param pagingMaxResult
	 * @return
	 */
	List<BetSchemePO> queryShowingGroupbuy(String schemeType, int parseInt,
			int pagingMaxResult);
	
	/**
	 * 判断方案对用户是否需要保密
	 * @param userId 执行查看方案这个动作的用户id
	 * @param displayMode
	 * @param betScheme
	 * @return
	 */
	public boolean needKeepSecret(long userId, int displayMode, BetScheme betScheme);

	/**
	 * 查找方案总金额限制
	 * @return
	 */
	int findTotalAmountLimit();

	/**
	 * 查询合买记录,不分页
	 * @param schemeType
	 * @return
	 */
	List<BetSchemePO> queryShowingGroupbuyWithoutPage(String schemeType);

	void computeMinAndMaxBonus(BetScheme scheme, Bet bet);
}
