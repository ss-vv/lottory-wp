package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PMPromotionDao;
import com.xhcms.lottery.commons.persist.entity.PromotionPO;

public class PMPromotionDaoImpl extends DaoImpl<PromotionPO> implements PMPromotionDao{

	private static final long serialVersionUID = 5934616990035857242L;
	
	protected  Logger log = LoggerFactory.getLogger(getClass());
	
	public PMPromotionDaoImpl(){
		super(PromotionPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getPromotionIdsByLotteryId(String LotteryId) {
        Criteria c = createCriteria()
                .setProjection(Property.forName("id"))
                .add(Restrictions.eq("lotteryId", LotteryId))
                .add(Restrictions.eq("status", 1));// 有效
        return c.list();
	}
}
