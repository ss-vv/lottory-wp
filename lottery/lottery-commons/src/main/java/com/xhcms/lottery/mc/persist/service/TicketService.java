package com.xhcms.lottery.mc.persist.service;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.client.TranslateException;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.data.PrintableTicket;
import com.xhcms.lottery.commons.data.PrintableFile;
import com.xhcms.lottery.commons.data.RemoteTicket;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.TicketRemotePO;

public interface TicketService {

    /**
     * 准备出票，设置投注方案状态为“已请求出票”
     * @param schemes
     */
    List<Ticket> buyTicket(Long schemeId);
    
    /**
     * 查询已请求出票或出票中的彩票ID
     * @return
     */
    List<Long> listRequired();
    
    /**
     * 更新出票状态
     * @param map
     */
    void updateTicketStatus(Map<Integer, List<Long>> map);
    
    /**
     * 更新出票状态为购买失败状态。
     * @param msgIdsMap - key 是错误描述，value是ticket id list。
     */
    //void updateTicketToBuyFailStatus(Map<String, List<Long>> msgIdsMap);
    
    /**
     * 确认出票信息
     * @param tickets
     */
    void check(Map<Long, Ticket> tickets);
    
    /**
     * 兑奖
     * @param tickets 键为ticket的id，值为ticket的map
     */
    void prize(Map<Long, Ticket> tickets);
    
    /**
     * 查询最近一周已请求出票状态的彩票ID
     */
    List<Ticket> listRequiredTicket();
    
    /**
     * 查询最近一周已出票状态的彩票ID
     */
    List<Ticket> listBuySuccessTickets();
    
    /**
     * 获取ticket对应的方案
     */
    BetScheme getBetSchemeOfTicket(Long tiecketId);
    
    /**
     * 获取某方案的所有 tickets.
     */
    List<Ticket> listTicketsOfScheme(Long schemeId);

	/**
	 * 调用“002”接口发送"订购票"请求后，根据返回值更新票的状态。<br/>
	 * 对于接票失败的情况，票的状态变为“未出票”，同时设置message字段和actual_status字段为非0，
	 * 让管理员手工发起重发。
	 * @param ticketId 票号
	 * @param statusCode 中民接口返回的状态码
	 * @param errorMsg 错误描述内容
	 */
	void updateTicketStatusForOrder(long ticketId, String statusCode,
			String errorMsg);

	/**
	 * 列出指定日期前未中奖的票
	 * @param endTime 查询截止日期
	 * @return 票的id
	 */
	List<Long> listNotPrizeTicketBefore(Date endTime);
	
    /**
     * 获取一个ticket
     */
	Ticket getTicket(Long ticketId);
	
	/**
	 * 保存ticket状态。
	 * @param tickets
	 */
	void saveTicketStatus(Collection<Ticket> tickets);
	
	/**
	 * 实体店出票
	 * @param ticketId 票的ID
	 * @return
	 */
	boolean physicalStoreTicket(long ticketId);
	
	/**
	 * 撤单需要实体店出的票
	 * @param ticketId
	 * @return
	 */
	boolean cancelStoreTicket(long ticketId);
	
	/**
	 * 找到可以自动派奖的方案id列表
	 * @param sMap
	 * @return
	 */
	List<Long> getSchemeIdList4AutoAward(HashMap<Long, BetSchemePO> sMap);

	/**
	 * 更新 tickets 的状态为 status 。
	 * @param status  用来更新的状态
	 * @param actualStatus 用来更新的接口状态
	 * @param tickets 被更新状态的票
	 */
	void updateTicketsStatusTo(int status, int actualStatus, Collection<Long> tickets);

	/**
	 * 用出票中心给出的odds更新ticket对象关联的比赛的属性，即赔率、预设分值属性。支持混合过关方式。
	 * <br/>
	 * 非混合的竞彩赔率格式：<br/>
	 * 1.700-1.700-1.750A1.8-1.750@173.5B152.5B190.5B174.5 <br/><br/>
	 * 混合过关方式的格式：<br/>
	 * RFSF@5-305[主胜=1.68,主负=123^-5.5]/SF@5-306[客胜=3.4]/FC@5-307[负21-25=120]
	 * 
	 * @param tickets 要更新的票。
	 * @throws TranslateException 如果解析赔率格式有错
	 */
	void updatePlayMatchByPlatformOdds(List<Ticket> tickets,Map<String, PlayMatch> matches);

	void updateTicketStatusAndLotteryPlatformIdAndActualCode(Long id,
			int status, int actualStatus, String message,
			String lotteryPlatformId, String actualCode, String number, String odds);

	public abstract boolean isZMSendTicketOKStatus(String statusCode);
	
	/**
	 * 查询实体店出票列表
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
	List<Ticket> listPhysicalStoreTicket(Paging paging, Date begin, Date end, int status, String lotteryId, Long schemeId, Long ticketId, String lotteryPlatformId);

	/**
	 * 查询实体店出票列表,不分页
	 * @param from
	 * @param to
	 * @param state
	 * @param lotteryId
	 * @param schemeId
	 * @param ticketId
	 * @param lotteryPlatformId TODO
	 * @return
	 */
	List<Ticket> listPhysicalStoreTicketWithoutPaging(Date from, Date to, int state,
			String lotteryId, Long schemeId, Long ticketId, String lotteryPlatformId);

	/**
	 * 查询可高速打印的票，分页
	 * @param paging
	 * @param from
	 * @param to
	 * @param state
	 * @param lotteryId
	 * @param schemeId
	 * @param ticketId
	 * @param lotteryPlatformId TODO
	 * @return
	 */
	List<PrintableTicket> listPrintableTickets(Paging paging, Date from,
			Date to, int state, String lotteryId, Long schemeId, Long ticketId,String playId, String lotteryPlatformId);

	/**
	 * 列出可以给高速打印使用的玩法id
	 * @param lotteryId 
	 * @return
	 */
	List<String> listAvaiablePlayIds4PrintableTickets(String lotteryId);
	/**
	 * 实体店票下的可高速打印文件
	 * @param paging
	 * @param from
	 * @param to
	 * @param ticketId
	 * @param lotteryPlatformId TODO
	 * @return
	 */
	List<PrintableFile> listHighSpeedPrintFileByTicketId(Paging paging,
			Date from, Date to, Long ticketId, String lotteryPlatformId);
	
	/**
	 * 
	 * @param ticketId 
	 * @return
	 */
	boolean tickExportSuccess(Long ticketId);
	 
	boolean physicalStoreTickeByBatch(List<Long> ticketIds);

	boolean cancelStoreTicket(List<Long> ticketIds);
	
	public void checkYuanChengChuPiao(Map<Long,TicketRemotePO> trMap);

	void updateOdds(List<RemoteTicket> ts);
}
