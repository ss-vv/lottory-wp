package com.davcai.lottery.weibo.analyse.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.davcai.lottery.weibo.analyse.dao.MatchAnalyseDao;
import com.davcai.lottery.weibo.analyse.dao.entity.LeagueScoreRandPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.BasketBallLeagueSeasonPO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbLeagueSeasonBasePO;
import com.unison.lottery.weibo.data.crawler.service.store.persist.dao.entity.FbMatchBaseInfoPO;

/**
 * @author baoxing.peng
 *
 */
@Repository
public class MatchAnalyseDaoImpl extends HibernateDaoSupport implements
		MatchAnalyseDao {
	@Autowired
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Override
	public List<String> queryAllJingcaiLeagueId(int source, int leagueType) {
		Session session = getSession();
		try {
			return session
					.createQuery(
							"select leagueId from LeagueInfoPO where type=? and source=?")
					.setInteger(0, leagueType).setInteger(1, source).list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LeagueScoreRandPO> queryQtFbHomeScoreNowSeasonByLeagueId(
			String leagueId, int source) {

		try {
			return getHibernateTemplate()
					.find("select new LeagueScoreRandPO(homeTeamId,count(homeTeamId) as totalMatches,sum(homeTeamScore) as goal,"
							+ " sum(guestTeamScore) as lost,sum(case when  homeTeamScore>guestTeamScore then 1 else 0 end) as winMatches,"
							+ " sum(case when homeTeamScore=guestTeamScore then 1 else 0 end) as drawMatches,"
							+ " sum(case when homeTeamScore<guestTeamScore then 1 else 0 end) as loseMatches,"
							+ " sum(case when homeTeamScore>guestTeamScore then 3 when homeTeamScore=guestTeamScore then 1 else 0 end) as leagueScore) "
							+ " from FbMatchBaseInfoPO where leagueId=? and season="
							+ "(select max(seasonName) from FbLeagueSeasonBasePO where leagueId=?) "
							+ "and matchStatus=-1 and source=? group by homeTeamId  order by homeTeamId desc",
							leagueId, leagueId, source);
		} catch (HibernateException e) {
			throw e;
		}
	}

	@Override
	public List<LeagueScoreRandPO> queryQtFbGuestScoreNowSeasonByLeagueId(
			String leagueId, int source) {
		Session session = getSession();
		try {
			Query query = session
					.createQuery("select new LeagueScoreRandPO(guestTeamId,count(guestTeamId) as totalMatches,sum(guestTeamScore) as goal,"
							+ " sum(homeTeamScore) as lost,sum(case when  homeTeamScore<guestTeamScore then 1 else 0 end) as winMatches,"
							+ " sum(case when homeTeamScore=guestTeamScore then 1 else 0 end) as drawMatches,"
							+ " sum(case when homeTeamScore>guestTeamScore then 1 else 0 end) as loseMatches,"
							+ " sum(case when homeTeamScore<guestTeamScore then 3 when homeTeamScore=guestTeamScore then 1 else 0 end) as leagueScore)"
							+ " from FbMatchBaseInfoPO where leagueId=? and season="
							+ "(select max(seasonName) from FbLeagueSeasonBasePO where leagueId=?) "
							+ "and matchStatus=-1 and source=? group by guestTeamId order by guestTeamId desc");
			query.setString(0, leagueId).setString(1, leagueId)
					.setInteger(2, source);
			return query.list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List<FbMatchBaseInfoPO> queryFbLastestMatch(String teamId,
			long latestCount, int qtSource) {
		Session session = getSession();
		try {
			Query query = session
					.createQuery("from FbMatchBaseInfoPO where (homeTeamId=? or guestTeamId=?) and matchStatus=-1 and source=? order by matchTime desc");
			query.setString(0, teamId);
			query.setString(1, teamId);
			query.setInteger(2, qtSource);
			query.setMaxResults(((Long) latestCount).intValue());
			return query.list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}

	}

	@Override
	public String getLeagueNowSeason(String leagueId) {
		Session session = getSession();
		try {
			return (String) session
					.createQuery(
							"select max(seasonName) from FbLeagueSeasonBasePO where leagueId=?")
					.setString(0, leagueId).uniqueResult();
		} finally {
			session.close();
		}
	}

	@Override
	public List<String> queryAllJingcaiLqLeagueId(int leagueType, int source) {
		Session session = getSession();
		try {
			return session
					.createQuery(
							"select leagueId from BasketBallLeagueInfoPO where sclassKind=? and source=?")
					.setInteger(0, leagueType).setInteger(1, source).list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List<LeagueScoreRandPO> queryQtBbHomeScoreNowSeasonByLeagueId(
			String leagueId, int qtSource) {
		Session session = getSession();
		try {
			Query query = session
					.createQuery("select new LeagueScoreRandPO(homeTeam,count(homeTeam) as num,sum(homeScore)*1.0/count(homeTeam),sum(guestScore)*1.0/count(homeTeam),"
							+ "sum(case when  homeScore>guestScore then 2 else 0 end),"
							+ "sum(case when homeScore<guestScore then 1 else 0 end))"
							+ " from BasketBallMatchPO where leagueId=? and season=(select max(seasonName) "
							+ "from BasketBallLeagueSeasonPO "
							+ "where leagueId=? and source=?) and matchState=-1 and source=? group by homeTeam order by homeTeam desc");
			query.setString(0, leagueId).setString(1, leagueId).setInteger(2, qtSource)
					.setInteger(3, qtSource);
			return query.list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List<LeagueScoreRandPO> queryQtBbGuestScoreNowSeasonByLeagueId(
			String leagueId, int qtSource) {
		Session session = getSession();
		try {
			Query query = session
					.createQuery("select new LeagueScoreRandPO(guestTeam,count(guestTeam) as num,sum(guestScore)*1.0/count(guestTeam),sum(homeScore)*1.0/count(guestTeam),"
							+ "sum(case when  homeScore<guestScore then 2 else 0 end),"
							+ "sum(case when homeScore>guestScore then 1 else 0 end))"
							+ " from BasketBallMatchPO where leagueId=? and season=(select max(seasonName) "
							+ "from BasketBallLeagueSeasonPO "
							+ "where leagueId=? and source=?) and matchState=-1 and source=? group by homeTeam order by guestTeam desc");
			query.setString(0, leagueId).setString(1, leagueId).setInteger(2, qtSource)
					.setInteger(3, qtSource);
			return query.list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List<FbMatchBaseInfoPO> queryBbLastestMatch(String teamId,
			long lqLatestCount, int qtSource) {
		Session session = getSession();
		try {
			Query query = session
					.createQuery("from BasketBallMatchPO where (homeTeamId=? or guestTeamId=?) and matchState=-1 and source=? order by matchTime desc");
			query.setString(0, teamId);
			query.setString(1, teamId);
			query.setInteger(2, qtSource);
			query.setMaxResults(((Long) lqLatestCount).intValue());
			return query.list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public String getBbLeagueNowSeason(String leagueId) {
		Session session = getSession();
		try {
			return (String) session
					.createQuery(
							"select max(seasonName) from BasketBallLeagueSeasonPO where leagueId=?")
					.setString(0, leagueId).uniqueResult();
		} finally {
			session.close();
		}
	}

	@Override
	public List<LeagueScoreRandPO> queryQtFbHomeHalfScoreNowSeasonByLeagueId(
			String leagueId, int qtSource) {
		Session session = getSession();
		try {
			Query query = session
					.createQuery("select new LeagueScoreRandPO(homeTeamId,count(homeTeamId) as totalMatches,sum(homeTeamHalfScore) as goal,"
							+ " sum(guestTeamHalfScore) as lost,sum(case when  homeTeamHalfScore>guestTeamHalfScore then 1 else 0 end) as winMatches,"
							+ " sum(case when homeTeamHalfScore=guestTeamHalfScore then 1 else 0 end) as drawMatches,"
							+ " sum(case when homeTeamHalfScore<guestTeamHalfScore then 1 else 0 end) as loseMatches,"
							+ " sum(case when homeTeamHalfScore>guestTeamHalfScore then 3 when homeTeamHalfScore=guestTeamHalfScore then 1 else 0 end) as leagueScore) "
							+ " from FbMatchBaseInfoPO where leagueId=? and season="
							+ "(select max(seasonName) from FbLeagueSeasonBasePO where leagueId=?) "
							+ "and matchStatus=-1 and source=? group by homeTeamId  order by homeTeamId desc");
			query.setString(0, leagueId).setString(1, leagueId)
					.setInteger(2, qtSource);
			return query.list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public List<LeagueScoreRandPO> queryQtFbGuestHalfScoreNowSeasonByLeagueId(
			String leagueId, int qtSource) {
		Session session = getSession();
		try {
			Query query = session
					.createQuery("select new LeagueScoreRandPO(guestTeamId,count(guestTeamId) as totalMatches,sum(guestTeamHalfScore) as goal,"
							+ " sum(homeTeamHalfScore) as lost,sum(case when  homeTeamHalfScore<guestTeamHalfScore then 1 else 0 end) as winMatches,"
							+ " sum(case when homeTeamHalfScore=guestTeamHalfScore then 1 else 0 end) as drawMatches,"
							+ " sum(case when homeTeamHalfScore>guestTeamHalfScore then 1 else 0 end) as loseMatches,"
							+ " sum(case when homeTeamHalfScore<guestTeamHalfScore then 3 when homeTeamHalfScore=guestTeamHalfScore then 1 else 0 end) as leagueScore)"
							+ " from FbMatchBaseInfoPO where leagueId=? and season="
							+ "(select max(seasonName) from FbLeagueSeasonBasePO where leagueId=?) "
							+ "and matchStatus=-1 and source=? group by guestTeamId order by guestTeamId desc");
			query.setString(0, leagueId).setString(1, leagueId)
					.setInteger(2, qtSource);
			return query.list();
		} catch (HibernateException e) {
			throw e;
		} finally {
			session.close();
		}
	}

	@Override
	public int queryNowSeasonByLeagueIdAndYear(String nowYear,String leagueId) {
		Session session = getSession();
		Criteria crite = session.createCriteria(FbLeagueSeasonBasePO.class);
		crite.add(Restrictions.like("seasonName", "%"+nowYear+"%"));
		crite.add(Restrictions.eq("leagueId",leagueId));
		List list = crite.list();
		session.close();
		if(list!=null && !list.isEmpty()){
			return 1;
		}
		return 0;
	}

	@Override
	public int countBbLeagueSeasonNowYear(String leagueId, String nowYear) {
		Session session = getSession();
		Criteria crite = session.createCriteria(BasketBallLeagueSeasonPO.class);
		crite.add(Restrictions.like("seasonName", "%"+nowYear+"%"));
		crite.add(Restrictions.eq("leagueId",leagueId));
		List list = crite.list();
		session.close();
		if(list!=null && !list.isEmpty()){
			return 1;
		}
		return 0;
	}

}
