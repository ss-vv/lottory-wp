package com.xhcms.lottery.commons.persist.dao.impl;

import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PMRechargeDao;
import com.xhcms.lottery.commons.persist.entity.PMRechargePO;

/**
 * @author zhuyongli
 * 
 */
public class PMRechargeDaoImpl extends DaoImpl<PMRechargePO> implements PMRechargeDao {

	private static final long serialVersionUID = 6674623978606970086L;

	public PMRechargeDaoImpl() {
        super(PMRechargePO.class);
    }

	public PMRechargePO find(Long userId, Long rechargeId) {
		return (PMRechargePO)createCriteria()
				.add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("rechargeId", rechargeId))
				.uniqueResult();
	}
}
