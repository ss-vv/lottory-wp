package com.xhcms.lottery.commons.persist.dao.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.RechargeDao;
import com.xhcms.lottery.commons.persist.entity.RechargePO;

public class RechargeDaoImpl extends DaoImpl<RechargePO> implements RechargeDao {

    private static final long serialVersionUID = -7339468585352733883L;
	private int maxResults=20;

    public RechargeDaoImpl() {
        super(RechargePO.class);
    }

    @Override
    public List<RechargePO> find(long userId, String username, Date startDate, Date endDate, int status, Paging paging) {
        PagingQuery<RechargePO> pq = pagingQuery(paging);
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
            pq.add(Restrictions.eq("status", status));
        }
        pq.desc("createdTime");
        return pq.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<RechargePO> find(Collection<Long> id) {
        return createCriteria().add(Restrictions.in("id", id)).list();
    }
    
    @Override
	public RechargePO findById(Long id) {
		return (RechargePO) createCriteria().add(Restrictions.eq("id", id))
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RechargePO> findWithPage(Long userId, int firstResult) {
		String hql="from RechargePO " +
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
	public List<BigInteger> findRechargeUserId() {
		String sql = "select distinct r.user_id from lt_recharge r where r.status = 90";
		SQLQuery sqlQuery = createSQLQuery(sql);
		List<BigInteger> list = sqlQuery.list();
		return list;
	}
}
