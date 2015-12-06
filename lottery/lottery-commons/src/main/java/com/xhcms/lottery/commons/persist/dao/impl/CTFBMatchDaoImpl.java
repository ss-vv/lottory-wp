package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.CTFBMatchDao;
import com.xhcms.lottery.commons.persist.entity.CTFBMatchPO;

/**
 * 传统足彩赛事dao
 * @author Wang Lei
 *
 */
@SuppressWarnings("unchecked")
public class CTFBMatchDaoImpl extends DaoImpl<CTFBMatchPO> implements CTFBMatchDao {

    private static final long serialVersionUID = 7267885448985701807L;

    public CTFBMatchDaoImpl() {
        super(CTFBMatchPO.class);
    }

	@Override
	public List<CTFBMatchPO> findOnSale(String playId) {
		String hql="SELECT m FROM CTFBMatchPO as m ,IssueInfoPO as i WHERE m.issueNumber=i.issueNumber " +
				"AND m.lotteryId=i.lotteryId AND i.status = 1 AND i.playId=? AND i.valid = 1 AND" +
				" (?> i.startTime OR i.startTime IS NULL) AND ?< i.closeTime";
		Date date = new Date();
		List<CTFBMatchPO> list=createQuery(hql)
		.setString(0,playId)
		.setTimestamp(1, date)
		.setTimestamp(2, date).list();
		return list;
	}
	
	@Override
	public List<CTFBMatchPO> findMAXIssueMatchsByPlayId(String playId) {
		String hql="FROM CTFBMatchPO as m WHERE m.issueNumber=(select max(issueNumber) " +
				"from IssueInfoPO where playId=:playId and valid = 1) and m.playId=:playId order by m.matchId";
		List<CTFBMatchPO> list=createQuery(hql)
				.setString("playId",playId).list();
		return list;
	}

	@Override
	public List<CTFBMatchPO> findByIssueNoAndPlayId(String issueNumber,
			String playId) {
		Criteria c = createCriteria();
		if(StringUtils.isBlank(issueNumber) || StringUtils.isBlank(playId)){
			return null;
		}
		c.add(Restrictions.eq("issueNumber", issueNumber));
		c.add(Restrictions.eq("playId", playId));
		c.addOrder(Order.asc("matchId"));
        return c.list();
	}
	
	@Override
	public List<CTFBMatchPO> find(Paging paging, int status, Date from, Date to) {
		PagingQuery<CTFBMatchPO> q = pagingQuery(paging);
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
	
	@Override
	public List<CTFBMatchPO> find(Paging paging, int status, Date from,
			Date to, String playId) {
		PagingQuery<CTFBMatchPO> q = pagingQuery(paging);
		if(status != -1) {
			q.add(Restrictions.eq("status", status));
		}
		if (from != null) {
            q.add(Restrictions.ge("playingTime", from));
        }
        if (to != null) {
            q.add(Restrictions.lt("playingTime", to));
        }
        if (null != playId){
        	q.add(Restrictions.eq("playId", playId));
        }
	    return q.desc("id").list();
	}
	
	@Override
	public List<Object[]> queryCTFBMatchCountByStatus(int status) {
		return createQuery("select count(*), min(offtime) from CTFBMatchPO m where m.status=?").setParameter(0, status).list();
	}

	@Override
	public List<String> queryCTFBLeaguesByStatus(int status) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("status", status));
		criteria.setProjection(Projections.groupProperty("leagueName"));
		return criteria.list();
	}

	@Override
	public List<CTFBMatchPO> findByIssueNoAndPlayId(String issueNumber,
			String playId, Paging paging) {
		Criteria c = createCriteria();
		if(StringUtils.isBlank(issueNumber) || StringUtils.isBlank(playId)){
			return null;
		}
		c.add(Restrictions.eq("issueNumber", issueNumber));
		c.add(Restrictions.eq("playId", playId));
		c.addOrder(Order.asc("matchId"));
		if(null != paging) {
			c.setFirstResult(paging.getFirstResult() - 1);
			c.setMaxResults(paging.getMaxResults());
		}
		return c.list();
	}
	@Override
	public Integer findMatchCount() {
		String sql="select count(*) from ctfb_match ct where ct.status=1";
		return Integer.parseInt(createSQLQuery(sql).uniqueResult().toString());
	}

	@Override
	public List<CTFBMatchPO> findCTFBMatchByIssue(String issue,String playId) {
		 Query query=this.createQuery("from CTFBMatchPO ct where ct.issueNumber=:issue and ct.playId=:playId order by ct.matchId asc");
		 query.setParameter("issue", issue);
		 query.setParameter("playId", playId);
		 return query.list();
	}

	@Override
	public CTFBMatchPO findCTFBMatchById(String id) {
		return this.get(id);
	}

	@Override
	public void updateCTFBMatchScore(CTFBMatchPO ctfb) {
		Query query=this.createQuery("update CTFBMatchPO ct set ct.halfScore=:halfScore,ct.score=:score,ct.status=:status "
				+ " where ct.id=:id");
		query.setParameter("halfScore", ctfb.getHalfScore());
		query.setParameter("score", ctfb.getScore());
		query.setParameter("status", ctfb.getStatus());
		query.setParameter("id", ctfb.getId());
		query.executeUpdate();
	}


	@Override
	public Long countHaveMatchResultByIssueNumberAndPlayId(String issueNumber,
			String playId) {
		Long result=null;
		if(StringUtils.isNotBlank(issueNumber)&&StringUtils.isNotBlank(playId)){
			String hql="select count(*) "
					+ "from CTFBMatchPO "
					+ "where "
					+ "issueNumber=:issueNumber "
					+ "and playId=:playId "
					+ "and ("
					+ "	(halfScore !='' and score !='' and status=-1) "
					+ " or "
					+ "	status=4"
					+ ")";
			Query query = createQuery(hql);
			query.setParameter("issueNumber", issueNumber);
			query.setParameter("playId", playId);
			result=(Long) query.uniqueResult();
			
		}
		return result;
	}

	@Override
	public List<Object> findCTFBMatchByIssue_(String issue, String playId) {
		String sql="select ct.id,fb.half_score,fb.score,fb.`status` "
				 + " from fb_match fb,ctfb_match ct "
				 + " where ct.issue_number=:issue"
		         + " and fb.home_team_name like concat('%',ct.home_team_name,'%') "
		         + " and fb.guest_team_name like concat('%',ct.guest_team_name,'%')"
		         + " and fb.playing_time=ct.offtime and ct.play_id=:playId";
		 SQLQuery sqlQuery=createSQLQuery(sql);
		 sqlQuery.setParameter("issue", issue);
		 sqlQuery.setParameter("playId", playId);
		 return sqlQuery.list();
	}

}
