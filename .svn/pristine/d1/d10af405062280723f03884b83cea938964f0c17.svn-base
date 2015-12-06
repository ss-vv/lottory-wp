package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.OrderDao;
import com.xhcms.lottery.commons.persist.entity.OrderPO;
import com.xhcms.lottery.lang.EntityType;

public class OrderDaoImpl extends DaoImpl<OrderPO> implements OrderDao {

    private static final long serialVersionUID = -2320317903536077890L;

    public OrderDaoImpl() {
        super(OrderPO.class);
    }

    @Override
    public List<OrderPO> list(Paging paging, String username, int type, Date from, Date to){
        PagingQuery<OrderPO> pq = pagingQuery(paging);
        if (StringUtils.isNotBlank(username)) {
            pq.add(Restrictions.like("username", username, MatchMode.ANYWHERE));
        }
        if(type != -1){
            pq.add(Restrictions.eq("type", type));
        }
        if (from != null) {
            pq.add(Restrictions.gt("createdTime", from));
        }
        if (to != null) {
            pq.add(Restrictions.lt("createdTime", to));
        }
        
        return pq.desc("id").list();
    }

    @Override
    public OrderPO getByRelated(int type, Long relatedId) {
        return (OrderPO) createQuery("from OrderPO where type=? and relatedId=?")
                .setInteger(0, type).setLong(1, relatedId).uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public OrderPO getByTypeAndRelated(int type, Long relatedId,Long userId) {
    	List<OrderPO> ls=createQuery("from OrderPO where type=? and relatedId=? and userId=?")
		.setInteger(0, type).setLong(1, relatedId).setLong(2, userId).list();
    	return ls.size()>0?ls.get(0):null;
    }

	@Override
	public List<OrderPO> listCommission(Paging paging, Long userId, Date from,
			Date to) {
		PagingQuery<OrderPO> pq = pagingQuery(paging);
        if (userId!=null) {
            pq.add(Restrictions.eq("userId", userId));
        }
        pq.add(Restrictions.eq("type", EntityType.ORDER_COMMISSION_ADD));
        if (from != null) {
            pq.add(Restrictions.gt("createdTime", from));
        }
        if (to != null) {
            pq.add(Restrictions.lt("createdTime", to));
        }
        List<OrderPO> orderPOs=pq.desc("id").list();
        return orderPOs.size()>0?orderPOs:null;
	}
	
	@Override
	public List<OrderPO> list(Paging paging, Long userId,int type, Date from,
			Date to) {
		PagingQuery<OrderPO> pq = pagingQuery(paging);
        if (userId!=null) {
            pq.add(Restrictions.eq("userId", userId));
        }
        if (type != 0) {
        	pq.add(Restrictions.eq("type", type));
        }
        if (from != null) {
            pq.add(Restrictions.gt("createdTime", from));
        }
        if (to != null) {
            pq.add(Restrictions.lt("createdTime", to));
        }
        List<OrderPO> orderPOs=pq.desc("id").list();
        return orderPOs.size()>0?orderPOs:null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<OrderPO> getByRelatedIds(int type,Set<Long> ids) {
		String hql="select o from OrderPO as o where o.type=:type and o.relatedId in (:ids) order by o.id desc";
		List<OrderPO> betSchemePOs =createQuery(hql).setParameterList("ids", ids).setParameter("type",  type).list();
		return betSchemePOs.size()>0?betSchemePOs:null;
	}
}
