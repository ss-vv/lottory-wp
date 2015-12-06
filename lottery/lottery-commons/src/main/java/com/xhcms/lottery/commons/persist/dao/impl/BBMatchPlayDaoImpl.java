package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion; 
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.dao.BBMatchPlayDao;
import com.xhcms.lottery.commons.persist.entity.BBMatchPlayPO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.utils.DateUtils;

public class BBMatchPlayDaoImpl extends DaoImpl<BBMatchPlayPO> implements
		BBMatchPlayDao {

	private static final long serialVersionUID = 5603497235385527803L;

	public BBMatchPlayDaoImpl() {
		super(BBMatchPlayPO.class);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BBMatchPlayPO> findByMatchId(Collection<Long> matchIds) {
		if(matchIds ==null || matchIds.size() < 1){
			return Collections.EMPTY_LIST;
		}
		return createCriteria().add(Restrictions.in("matchId", matchIds)).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BBMatchPlayPO> findByMatchIdAndPlayIds(Long matchId, Collection<String> playIds) {
		if(matchId == null || playIds == null || playIds.size() < 1){
			return Collections.EMPTY_LIST;
		}
		return createCriteria().add(Restrictions.eq("matchId", matchId)).add(Restrictions.in("playId", playIds)).list();
	}
	
    @Override
    public List<BBMatchPlayPO> find(Paging paging, String playId, Collection<Long> matchs) {
		PagingQuery<BBMatchPlayPO> q = pagingQuery(paging);
		if (playId != null) {
			q.add(Restrictions.eq("playId", playId));
		}
		if (matchs != null) {
			q.add(Restrictions.in("matchId", matchs));
		}
        return q.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<BBMatchPlayPO> find(String playId, Collection<Long> matchs) {
        Criteria c = createCriteria().add(Restrictions.eq("playId", playId));
        if (matchs != null && matchs.size() > 0) {
            c.add(Restrictions.in("matchId", matchs));
        }
        return c.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findByStatus(String playId, int status) {
        return createQuery("select mp, m from BBMatchPO m, BBMatchPlayPO mp where m.id=mp.matchId and mp.playId=? and m.status=? and mp.status=0 order by m.code asc")
                .setString(0, playId).setInteger(1, status).list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findByStatusForSinglePass(String playId, int status) {
    	return createQuery("select mp, m from BBMatchPO m, BBMatchPlayPO mp, MatchSupportPlayPO msp "
    			+ " where m.id=mp.matchId and mp.playId=? and m.status=? and mp.status=0 "
    			+ " and mp.matchId=msp.matchId and msp.playId=mp.playId order by m.code asc")
    			.setString(0, playId).setInteger(1, status).list();
    }
    
    @SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMatchByPlayIdAndStatusAndLeagueName(
			String playId, int status, List<String> leagues,Integer firstResult,Integer pageMaxResult) {
		return createQuery("select m, mp from BBMatchPO m, BBMatchPlayPO mp where m.id=mp.matchId and mp.playId=? and m.status=? and mp.status=0 and m.longLeagueName in (:leagues) order by m.playingTime, m.code asc")
        .setString(0, playId).setInteger(1, status).setParameterList("leagues", leagues).setFirstResult(firstResult).setMaxResults(pageMaxResult).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BBMatchPlayPO> findByMatches(Collection<PlayMatch> matchs) {
		Criteria c = createCriteria();
		Disjunction disj = Restrictions.disjunction();
		for (PlayMatch m : matchs){
			Criterion and = Restrictions.and(Restrictions.eq("playId", m.getPlayId()), 
					Restrictions.eq("matchId", m.getMatchId()));
			disj.add(and);
		}
		c.add(disj);
		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByStatus(String playId, int status,
			List<String> leagueList, Integer firstResult, Integer pageMaxResult) {
		return createQuery("select  mp, m from BBMatchPO m, BBMatchPlayPO mp where m.id=mp.matchId and mp.playId=? and m.status=? and mp.status=0 and m.longLeagueName in (:leagues) order by m.playingTime, m.code asc")
		        .setString(0, playId).setInteger(1, status).setParameterList("leagues", leagueList).setFirstResult(firstResult).setMaxResults(pageMaxResult).list();
	}

	@Override
	public Float findByMatchIdAndPlayId(String matchId, String playId) {
		Float defaultScore = (Float) createQuery("select mp.defaultScore from BBMatchPlayPO mp where mp.matchId = ? and mp.playId=?")
			.setString(0, matchId)
	        .setString(1, playId).uniqueResult();
		return defaultScore;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMatchBySelector(String playId,
			boolean isShowStopSell, List<String> leagueList, Date playingTime) {
		if(StringUtils.isBlank(playId)) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		buf.append("select mp, m  from BBMatchPO m, BBMatchPlayPO mp ")
		.append("where m.id=mp.matchId and mp.status=0 and mp.playId=:playId ");
		if(!isShowStopSell) {
			buf.append(" and m.status=:status ");
		}
		if(null != leagueList && leagueList.size() > 0) {
			buf.append(" and m.leagueName in (:leagues) ");
		}
		if(null == playingTime) {
			playingTime = new Date();
		}
		buf.append(" and date_format(m.playingTime, '%Y-%m-%d') = :playingTime ")
		.append(" order by m.playingTime, m.code asc");
		
		Query query = createQuery(buf.toString());
		query.setParameter("playId", playId);
		query.setParameter("playingTime", DateUtils.formatShort(playingTime));
		if(!isShowStopSell) {
			query.setParameter("status", EntityStatus.MATCH_ON_SALE);
		}
		if(null != leagueList && leagueList.size() > 0) {
			query.setParameterList("leagues", leagueList);
		}
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findOptionsOddsById(String id) {
		String sql="select options,odds from bb_match_play where id=:id";
		return createSQLQuery(sql).setString("id",id).list();
	}
	@Override
	   public String getPointsById(String id) {
		Object o=createQuery("select defaultScore from BBMatchPlayPO where id=:id").setParameter("id", id).uniqueResult();
		return o!=null?o.toString():"0";
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findMatchInfoBySchemeId(Long schemeId,String play_id) {
		String sql="select bmatch.match_id,bmatch.code,bmatchplay.play_id,bmatchplay.win_option "
				+ " from lt_bet_match bmatch,bb_match_play bmatchplay where"
				+ "  bmatch.match_id=bmatchplay.match_id and bmatch.scheme_id=:mid"
				+ " and bmatchplay.play_id=:playid";
		return createSQLQuery(sql).setLong("mid", schemeId).setString("playid", play_id).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findSignalsByStatus(String playId, int matchOnSale,
			List<String> leagueList, Integer firstResult, Integer pageMaxResult) {
		return createQuery("select  mp, m from BBMatchPO m, BBMatchPlayPO mp where m.id=mp.matchId and mp.playId=? and m.status=? and mp.status=0 and m.longLeagueName in (:leagues) order by m.playingTime, m.code asc")
		        .setString(0, playId).setInteger(1, matchOnSale).setParameterList("leagues", leagueList).setFirstResult(firstResult).setMaxResults(pageMaxResult).list();
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByStatusAndAfterDeadline(String playId,
			int status, List<String> leagueList, Integer firstResult,
			Integer pageMaxResult, Date currentDateTime) {
		return createQuery("select  mp, m "
				+ "from BBMatchPO m, BBMatchPlayPO mp "
				+ "where m.id=mp.matchId "
				+ "and mp.playId=:playId "
				+ "and m.status=:status "
				+ "and mp.status=0 "
				+ "and m.longLeagueName in (:leagues) "
				+ "and m.offtime > :currentDateTime "
				+ "order by m.playingTime, m.code asc")
		        .setParameter("playId", playId)
		        .setParameter("status", status)
		        .setParameterList("leagues", leagueList)
		        .setParameter("currentDateTime", currentDateTime)
		        .setFirstResult(firstResult)
		        .setMaxResults(pageMaxResult).list();
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findSignalsByStatusAndAfterDeadline(String playId,
			int status, List<String> leagueList, Integer firstResult,
			Integer pageMaxResult,Date minMatchPlayingTime) {
		return createQuery("select  mp, m "
				+ "from BBMatchPO m, BBMatchPlayPO mp, MatchSupportPlayPO msp "
				+ "where m.id=mp.matchId "
				+ "and mp.playId=:playId "
				+ "and m.status=:status "
				+ "and mp.status=0 "
				+ "and m.longLeagueName in (:leagues) "
				+ "and m.offtime > :minMatchPlayingTime "
				+ "and msp.playId=mp.playId "
				+ "and mp.matchId=msp.matchId "
				+ "order by m.playingTime, m.code asc")
		        .setParameter("playId", playId)
		        .setParameter("status", status)
		        .setParameterList("leagues", leagueList)
		        .setParameter("minMatchPlayingTime", minMatchPlayingTime)
		        .setFirstResult(firstResult)
		        .setMaxResults(pageMaxResult)
		        .list();
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(
			String playId, int status, List<String> leagues, int firstResult,
			int pageMaxResult, Date minMatchPlayingTime) {
		return createQuery("select m, mp "
				+ "from BBMatchPO m, BBMatchPlayPO mp "
				+ "where m.id=mp.matchId "
				+ "and mp.playId=:playId "
				+ "and m.status=:status "
				+ "and mp.status=0 "
				+ "and m.longLeagueName in (:leagues) "
				+ "and m.playingTime > :minMatchPlayingTime "
				+ "order by m.playingTime asc, m.code asc")
		        .setParameter("playId", playId)
		        .setParameter("status", status)
		        .setParameterList("leagues", leagues)
		        .setParameter("minMatchPlayingTime", minMatchPlayingTime)
		        .setFirstResult(firstResult)
		        .setMaxResults(pageMaxResult)
		        .list();

	}

	@Override
	public List<Object[]> findByPlayingtime(String playId, Date from, Date to) {
		String toSql="";
		if(null!=to){
			toSql = " and m.playingTime<:to ";
		}
		String sql = "select mp, m from BBMatchPO m, BBMatchPlayPO mp "+
				"where m.id=mp.matchId and mp.playId=:playId and m.playingTime>=:from" + toSql +
				" and mp.status=0 "+
				"order by m.code asc";
		Query q = createQuery(sql).setParameter("playId", playId).setParameter("from", from);
		if(null!=to){
			q.setParameter("to", to);
		}
		List<Object[]> matches = q.list();
        return matches;
	}
	@Override
	public List<Object[]> findByPlayingtimeForSinglePass(String playId, Date from, Date to) {
		String toSql="";
		if(null!=to){
			toSql = " and m.playingTime<:to ";
		}
		String sql = "select mp, m from BBMatchPO m, BBMatchPlayPO mp, MatchSupportPlayPO msp "+
				"where m.id=mp.matchId and mp.playId=:playId and m.playingTime>=:from" + toSql +
				" and mp.status=0 and mp.matchId=msp.matchId and msp.playId=mp.playId "+
				"order by m.code asc";
		Query q = createQuery(sql).setParameter("playId", playId).setParameter("from", from);
		if(null!=to){
			q.setParameter("to", to);
		}
		List<Object[]> matches = q.list();
		return matches;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMatchAndMatchPlayByMatchId(Set<Long> matchIdSet) {
		if(null!=matchIdSet&&!matchIdSet.isEmpty()){
			return createQuery("select mp, m "
					+ " from BBMatchPO m, BBMatchPlayPO mp "
					+ "where m.id=mp.matchId "
					+ "and mp.status=0 "
					+ "and m.id in (:matchIdSet) "
					+ "order by m.playingTime, m.code asc")
			        .setParameterList("matchIdSet", matchIdSet)
			        .list();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findSingleMatchAndMatchPlayByMatchId(
			Set<Long> matchIdSet) {
		if(null!=matchIdSet&&!matchIdSet.isEmpty()){
			return createQuery("select mp, m "
					+ " from BBMatchPO m, BBMatchPlayPO mp, MatchSupportPlayPO msp "
					+ "where m.id=mp.matchId "
					+ "and mp.status=0 "
					+ "and msp.playId=mp.playId "
					+ "and mp.matchId=msp.matchId "
					+ "and m.id in (:matchIdSet) "
					+ "order by m.playingTime, m.code asc")
					.setParameterList("matchIdSet", matchIdSet)
			        .list();
		}
		return null;
	}
}
