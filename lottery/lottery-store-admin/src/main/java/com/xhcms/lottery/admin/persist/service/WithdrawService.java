package com.xhcms.lottery.admin.persist.service;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Withdraw;

/**
 * 
 * <p>Title: 提现服务</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
public interface WithdrawService {
    /**
     * 
     * @param paging
     * @param username
     * @param status
     * @param from
     * @param to
     */
	void listWithdraw(Paging paging, long userId, String username, int status, Date from, Date to);
	
	/**
	 * 
	 * @param withdrawId
	 * @return
	 */
	Withdraw getWithdraw(Long withdrawId);
	
	/**
	 * 批量确认，扣除该笔订单冻结的全部资金
	 * @param id
	 * @param operator
	 * @param bankOrder
	 * @param note
	 */
	void batchConfirm(List<Long> id, int operator);
	
	/**
	 * 通过初审、复审，或确认提现成功
	 * 1、确认提现成功：扣除该笔订单冻结的全部资金
	 * @param id
	 * @param operator
	 * @param bankOrder
	 * @param note
	 */
	void pass(Long id, int operator, String bankOrder, String note);
	
	/**
	 * 驳回重审
	 * @param id
	 * @param operator
	 * @param note
	 */
	void reject(Long id, int operator, String note);
	
	/**
	 * 直接驳回申请
	 * 1、未尝试打款：返还该笔订单冻结的全部资金
	 * 2、已经尝试打款：扣除手续费，返还该笔订单剩余冻结资金
	 * @param id
	 * @param operator
	 * @param note
	 */
	void fail(Long id, int operator, String note);

}
