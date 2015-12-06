package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.WinDaoWithBetScheme;
import com.xhcms.lottery.commons.persist.entity.WinPOWithSchemePO;

public class WinDaoWithBetSchemeImpl extends DaoImpl<WinPOWithSchemePO> implements WinDaoWithBetScheme {

	private static final long serialVersionUID = 8860331879101988406L;

	public WinDaoWithBetSchemeImpl() {
		super(WinPOWithSchemePO.class);
	}
	
	@Override
	public List<WinPOWithSchemePO> findByLotteryId(String lotteryId,
			Paging paging, long userId, Date begin, Date end) {
		
		PagingQuery<WinPOWithSchemePO> q = pagingQuery(paging);
		q.alias("scheme", "s");
		
		if(lotteryId != null){
             q.add(Restrictions.eq("s.lotteryId", lotteryId));
        }
        if (userId > 0) {
            q.add(Restrictions.eq("userId", userId));
        }
        if (begin != null) {
            q.add(Restrictions.ge("createdTime", begin));
        }
        if (end != null) {
            q.add(Restrictions.lt("createdTime", end));
        }
        q.desc("createdTime");
        
        return q.list();
	}
	



}
