package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.ActivityNotify;
import com.xhcms.lottery.commons.persist.dao.ActivityNotifyDao;
import com.xhcms.lottery.commons.persist.entity.ActivityNotifyPO;

public class ActivityNotifyDaoImpl extends  DaoImpl<ActivityNotifyPO> implements ActivityNotifyDao {
	
	private static final long serialVersionUID = 3560033085392347914L;
	private int maxResults=10;

	public ActivityNotifyDaoImpl() {
		super(ActivityNotifyPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ActivityNotifyPO> listWithPage(String firstResult) {
		int first=Integer.parseInt(firstResult);
		String hql="from ActivityNotifyPO " +
				"where status=:status  " +
				"order by sequenceNumber asc";
		Query query = this.createQuery(hql);
		query.setParameter("status", 1).setFirstResult(first).setMaxResults(maxResults);
		return query.list();
	}

	public int getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(int maxResults) {
		this.maxResults = maxResults;
	}
	
	@Override
	public List<ActivityNotifyPO> listActivity(Paging paging, String activityName) {
		PagingQuery<ActivityNotifyPO> pq = pagingQuery(paging);
        if(StringUtils.isNotBlank(activityName)){
            pq.add(Restrictions.like("activityName", activityName, MatchMode.ANYWHERE));
        }
        return pq.asc("sequenceNumber").list();
	}

	@Override
	public void add(ActivityNotifyPO notify) {
		save(notify);
	}

	@Override
	public void modify(ActivityNotify notify) {
		Long id = notify.getId();
		ActivityNotifyPO po = get(id);
		BeanUtils.copyProperties(notify, po);
	}

	@Override
	public void remove(Long activityNotifyId) {
		deleteById(activityNotifyId);
	}
}