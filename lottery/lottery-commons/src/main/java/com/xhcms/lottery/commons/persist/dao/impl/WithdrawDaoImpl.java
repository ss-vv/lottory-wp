package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Collection;
import java.util.List;

import com.xhcms.commons.lang.Paging;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.WithdrawDao;
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
public class WithdrawDaoImpl extends DaoImpl<WithdrawPO> implements WithdrawDao {
    private static final long serialVersionUID = 7671883878720220508L;
	
    private int maxResults=20;

    public WithdrawDaoImpl() {
        super(WithdrawPO.class);
    }

    @Override
    public List<WithdrawPO> find(long userId, String username, Date startDate, Date endDate, int status, Paging paging) {
        PagingQuery<WithdrawPO> pq = pagingQuery(paging);
        if(userId > 0L){
            pq.add(Restrictions.eq("userId", userId));
        }
        if (StringUtils.isNotBlank(username)) {
            pq.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
        }
        if (startDate != null) {
            pq.add(Restrictions.ge("createdTime", startDate));
        }
        if (endDate != null) {
            pq.add(Restrictions.lt("createdTime", endDate));
        }
        if (status != -1) {
            pq.add(Restrictions.le("status", status));
        }
        pq.desc("createdTime");
        return pq.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WithdrawPO> find(Collection<Long> id) {
        return createCriteria().add(Restrictions.in("id", id)).list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<WithdrawPO> findWithPage(Long userId, int firstResult) {
		String hql="from WithdrawPO " +
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

	@SuppressWarnings("unchecked")
	@Override
	public WithdrawPO findById(long id) {
		String hql="from WithdrawPO " +
				"where id=:id  ";
		Query query = this.createQuery(hql);
		query.setParameter("id", id);
		List<WithdrawPO> list = query.list();
		return list.size() > 0 ? list.get(0) : null;
	}

}
