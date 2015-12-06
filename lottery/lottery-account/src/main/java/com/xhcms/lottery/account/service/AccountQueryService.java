package com.xhcms.lottery.account.service;

import java.math.BigDecimal;
import java.util.Date;

import com.xhcms.commons.lang.Paging;

/**
 * 
 * <p>Title: 账务查询</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author jiajiancheng
 * @version 1.0.0
 */
public interface AccountQueryService {
    /**
     * 查询用户在指定投注方案中的投注总额
     * @param schemeId
     * @param userId
     * @return
     */
    BigDecimal[] sumBonus(Long schemeId, Long userId);
    
    /**
     * 查询提款记录
     * @param userId    用户
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @param status    -1 表示查询全部状态
     * @param paging
     */
    void listWithdraw(Long userId, Date begin, Date end, int status, Paging paging);

    /**
     * 查询充值记录
     * @param userId    用户
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @param status    -1 表示查询全部状态
     * @param paging
     */
    void listRecharge(Long userId, Date begin, Date end, int status, Paging paging);
    
    /**
     * 资金流水明细
     * @param paging
     * @param userId
     * @param from
     * @param to
     */
    void listJournal(Paging paging,Long userId,int type,Date from,Date to);
}
