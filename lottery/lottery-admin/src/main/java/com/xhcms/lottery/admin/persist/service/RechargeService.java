package com.xhcms.lottery.admin.persist.service;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.Recharge;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
public interface RechargeService {
    
	void listRecharge(Paging paging, long id, String username, int status, Date beginTime, Date endTime);
	
	Recharge getRecharge(long id);
	
	/**
	 * 充值审核, 已付款,待确认
	 * @param id
	 * @param operator
	 * @param note
	 */
	void pass(long id, int operator, String note);
	
	
	/**
	 * 驳回重审
	 * @param id
	 * @param operator
	 * @param note
	 */
	void reject(long id, int operator, String note);
	
	/**
	 * 直接驳回
	 * @param id
	 * @param operator
	 * @param note
	 */
	void fail(long id, int operator, String note);
	
	
	/**
     * 批量确认，向用户账户打入资金
     * @param id
     * @param operator
     */
    void batchConfirm(List<Long> id, int operator);
}
