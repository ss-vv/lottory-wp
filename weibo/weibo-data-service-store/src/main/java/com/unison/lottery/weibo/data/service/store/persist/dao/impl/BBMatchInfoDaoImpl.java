package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import com.unison.lottery.weibo.data.service.store.persist.dao.BBMatchInfoDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.BBMatchInfoPO;

/**
 * 篮球比赛信息DAO
 */
@Repository
public class BBMatchInfoDaoImpl extends BasicDaoImpl<BBMatchInfoPO> implements BBMatchInfoDao {

	private static final long serialVersionUID = -5167797685294484321L;

	public BBMatchInfoDaoImpl(){
		super(BBMatchInfoPO.class);
	}
	
	@Override
	public BBMatchInfoPO queryBBMatchInfo(long qtMatchId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("id", qtMatchId));
		return (BBMatchInfoPO) c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BBMatchInfoPO> findBBMatchs(long homeTeamId,
			long guestTeamId, Date from, Date to, int size) {
		Criteria criteria = createCriteria();
		if(homeTeamId > 0) {
			criteria.add(Restrictions.eq("homeTeamId", homeTeamId));
		}
		if(guestTeamId > 0) {
			criteria.add(Restrictions.eq("guestTeamId", guestTeamId));
		}
		if(null != to){
			criteria.add(Restrictions.lt("matchTime", to));
		}
		if(null != from){
			criteria.add(Restrictions.gt("matchTime", from));
		}
		criteria.addOrder(Order.desc("matchTime"));
		criteria.setMaxResults(size);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BBMatchInfoPO> findBBMatchPO(long teamId, Date from, Date to,
			int size) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.or(Restrictions.eq("homeTeamId", teamId),
				Restrictions.eq("guestTeamId", teamId)));
		if(null != to){
			criteria.add(Restrictions.lt("matchTime", to));
		}
		if(null != from){
			criteria.add(Restrictions.gt("matchTime", from));
		}
		criteria.addOrder(Order.desc("matchTime"));
		criteria.setMaxResults(size);
		return criteria.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BBMatchInfoPO> findBBMatchPO(long teamId, Date from, Date to,
			int size, int matchStatus) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.or(Restrictions.eq("homeTeamId", teamId),
				Restrictions.eq("guestTeamId", teamId)));
		if(null != to){
			criteria.add(Restrictions.lt("matchTime", to));
		}
		if(null != from){
			criteria.add(Restrictions.gt("matchTime", from));
		}
		criteria.add(Restrictions.eq("matchState", matchStatus));
		criteria.addOrder(Order.desc("matchTime"));
		criteria.setMaxResults(size);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BBMatchInfoPO> findBBMatchPO(String leagueName,
			String[] currMatchSeason) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("name", leagueName));
		c.add(Restrictions.in("season", currMatchSeason));
		return c.list();
	}
}
