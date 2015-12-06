package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.dao.RecommendUserDao;
import com.xhcms.lottery.commons.persist.entity.RecommendUserPO;

public class RecommendUserDaoImpl extends DaoImpl<RecommendUserPO> implements
		RecommendUserDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -102967211172760992L;
	
	public RecommendUserDaoImpl() {
		super(RecommendUserPO.class);
	}

	@Override
	public List<RecommendUserPO> findAllWithPaging(Paging paging) {
		PagingQuery<RecommendUserPO> pq = pagingQuery(paging);
		return pq.desc("createTime").list();
	}

	public List<RecommendUserPO> getRecommendUser(BetScheme scheme) {
		return createCriteria()
				.add(Restrictions.eq("userId", scheme.getSponsorId()))
				.add(Restrictions.eq("lotteryId", scheme.getLotteryId()))
				.list();
	}

	@Override
	public RecommendUserPO findByUserIdAndLotteryId(long userId, String lotteryId) {
		return (RecommendUserPO) createQuery("from RecommendUserPO where userId=? and lotteryId=?")
			.setLong(0, userId).setString(1, lotteryId).uniqueResult();
	}
}
