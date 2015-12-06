package com.xhcms.lottery.commons.persist.dao.impl;

import org.hibernate.criterion.Restrictions;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.ColorRingDao;
import com.xhcms.lottery.commons.persist.entity.PmColorRingGrantPO;

/**
 * 彩铃订购dao
 * @author zhuyongli
 */
public class ColorRingDaoImpl extends DaoImpl<PmColorRingGrantPO> implements ColorRingDao {

	private static final long serialVersionUID = 8920357824877935804L;

	public ColorRingDaoImpl() {
        super(PmColorRingGrantPO.class);
    }

    @Override
    public PmColorRingGrantPO findByTradeNo(String tradeNo) {
		return (PmColorRingGrantPO) createCriteria().add(
				Restrictions.eq("tradeNo", tradeNo)).uniqueResult();
    }    

}
