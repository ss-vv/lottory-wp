package com.unison.lottery.weibo.data.service.store.persist.dao.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.data.service.store.data.MatchStatus;
import com.unison.lottery.weibo.data.service.store.data.QTMatchVO;
import com.unison.lottery.weibo.data.service.store.persist.dao.QTMatchDao;
import com.unison.lottery.weibo.data.service.store.persist.entity.QTMatchPO;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.utils.BeanUtilsTools;

/**
 * 球探赛事记录dao
 * @author Wang Lei
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class QTMatchDaoImpl extends DaoImpl<QTMatchPO>  implements QTMatchDao {

	private static final long serialVersionUID = -4375924861199028184L;

	public QTMatchDaoImpl(){
		super(QTMatchPO.class);
	}

	@Override
	public List<Object[]> findExistMatchs(Set<Long> ids){
		if(ids ==null || ids.isEmpty()){
			return new ArrayList<Object[]>();
		}
		return createQuery("select m.id,m.alreadyCrawl from QTMatchPO as m where m.id in (:id)").setParameterList("id", ids).list();
	}
	
	@Override
	public List<Long> findExistMatchIds(Set<Long> ids){
		if(ids ==null || ids.isEmpty()){
			return new ArrayList<Long>();
		}
		return createQuery("select m.id from QTMatchPO as m where m.id in (:id)").setParameterList("id", ids).list();
	}

	@Override
	public void flushAndEvict(Object po) {
		session().flush();
		session().evict(po);
	}

	@Override
	public int queryQTMatchId(String lcMatchId) {
		Object o = createSQLQuery("select qiuTanWangMatchId from md_qt_lc_matchId where lcMatchId = :lcMatchId")
				.setParameter("lcMatchId", lcMatchId).uniqueResult();
		if(null == o) {
			return 0;
		} else {
			return Integer.parseInt(String.valueOf(o));
		}
	}
	
	@Override
	public QTMatchPO queryQTMatchInfo(long qtMatchId) {
		if(qtMatchId > 0) {
			Query qs = createQuery("from QTMatchPO as m where m.id = :id");
			qs.setParameter("id", qtMatchId);
			return (QTMatchPO) qs.uniqueResult();
		}
		return null;
	}
	
	@Override
	public void updateQTSiteDataToQTMatch(QTMatchPO qt){
//		createSQLQuery("update md_qt_match set leagueId = ? , leagueShortName = ?,color = ?," +
//				"homeTeamId = ? , guestTeamId=?, homeTeamName=?, guestTeamName=? , score=?, halfScore=? ," +
//				"matchStatus=? ,updateTime=? where id = ?")//, alreadyCrawl=?
//				.setLong(0, qt.getLeagueId())
//				.setString(1, qt.getLeagueShortName())
//				.setString(2, qt.getColor())
////				.setLong(3, qt.getHomeTeamId())
////				.setLong(4, qt.getGuestTeamId())
//				.setString(5, qt.getHomeTeamName())
//				.setString(6, qt.getGuestTeamName())
//				.setString(7, qt.getScore())
//				.setString(8, qt.getHalfScore())
//				.setInteger(9, qt.getMatchStatus())
//				//.setInteger(10, qt.getAlreadyCrawl())
//				.setDate(10, new Date())
//				.setLong(11, qt.getId())
//				.executeUpdate();
	}
	
	@Override
	public void updateQTBFDataToQTMatch(QTMatchPO qt){
		try {
			QTMatchPO qtMatchPO = get(qt.getId());
//			if (qt.getHalfStartTime() == null) {
//				//半场时间为null，不更新该字段
//				BeanUtils.copyProperties(qt, qtMatchPO,
//						new String[] { "halfStartTime" });
//				
//			} else {
//				org.apache.commons.beanutils.BeanUtils.copyProperties(qtMatchPO,
//						qt);
//			}
			BeanUtilsTools.copyNotNullProperties(qt, qtMatchPO, null);
			qtMatchPO.setUpdateTime(new Date());
			update(qtMatchPO);
		} catch (HibernateException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	@Override
	public List<QTMatchPO> findQTMatchPO(long homeTeamId,long guestTeamId,
			Date from, Date to, int size) {
		Criteria criteria = createCriteria();
		if(homeTeamId > 0){
			criteria.add(Restrictions.eq("homeTeamId", homeTeamId));
		}
		if(guestTeamId > 0){
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

	@Override
	public List<QTMatchPO> findLastestCompleteQTMatchPO(long teamId, int size) {
		List<QTMatchPO> qtMList = new ArrayList<QTMatchPO>();
		if(teamId > 0 && size > 0) {
			Criteria criteria = createCriteria();
			criteria.add(Restrictions.or(Restrictions.eq("homeTeamId", teamId), 
					Restrictions.eq("guestTeamId", teamId)));
			criteria.add(Restrictions.eq("matchStatus", MatchStatus.COMPLETE));
			criteria.addOrder(Order.desc("matchTime"));
			criteria.setMaxResults(size);
			qtMList = criteria.list();
		}
		return qtMList;
	}

	@Override
	public List<QTMatchPO> findQTMatchPO(long teamId, Date from, Date to,
			int size) {
		Criteria criteria = createCriteria();
		criteria.add(
				Restrictions.or(
						Restrictions.eq("homeTeamId", teamId),
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
	
	@Override
	public List<QTMatchVO> findQTMatchByDate(Date start, Date end){
		return createQuery("select new com.unison.lottery.weibo.data.service.store.data.QTMatchVO(" +
				"m.id, l.id, m.color, m.leagueShortName, l.matchTime, m.matchStatus, m.homeTeamPosition, m.guestTeamPosition, m.homeTeamName," +
				"m.guestTeamName, m.score, m.halfScore" +
				") from QTLCMatchidPO as l , QTMatchPO as m where l.qiuTanWangMatchId = m.id and l.lotteryName='竞彩足球'" +
				" and l.matchTime between ? and ? order by l.matchTime,l.id asc").setParameter(0, start).setParameter(1, end).list();
	}
	
	@Override
	public List<QTMatchVO> findQTMatchByIssueNum(String issueNum){
		return createQuery("select new com.unison.lottery.weibo.data.service.store.data.QTMatchVO(" +
				"m.id, l.id, m.color, m.leagueShortName, l.matchTime, m.matchStatus, m.homeTeamPosition, m.guestTeamPosition, m.homeTeamName," +
				"m.guestTeamName, m.score, m.halfScore" +
				") from QTLCMatchidPO as l , QTMatchPO as m where l.qiuTanWangMatchId = m.id and l.lotteryName='14场胜负彩 '" +
				" and l.issueNum = ? ")
				.setParameter(0, issueNum).list();
	}

	@Override
	public String findLCMatchId(long qiuTanMatchId) {
		return (String) createQuery("select m.lcMatchId from QTLCMatchidPO as m where qiuTanWangMatchId = :qiuTanMatchId")
				.setParameter("qiuTanMatchId", qiuTanMatchId).uniqueResult();
	}

	@Override
	public List<Object[]> findQTFBMatchPosition(Collection<String> lcMatchIdList) {
		if(null == lcMatchIdList || lcMatchIdList.size() == 0) {
			return null;
		}
		StringBuffer buf = new StringBuffer("select r.lcMatchId,fb.homeTeamPosition, ");
		buf.append("fb.guestTeamPosition, fb.matchStatus, fb.score, r.qiuTanWangTurnHomeAndAway ")
		.append("from md_qt_match fb, md_qt_lc_matchId r ")
		.append("where r.qiuTanWangMatchId = fb.id ")
		.append("and r.lcMatchId in (:lcMatchIdList)");
		SQLQuery query = createSQLQuery(buf.toString());
		query.setParameterList("lcMatchIdList", lcMatchIdList);
		return query.list();
	}
	
	@Override
	public List<Object[]> findQTBBMatchPosition(Collection<String> lcMatchIdList) {
		if(null == lcMatchIdList || lcMatchIdList.size() == 0) {
			return null;
		}
		StringBuffer buf = new StringBuffer("select r.lcMatchId, ");
		buf.append("bb.matchState, bb.homeScore, bb.guestScore, r.qiuTanWangTurnHomeAndAway ")
		.append("from md_bb_match bb, md_qt_lc_matchId r ")
		.append("where r.qiuTanWangMatchId = bb.id ")
		.append("and r.lcMatchId in (:lcMatchIdList)");
		SQLQuery query = createSQLQuery(buf.toString());
		query.setParameterList("lcMatchIdList", lcMatchIdList);
		return query.list();
	}

	@Override
	public List<Object[]> findQTFBMatchResult() {
		//String sql="select lc.lcMatchId,md.leagueShortName,md.homeTeamName,md.guestTeamName,md.score,md.homeTeamPosition, md.guestTeamPosition from md_qt_match md,md_qt_lc_matchId lc where md.matchStatus=-1  and md.id=lc.qiuTanWangMatchId order by md.matchTime desc limit 0,22";
		String sql= "select  fb.id,fl.chineseName,fb.homeTeamName,fb.guestTeamName,concat(fb.homeTeamScore,':',fb.guestTeamScore) as score,"
				  +" fb.homeTeamPosition,fb.guestTeamPosition from md_qt_match_base fb,md_fb_league_base fl"
				  +" where fb.leagueId=fl.leagueId and  fb.matchStatus=-1 and fb.jingcaiId is not null "
				  + " order by fb.createTime desc  limit 22";
		return createSQLQuery(sql).list();
	}

	@Override
	public String getTeamColor(Long id) {
		//String sql="select md.color from md_qt_match md where id=(select m.qiuTanWangMatchId from md_qt_lc_matchId m where m.lcMatchId=?)";
		String sql="select l.colour from md_fb_league_base l "
				 +" where l.leagueId="
				 +"(" 
                 +"  select leagueId from md_qt_match_base mb,"
                 +"  ("
                 +"   select cn_code,playing_time from fb_match where id=?"
                 +"  ) t"
                 +"  where t.cn_code=mb.jingcaiId and t.playing_time =mb.matchTime"
                 +")"; 
		Object o=createSQLQuery(sql).setParameter(0, id).uniqueResult();
		return o==null?"green":o.toString();
	}
}
