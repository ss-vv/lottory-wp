package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.OrderPO;

public interface OrderDao extends Dao<OrderPO>{

	List<OrderPO> list(Paging paging, String username, int type, Date from, Date to);
	
	List<OrderPO> listCommission(Paging paging, Long userId, Date from, Date to);
	
	List<OrderPO> list(Paging paging, Long userId,int type, Date from, Date to);
    
	OrderPO getByRelated(int type, Long relatedId);
	
	OrderPO getByTypeAndRelated(int type, Long relatedId,Long userId);
	
	List<OrderPO> getByRelatedIds(int type,Set<Long> ids);
}
