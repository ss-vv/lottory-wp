package com.xhcms.lottery.admin.persist.dao.impl;

import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.admin.persist.dao.AdminDao;
import com.xhcms.lottery.commons.persist.entity.AdminPO;

public class AdminDaoImpl extends DaoImpl<AdminPO> implements AdminDao {
	private static final long serialVersionUID = 2103011705787674156L;
	
	public AdminDaoImpl() {
		super(AdminPO.class);
	}

	@Override
	public AdminPO getAdmin(String username) {
		return (AdminPO) createCriteria().add(Restrictions.eq("username", username)).uniqueResult();
	}
	
	@Override
	public void saveAdmin(AdminPO adminPO) {
		save(adminPO);
	}
}
