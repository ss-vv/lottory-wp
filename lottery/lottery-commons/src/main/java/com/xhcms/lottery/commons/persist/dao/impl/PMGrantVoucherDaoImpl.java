package com.xhcms.lottery.commons.persist.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PMGrantVoucherDao;
import com.xhcms.lottery.commons.persist.entity.PMGrantVoucherPO;

public class PMGrantVoucherDaoImpl extends DaoImpl<PMGrantVoucherPO> implements PMGrantVoucherDao {

	private static final long serialVersionUID = -5275721626636747128L;

	public PMGrantVoucherDaoImpl() {
		super(PMGrantVoucherPO.class);
	}

	@Override
	public PMGrantVoucherPO find(Long userId, Long pmId, Long pmWeek) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("userId", userId))
			.add(Restrictions.eq("voucherPmId", pmId))
			.add(Restrictions.eq("pmWeek", pmWeek));
		
		return (PMGrantVoucherPO)c.uniqueResult();
	}
}
