package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBCupScoreDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBCupScorePO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

@Repository
public class FBCupScoreDaoImpl extends DaoImpl<FBCupScorePO> implements FBCupScoreDao {

	private static final long serialVersionUID = -6905787529211386586L;
	
	public FBCupScoreDaoImpl(){
		super(FBCupScorePO.class);
	}

	@Override
	public FBCupScorePO findCupScoreBy(long subCupId, String groupName,
			long teamId) {
		Criteria criterial = createCriteria();
		criterial.add(Restrictions.eq("subCupId", subCupId));
		criterial.add(Restrictions.eq("groupName", groupName));
		criterial.add(Restrictions.eq("teamId", teamId));
		return (FBCupScorePO) criterial.uniqueResult();
	}

	@SuppressWarnings({"unchecked" })
	@Override
	public List<Object[]> findCupScoreBy(long subCupId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("subCupId", subCupId));
		c.addOrder(Order.asc("groupName"));
		c.addOrder(Order.asc("rank"));
		return c.list();
	}

}
