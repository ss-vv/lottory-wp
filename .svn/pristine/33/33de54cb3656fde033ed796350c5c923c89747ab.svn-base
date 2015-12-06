package com.xhcms.lottery.commons.persist.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetRecord;

/**
 * 
 * <p>
 * Title: 账务查询
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright：Copyright (c) 2011
 * </p>
 * <p>
 * Company：XingHui Spirit (Beijing) Technical Co.,Ltd.
 * </p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public interface AccountQueryService {
	/**
	 * 查询用户在指定投注方案中的投注总额
	 * 
	 * @param schemeId
	 * @param userId
	 * @return
	 */
	BigDecimal[] sumBonus(Long schemeId, Long userId);

	/**
	 * 查询提款记录
	 * 
	 * @param userId
	 *            用户
	 * @param startDate
	 *            起始日期
	 * @param endDate
	 *            终止日期
	 * @param status
	 *            -1 表示查询全部状态
	 * @param paging
	 */
	void listWithdraw(Long userId, Date begin, Date end, int status,
			Paging paging);

	/**
	 * 查询充值记录
	 * 
	 * @param userId
	 *            用户
	 * @param startDate
	 *            起始日期
	 * @param endDate
	 *            终止日期
	 * @param status
	 *            -1 表示查询全部状态
	 * @param paging
	 */
	void listRecharge(Long userId, Date begin, Date end, int status,
			Paging paging);

	/**
	 * 查询投注记录
	 * 
	 * @param lotteryId
	 * @param userId
	 * @param begin
	 * @param end
	 * @param status
	 * @param firstResult
	 * @param maxResult
	 * @param type
	 * @param showScheme
	 */
	List<BetRecord> listBetHistory(String lotteryId, Long userId, Date begin,
			Date end, int status, int firstResult, int maxResult, int type,
			int showScheme);
	 /**
     * 查询投注记录，支持自动分页
     * @param userId    用户
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @param status -1：查询全部
     * @param paging
     * @param type
     * @param showScheme
     */
	void listBetHistory(String lotteryId, Long userId, Date begin, 
			Date end, int status, Paging paging,int type,int showScheme);

	/**
	 * 资金流水明细
	 * 
	 * @param paging
	 * @param userId
	 * @param from
	 * @param to
	 */
	void listJournal(Paging paging, Long userId, int type, Date from, Date to);
	
	void listBetRecordByDuration(Long userId, String lotteryId, int duration,
			int status, Paging paging, int type, int showScheme);

	List<BetRecord> listBetRecord(Long userId, String lotteryId, int duration,
			int status, Paging paging, int type, int showScheme);
	
	List<BetRecord> listBetRecord(Long userId, String[] lotteryId, int duration,
			int status, Paging paging, int type, int showScheme);
	
	List<BigInteger> findRechargeUserId();
}
