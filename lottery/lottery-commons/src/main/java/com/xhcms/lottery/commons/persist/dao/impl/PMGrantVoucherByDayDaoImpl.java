package com.xhcms.lottery.commons.persist.dao.impl;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PMGrantVoucherByDayDao;
import com.xhcms.lottery.commons.persist.entity.PMGrantVoucherByDayPO;

/**
 * 按日赠送优惠劵记录dao
 * @author Wang Lei
 *
 */
public class PMGrantVoucherByDayDaoImpl extends DaoImpl<PMGrantVoucherByDayPO> implements PMGrantVoucherByDayDao {
	private static final long serialVersionUID = 1L;

	public PMGrantVoucherByDayDaoImpl(){
		super(PMGrantVoucherByDayPO.class);
	}
}
