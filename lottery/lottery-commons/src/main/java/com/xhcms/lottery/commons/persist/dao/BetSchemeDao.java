package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

/**
 * 
 * <p>Title: 投注方案</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public interface BetSchemeDao extends Dao<BetSchemePO> {

	/**
	 * 查询已经到截止时间并且状态为可参与的合买
	 * @param from
	 * @param to
	 * @return
	 */
	List<Long> findDeadlinesGroupbuySchemeIds(Date from,Date to);
	
	/**
	 * 更新合买流标方案状态为流标
	 * @param schemeId
	 * @return 
	 */
	void forceGroupBuyFlow(Long schemeId);
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
    List<BetSchemePO> find(Paging paging, Date from, Date to, int status, String lotteryId, Long schemeId, String sponsor, String playId, String passType);

    /**
     * 按投注状态查询方案
     * 
     * @param saleStatus
     * @param from
     * @param to
     * @return
     */
    List<Long> findBySaleStatus(int saleStatus, Date from, Date to);
    
    /**
     * 按id集合查询投注方案
     * @param ids
     * @return
     */
    List<BetSchemePO> find(Collection<Long> ids);
    
    /**
     * 按晒单id查询跟单方案列表
     * @param followedSchemeId
     * @return
     */
    List<BetSchemePO> findFollowSchemes(Long id);
    
    /**
     * 按晒单id和用户id查询跟单方案列表
     * @param followedSchemeId
     * @param userid
     * @return
     */
    List<BetSchemePO> findFollowSchemesByUser(Long followedSchemeId,Long userid) ;
    
    /**
     * 查询可出票的方案
     * @param from
     * @return [lotteryId, schemeId]
     */
    List<BetSchemePO> findByStatus(int status, Date from);
    
    /**
     * 按条件查询在售(可跟单购买)的晒单方案
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
    List<BetSchemePO> findOnSaleShowingScheme(Paging paging, int status, String lotteryId, Long schemeId, String sponsor, String playId, String passType);
    
    /**
     * 
     * @param paging
     * @param status
     * @param lotteryId
     * @param schemeId
     * @param sponsor
     * @param playId
     * @param passType
     * @return
     */
	List<BetSchemePO> findOnGroupbuyScheme(Paging paging, int status, String lotteryId, Long schemeId, String sponsor, String playId,
			String passType);
	
    /**
     * 查询我参加的跟单列表
     * @param paging
     * @param status
     * @param lotteryId
     * @param schemeId
     * @param sponsor
     * @param playId
     * @param passType
     * @param from
     * @param to
     * @return
     */
    List<BetSchemePO> findMyJoinFollowScheme(Paging paging, int status, String lotteryId, Long schemeId, Long sponsorid, String playId, String passType,Date from,Date to);
    
    /**
     * 查询用户发起的跟单列表
     * @param paging
     * @param status
     * @param lotteryId
     * @param schemeId
     * @param sponsor
     * @param playId
     * @param passType
     * @param starttime
     * @param offtime
     * @return
     */
    List<BetSchemePO> findLaunchShowingScheme(Paging paging, int status,String lotteryId, Long schemeId, String sponsor, String playId,String passType,Date starttime,Date offtime);
    
    List<BetSchemePO> findLaunchShowingScheme(Paging paging, int status,String lotteryId, Long schemeId, Long sponsorId, String playId,String passType,Date starttime,Date offtime);
    
    /**
     * 查询我参加的合买列表
     * @param paging
     * @param status
     * @param lotteryId
     * @param sponsorid
     * @param playId
     * @param passType
     * @param from
     * @param to
     * @return
     */
    List<BetSchemePO> findMyJoinGroupbuyScheme(Paging paging, int status, String lotteryId, Long sponsorId, String playId, String passType,Date from,Date to);
    
    /**
     * 查询我发起的合买列表
     * @param paging
     * @param status
     * @param lotteryId
     * @param sponsorId
     * @param playId
     * @param passType
     * @param from
     * @param to
     * @return
     */
    List<BetSchemePO> findLaunchGroupbuyScheme(Paging paging, int status, String lotteryId, Long sponsorId, String playId,String passType,Date from,Date to);
    /**
     * 查询推荐方案。
     * @param paging 翻页对象
     * @param isOnSale true 只查询在售的，即停售时间大于当前时间的方案。false 查询全部的方案。
     */
    List<BetSchemePO> findRecommended(Paging paging, boolean isOnSale);
    
	List<BetSchemePO> findRecommended(Paging paging, boolean isOnSale, int type);

    /**
     * 查询晒单合买已派奖的方案
     */
    List<BetSchemePO> getWinSchemeList();

    /**
     * 根据partnerid返回方案列表
     * @param ids
     * @return
     */
    List<Object[]> findBetSchemeListByPartnerIds(Set<Long> ids);
    
    /**
     * 返回用户有佣金支出的购买记录
     * @param paging
     * @param userId
     * @param from
     * @param to
     * @return
     */
    List<Object[]> findCommissionOutList(Paging paging, Long userId,Date from,Date to);
    
     /**
     * 
     * @param criteria
     * @return
     */
    List<?> findBetSchemeByDetachCriteria(DetachedCriteria criteria, Paging paging);

    /**
     * 返回在指定的彩票id列表里，创建时间大于from且状态为status的方案列表
     * @param status
     * @param from
     * @param targetLotteryIdList
     * @return
     */
	List<BetSchemePO> findByStatusAndLotteryIdList(int status, java.sql.Date from,
			List<String> targetLotteryIdList);

	List<BetSchemePO> queryShowingScheme(String schemeType,
			Integer firstResult, Integer pageMaxResult);

	/**
	 * 取得最新5个中奖方案
	 * @return
	 */
	List<BetSchemePO> findNewestWinScheme(String lotteryId, int status, int maxResults);
	
	
	/**
     * 按晒单id查询跟单方案列表,带有分页
     * @param followedSchemeId
     * @return
     */
    List<BetSchemePO> findFollowSchemesWithPager(Long id, int startPosition);
    
    /**
     * 查询一段时间内用户的投注成功出票注数
     * @param start
     * @param end
     * @return
     */
    List<Object[]> findBetSuccessNote(Long userId, Date start, Date end);
    
    /**
     * 查询用户过关投注成功出票注数
     * @param userId
     * @param start
     * @param end
     * @return
     */
    List<Object[]> findBetPassSuccessNote(Date start, Date end);

	List<BetSchemePO> queryShowingSchemeAll(String schemeType,
			Integer firstResult, Integer pageMaxResult);
	
	List<BetSchemePO> findByStatus(int status, Date from, Date to);
	
	List<BetSchemePO> findByStatusDesc(int status, Date from, Date to);
	
	/**
	 * 根据状态以及 销售状态（!saleStatus）查询
	 * @param status
	 * @param saleStatus 
	 * @param from
	 * @param to
	 * @return
	 */
	List<BetSchemePO> findByStatusAndNotSaleStatus(int status,int saleStatus, Date from, Date to);
	
	List<BetSchemePO> findBySponsorAndShowScheme(Long sponsorId,boolean isShowScheme, Date from, Date to);
	
	List<BetSchemePO> findScheme(int publishShow, int type, Date from,
			int status, int maxResults);
	
	/**
	 * 查询特殊用户发起的方案且方案出票成功，方案已截止
	 * @param lotteryId 彩种Id
	 * @return
	 */
	List<Object> findProfessionalSchemeTicketSuccess(String lotteryId);
	
	/**
	 * 查询竞彩足球可以进行预兑奖的方案的赛事Id集合
	 * @param schemeId 方案Id集合
	 * @return
	 */
	List<Long> findFBPresetMatchIds(long schemeId);
	
	/**
	 * 查询竞彩篮球可以进行预兑奖的方案的赛事Id集合
	 * @param schemeId 方案Id集合
	 * @return
	 */
	List<Long> findBBPresetMatchIds(long schemeId);
	/**
	 * 获取最新的4个晒单id
	 * @return
	 */
	List<Object> getLastFourSchemeId();
	
	Integer getShowBetShemeNum(String beginTime,String endTime);
	/**
	 * 晒单助人50单
	 * @return
	 */
	List<Object[]> findMatch50ShowSchemeWin();
	/**
	 * 跟单中奖50单
	 * @return
	 */
	List<Object[]> findMatch50FollowSchemeWin();
	/**
	 * 七天晒单胜率
	 * @return
	 */
	List<Object[]> findDay7ShowSchemeWinLiLv();
	/**
	 * 50单晒单胜率
	 * @return
	 */
	List<Object[]> findMatch50ShowSchemeWinLilv();
	/**
	 * 跟单50场胜率
	 * @return
	 */
	List<Object[]> findMath50FollowSchemeWinLiLv();
	/**
	 * 七天跟单胜率
	 * @return
	 */
	List<Object[]> findDay7FollowSchemeWinLiLv();
	/**
	 * 查询昨天所有投注的方案
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<BetSchemePO> findYesterdayScheme(Date beginTime,Date endTime);
	
	List<Object[]> findBetRecord(String lotteryId, long userId, int count);
	
	List<Object[]> queryShowingSchemeBy(String fromDate,
			String followCountSort, String buyAmountSort,
			String rateOfReturnSort, String timeSort,String lottery);

	List<BetSchemePO> queryShowingGroupbuy(String schemeType, int parseInt,
			int pagingMaxResult);

	List<BetSchemePO> findOrderByOfftimeAsc(Paging paging, Date from, Date to,
			int status, String lotteryId, Long schemeId, String sponsor,
			String playId, String passType);

	List<BetSchemePO> queryShowingGroupbuyWithoutPage(String schemeType);
}
