package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBSubCupDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubCupPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

@Repository
public class FBSubCupDaoImpl extends DaoImpl<FBSubCupPO> implements FBSubCupDao {

	private static final long serialVersionUID = -6905787529211386586L;

	public FBSubCupDaoImpl(){
		super(FBSubCupPO.class);
	}

	@Override
	public FBSubCupPO findBy(long leagueId, String season) {
		return (FBSubCupPO) createQuery("from FBSubCupPO where leagueId=? and season=? and groupingMatch = 1")
		.setLong(0, leagueId).setString(1, season).uniqueResult();
	}
	
}
