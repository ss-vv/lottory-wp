package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.BetPartner;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.DigitalBetRequest;
import com.xhcms.lottery.commons.data.DigitalBetContent;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.lang.PlayType;

/**
 * 数字彩投注服务。
 * 
 * @author Yang Bo
 *
 */
public interface DigitalBetService extends BetSchemeBaseService {

	/**
	 * 直接投注数字彩，主要是用于客户端接口，确认工作已经由客户端完成，调用本方法会直接完成投注。
	 * 
	 * @param userId 用户id
	 * @param betContent 投注内容。
	 * @return 投注方案对象。失败抛出异常。
	 * @throws BetException 导致不能正常投注的异常 
	 */
	BetScheme bet(DigitalBetRequest betContent) throws BetException;
	
	/**
	 * 确认投注数字彩方案，入库、扣费。所有数字彩，如果没有特殊需求，都可以用此方法投注。
	 * @param scheme 待确认投注的方案对象。
	 * @return 投注成功后的方案
	 * @throws BetException 如果投注失败
	 */
	BetScheme betConfirm(BetScheme scheme) throws BetException;
	
	/**
	 * 准备待确认的数字彩投注方案。
	 * @param request 数字彩投注请求。
	 * @return 未保存的方案对象
	 * @throws BetException 如果方案不合法。
	 */
	BetScheme prepareBet(DigitalBetRequest betRequest) throws BetException;
	
	/**
	 * 列出方案的所有票
	 * @param schemeId 方案id
	 * @return 方案的所有票
	 */
	List<Ticket> listTicketsOfScheme(long schemeId);

	/**
	 * 根据方案ID获得用户高频彩投注内容
	 * @param schemeId 方案ID
	 * @return 投注内容列表
	 */
	List<DigitalBetContent> findHfBetContent(Long schemeId);

	/**
	 * 查找方案的所有参与购买人。
	 * @param schemeId
	 * @return
	 */
	List<BetPartner> findBetPartners(long schemeId);

	/**
	 * 从投注内容格式推出玩法类型。
	 * @param bet 投注内容
	 * @return 玩法类型
	 */
	PlayType deduceSSQPlayType(String bet);
}
