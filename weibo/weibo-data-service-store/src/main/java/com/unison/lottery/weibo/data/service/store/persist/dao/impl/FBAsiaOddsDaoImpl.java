package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.data.service.store.persist.dao.FBAsiaOddsDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBAsiaOddsPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * 足球亚赔DAO
 * 
 * @author Yang Bo
 */
@Repository
@SuppressWarnings("unchecked")
public class FBAsiaOddsDaoImpl extends DaoImpl<FBAsiaOddsPO> implements FBAsiaOddsDao {

	public FBAsiaOddsDaoImpl(){
		super(FBAsiaOddsPO.class);
	}
	
	private static final long serialVersionUID = -6878032846236242641L;

	@Override
	public FBAsiaOddsPO findBy(long matchId, long corpId) {
		return (FBAsiaOddsPO) createQuery("from FBAsiaOddsPO where matchId=? and corpId=?")
		.setLong(0, matchId)
		.setLong(1, corpId).uniqueResult();
	}

	@Override
	public void flushAndEvict(Object po) {
		session().flush();
		session().evict(po);
	}

	@Override
	public List<FBAsiaOddsPO> findByMatchId(long matchId) {
		return createQuery("from FBAsiaOddsPO where matchId=?")
				.setLong(0, matchId).list();
	}

	@Override
	public List<FBAsiaOddsPO> findAsianOddsList(long qtMatchId) {
		if(qtMatchId > 0) {
			Criteria c = createCriteria();
			c.add(Restrictions.eq("matchId", qtMatchId));
			return c.list();
		}
		return null;
	}
}
