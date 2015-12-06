package com.xhcms.lottery.commons.persist.dao.impl;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.BBMatchDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.lang.EntityStatus;

@Repository
public class BBMatchDaoImpl extends DaoImpl<BBMatchPO> implements BBMatchDao {

    private static final long serialVersionUID = 7267885448985701807L;

    public BBMatchDaoImpl() {
        super(BBMatchPO.class);
    }

	@Override
	public List<BBMatchPO> find(Paging paging, int status, Date from, Date to) {
		PagingQuery<BBMatchPO> q = pagingQuery(paging);
		if(status != -1) {
			q.add(Restrictions.eq("status", status));
		}
		if (from != null) {
            q.add(Restrictions.ge("playingTime", from));
        }
        if (to != null) {
            q.add(Restrictions.lt("playingTime", to));
        }
	    return q.desc("id").list();
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<BBMatchPO> find(Set<Long> idSet) {
        return createCriteria().add(Restrictions.in("id", idSet)).list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryBBMatchCountByStatus(int status) {
		return createQuery("select count(*), min(offtime) from BBMatchPO m where  m.status=?").setParameter(0, status).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryBBLeaguesByStatus(int status) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("status", status));
		criteria.setProjection(Projections.groupProperty("longLeagueName"));
		List<String> list = criteria.list();
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryLotteryInfoForBB() {
		StringBuffer buf = new StringBuffer();
		buf.append("select date_format(playingTime, '%Y-%m-%d'), count(*) ")
		.append("from BBMatchPO where status = 5 and date_format(playingTime, '%Y-%m-%d') = date_format(now(), '%Y-%m-%d') ")
		.append("group by date_format(playingTime, '%Y-%m-%d') order by playingTime desc");
		return createQuery(buf.toString()).list();
	}

	@Override
	public List<BBMatchPO> find(Paging paging, int status, Date from, Date to,
			int matchResult) {
		PagingQuery<BBMatchPO> q = pagingQuery(paging);
		if(status != -1) {
			q.add(Restrictions.eq("status", status));
		}
		if(matchResult == 0) {
    		q.add(Restrictions.isNotNull("finalScorePreset"));
    	}
    	if(matchResult == 1) {
    		q.add(Restrictions.isNull("finalScorePreset"));
    	}
		if (from != null) {
            q.add(Restrictions.ge("playingTime", from));
        }
        if (to != null) {
            q.add(Restrictions.lt("playingTime", to));
        }
	    return q.desc("id").list();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<BBMatchPO> findByLeagueName(String longLeagueName, long fromTime) {
		if(fromTime <= 0){
			Criteria criteria = createCriteria();
			criteria.add(Restrictions.eq("longLeagueName", longLeagueName));
			List<BBMatchPO> list = criteria.list();
//			if(list.size() < 1){
//				criteria = createCriteria();
//				criteria.add(Restrictions.eq("longLeagueName", longLeagueName));
//				list = criteria.list();
//			}
			return list;
		} else {
			Criteria criteria = createCriteria();
			criteria.add(Restrictions.eq("longLeagueName", longLeagueName));
			criteria.add(Restrictions.ge("playingTime", new Date(fromTime)));
			List<BBMatchPO> list = criteria.list();
//			if(list.size() < 1){
//				criteria = createCriteria();
//				criteria.add(Restrictions.eq("longLeagueName", longLeagueName));
//				criteria.add(Restrictions.ge("playingTime", new Date(fromTime)));
//				list = criteria.list();
//			}
			return list;
		}
	}

	@Override
	public long findBBMatchId(String playingDate, String code) {
		long result = -1;
		if(StringUtils.isNotBlank(code) && 
				StringUtils.isNotBlank(playingDate)) {
			StringBuffer sql = new StringBuffer();
			
			sql.append("select id from bb_match bb ")
			.append("where date_format(bb.playing_time, '%Y%m%d') = ? ")
			.append("and bb.code = ?");
			
			SQLQuery query = createSQLQuery(sql.toString());
			query.setParameter(0, playingDate);
			query.setParameter(1, code);
			Object obj = query.uniqueResult();
			if(null != obj) {
				BigInteger rs = (BigInteger) obj;
				result = rs.longValue();
			}
		}
		return result;
	}
	
    @Override
    public int updatePresetScore(long lcMatchId, String finalScorePreset) {
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("update bb_match set final_score_preset = ? where id = ?");
    	SQLQuery query = createSQLQuery(sql.toString());
    	query.setParameter(0, finalScorePreset);
    	query.setParameter(1, lcMatchId);
    	
    	return query.executeUpdate();
    }
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findMatchById(Long id) {
		String sql="select league_name,playing_time,entrust_deadline,home_team_name,guest_team_name,id from bb_match where id=:id";
		
		return createSQLQuery(sql).setLong("id", id).list();
	}

	@Override
	public Integer findMatchCount() {
		
		String sql="select count(*) from bb_match bm where bm.status=1";
		return Integer.parseInt(createSQLQuery(sql).uniqueResult().toString());
	}

	@Override
	@SuppressWarnings("unchecked")
	public BBMatchPO findBBMatch(Long matchid) {
		List<BBMatchPO> bbMatch=createQuery("from BBMatchPO where id=:matchid").setLong("matchid", matchid).list();
		return bbMatch!=null&&bbMatch.size()>0?bbMatch.get(0):new BBMatchPO();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BBMatchPO> findBBMatchByPlayTime(Date beginTime) {
		Criteria cr=createCriteria();
		cr.add(Restrictions.gt("playingTime", beginTime));
		cr.add(Restrictions.le("playingTime", new Date()));
		cr.addOrder(Order.asc("id"));
		List<BBMatchPO> bbmatch=cr.list();
		return bbmatch;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryBBMatchCountByStatusAndAfterDeadLine(
			int status, Date minMatchPlayingTime) {
		return createQuery("select count(distinct m.id), min(m.offtime) "
				+ "from BBMatchPO m, BBMatchPlayPO mp  "
				+ "where  m.status=:status "
				+ "and m.playingTime>:minMatchPlayingTime "
				+ "and m.id=mp.matchId "
				+ "and mp.status=0 ")
				.setParameter("status", status)
				.setParameter("minMatchPlayingTime", minMatchPlayingTime)
				.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryBBLeaguesByStatusAndAfterDeadLine(int status,
			Date minMatchPlayingTime) {
//		Criteria criteria = createCriteria();
//		criteria.add(Restrictions.eq("status", status));
//		criteria.add(Restrictions.gt("playingTime", minMatchPlayingTime));
//		criteria.setProjection(Projections.groupProperty("longLeagueName"));
//		List<String> list = criteria.list();
		
		String hql="select distinct m.longLeagueName "
				+ "from BBMatchPO m, BBMatchPlayPO mp  "
				+ "where  m.status=:status "
				+ "and m.playingTime>:minMatchPlayingTime "
				+ "and m.id=mp.matchId "
				+ "and mp.status=0 ";
		Query query = createQuery(hql);
		query.setParameter("status", status);
		query.setParameter("minMatchPlayingTime", minMatchPlayingTime);
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryIdAndCodesByStatusAndLeagueListAndAfterDeadlineWithPage(
			int status, List<String> leagueList, Date minMatchPlayingTime,
			Integer firstResult, Integer pageMaxResult) {
		return createQuery("select distinct m.id,m.code "
				+ "from BBMatchPO m "
				+ "where  m.status=:status "
				+ "and m.playingTime>:minMatchPlayingTime "
				+ "and m.longLeagueName in (:leagueList) "
				+ "order by m.playingTime asc,m.code asc")
				.setParameter("status", status)
				.setParameter("minMatchPlayingTime", minMatchPlayingTime)
				.setParameterList("leagueList", leagueList)
				.setFirstResult(firstResult)
				.setMaxResults(pageMaxResult)
				.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> filterCancelMatch(Collection<Long> matchIds) {
		Query hqlQuery = createQuery("select distinct m.code from BBMatchPO m where m.status=:status and id in (:matchIds)");
		hqlQuery.setParameter("status", EntityStatus.MATCH_CANCEL);
		hqlQuery.setParameterList("matchIds", matchIds);
		return hqlQuery.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getBBbifen(Date from, Date to, String leagueShortName) {
		String toSql = "";
		String toLeague = "";
		if(to!=null){
			toSql = " and b.matchTime<:to";
		}
		if(StringUtils.isNotBlank(leagueShortName)){
			toLeague = " and a.leagueName=:leagueShortName";
		}
		Query query  = createQuery("select b from BBMatchPO a,BasketBallMatchPO b "
				+ "where a.playingTime=b.matchTime and a.cnCode=b.jingcaiId and b.matchTime>=:from" + toSql + toLeague)
				.setParameter("from", from);
		if(StringUtils.isNotBlank(toSql)){
			query.setParameter("to", to);
		}
		if(StringUtils.isNotBlank(toLeague)){
			query.setParameter("leagueShortName", leagueShortName);
		}
		return query.list();
	}
}
