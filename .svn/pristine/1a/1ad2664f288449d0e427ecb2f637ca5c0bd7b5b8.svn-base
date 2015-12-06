package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.dao.FBMatchPlayDao;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPlayPO;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.utils.DateUtils;

public class FBMatchPlayDaoImpl extends DaoImpl<FBMatchPlayPO> implements
		FBMatchPlayDao {

	private static final long serialVersionUID = 5603497235385527803L;

	public FBMatchPlayDaoImpl() {
		super(FBMatchPlayPO.class);
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public List<FBMatchPlayPO> findByMatchId(Collection<Long> matchIds) {
		if(matchIds ==null || matchIds.size() < 1){
			return Collections.EMPTY_LIST;
		}
		return createCriteria().add(Restrictions.in("matchId", matchIds)).list();
	}

    @SuppressWarnings("unchecked")
    @Override
    public List<FBMatchPlayPO> find(String playId, Collection<Long> matchs) {
        Criteria c = createCriteria().add(Restrictions.eq("playId", playId));
        if (matchs != null && matchs.size() > 0) {
            c.add(Restrictions.in("matchId", matchs));
        }
        return c.list();
    }

    /**
     * 只能按照比赛code排，否则前台在投注截止时间和“code”的星期不一致时会出现合并错误。
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findByStatus(String playId, int status) {
        List<Object[]> matches = createQuery("select mp, m from FBMatchPO m, FBMatchPlayPO mp "+
        		"where m.id=mp.matchId and mp.playId=? and m.status=? and mp.status=0 "+
        		"order by date(m.entrustDeadline), m.code")	// m.playingTime, 
                .setString(0, playId).setInteger(1, status).list();
        List<Object[]> sortedMatches = sortMatchesByCode(matches);
        return sortedMatches;
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findByStatusForSinglePass(String playId, int status) {
    	List<Object[]> matches = createQuery("select mp, m from FBMatchPO m, FBMatchPlayPO mp, MatchSupportPlayPO msp "+
    			"where m.id=mp.matchId and mp.playId=? and m.status=? and mp.status=0  and msp.playId=mp.playId and mp.matchId=msp.matchId "+
    			"order by date(m.entrustDeadline), m.code")	// m.playingTime, 
    			.setString(0, playId).setInteger(1, status).list();
    	List<Object[]> sortedMatches = sortMatchesByCode(matches);
    	return sortedMatches;
    }
    
    private List<Object[]> sortMatchesByCode(List<Object[]> data) {
    	List<List<Object[]>> groupedMatches = new LinkedList<List<Object[]>>();
    	char prevWeekDay = 0;
    	List<Object[]> matchesWithSameCode = new LinkedList<Object[]>();
    	for (Object[] d : data) {
            FBMatchPO match = (FBMatchPO) d[1];
            if( prevWeekDay == 0 || prevWeekDay !=match.getCode().charAt(0) ){
            	if (matchesWithSameCode.size()>0){
            		groupedMatches.add(matchesWithSameCode);
            	}
            	matchesWithSameCode = new LinkedList<Object[]>();
            }
            matchesWithSameCode.add(d);
            prevWeekDay = match.getCode().charAt(0);
        }
    	if (matchesWithSameCode.size()>0){
    		groupedMatches.add(matchesWithSameCode);
    	}
    	sortGroupedMatches(groupedMatches);
		return flatten(groupedMatches);
	}

	private List<Object[]> flatten(List<List<Object[]>> groupedMatches) {
		List<Object[]> flatList = new LinkedList<Object[]>();
		for(List<Object[]> group : groupedMatches){
			for(Object[] matches : group){
				flatList.add(matches);
			}
		}
		return flatList;
	}

	private void sortGroupedMatches(List<List<Object[]>> groupedMatches) {
		Comparator<List<Object[]>> comparator = new Comparator<List<Object[]>>() {
			@Override
			public int compare(List<Object[]> o1, List<Object[]> o2) {
				FBMatchPO m1 = (FBMatchPO) o1.get(0)[1];
				FBMatchPO m2 = (FBMatchPO) o2.get(0)[1];
				Date m1PlayingTime = m1.getPlayingTime();
				Date m2PlayingTime = m2.getPlayingTime();
				return m1PlayingTime.compareTo(m2PlayingTime);
			}
		};
		Collections.sort(groupedMatches, comparator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMatchByPlayIdAndStatusAndLeagueName(
			String playId, int status, List<String> leagues,Integer firstResult, Integer pageMaxResult) {
		return createQuery("select m, mp from FBMatchPO m, FBMatchPlayPO mp where m.id=mp.matchId and mp.playId=? and m.status=? and mp.status=0 and m.longLeagueName in (:leagues) order by m.playingTime, m.code asc")
        .setString(0, playId).setInteger(1, status).setParameterList("leagues", leagues).setFirstResult(firstResult).setMaxResults(pageMaxResult).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FBMatchPlayPO> findByMatches(Collection<PlayMatch> matchs) {
		Criteria c = createCriteria();
		Disjunction disj = Restrictions.disjunction();
		for (PlayMatch m : matchs){
			AssertUtils.assertNotBlank(m.getPlayId(), "PlayMatch.playId");
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
		return createQuery("select mp, m  from FBMatchPO m, FBMatchPlayPO mp where m.id=mp.matchId and mp.playId=? and m.status=? and mp.status=0 and m.longLeagueName in (:leagues) order by m.playingTime, m.code asc")
		        .setString(0, playId).setInteger(1, status).setParameterList("leagues", leagueList).setFirstResult(firstResult).setMaxResults(pageMaxResult).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMatchBySelector(String playId,
			boolean isShowStopSell, List<String> leagueList, Date playingTime) {
		if(StringUtils.isBlank(playId)) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		buf.append("select mp, m  from FBMatchPO m, FBMatchPlayPO mp ")
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
		.append(" order by m.entrustDeadline, m.code");
		
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findLeagues(String playId, Date playingTime) {
		if(StringUtils.isBlank(playId)) {
			return null;
		}
		if(null == playingTime) {
			playingTime = new Date();
		}
		StringBuffer buf = new StringBuffer();
		buf.append("SELECT fb.league_name, count(*) FROM fb_match fb, fb_match_play fp ")
		.append("WHERE fb.id=fp.match_id AND fp.play_id=:playId ")
		.append(" AND DATE_FORMAT(fb.playing_time, '%Y-%m-%d')=:playingTime ")
		.append(" GROUP BY fb.league_name")
		.append(" ORDER BY DATE(fb.entrust_deadline), fb.code");
		
		SQLQuery query = createSQLQuery(buf.toString());
		query.setParameter("playId", playId);
		query.setParameter("playingTime", DateUtils.formatShort(playingTime));
		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findOptionsOddsById(String id) {
		String sql="select options,odds from fb_match_play where id=:id";
		return createSQLQuery(sql).setString("id", id).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findMatchInfoBySchemeId(Long schemeId,String play_id) {
		String  sql="select bmatch.match_id,bmatch.code,bmatchplay.play_id,bmatchplay.win_option "
				   + " from lt_bet_match bmatch,fb_match_play bmatchplay where "
				   + " bmatch.match_id=bmatchplay.match_id and bmatch.scheme_id=:mid"
				   + " and bmatchplay.play_id=:playid";
		return createSQLQuery(sql).setLong("mid", schemeId).setString("playid", play_id).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByStatusAndAfterDeadLine(String playId,
			int status, List<String> leagueList, Integer firstResult,
			Integer pageMaxResult, Date minMatchPlayingTime) {
		return createQuery("select mp, m "
				+ " from FBMatchPO m, FBMatchPlayPO mp "
				+ "where m.id=mp.matchId "
				+ "and mp.playId=:playId "
				+ "and m.status=:status "
				+ "and mp.status=0 "
				+ "and m.longLeagueName in (:leagues) "
				+ "and m.playingTime>:minMatchPlayingTime "
				+ "order by m.playingTime, m.code asc")
		        .setParameter("playId", playId)
		        .setParameter("status", status)
		        .setParameterList("leagues", leagueList)
		        .setParameter("minMatchPlayingTime",minMatchPlayingTime)
		        .setFirstResult(firstResult)
		        .setMaxResults(pageMaxResult)
		        .list();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMatchByPlayIdAndStatusAndLeagueNameAndAfterDeadLine(
			String playId, int status, List<String> leagues,
			int firstResult, int pagingMaxResult, Date minMatchPlayingTime) {
		return createQuery("select m, mp "
				+ "from FBMatchPO m, FBMatchPlayPO mp "
				+ "where m.id=mp.matchId "
				+ "and mp.playId=:playId "
				+ "and m.status=:status "
				+ "and mp.status=0 "
				+ "and m.longLeagueName in (:leagues) "
				+ "and m.playingTime>:minMatchPlayingTime "
				+ "order by m.playingTime asc, m.code asc")
		        .setParameter("playId", playId)
		        .setParameter("status", status)
		        .setParameterList("leagues", leagues)
		        .setParameter("minMatchPlayingTime",minMatchPlayingTime)
		        .setFirstResult(firstResult)
		        .setMaxResults(pagingMaxResult)
		        .list();

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findByEntrustDeadline(String playId,Date from, Date to) {
		String toSql="";
		if(null!=to){
			toSql = " and m.entrustDeadline<:to ";
		}
		String sql = "select mp, m from FBMatchPO m, FBMatchPlayPO mp "+
        		"where m.id=mp.matchId and mp.playId=:playId and m.entrustDeadline>=:from" + toSql +
        		" and mp.status=0 "+
        		"order by date(m.entrustDeadline), m.code";
		Query q = createQuery(sql).setParameter("playId", playId).setParameter("from", from);
		if(null!=to){
			q.setParameter("to", to);
		}
		List<Object[]> matches = q.list();
        List<Object[]> sortedMatches = sortMatchesByCode(matches);
        return sortedMatches;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findByPlayingtime(String playId,Date from, Date to) {
		String toSql="";
		if(null!=to){
			toSql = " and m.playingTime<:to ";
		}
		String sql = "select mp, m from FBMatchPO m, FBMatchPlayPO mp "+
				"where m.id=mp.matchId and mp.playId=:playId and m.playingTime>=:from" + toSql +
				" and mp.status=0 "+
				"order by date(m.playingTime), m.code";
		Query q = createQuery(sql).setParameter("playId", playId).setParameter("from", from);
		if(null!=to){
			q.setParameter("to", to);
		}
		List<Object[]> matches = q.list();
        List<Object[]> sortedMatches = sortMatchesByCode(matches);
        return sortedMatches;
	}
	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findByPlayingtimeForSinglePass(String playId,Date from, Date to) {
		String toSql="";
		if(null!=to){
			toSql = " and m.playingTime<:to ";
		}
		String sql = "select mp, m from FBMatchPO m, FBMatchPlayPO mp, MatchSupportPlayPO msp "+
				"where m.id=mp.matchId and mp.playId=:playId and m.playingTime>=:from" + toSql +
				" and mp.status=0 and msp.playId=mp.playId and mp.matchId=msp.matchId "+
				"order by date(m.playingTime), m.code";
		Query q = createQuery(sql).setParameter("playId", playId).setParameter("from", from);
		if(null!=to){
			q.setParameter("to", to);
		}
		List<Object[]> matches = q.list();
		List<Object[]> sortedMatches = sortMatchesByCode(matches);
		return sortedMatches;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findSingleByStatusAndAfterDeadLine(String playId,
			int status, List<String> leagueList, int firstResult,
			int pageMaxResult, Date minMatchPlayingTime) {
		return createQuery("select mp, m "
				+ " from FBMatchPO m, FBMatchPlayPO mp, MatchSupportPlayPO msp "
				+ "where m.id=mp.matchId "
				+ "and mp.playId=:playId "
				+ "and m.status=:status "
				+ "and mp.status=0 "
				+ "and m.longLeagueName in (:leagues) "
				+ "and m.playingTime>:minMatchPlayingTime "
				+ "and msp.playId=mp.playId "
				+ "and mp.matchId=msp.matchId "
				+ "order by m.playingTime, m.code asc")
		        .setParameter("playId", playId)
		        .setParameter("status", status)
		        .setParameterList("leagues", leagueList)
		        .setParameter("minMatchPlayingTime",minMatchPlayingTime)
		        .setFirstResult(firstResult)
		        .setMaxResults(pageMaxResult)
		        .list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMatchAndMatchPlayByMatchId(Set<Long> matchIdSet) {
		if(null!=matchIdSet&&!matchIdSet.isEmpty()){
			return createQuery("select mp, m "
					+ " from FBMatchPO m, FBMatchPlayPO mp "
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
					+ " from FBMatchPO m, FBMatchPlayPO mp, MatchSupportPlayPO msp "
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
