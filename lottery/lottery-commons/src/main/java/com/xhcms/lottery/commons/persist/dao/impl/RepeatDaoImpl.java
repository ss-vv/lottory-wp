package com.xhcms.lottery.commons.persist.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.RepeatDao;
import com.xhcms.lottery.commons.persist.entity.RepeatPlanPO;
import com.xhcms.lottery.lang.EntityStatus;
import com.xhcms.lottery.lang.RepeatPlanStatus;

public class RepeatDaoImpl extends DaoImpl<RepeatPlanPO> implements RepeatDao {

    private static final long serialVersionUID = -8866968344416859253L;

    public RepeatDaoImpl() {
        super(RepeatPlanPO.class);
    }

	@Override
	public long savePlan(RepeatPlanPO repeatPlanPO) {
		Session session = getSessionFactory().getCurrentSession();
		Serializable id = session.save(repeatPlanPO);
		return Long.parseLong(id.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RepeatPlanPO> queryRepeatPlanList(RepeatPlanStatus planStatus, String lotteryId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("status", planStatus.getType()));
		c.add(Restrictions.eq("lotteryId", lotteryId));
		return c.list();
	}

	@Override
	public RepeatPlanPO findRepeatPlanById(long planId) {
		return get(planId);
	}

	@Override
	public BigDecimal queryBonusAmountByPlan(long planId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select count(after_tax_bonus) from lt_repeat_issue_plan IP, lt_repeat_plan P, ")
		.append("lt_bet_scheme BS ")
		.append("where IP.plan_id = P.id and IP.is_executed = 1 ")
		.append("and IP.scheme_id = BS.id ");
		
		SQLQuery query = createSQLQuery(sql.toString());
		Object rs = query.uniqueResult();
		BigDecimal bonusAmount =  new BigDecimal(rs.toString());
		return bonusAmount;
	}

	@Override
	public int winNoteIssuePlan(long planId) {
		StringBuffer sql = new StringBuffer();
		
		sql.append("select count(win_note) from lt_repeat_issue_plan IP, lt_repeat_plan P, ")
		.append("lt_bet_scheme BS ")
		.append("where IP.plan_id = P.id and IP.is_executed = 1 ")
		.append("and IP.scheme_id = BS.id and IP.plan_id = ? and BS.status in (?,?) ");
		
		SQLQuery query = createSQLQuery(sql.toString());
		query.setParameter(0, planId);
		query.setParameter(1, EntityStatus.TICKET_NOT_AWARD);
		query.setParameter(2, EntityStatus.TICKET_AWARDED);
		
		Object rs = query.uniqueResult();
		int winNote = Integer.parseInt(rs.toString());
		return winNote;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RepeatPlanPO> findRepeatPlan(String lottery, Long userId,
			Date from, Date to, int status, Paging paging) {
		if(null != userId && null != paging) {
			Criteria criteria = createCriteria();
			criteria.add(Restrictions.eq("sponsorId", userId));
			Long totalCount = (Long)criteria.setProjection(Projections.rowCount()).uniqueResult(); 
			paging.setTotalCount(totalCount.intValue());
			
			Criteria c = createCriteria();
			c.add(Restrictions.eq("sponsorId", userId));
			if(null != from) {
				c.add(Restrictions.ge("createdTime", from));
			}
			if(null != to) {
				c.add(Restrictions.le("createdTime", to));
			}
			if(-1 != status) {
				c.add(Restrictions.eq("status", status));
			}
			c.setFirstResult(paging.getFirstResult());
			c.setMaxResults(paging.getMaxResults());
			return c.list();
		}
		return null;
	}
}