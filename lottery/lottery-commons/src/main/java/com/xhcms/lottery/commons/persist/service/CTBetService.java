package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.CTBetRequest;
import com.xhcms.lottery.commons.data.ctfb.CTBetContent;

/**
 * 传统足彩投注服务。
 * 
 * @author Yang Bo
 */
public interface CTBetService {
	
	/**
	 * 准备要购买的投注方案对象。
	 * @param betRequest 投注请求
	 * @return 包含注数、金额的投注方案。
	 * @throws BetException 准备方案失败
	 */
	public BetScheme prepareScheme(CTBetRequest betRequest) throws BetException;
	
	/**
	 * 确认要购买方案的有效性。
	 * @param scheme 要购买的方案
	 * @return 生成的投注方案。
	 * @throws BetException
	 */
	public void confirmScheme(BetScheme scheme) throws BetException;
	
	/**
	 * 购买投注方案。
	 * @param scheme 投注方案
	 * @return 已经购买了的投注方案。
	 * @throws BetException
	 */
	public BetScheme buyScheme(BetScheme scheme) throws BetException;
	
	/**
	 * 查询投注内容
	 * @param schemeId
	 * @return
	 */
	public List<CTBetContent> findCtBetContent(Long schemeId);
}
