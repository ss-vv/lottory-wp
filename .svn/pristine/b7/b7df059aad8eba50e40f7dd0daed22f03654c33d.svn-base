package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.data.service.store.persist.dao.FBLeagueScoreDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBLeagueScorePO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

@Repository
public class FBLeagueScoreDaoImpl extends DaoImpl<FBLeagueScorePO> implements FBLeagueScoreDao {
	
	private static final long serialVersionUID = -6905787529211386586L;

	public FBLeagueScoreDaoImpl(){
		super(FBLeagueScorePO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FBLeagueScorePO> getLeagueScore(long subLeagueId, int scoreType) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("subLeagueId", subLeagueId));
		c.add(Restrictions.eq("scoreType", scoreType));
		c.addOrder(Order.asc("rank"));
		return c.list();
	}

	@Override
	public FBLeagueScorePO findLeagueScoreBy(long subLeagueId, long teamId, int scoreType) {
		Query query = createQuery(
				"from FBLeagueScorePO where subLeagueId=? and teamId=? and scoreType=?")
		.setLong(0, subLeagueId).setLong(1, teamId).setInteger(2, scoreType);
		return (FBLeagueScorePO) query.uniqueResult();
	}

}
