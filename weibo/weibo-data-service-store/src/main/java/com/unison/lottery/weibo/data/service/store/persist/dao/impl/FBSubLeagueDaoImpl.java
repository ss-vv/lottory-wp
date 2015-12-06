package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.data.service.store.persist.dao.FBSubLeagueDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.FBSubLeaguePO;
import com.xhcms.commons.persist.hibernate.DaoImpl;

@Repository
public class FBSubLeagueDaoImpl extends DaoImpl<FBSubLeaguePO> implements FBSubLeagueDao {
	
	private static final long serialVersionUID = -6905787529211386586L;

	public FBSubLeagueDaoImpl(){
		super(FBSubLeaguePO.class);
	}

	@Override
	public FBSubLeaguePO findFBSubLeagueBy(long leagueId,
			String season) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("leagueId", leagueId));
		c.add(Restrictions.eq("season", season));
		return (FBSubLeaguePO) c.uniqueResult();
	}
}
