package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBOddsEuroDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBOddsEuroPO;

@Repository
public class BBOddsEuroDaoImpl extends BasicDaoImpl<BBOddsEuroPO> implements
		BBOddsEuroDao {

	private static final long serialVersionUID = -8732912011842344710L;

	public BBOddsEuroDaoImpl(){
		super(BBOddsEuroPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BBOddsEuroPO> findEuropeOddsList(long qtMatchId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("matchId", qtMatchId));
		return c.list();
	}


	@Override
	public BBOddsEuroPO findBy(long matchId, long corpId) {
		return (BBOddsEuroPO) createQuery(
				"from BBOddsEuroPO where matchId=? and corpId=?")
				.setLong(0, matchId).setLong(1, corpId).uniqueResult();
	}
	
}
