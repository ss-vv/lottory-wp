package com.xhcms.lottery.commons.persist.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.PrintableFilePO;
import com.xhcms.lottery.commons.persist.entity.TicketPO;

public interface TicketDao extends Dao<TicketPO> {
    /**
     * 根据投注方案ID查询出票记录
     * @param schemeId
     * @param status -1：表示查询所有状态
     * @return
     */
    List<TicketPO> findByScheme(Long schemeId, int status);
    
    /**
     * 查询方案包含票的信息
     * @param schemeId	方案状态
     * @param status	票的状态
     * @return
     */
    List<TicketPO> findByScheme(Long schemeId, List<Integer> status);

    /**
     * 测试类，查找出票成功并且成功扣款的票id集合
     * @param status
     * @param from
     * @param to
     * @return
     */
    List<Long> findByStatusTest(int status, Date from, Date to);
    
    /**
     * 分页查询出票记录
     * 
     * @param betSchemeIds 方案id列表
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @param status    -1：表示查询所有状态
     * @param paging
     * @return
     */
    List<TicketPO> find(List<Long> betSchemeIds, Date startDate, Date endDate, int status, Paging paging);
    
    /**
     * 按出票状态查询彩票ID
     * @param status
     * @param from
     * @param to
     * @return
     */
    List<Long> findByStatus(int status, Date from, Date to);
    
    /**
     * 按ID查询出票记录
     * @param ids
     * @return
     */
    List<TicketPO> find(Collection<Long> ids);
    
    /**
     * 更新彩票状态
     * @param ids 要更新的彩票ID集合
     * @param oldStatus 老状态
     * @param newStatus 新状态
     * @param message
     */
    void updateStatus(Collection<Long> ids, int oldStatus, int newStatus, String message);
    
    /**
     * 按方案更新彩票状态
     * @param ids 要更新的彩票ID集合
     * @param oldStatus 老状态
     * @param newStatus 新状态
     * @param message
     */
    void updateStatusByScheme(Collection<Long> sids, int oldStatus, int newStatus, String message);
    
    /**
     * 统计方案出票成功或失败的总票数
     * @param schemes
     * @return
     */
    List<Object[]> sumBoughtNote(Collection<Long> schemes);
    
    /**
     * 统计方案已兑奖注数和金额
     * @param schemes
     * @return 对象数组列表，列表的每一项为大小为5的对象数组，从0到4，分别
     * 代表schemeId,status,sum(note),sum(preTaxBonus),sum(afterTaxBonus)
     */
    List<Object[]> sumPrized(Collection<Long> schemes);

	
	
	/**
	 * 撤销多张票，根据方案id集合
	 * @param oldStatus
	 * @param sids
	 */
	void cancelTicketsBySchemeIds(int oldStatus,Collection<Long> sids);

	/**
	 * 列出lotteryId在指定列表中，并且没有中奖的ticket。按playId和日期分组
	 * @param targetLotteryIds
	 * @return
	 */
	List<TicketPO> listNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndDate(
			List<String> targetLotteryIds);
	
	/**
	 * 列出lotteryId在指定列表中，并且没有中奖的ticket。按playId和期号分组
	 * @param targetLotteryIds
	 * @return
	 */
	List<TicketPO> listNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndIssue(
			List<String> targetLotteryIds);
	
	/**
	 * 列出lotteryId在指定列表中，并且没有开奖的ticket。
	 * @param targetLotteryIds
	 * @return
	 */
	List<TicketPO> listNotPrizeAndLotteryIdInTargetListTicket(
			List<String> targetLotteryIds);
	
	/**
	 * 根据玩法、日期、状态查询票信息
	 * @param playId
	 * @param creatTime
	 * @param status
	 * @return
	 */
	List<TicketPO> find(String playId, Date createdTime, List<Integer> status);

	/**
	 * 列出未开奖的竞彩混合过关票
	 */
	List<TicketPO> listNotPrizedHHTickets();

	/**
	 * 批量更新预兑奖票状态
	 * @param oldStatus
	 * @param newStatus
	 * @param message
	 */
	void updateStatusByPresetTicket(Long schemeId, int oldStatus, int newStatus, String message);

	TicketPO findById(Long id);

