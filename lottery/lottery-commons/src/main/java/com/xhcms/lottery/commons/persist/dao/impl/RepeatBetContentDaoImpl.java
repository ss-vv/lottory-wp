package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.RepeatBetContentDao;
import com.xhcms.lottery.commons.persist.entity.RepeatBetContentPO;

public class RepeatBetContentDaoImpl extends DaoImpl<RepeatBetContentPO> implements RepeatBetContentDao {

    private static final long serialVersionUID = -8866968344416859253L;

    public RepeatBetContentDaoImpl() {
        super(RepeatBetContentPO.class);
    }

	@Override
	public void saveBetContentBatch(List<RepeatBetContentPO> list) {
		if(null != list && list.size() > 0) {
			Session session = null;
			try {
				session = getSessionFactory().openSession();
				int size = list.size();
				for(int i = 0; i < size; i++) {
					RepeatBetContentPO betContentPO = list.get(i);
					session.save(betContentPO);
					if(i % 50 == 0) {
						session.flush();
						session.clear();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException("保存追号投注内容时出现异常。");
			} finally {
				if(null != session) {
					session.close();
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RepeatBetContentPO> queryBetContentOfPlanId(long planId) {
		Criteria c = createCriteria();
		c.add(Restrictions.eq("planId", planId));
		return c.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> queryBetListOfPlanId(long planId) {
		List<String> list = null;
		if(planId > 0) {
			Session session = getSessionFactory().getCurrentSession();
			StringBuilder queryString = new StringBuilder();
			
			queryString.append("select B.code from lt_repeat_bet_content B where B.plan_id = ?");
			Query query = session.createSQLQuery(queryString.toString());
			query.setParameter(0, planId);
			list = query.list();
		}
		return list;
	}
}
