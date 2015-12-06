package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.Top5RecommendDBDao;
import com.xhcms.lottery.commons.persist.entity.Top5RecommendPO;
/**
 * 
 * @author Administrator
 *
 */
@Repository
public class Top5RecommendDBDaoImpl extends DaoImpl<Top5RecommendPO> implements Top5RecommendDBDao{

	private static final long serialVersionUID = 6630411813278284442L;
	public Top5RecommendDBDaoImpl() {
		super(Top5RecommendPO.class);
	}

	@SuppressWarnings("unchecked")
	public List<Top5RecommendPO> generalFindTop5Recommend(String topType,String dimension){
		Criteria cr=createCriteria();
		cr.add(Restrictions.eq("topType", topType));
		cr.add(Restrictions.eq("dimension", dimension));
		cr.addOrder(Order.asc("sequenceNumber"));
		cr.setFirstResult(0);
		cr.setMaxResults(5);
		return cr.list();
	}

}
