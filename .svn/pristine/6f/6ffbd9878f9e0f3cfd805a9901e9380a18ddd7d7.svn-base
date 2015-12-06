package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.RegistrationCodeDao;
import com.xhcms.lottery.commons.persist.entity.RegistCodePO;
@Repository
public class RegistrationCodeDaoImpl extends DaoImpl<RegistCodePO> implements RegistrationCodeDao{
	private static final long serialVersionUID = -6088194804109667671L;

	public RegistrationCodeDaoImpl(){
		super(RegistCodePO.class);
	}
	@Override
	public RegistCodePO getRegistrationCodeWithCode(String code) {
		String hql="FROM RegistCodePO WHERE code=:code";
		Query query = createQuery(hql);
		query.setParameter("code", code);
		return (RegistCodePO) query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RegistCodePO> listByPagger(Paging paging, int status, int vid) {
		List<RegistCodePO> registCodePOs = new ArrayList<>();
		Query query = createQuery(getListByPaggerHql(status,vid));
		setParameterForListByPaggerHql(query, status, vid);
		if(null != paging){
			Query countQuery = createQuery("select count(*) "+getListByPaggerHql(status,vid));
			setParameterForListByPaggerHql(countQuery, status, vid);
			Long countInteger = (Long) countQuery.uniqueResult();
			paging.setTotalCount(countInteger.intValue());
			query.setFirstResult(paging.getFirstResult());
			query.setMaxResults(paging.getMaxResults());
			if (countInteger > paging.getFirstResult()) {
				registCodePOs = query.list();
			}
			paging.setResults(registCodePOs);
		} else {
			registCodePOs = query.list();
		}
		return registCodePOs;
	}
	
	private void setParameterForListByPaggerHql(Query query, int status, int vid){
		if(status > 0){
			query.setParameter("status", status);
		}
		if(vid > 0){
			query.setParameter("vid", vid);
		}
	}
	private String getListByPaggerHql( int status, int vid){
		String hql = "from RegistCodePO where 1=1 ";
		if(status > 0){
			hql += " and status=:status ";
		}
		if(vid > 0){
			hql += " and vid=:vid ";
		}
		hql += " order by id desc";
		return hql;
	}
}
