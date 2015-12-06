package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.SystemConfDao;
import com.xhcms.lottery.commons.persist.entity.SystemConfPO;

public class SystemConfDaoImpl extends DaoImpl<SystemConfPO> implements
		SystemConfDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5130641159238044193L;

	@Override
	public List<SystemConfPO> loadAll() {

		return super.list();
	}

	public SystemConfDaoImpl() {
		super(SystemConfPO.class);

	}

	@Override
	public Integer findIntValueByKey(String key) {
		String hql = "select confValue " + "from SystemConfPO a "
				+ "where confKey=:key ";
		Query query = createQuery(hql);
		query.setParameter("key", key);
		String value = (String) query.uniqueResult();
		Integer result = null;
		try {
			result = Integer.parseInt(value);
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}

		return result;
	}

	@Override
	public String findValueByKey(String key) {
		String hql = "select confValue " + "from SystemConfPO a "
				+ "where confKey=:key ";
		Query query = createQuery(hql);
		query.setParameter("key", key);
		return (String) query.uniqueResult();
	}
	
	@Override
	public void updateIntegerValueByKey(String key,String value)
	{
		String hql = "update SystemConfPO s set s.confValue=:value where s.confKey=:key";
		Query query = createQuery(hql);
		query.setParameter("value", value);
		query.setParameter("key", key);
		query.executeUpdate();
	}
}
