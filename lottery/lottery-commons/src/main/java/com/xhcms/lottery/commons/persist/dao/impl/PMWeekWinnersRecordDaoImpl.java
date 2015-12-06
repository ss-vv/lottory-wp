package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.lang.PMWeek.WinnersRecord;
import com.xhcms.lottery.commons.persist.dao.PMWeekWinnersRecordDao;
import com.xhcms.lottery.commons.persist.entity.PMWeekWinnersRecordPO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.EntityType;

@Repository
public class PMWeekWinnersRecordDaoImpl extends DaoImpl<PMWeekWinnersRecordPO>
		implements PMWeekWinnersRecordDao {

	private static final long serialVersionUID = 5678860346796275633L;

	public PMWeekWinnersRecordDaoImpl() {
		super(PMWeekWinnersRecordPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Long> findWinSchemeIds(Date beginTime, Date endTime,
			String[] lotteryIds) {
		StringBuilder hql = new StringBuilder();
		hql.append(
				"select s.id from PMWeekWinnersRecordPO as w right join w.scheme as s ")
				.append("where s.lotteryId in (:lotteryIds) ")
				.append("and locate('_2', s.playId) > 0 ")
				.append("and s.createdTime > :beginTime and s.createdTime < :endTime ")
				.append("and s.status = :schemeStatus and w.id is null ");
		StringBuilder aloneSql = new StringBuilder("and (s.type = :aloneScheme and s.showScheme = 1) ");
		StringBuilder followSql = new StringBuilder("and (s.type = :followScheme and s.isPublishShow = 1)");
		List<Long> aloneSchemeIds = createQuery(hql.toString() + aloneSql.toString())
				.setParameterList("lotteryIds", lotteryIds)
				.setTimestamp("beginTime", beginTime)
				.setTimestamp("endTime", endTime)
				.setParameter("schemeStatus", EntityStatus.TICKET_AWARDED)
				.setParameter("aloneScheme", EntityType.BETTING_ALONE)
				.list();
		
		List<Long> followSchemeIds = createQuery(hql.toString() + followSql.toString())
				.setParameterList("lotteryIds", lotteryIds)
				.setTimestamp("beginTime", beginTime)
				.setTimestamp("endTime", endTime)
				.setParameter("schemeStatus", EntityStatus.TICKET_AWARDED)
				.setParameter("followScheme", EntityType.BETTING_FOLLOW)
				.list();
		
		Set<Long> ids = new HashSet<Long>();
		if(null != aloneSchemeIds && aloneSchemeIds.size() > 0) {
			ids.addAll(aloneSchemeIds);
		}
		if(null != followSchemeIds && followSchemeIds.size() > 0) {
			ids.addAll(followSchemeIds);
		}
		
		return ids;
	}

	@Override
	public PMWeekWinnersRecordPO getBySid(Long sid) {
		Criteria c = createCriteria().add(Restrictions.eq("scheme.id", sid));
		return (PMWeekWinnersRecordPO) c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PMWeekWinnersRecordPO> getRecordsByUsers(Collection<Long> ids) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) - 1);
		
		Criteria c = createCriteria().add(Restrictions.in("userId", ids))
				.add(Restrictions.eq("status", WinnersRecord.not_prize))
				.add(Restrictions.gt("createdTime", cal.getTime()));
		List<PMWeekWinnersRecordPO> result = c.list();
		
		return result;
	}
}