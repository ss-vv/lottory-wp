package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.GrantTypeDao;
import com.xhcms.lottery.commons.persist.entity.GrantTypePO;

/**
 * 
 * @author Wang Lei
 *
 */
public class GrantTypeDaoImpl extends DaoImpl<GrantTypePO> implements GrantTypeDao {
	private static final long serialVersionUID = 4280232075877966647L;
	
	public GrantTypeDaoImpl() {
		super(GrantTypePO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrantTypePO> findGrantTypeListByLotteryId(String lotteryId) {
		String hql="select g from GrantTypePO g,PromotionPO p where g.id=p.grantTypeId and p.lotteryId=:lotteryId";
		List<GrantTypePO> list = createQuery(hql)
				.setString("lotteryId", lotteryId).list();
		return list;
	}
}
