package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PsDao;
import com.xhcms.lottery.commons.persist.entity.PsPO;

public class PsDaoImpl extends DaoImpl<PsPO> implements PsDao{
	private static final long serialVersionUID = 4455939644044239287L;

	public PsDaoImpl(){
		super(PsPO.class);
	}
	
	@Override
	public PsPO findDollByShadow(long sid) {
		Criteria cri = createCriteria();
		cri.add(Restrictions.eq("sid", sid));
		return (PsPO) cri.uniqueResult();
	}

	@Override
	public PsPO addDollShadowPair(long dollId, long shadowId) {
		PsPO ps = new PsPO();
		ps.setDid(dollId);
		ps.setSid(shadowId);
		Date cur = new Date();
		ps.setCreatedAt(cur);
		ps.setUpdatedAt(cur);
		save(ps);
		return ps;
	}

	@Override
	public PsPO findShadowByDoll(long did) {
		Criteria cri = createCriteria();
		cri.add(Restrictions.eq("did", did));
		return (PsPO) cri.uniqueResult();
	}

}
