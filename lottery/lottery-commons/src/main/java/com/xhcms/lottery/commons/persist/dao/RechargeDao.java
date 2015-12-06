package com.xhcms.lottery.commons.persist.dao;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.RechargePO;

public interface RechargeDao extends Dao<RechargePO>{
    
    /**
     * 查询充值记录
     * @param username    用户
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @param status    -1 表示查询全部状态
     * @param paging
     * @return 
     */
    List<RechargePO> find(long userId, String username, Date startDate, Date endDate, int status, Paging paging);
    
    List<RechargePO> find(Collection<Long> id);
    
    RechargePO findById(Long id);

	List<RechargePO> findWithPage(Long userId, int firstResult);
	
	/**
	 * 查询有投注记录的用户ID集合
	 * @return
	 */
	List<BigInteger> findRechargeUserId();
}
