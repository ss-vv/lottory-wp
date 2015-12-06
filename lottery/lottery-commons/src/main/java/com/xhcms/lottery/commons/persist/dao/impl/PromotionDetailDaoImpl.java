package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PromotionDetailDao;
import com.xhcms.lottery.commons.persist.entity.PromotionDetailPO;

@SuppressWarnings("unchecked")
public class PromotionDetailDaoImpl extends DaoImpl<PromotionDetailPO> implements PromotionDetailDao{

	private static final long serialVersionUID = 1L;
	
	public PromotionDetailDaoImpl() {
		super(PromotionDetailPO.class);
	}
	
	@Override
	public List<PromotionDetailPO> findListByPromotionId(Long promotionId) {
		Criteria c = createCriteria();
		if(null == promotionId || promotionId==0){
			return null;
		}
		c.add(Restrictions.eq("promotionId", promotionId));
		return c.list();
	}
}
