package com.unison.lottery.weibo.common.persist.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertyProjection;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.unison.lottery.weibo.common.entity.QTLCMatchidPO;
import com.unison.lottery.weibo.common.persist.QTMatchidDao;
import com.xhcms.commons.persist.hibernate.DaoImpl;

/**
 * 足球球队信息dao
 * 
 * @author Wang Lei
 *
 */
@Repository
@SuppressWarnings("unchecked")
public class QTMatchidImpl extends DaoImpl<QTLCMatchidPO> implements
		QTMatchidDao {

	private static final long serialVersionUID = -5167797685294484328L;

	public QTMatchidImpl() {
		super(QTLCMatchidPO.class);
	}

	@Override
	public void merge(Collection<QTLCMatchidPO> data) {
		for (QTLCMatchidPO d : data) {
			this.session().merge(d);
		}
	}

	@Override
	public List<Integer> findNewQTMatchIds() {
		Calendar expireTime = Calendar.getInstance();
		expireTime.add(Calendar.MINUTE, -5);
		List<Integer> ids = createSQLQuery(
				"select f.qiuTanWangMatchId from md_qt_lc_matchId as f where f.createTime > ? ")
				.setTimestamp(0, expireTime.getTime()).list();
		return ids;
	}

	@Override
	public List<Object[]> findMatchTime(Set<Long> ids) {
		return createQuery(
				"select m.qiuTanWangMatchId,m.matchTime from QTLCMatchidPO as m where qiuTanWangMatchId in(:qiuTanWangMatchId)")
				.setParameterList("qiuTanWangMatchId", ids).list();
	}

	@Override
	public long findQTMatchId(String lcMatchId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("lcMatchId", lcMatchId));
		PropertyProjection prop = Projections.property("qiuTanWangMatchId");
		c.setProjection(prop);
		Object result = c.uniqueResult();
		long qtMatchId = 0L;
		if (null != result) {
			qtMatchId = (Long) result;
		}
		return qtMatchId;
	}

	@Override
	public long findLCMatchId(Long qtMatchId, String lotteryName) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("qiuTanWangMatchId", qtMatchId));
		c.add(Restrictions.eq("lotteryName", lotteryName));
		c.addOrder(Order.desc("matchTime"));
		PropertyProjection prop = Projections.property("lcMatchId");
		c.setProjection(prop);
		List<String> result = c.list();
		long lcMatchId = 0L;
		if (null != result && !result.isEmpty()) {
			if (StringUtils.isNotBlank(result.get(0))) {
				try {
					lcMatchId = Long.parseLong(result.get(0).toString());
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}
		return lcMatchId;
	}

	@Override
	public List<Object[]> findTeamPosition(Long matchId) {
		//String sql="select qt.homeTeamPosition,qt.guestTeamPosition from md_qt_lc_matchid m,md_qt_match qt "
	     //         +" where m.qiuTanWangMatchId=qt.id and m.lcMatchId=:matchid";
		String sql="select "
				  +"  qt.homeTeamPosition,"
				  + " qt.guestTeamPosition "
				  + " from "
				  + " md_qt_match_base qt,"
				  + " ("
				  + " select fb.cn_code,fb.playing_time from fb_match fb where fb.id=:matchid"
				  + " ) nfb"
	              + "  where "
	              + " qt.matchTime=nfb.playing_time and"
	              + " qt.jingcaiId=nfb.cn_code";
		return createSQLQuery(sql).setLong("matchid", matchId).list();
	}
}
