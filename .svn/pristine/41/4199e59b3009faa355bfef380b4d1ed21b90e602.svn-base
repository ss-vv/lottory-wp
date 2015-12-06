package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.BetPartnerPresetDao;
import com.xhcms.lottery.commons.persist.entity.BetPartnerPresetPO;

public class BetPartnerPresetDaoImpl extends DaoImpl<BetPartnerPresetPO> implements BetPartnerPresetDao {

	private static final long serialVersionUID = -4995122572909382504L;

	BetPartnerPresetDaoImpl(){
		super(BetPartnerPresetPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BetPartnerPresetPO> findBySchemeId(Long schemeId) {
		   Criteria c = createCriteria();
	        c.setFetchMode("scheme", FetchMode.SELECT);
	        c.add(Restrictions.eq("scheme.id", schemeId));
	        return c.list();
	}
	
	@SuppressWarnings("unchecked")
    @Override
    public BetPartnerPresetPO get(Long schemeId, Long userId) {
        Criteria c = createCriteria();
        c.setFetchMode("scheme", FetchMode.SELECT);
        c.add(Restrictions.eq("scheme.id", schemeId));
        c.add(Restrictions.eq("userId", userId));
		List<BetPartnerPresetPO> list=c.list();
        return list.size()>0?(BetPartnerPresetPO)list.get(0):null;
    }

}
