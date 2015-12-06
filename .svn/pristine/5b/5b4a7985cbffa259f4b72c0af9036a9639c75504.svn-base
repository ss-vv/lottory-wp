package com.xhcms.lottery.dc.persist.service;

import java.util.List;
import java.util.Map;

import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.BetSchemeWithIssueInfoPO;

public interface TicketService {

	/**
	 * 查询已经到截止时间的可参与的方案id
	 * @param second
	 * @return
	 */
	List<Long> listDeadlinesGroupbuyScheme(int second);
	
    /**
     * 查询所有已请求出票的彩票ID
     * @return
     */
    Map<String, List<Long>> listAllowBuyScheme();
    
    /**
     * 查询竞彩的所有可出票的彩票对象，并按 playId 合并返回。
     */
    Map<String, List<BetSchemePO>> listJCAllowBuySchemesGroupByPlayId();
    
    /**
     * 强制合买购票或流标
     * @return
     */
    void forceBuyTicketAndFlow(Long schemeId);
    
    /**
     * 查询所有已请求出票的彩票ID
     * @return
     */
    List<Long> listRequiredTicket();
    
    /**
     * 查询出票成功（失败）但未兑奖的彩票ID
     * @return
     */
    List<Long> listNotPrizeTicket();
    
    /**
     *测试方法，查询已经出票成功并且扣款成功的票id集合 
     */
    List<Long> listNotPrizeTicketTest();
    
    /**
     * 出票条件：
     * 1、代购类投注时出票，方案进度100%；
     * 2、合买类达到出票条件即可出票，此时方案可能允许继续跟单，即进度未达到100%
     * 
     * 方案进度达到100%时执行该方法，对方案合买人进行扣款（出票成功部分）和返还（出票失败部分）操作
     * @param schemeId 
     * 
     */
    void deductAndReturn(Long schemeId);
    
    /**
     * 查询近一周（时间不精确）满员未扣款的方案
     * @return
     */
    List<Long> listStopScheme();
    
    /**
     * 列出没有中奖信息的竟彩ticket。按照playId和时间分组。
     */
    List<Ticket> listZunAoJCNotPrizeTicketGroupByPlayIdAndDate();
    
    List<Ticket> listCTZCNotPrizeTickets();
    
    /**
     * 查询没有开奖的北单票的信息
     * @return
     */
    List<Ticket> listBJDCNotPrizeTickets();
    
    /**
     * 列出没有中奖信息的高频彩ticket。按照playId和期号分组。
     */
	List<Ticket> listHFNotPrizeTicketGroupByPlayIdAndIssue();
	
	/**
	 * 列出没有中奖信息的重庆时时彩ticket。按照playId和期号分组。
	 */
	List<Ticket> listCQSSNotPrizeTicketGroupByPlayIdAndIssue();
    
    /**
     * 查询高频彩的所有可出票的彩票对象，并按 PlayId 合并返回。
     */

	Map<String, List<BetSchemeWithIssueInfoPO>> listHFAllowBuySchemesGroupByPlayId();

	/**
     * 查询竞彩的所有彩票id在targetLotteryIdList中得可出票的彩票对象，并按 playId 合并返回。
     */
	Map<String, List<BetSchemePO>> listAllowBuySchemesWhereLotteryIdInTargetListGroupByPlayId(
			List<String> targetLotteryIdList);

	/**
     * 查询高频彩的所有彩票id在targetLotteryIdList中得可出票的彩票对象，并按 playId 合并返回。
     */
	Map<String, List<BetSchemeWithIssueInfoPO>> listHFAllowBuySchemesWhereLotteryIdInTargetListGroupByPlayId(
			List<String> targetLotteryIdList);

	/**
	 * 列出传统足彩可出票的方案。
	 */
	Map<String, List<BetSchemePO>> listCTAllowBuySchemesGroupByPlayId();
	
	/**
	 * 列出传统北单可出票的方案。
	 */
	Map<String, List<BetSchemePO>> listBDAllowBuySchemesGroupByPlayId();
	
	/**
	 * 查询福彩可出票的方案
	 * @return
	 */
	Map<String, List<BetSchemePO>> listWFAllowBuySchemesGroupByPlayId();
	
	List<Ticket> listWFNotPrizeTickets();

	/**
	 * 列出指定lotteryPlatformId的没有中奖信息的竟彩ticket
	 * @param lotteryPlatformId
	 * @return
	 */
	List<Ticket> listNotPrizeTicketWithTargetLotteryPlatformId(
			String lotteryPlatformId);
}
