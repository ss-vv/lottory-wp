package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.GrantDao;
import com.xhcms.lottery.commons.persist.entity.GrantPO;

public class GrantDaoImpl extends DaoImpl<GrantPO> implements GrantDao {
	private static final long serialVersionUID = 4280232075877966647L;
	
	public GrantDaoImpl() {
		super(GrantPO.class);
	}

	@Override
	public List<GrantPO> find(Paging paging, long userId, Date begin, Date end, Integer status) {
		PagingQuery<GrantPO> q = pagingQuery(paging);
        if (userId > 0) {
            q.add(Restrictions.eq("userId", userId));
        }
        if (begin != null) {
            q.add(Restrictions.ge("createdTime", begin));
        }
        if (end != null) {
            q.add(Restrictions.lt("createdTime", end));
        }
        if (status != -1) {
        	 q.add(Restrictions.eq("status", status));
        }
        return q.desc("id").list();
	}

	@Override
	public List<GrantPO> find(Collection<Long> ids) {
		return topQuery(0).add(Restrictions.in("id", ids)).list();
	}

	@Override
	public int getGrantCountByGrantTypeIdsAndTime(Set<Long> grantTypeIds,Long userId,
			Date startTime, Date endTime) {
		int count=0;
		Criteria criteria = createCriteria();
		if(grantTypeIds == null || grantTypeIds.isEmpty() || null == userId){
			return count;
		}
		if(null != startTime){
			criteria.add(Restrictions.ge("createdTime", startTime));
		}
		if(null != endTime){
			criteria.add(Restrictions.le("createdTime", endTime));
		}
		criteria.setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.in("grantTypeId", grantTypeIds));
		Object obj = criteria.uniqueResult();
		if(null != obj){
			count=((Long) obj).intValue();
		}
		return count;
	}
	
	@Override
	public GrantPO find(Long orderId, Long grantTypeId) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("orderId", orderId))
		.add(Restrictions.eq("grantTypeId", grantTypeId));
		return (GrantPO)criteria.uniqueResult();
	}
	
}
