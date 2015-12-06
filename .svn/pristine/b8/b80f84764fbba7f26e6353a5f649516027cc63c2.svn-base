package com.unison.lottery.weibo.bbs.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.unison.lottery.weibo.bbs.persist.dao.UserDao;
import com.unison.lottery.weibo.bbs.persist.entity.UserPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

public class UserDaoImpl extends DaoImpl<UserPO> implements UserDao {

	private static final long serialVersionUID = -6751662168463874743L;

	public UserDaoImpl() {
        super(UserPO.class);
    }
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UserPO> findEffectiveUsers(int status) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("status", status));
		return (List<UserPO>)c.list();
	}

}
