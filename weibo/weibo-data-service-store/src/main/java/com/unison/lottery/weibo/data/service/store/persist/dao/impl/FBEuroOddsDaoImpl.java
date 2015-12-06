package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBEuroOddsDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBEuroOddsPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * 足球欧赔DAO
 * 
 * @author Yang Bo
 */
@Repository
public class FBEuroOddsDaoImpl extends DaoImpl<FBEuroOddsPO> implements FBEuroOddsDao {

	private static final long serialVersionUID = 7271752809465981712L;

	
	
	public FBEuroOddsDaoImpl() {
		super(FBEuroOddsPO.class);
	}

	@Override
	public FBEuroOddsPO findBy(long matchId, long corpId) {
		return (FBEuroOddsPO) createQuery("from FBEuroOddsPO where matchId=? and corpId=?")
		.setLong(0, matchId)
		.setLong(1, corpId).uniqueResult();
	}

	@Override
	public void flushAndEvict(Object po) {
		session().flush();
		session().evict(po);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<FBEuroOddsPO> findEuropeOddsList(long qtMatchId) {
		if(qtMatchId > 0) {
			Criteria c = createCriteria();
			c.add(Restrictions.eq("matchId", qtMatchId));
			return c.list();
		}
		return null;
	}
}
