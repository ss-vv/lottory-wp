package com.xhcms.lottery.commons.persist.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.repeat.RepeatPlanSchedule;
import com.xhcms.lottery.commons.persist.dao.RepeatIssuePlanDao;
import com.xhcms.lottery.commons.persist.entity.RepeatIssuePlanPO;
import com.xhcms.lottery.lang.RepeatIssuePlanExecuteStatus;
import com.xhcms.lottery.lang.RepeatIssuePlanIsValid;

public class RepeatIssuePlanDaoImpl extends DaoImpl<RepeatIssuePlanPO> implements RepeatIssuePlanDao {

    private static final long serialVersionUID = -8866968344416859253L;

    public RepeatIssuePlanDaoImpl() {
        super(RepeatIssuePlanPO.class);
    }

	@Override
	public long saveIssuePlan(RepeatIssuePlanPO issuePlanPO) {
		Session session = getSessionFactory().getCurrentSession();
		Serializable id = session.save(issuePlanPO);
		return Long.parseLong(id.toString());
	}

	@Override
	public void saveIssuePlanBatch(List<RepeatIssuePlanPO> list) {
		if(null != list && list.size() > 0) {
			Session session = null;
			try {
				session = getSessionFactory().openSession();
				int size = list.size();
				for(int i = 0; i < size; i++) {
					RepeatIssuePlanPO issuePlan = list.get(i);
					session.save(issuePlan);
					if(i % 50 == 0) {
						session.flush();
						session.clear();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("生成追号期计划时出现异常。");
			} finally {
				if(null != session) {
					session.close();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RepeatIssuePlanPO> queryIssuePlanList(long planId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("planId", planId));
		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RepeatIssuePlanPO> queryIssuePlanList(long planId,
			RepeatIssuePlanIsValid issuePlanIsValid,
			RepeatIssuePlanExecuteStatus executeStatus) {
		
		Criteria c = createCriteria();
		c.add(Restrictions.eq("planId", planId));
		c.add(Restrictions.eq("valid", issuePlanIsValid.isType()));
		c.add(Restrictions.eq("executed", executeStatus.isType()));
		return c.list();
	}

	@Override
	public void updateIssuePlan(long planId, RepeatIssuePlanIsValid issuePlanIsValid) {
		Session session = getSessionFactory().getCurrentSession();
		RepeatIssuePlanPO issuePlan = (RepeatIssuePlanPO) session.get(RepeatIssuePlanPO.class, planId);
		issuePlan.setValid(issuePlanIsValid.isType());
		session.update(issuePlan);
	}

	@Override
	public RepeatIssuePlanPO findValidUnExecuteIssuePlan(long planId, String issueNumber) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("planId", planId));
		c.add(Restrictions.eq("issueNumber", issueNumber));
		c.add(Restrictions.eq("valid", RepeatIssuePlanIsValid.VALID.isType()));
		c.add(Restrictions.eq("executed", RepeatIssuePlanExecuteStatus.NOT_EXECUTED.isType()));
		return (RepeatIssuePlanPO) c.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RepeatIssuePlanPO> queryIssuePlanByIssueNumber(
			String issueNumber) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("issueNumber", issueNumber));
		return c.list();
	}
	
	@Override
	public RepeatPlanSchedule loadPlanScheduleByPlanId(long planId) {
		RepeatPlanSchedule planSchedule = new RepeatPlanSchedule();
		if(planId > 0) {
			StringBuilder queryString = new StringBuilder();
			queryString.append("select I.is_executed,count(distinct I.id) from lt_repeat_issue_plan I ")
			.append("where I.plan_id = ? group by I.is_executed order by I.is_executed asc");
			
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createSQLQuery(queryString.toString());
			query.setParameter(0, planId);
			List<?> list = query.list();
			if(null != list && list.size() > 0) {
				int length = list.size();
				int unExecuteCnt = 0;
				int executedCnt = 0;
				for(int i=0; i<length; i++) {
					Object[] plan = (Object[])list.get(i);
					int isExecutedFlag = Integer.parseInt(String.valueOf(plan[0]));
					int cnt = Integer.parseInt(String.valueOf(plan[1]));
					if(isExecutedFlag == 1) {
						executedCnt += cnt;
					} else {
						unExecuteCnt += cnt;
					}
				}
				planSchedule.setTotalIssue(unExecuteCnt + executedCnt);
				planSchedule.setCompleteIssue(executedCnt);
			}
		}
		return planSchedule;
	}
}