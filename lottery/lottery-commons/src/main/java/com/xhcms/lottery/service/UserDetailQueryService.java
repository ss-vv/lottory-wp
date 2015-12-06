package com.xhcms.lottery.service;

import java.util.List;

import com.xhcms.lottery.commons.data.Recharge;
import com.xhcms.lottery.commons.data.Win;
import com.xhcms.lottery.commons.data.Withdraw;

/**
 * 
 * <p>Title: 用户详情查询</p>
 *
 * 
 * @author 陈岩
 * @version 1.0.0
 */
public interface UserDetailQueryService {
    
    
   /**
    * 查询提款记录
    * @param userId
    * @param firstResult
    */
    List<Withdraw> listWithdraw(Long userId, int firstResult );

    /**
     * 查询充值记录
     * @param userId
     * @param firstResult
     * @return
     */
    List<Recharge> listRecharge(Long userId, int firstResult);

    /**
     * 查询中奖记录
     * @param userId
     * @param firstResult
     * @return
     */
    List<Win> listWin(Long userId, int firstResult);
}
