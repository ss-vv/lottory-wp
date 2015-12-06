package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.WinPO;

public interface WinDao extends Dao<WinPO> {
    
	List<WinPO> find(Paging paging, long userId, Date begin, Date end);

	List<WinPO> findWithPage(Long userId, int firstResult);
	
}
