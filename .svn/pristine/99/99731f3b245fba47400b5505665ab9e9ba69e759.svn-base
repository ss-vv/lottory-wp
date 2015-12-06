package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.impl.CriteriaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.ShowSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.service.ShowFollowQueryCondition;
import com.xhcms.lottery.lang.Constants;

public class ShowSchemeDaoImpl extends DaoImpl<BetSchemePO> implements
		ShowSchemeDao {

	private static final long serialVersionUID = -558323323302227322L;

	private Logger logger = LoggerFactory.getLogger(getClass());

	public ShowSchemeDaoImpl() {
		super(BetSchemePO.class);
	}

	/**
	 * 查找在售的晒单方案。 排序规则: (全部推荐彩种) 人气、截止时间、投注金额、战绩; <br/>
	 * 排序: (足球、篮球) 是否推荐、人气、截止时间、投注金额、战绩;
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BetSchemePO> findOnSaleShowingSchemes(Paging paging,
			DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSessionFactory().getCurrentSession());
		Projection projection = ((CriteriaImpl)criteria).getProjection();
        if (paging.isCount()) {
            int totalCount = ((Number) criteria.setProjection(Projections.rowCount())
            			.uniqueResult()).intValue();
            paging.setTotalCount(totalCount);
        }
        
    	criteria.setProjection(projection);
        if (projection == null) {
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        
        List<BetSchemePO> results = criteria.setMaxResults(paging.getMaxResults())
				.setFirstResult(paging.getFirstResult())
				.list();
    
        
		return results;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BetSchemePO> findShowSchemes(
			ShowFollowQueryCondition condition) {
		Criteria criteria = createCriteria()
				.add(Restrictions.eq("showScheme", 1))
				.add(Restrictions.eq("sponsorId", condition.getUserId()));
		if (StringUtils.isNotBlank(condition.getCurDate())) {
			criteria.add(Restrictions.gt("offtime", new Date()));
		}
		if (condition.getStatus() > 0) {
			criteria.add(Restrictions.eq("status", condition.getStatus()));
		}
		if (StringUtils.isBlank(condition.getOrderColumn())) {
			criteria.addOrder(Order.desc("followingCount"));
		} else {
			if (condition.getIsAsc()) {
				criteria.addOrder(Order.asc(condition.getOrderColumn()));
			} else {
				criteria.addOrder(Order.desc(condition.getOrderColumn()));
			}
		}

		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BetSchemePO> findFollowSchemes(
			ShowFollowQueryCondition condition) {
		DetachedCriteria followedSchemeCriteria = DetachedCriteria
				.forClass(BetSchemePO.class)
				.setProjection(
						Projections.distinct(Projections
								.property("followedSchemeId")))
				.add(Restrictions.eq("sponsorId", condition.getUserId()))
				.add(Restrictions.eq("type", 2))
				.add(Restrictions.ne("followedSchemeId", -1L));

		Criteria criteria = createCriteria()
				.add(Restrictions.eq("showScheme", 1))
				.add(Property.forName("id").in(followedSchemeCriteria));

		if (StringUtils.isNotBlank(condition.getCurDate())) {
			criteria.add(Restrictions.gt("offtime", new Date()));
		}
		if (condition.getStatus() > 0) {
			criteria.add(Restrictions.eq("status", condition.getStatus()));
		}

		if (StringUtils.isBlank(condition.getOrderColumn())) {
			criteria.addOrder(Order.desc("followingCount"));
		} else {
			if (condition.getIsAsc()) {
				criteria.addOrder(Order.asc(condition.getOrderColumn()));
			} else {
				criteria.addOrder(Order.desc(condition.getOrderColumn()));
			}
		}

		return criteria.list();
	}

	@Override
	public List findShowWinListFromScheme() {
		List results = createCriteria(BetSchemePO.class)
				.setProjection(
						Projections.projectionList()
								.add(Projections.property("sponsor"))
								.add(Projections.count("id"))
								.add(Projections.sum("totalAmount"))
								.add(Projections.sum("afterTaxBonus"))
								.add(Projections.sum("followingCount"))
								.add(Projections.groupProperty("sponsorId"))
								.add(Projections.groupProperty("lotteryId")))
				.add(Restrictions.eq("status", 12))
				.add(Restrictions.eq("showScheme", 1)).list();

		return results;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List countFollowDataforShowWinList(long sponsorId, String lotteryId) {
		DetachedCriteria followedSchemeCriteria = DetachedCriteria
				.forClass(BetSchemePO.class)
				.setProjection(Projections.property("id"))
				.add(Restrictions.eq("sponsorId", sponsorId))
				.add(Restrictions.eq("lotteryId", lotteryId))
				.add(Restrictions.eq("status", 12))
				.add(Restrictions.eq("showScheme", 1));

		List results = createCriteria(BetSchemePO.class)
				.setProjection(
						Projections.projectionList()
								.add(Projections.sum("totalAmount"))
								.add(Projections.sum("afterTaxBonus")))
				.add(Property.forName("followedSchemeId").in(
						followedSchemeCriteria)).list();

		return results;
	}

	@Override
	public List findFollowWinListFromScheme() {
		List results = createCriteria(BetSchemePO.class)
				.setProjection(
						Projections.projectionList()
								.add(Projections.property("sponsor"))
								.add(Projections.count("id"))
								.add(Projections.sum("totalAmount"))
								.add(Projections.sum("afterTaxBonus"))
								.add(Projections.groupProperty("sponsorId"))
								.add(Projections.groupProperty("lotteryId")))
				.add(Restrictions.eq("status", 12))
				.add(Restrictions.eq("type", 2)).list();
		return results;
	}

	@Override
	public List<BetSchemePO> queryShowSchemes(int recommend,
			Date startTime, Date endTime, Paging paging) {
		Criteria criteria = createCriteria(BetSchemePO.class);
		
		if(Constants.RECOMMEND == recommend ||
				Constants.NOT_RECOMMEND == recommend) {
			criteria.add(Restrictions.eq("recommendation", recommend));
		}
		
		if(startTime != null) {
			criteria.add(Restrictions.ge("createdTime", startTime));
		}
		
		if(endTime != null) {
			criteria.add(Restrictions.le("createdTime", endTime));
		}
		
		criteria.add(Restrictions.eq("showScheme", Constants.SHOW_SCHEME));
		
		Projection projection = ((CriteriaImpl)criteria).getProjection();
		if(paging.isCount()) {
			paging.setTotalCount(((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue());
		}
		
		if(paging.getMaxResults() > 0) {
			criteria.setMaxResults(paging.getMaxResults());
		}
		criteria.setFirstResult(paging.getFirstResult());
		
		criteria.add(Restrictions.ge("offtime", new Date()));
		criteria.addOrder(Order.desc("followingCount"));
		criteria.addOrder(Order.desc("totalAmount"));

		criteria.setProjection(projection);
        if (projection == null) {
            criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
        }
        
		return criteria.list();
	}
	
	@Override
	public List findFollowByUserId(Long showSchemeId, Long userId) {
		Criteria criteria = createCriteria(BetSchemePO.class)
				.setProjection(
						Projections.projectionList()
								.add(Projections.sum("totalAmount"))
								.add(Projections.sum("afterTaxBonus")))
				.add(Restrictions.eq("followedSchemeId", showSchemeId));
		if(null != userId) {
			criteria.add(Restrictions.eq("sponsorId", userId));
		}
		List results = criteria.add(Restrictions.eq("status", 12))
				.add(Restrictions.eq("type", 2)).list();
		return results;
	}
}