	/**
	 * 如果status为-1，则不更新status字段；否则，更新status字段和其他字段
	 * @param id
	 * @param status
	 * @param actualStatus
	 * @param message
	 * @param lotteryPlatformId
	 * @param actualCode
	 * @param number 
	 * @param odds 
	 */
	void updateTicketStatusAndLotteryPlatformIdAndActualCode(Long id,
			int status, int actualStatus, String message,
			String lotteryPlatformId, String actualCode, String number, String odds);

	/**
	 * 列出尊奥lotteryId在指定列表中，并且没有中奖的ticket。按playId和日期分组
	 * @param jCLotteryIdList
	 * @return
	 */
	List<TicketPO> listZunAoNotPrizeAndLotteryIdInTargetListTicketGroupByPlayIdAndDate(
			List<String> jCLotteryIdList);

	/**
	 * 列出尊奥未开奖的竞彩混合过关票
	 * @return
	 */
	List<TicketPO> listZunAoNotPrizedHHTickets();

	List<TicketPO> listNotPrizeTicketWithTargetLotteryPlatformId(
			String lotteryPlatformId);

	/**
	 * 查找尊奥指定状态值的票
	 * @param ticketRequired
	 * @param from
	 * @param object
	 * @return
	 */
	List<Long> findZunAoTicketByStatus(int ticketRequired, Date from, Date to);
	List<TicketPO> find(Paging paging, Date begin, Date end, int status, String lotteryId, Long schemeId, Long ticketId);

	/**
	 * 查询彩票平台id在指定的实体店平台id中，状态为3的竞彩足球票的总数和总金额和最早截止时间
	 * @param availableShiTiDian
	 * @return 返回对象数组，第一项是最早截止时间，第二项是总钱数，第三项是总数
	 */

	Object[] findShiTiDianFBMinEntrustDeadLine(List<String> availableShiTiDian);

	/**
	 * 查询彩票平台id在指定的实体店平台id中，状态为3的竞彩篮球票的总数和总金额和最早截止时间
	 * @param availableShiTiDian
	 * @return 返回对象数组，第一项是最早截止时间，第二项是总钱数，第三项是总数
	 */
	Object[] findShiTiDianBBMinEntrustDeadLine(List<String> availableShiTiDian);
	
	/**
	 * 查询彩票平台id在指定的实体店平台id中，状态为3的传统足彩票的总数和总金额和最早截止时间
	 * @param availableShiTiDian
	 * @return 返回对象数组，第一项是最早截止时间，第二项是总钱数，第三项是总数
	 */

	Object[] findShiTiDianCtfbMinEntrustDeadLine(List<String> availableShiTiDian);

	/**
	 * 查询分配给实体店的票，并且按照票对应的方案的截止时间做升序排列，按照票的总钱数做降序排列,不分页
	 * 
	 * @param begin
	 * @param end
	 * @param status
	 * @param lotteryId 必须提供
	 * @param schemeId
	 * @param ticketId
	 * @param lotteryPlatformId TODO
	 * @return
	 */
	
	List<Object> findShiTiDianTicketsAndOrderByDeadline( Date begin, Date end,
			int status, String lotteryId, Long schemeId, Long ticketId, String lotteryPlatformId);

	/**
	 * 查询分配给实体店的票，并且按照票对应的方案的截止时间做升序排列，按照票的总钱数做降序排列,分页
	 * @param paging
	 * @param begin
	 * @param end
	 * @param status
	 * @param lotteryId
	 * @param schemeId
	 * @param ticketId
	 * @param lotteryPlatformId TODO
	 * @return
	 */
	List<Object> findShiTiDianTicketsAndOrderByDeadlineWithPaging(
			Paging paging, Date begin, Date end, int status, String lotteryId,
			Long schemeId, Long ticketId, String lotteryPlatformId);

	List<PrintableFilePO> findHighSpeedPrintFilePagingByTicketId(Paging paging,
			Long ticketId, String lotteryPlatformId);
	
	List<Object[]> findTicketWithOfftime(List<Long> ptIds);

	List<PrintableFilePO> findHighSpeedPrintFileByTime(Date from, Date to,
			String lotteryPlatformId);

	void updateLotteryPlatformId(List<Long> printableTicketIds,
			String exportToShitidian);

	void updateTicketOdds(Long id, String odds);
	
	List<TicketPO> findBySchemeWithLock(Long schemeId, Integer status);
}
