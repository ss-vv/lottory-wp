package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.PlayMatch;
import com.xhcms.lottery.commons.persist.dao.BJDCMatchPlayDao;
import com.xhcms.lottery.commons.persist.entity.BJDCMatchPlayPO;
import com.xhcms.lottery.lang.AssertUtils;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.utils.DateUtils;


public class BJDCMatchPlayDaoImpl extends DaoImpl<BJDCMatchPlayPO> implements BJDCMatchPlayDao {

	private static final long serialVersionUID = 5603497235385527803L;

	public BJDCMatchPlayDaoImpl() {
		super(BJDCMatchPlayPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByStatus(String issueNumber, String playId, int status) {
		List<Object[]> matches = createQuery("select mp, m from BJDCMatchPO m, BJDCMatchPlayPO mp "+
    		"where m.id=mp.matchId and m.issueNumber = mp.issueNumber " +
    		"and m.issueNumber=? and mp.playId=? " +
    		"and m.status=? order by m.code") 
    		.setString(0, issueNumber)
            .setString(1, playId).setInteger(2, status).list();
		return matches;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findBy(String issueNumber, String playId) {
		List<Object[]> matches = createQuery("select mp, m from BJDCMatchPO m, BJDCMatchPlayPO mp "+
			"where m.id=mp.matchId and m.issueNumber = mp.issueNumber " +
			"and m.issueNumber=? and mp.playId=? " +
			"order by m.code") 
			.setString(0, issueNumber)
	        .setString(1, playId).list();
		return matches;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<BJDCMatchPlayPO> find(String playId, Collection<Long> matchs) {
		Criteria c = createCriteria().add(Restrictions.eq("playId", playId));
        if (matchs != null && matchs.size() > 0) {
            c.add(Restrictions.in("matchId", matchs));
        }
        return c.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<BJDCMatchPlayPO> findByMatches(Collection<PlayMatch> matchs) {
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
	public List<BJDCMatchPlayPO> findById(Collection<String> ids) {
		return createQuery("from BJDCMatchPlayPO where id in (:ids)").setParameterList("ids", ids).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findMatchBySelector(String playId,
			boolean showStopSell, List<String> leagueList, Date playingTime) {
		if(StringUtils.isBlank(playId)) {
			return null;
		}
		StringBuffer buf = new StringBuffer();
		buf.append("select mp, m  from BJDCMatchPO m, BJDCMatchPlayPO mp ")
		.append("where m.id=mp.matchId and mp.playId=:playId ");
		if(!showStopSell) {
			buf.append(" and m.status=:status ");
		}
		if(null != leagueList && leagueList.size() > 0) {
			buf.append(" and m.leagueName in (:leagues) ");
		}
		if(null == playingTime) {
			playingTime = new Date();
		}
		buf.append(" and date_format(m.playingTime, '%Y-%m-%d') = :playingTime ")
		.append(" order by m.entrustDeadline asc");
		
		Query query = createQuery(buf.toString());
		query.setParameter("playId", playId);
		query.setParameter("playingTime", DateUtils.formatShort(playingTime));
		if(!showStopSell) {
			query.setParameter("status", EntityStatus.MATCH_ON_SALE);
		}
		if(null != leagueList && leagueList.size() > 0) {
			query.setParameterList("leagues", leagueList);
		}
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> findByStatus(String playId, int matchOnSale) {
		return createQuery("select mp, m from BJDCMatchPO m, BJDCMatchPlayPO mp where m.id=mp.matchId and mp.playId=? and m.status=? order by m.entrustDeadline asc")
                .setString(0, playId).setInteger(1, matchOnSale).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findOptionsOddsById(String id) {
		String sql="select options,odds from bjdc_match_play where id=:id";
		return createSQLQuery(sql).setString("id", id).list();
	}
	@Override
	public String getPoints(String id) {
		Object o=createQuery("select concedePoints from BJDCMatchPlayPO where id=:id").setParameter("id", id).uniqueResult();
		return o!=null?o.toString():"0";
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findMatchInfoBySchemeId(Long schemeId,String play_id) {
		String sql="select bmatch.match_id,bmatch.code,bmatchplay.play_id,bmatchplay.win_option" 
                  +" from lt_bet_match bmatch,bjdc_match_play bmatchplay "
                  + "where bmatch.match_id=bmatchplay.match_id and bmatch.scheme_id=:mid"
                  + " and bmatchplay.play_id=:playid";
		return createSQLQuery(sql).setLong("mid", schemeId).setString("playid", play_id).list();
	}
}