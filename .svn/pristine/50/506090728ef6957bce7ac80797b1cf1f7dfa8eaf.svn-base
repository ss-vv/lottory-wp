package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.WinPOWithSchemePO;

public interface WinDaoWithBetScheme extends Dao<WinPOWithSchemePO> {
    
	List<WinPOWithSchemePO> findByLotteryId(String lotteryId,Paging paging, long userId, Date begin, Date end);
	
}
