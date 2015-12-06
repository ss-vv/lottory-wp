package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.data.service.store.persist.dao.BBLeagueDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBLeaguePO;
import com.xhcms.commons.persist.hibernate.DaoImpl;


@Repository
public class BBLeagueDaoImpl extends DaoImpl<BBLeaguePO> implements BBLeagueDao {

	private static final long serialVersionUID = -7314686034001490363L;
	
	public BBLeagueDaoImpl(){
		super(BBLeaguePO.class);
	}

	@Override
	public void save(final BBLeaguePO bbLeaguePO) {
		Session session = getSessionFactory().getCurrentSession();
		session.save(bbLeaguePO);
	}
	
	@Override
	public BBLeaguePO findByLeagueId(Long leagueId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("leagueId", leagueId));
		return (BBLeaguePO) c.uniqueResult();
	}

	@Override
	public BBLeaguePO findByShortName(String leagueName) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("shortName", leagueName));
		return (BBLeaguePO) c.uniqueResult();
	}
}
