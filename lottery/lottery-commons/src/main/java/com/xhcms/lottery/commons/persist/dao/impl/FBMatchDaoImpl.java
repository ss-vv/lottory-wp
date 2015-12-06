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
import com.xhcms.lottery.commons.persist.dao.FBMatchDao;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.lang.EntityStatus;

@Repository
public class FBMatchDaoImpl extends DaoImpl<FBMatchPO> implements FBMatchDao {

    private static final long serialVersionUID = 7267885448985701807L;

    public FBMatchDaoImpl() {
        super(FBMatchPO.class);
    }

    @Override
    public int updatePresetScore(long lcMatchId, String scorePreset,
    		String halfScorePreset) {
    	StringBuffer sql = new StringBuffer();
    	sql.append("update fb_match set half_score_preset = ?, score_preset = ? where id = ?");
    	SQLQuery query = createSQLQuery(sql.toString());
    	
    	query.setParameter(0, halfScorePreset);
    	query.setParameter(1, scorePreset);
    	query.setParameter(2, lcMatchId);
    	
    	return query.executeUpdate();
    }
    
    @Override
    public List<FBMatchPO> find(Paging paging, int status, Date from, Date to , int matchResult) {
    	PagingQuery<FBMatchPO> q = pagingQuery(paging);
    	if(status != -1) {
    		q.add(Restrictions.eq("status", status));
    	}
    	if(matchResult == 0) {
    		q.add(Restrictions.isNotNull("scorePreset"));
    	}
    	if(matchResult == 1) {
    		q.add(Restrictions.isNull("scorePreset"));
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
	public List<FBMatchPO> find(Paging paging, int status, Date from, Date to) {
		PagingQuery<FBMatchPO> q = pagingQuery(paging);
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
    public List<FBMatchPO> find(Set<Long> idSet) {
        return createCriteria().add(Restrictions.in("id", idSet)).list();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryFBMatchCountByStatus(int status) {
		return createQuery("select count(*), min(offtime) from FBMatchPO m where  m.status=?").setParameter(0, status).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryFBLeaguesByStatus(int status) {
		
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("status", status));
		criteria.setProjection(Projections.groupProperty("longLeagueName"));
		List<String> list = criteria.list();
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryLotteryInfoForFB() {
		StringBuffer buf = new StringBuffer();
		buf.append("select date_format(playingTime, '%Y-%m-%d'), count(*) ")
			.append("from FBMatchPO where status = 5 and date_format(playingTime, '%Y-%m-%d') = date_format(now(), '%Y-%m-%d') ")
			.append("group by date_format(playingTime, '%Y-%m-%d') order by playingTime desc");
		return createQuery(buf.toString()).list();
	}

	@Override
	@Transactional
	@SuppressWarnings("unchecked")
	public List<FBMatchPO> findByLeagueName(String longLeagueName, long fromTime) {
		if(fromTime <= 0){
			Criteria criteria = createCriteria();
			criteria.add(Restrictions.eq("longLeagueName", longLeagueName));
			List<FBMatchPO> list = criteria.list();
			return list;
		} else {
			Criteria criteria = createCriteria();
			criteria.add(Restrictions.eq("longLeagueName", longLeagueName));
			criteria.add(Restrictions.ge("playingTime", new Date(fromTime)));
			List<FBMatchPO> list = criteria.list();
			return list;
		}
	}

	@Override
	public long findFBMatchId(String playingDate, String code) {
		long result = -1;
		if(StringUtils.isNotBlank(code) && 
				StringUtils.isNotBlank(playingDate)) {
			StringBuffer sql = new StringBuffer();
			
			sql.append("select id from fb_match fb ")
			.append("where date_format(fb.playing_time, '%Y%m%d') = ? ")
			.append("and fb.code = ?");
			
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
	@SuppressWarnings("unchecked")
	public List<Object[]> findFBMatchById(Long id) {
		
		String sql="select league_name,playing_time,entrust_deadline,home_team_name,guest_team_name,id from fb_match where id=:id";
		
		return createSQLQuery(sql).setLong("id", id).list();
	}

	@Override
	public Integer findMatchCount() {
		
		String sql="select count(*) from fb_match fm where fm.status=1";
		
		return Integer.parseInt(createSQLQuery(sql).uniqueResult().toString());
	}

	@Override
	public Integer getConcedePointsById(Long id) {
		Object o=createSQLQuery("select f.concede_points from fb_match f where f.id=:id").setParameter("id", id).uniqueResult();
		return o==null?0:Integer.parseInt(o.toString());
	}

	@Override
	@SuppressWarnings("unchecked")
	public FBMatchPO findFBMatch(Long matchId) {
		List<FBMatchPO> fbList=createQuery("from FBMatchPO where id=:matchid").setLong("matchid", matchId).list();
		return fbList!=null && fbList.size()>0?fbList.get(0):new FBMatchPO();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<FBMatchPO> findFBMatchByPlayTime(Date beginTime) {
		Criteria cr= createCriteria();
		cr.add(Restrictions.gt("playingTime", beginTime));
		cr.add(Restrictions.le("playingTime", new Date()));
		cr.addOrder(Order.asc("id"));
		List<FBMatchPO> fbList=cr.list();
		return fbList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryFBMatchCountByStatusAndAfterDeadLine(
			int status, Date minMatchPlayingTime) {
		return createQuery("select count(distinct m.id), min(m.offtime) "
				+ "from FBMatchPO m,FBMatchPlayPO mp "
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
	public List<String> queryFBLeaguesByStatusAndAfterDeadLine(int status,
			Date minMatchPlayingTime) {
//		Criteria criteria = createCriteria();
//		criteria.add(Restrictions.eq("status", status));
//		criteria.add(Restrictions.gt("playingTime", minMatchPlayingTime));
//		criteria.setProjection(Projections.groupProperty("longLeagueName"));
		
		String hql="select distinct m.longLeagueName "
				+ "from FBMatchPO m,FBMatchPlayPO mp "
				+ "where  m.status=:status "
				+ "and m.playingTime>:minMatchPlayingTime "
				+ "and m.id=mp.matchId "
				+ "and mp.status=0 ";
		Query query = createQuery(hql);
		query.setParameter("status", status);
		query.setParameter("minMatchPlayingTime", minMatchPlayingTime);
		List<String> list = query.list();
		
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> queryIdAndCodesByStatusAndLeagueListAndAfterDeadlineWithPage(
			int status, List<String> leagueList, Date minMatchPlayingTime,
			Integer firstResult, Integer pageMaxResult) {
		return createQuery("select distinct m.id,m.code "
				+ "from FBMatchPO m "
				+ "where  m.status=:status "
				+ "and m.playingTime>:minMatchPlayingTime "
				+ "and m.longLeagueName in (:leagueList) "
				+ "order by playingTime asc,code asc ")
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
		Query hqlQuery = createQuery("select distinct m.code from FBMatchPO m where m.status=:status and id in (:matchIds)");
		hqlQuery.setParameter("status", EntityStatus.MATCH_CANCEL);
		hqlQuery.setParameterList("matchIds", matchIds);
		return hqlQuery.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getZQbifen(Date from, Date to, String leagueShortName) {
		String toSql = "";
		String toLeague = "";
		if(to!=null){
			toSql = " and b.matchTime<:to";
		}
		if(StringUtils.isNotBlank(leagueShortName)){
			toLeague = " and a.leagueName=:leagueShortName";
		}
		Query query  = createQuery("select b from FBMatchPO a,FbMatchBaseInfoPO b "
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
	@Override
	@SuppressWarnings("unchecked")
	public List<FBMatchPO> getFBMatchByPlayTime_(Date playingTime,String weekDay){
		String hql="from FBMatchPO fb where date_format(fb.playingTime,'%Y-%m-%d') = date_format(:playTime,'%Y-%m-%d')"
				  +" and fb.halfScorePreset is not null and fb.scorePreset is not null"
				  +" and fb.status=5"
				  +" and fb.cnCode like :weekDay"
				  +" order by fb.playingTime desc";
		Query query=createQuery(hql);
		query.setParameter("playTime", playingTime);
		query.setParameter("weekDay", weekDay+'%');
		return (List<FBMatchPO>)query.list();
	}
}
