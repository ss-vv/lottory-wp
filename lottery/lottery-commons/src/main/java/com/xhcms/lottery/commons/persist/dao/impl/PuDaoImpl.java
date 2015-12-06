package com.xhcms.lottery.commons.persist.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PuDao;
import com.xhcms.lottery.commons.persist.entity.PuPO;

public class PuDaoImpl extends DaoImpl<PuPO> implements PuDao {
	private static final long serialVersionUID = -1578285679849558660L;

    public PuDaoImpl() {
        super(PuPO.class);
    }
    
	@SuppressWarnings("rawtypes")
	@Override
	public PuPO getSU() {
		Criteria cri = createCriteria().add(Restrictions.eq("ut", PuPO.SHADOW));
		List l = cri.list();
		if (l==null || l.size()==0){
			return null;
		}else{
			return (PuPO) l.get(0);
		}
	}

	@Override
	public PuPO findByUid(long uid) {
		Criteria cri = createCriteria().add(Restrictions.eq("uid", uid));
		return (PuPO) cri.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigInteger> listUser() {
		String sql = "select distinct p.uid from lt_pu p where p.ut = 1";
		SQLQuery sqlQuery = createSQLQuery(sql);
		List<BigInteger> list = sqlQuery.list();
		return list;
	}
}
