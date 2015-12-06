package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchDao;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPO;

public class BJDCMatchDaoImpl extends DaoImpl<BJDCMatchPO> implements
		BJDCMatchDao {

	private static final long serialVersionUID = 1L;

	public BJDCMatchDaoImpl() {
		super(BJDCMatchPO.class);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BJDCMatchPO> find(Set<Long> idSet) {

		return createCriteria().add(Restrictions.in("id", idSet)).list();
	}
	
	@Override
	public List<BJDCMatchPO> find(Paging paging, Date from, Date to) {
		PagingQuery<BJDCMatchPO> q = pagingQuery(paging);
		if (from != null) {
            q.add(Restrictions.ge("playingTime", from));
        }
        if (to != null) {
            q.add(Restrictions.lt("playingTime", to));
        }
	    return q.desc("id").list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findMatchById(Long id) {
		
		String sql="select league_name,playing_time,entrust_deadline,home_team_name,guest_team_name,id from bjdc_match where id=:id";
		return createSQLQuery(sql).setLong("id", id).list();
	}

	@Override
	public Integer findMatchCount() {
		String sql="select count(*) from bjdc_match bjm where bjm.status=1";
		return Integer.parseInt(createSQLQuery(sql).uniqueResult().toString());
	}

	@Override
	public BJDCMatchPO findMatch(Long matchid) {
		List<BJDCMatchPO> bjdcMatch=createQuery("from BJDCMatchPO where id=:matchid").setLong("matchid", matchid).list();
		return bjdcMatch!=null&&bjdcMatch.size()>0?bjdcMatch.get(0):new BJDCMatchPO();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BJDCMatchPO> findBJDCMatchByPlayTime(Date beginTime) {
		Criteria cr=createCriteria();
		cr.add(Restrictions.gt("playingTime", beginTime));
		cr.add(Restrictions.le("playingTime", new Date()));
		cr.addOrder(Order.asc("id"));
		List<BJDCMatchPO> bjdcMatch=cr.list();
		return bjdcMatch;
	}
}