package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.WinDao;
import com.xhcms.lottery.commons.persist.entity.WinPO;

public class WinDaoImpl extends DaoImpl<WinPO> implements WinDao {
	private static final long serialVersionUID = -5425734811804363494L;
	private int maxResults=20;
	
	public WinDaoImpl() {
		super(WinPO.class);
	}

	@Override
	public List<WinPO> find(Paging paging, long userId, Date begin, Date end) {
		PagingQuery<WinPO> q = pagingQuery(paging);
        if (userId > 0) {
            q.add(Restrictions.eq("userId", userId));
        }
        if (begin != null) {
            q.add(Restrictions.ge("createdTime", begin));
        }
        if (end != null) {
            q.add(Restrictions.lt("createdTime", end));
        }
        q.desc("id");
        return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<WinPO> findWithPage(Long userId, int firstResult) {
		String hql="from WinPO " +
				"where userId=:userId  " +
				"order by createdTime desc";
		Query query = this.createQuery(hql);
		query.setParameter("userId", userId).setFirstResult(firstResult).setMaxResults(maxResults);
		return query.list();
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}

}
