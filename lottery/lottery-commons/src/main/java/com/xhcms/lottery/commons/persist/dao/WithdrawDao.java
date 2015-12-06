package com.xhcms.lottery.commons.persist.dao;


import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.WithdrawPO;

/**
 * 
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author xulang
 * @version 1.0.0
 */
public interface WithdrawDao extends Dao<WithdrawPO> {
    /**
     * 查询提款记录
     * @param username    用户
     * @param startDate 起始日期
     * @param endDate   终止日期
     * @param status  -1 表示查询全部状态
     * @param paging
     * @return
     */
    List<WithdrawPO> find(long userId, String username, Date startDate, Date endDate, int status, Paging paging);

    List<WithdrawPO> find(Collection<Long> id);

	List<WithdrawPO> findWithPage(Long userId, int firstResult);

	WithdrawPO findById(long id);
}
