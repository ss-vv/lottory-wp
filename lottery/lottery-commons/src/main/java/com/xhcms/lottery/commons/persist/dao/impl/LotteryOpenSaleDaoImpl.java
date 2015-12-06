package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.LotteryOpenSaleDao;
import com.xhcms.lottery.commons.persist.entity.LotteryOpenSalePO;
import com.xhcms.lottery.commons.persist.entity.LtLotteryHomeRecommendPO;
import com.xhcms.lottery.lang.LotteryId;

@SuppressWarnings("deprecation")
public class LotteryOpenSaleDaoImpl extends DaoImpl<LotteryOpenSalePO> implements LotteryOpenSaleDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2224880977048561346L;

	@Override
	public Long countByLotteryIdAndDayOfWeek(LotteryId lotteryId,
			int dayOfWeek) {
		Long result=null;
		if(null!=lotteryId&&dayOfWeek>=1&&dayOfWeek<=7){
			String hql="select count(*) "
					+ "from LotteryOpenSalePO a "
					+ "where "
					+ "a.lotteryId=:lotteryId "
					+ "and Time(now())>a.openTime "
					+ "and Time(now())<a.endTime "
					+ "and a.dayOfWeek=:dayOfWeek";
			Query query = createQuery(hql);
			query.setParameter("lotteryId", lotteryId.toString());
			query.setParameter("dayOfWeek", dayOfWeek);
			result=(Long) query.uniqueResult();
		}
		return result;
	}

	@Override
	public LotteryOpenSalePO findByLotteryIdAndDayOfWeek(LotteryId lotteryId,
			int dayOfWeek) {
		LotteryOpenSalePO result = null;
		if(null!=lotteryId&&dayOfWeek>=1&&dayOfWeek<=7){
			String hql="from LotteryOpenSalePO a "
					+ "where "
					+ "a.lotteryId=:lotteryId "
					+ "and a.dayOfWeek=:dayOfWeek";
			Query query = createQuery(hql);
			query.setParameter("lotteryId", lotteryId.toString());
			query.setParameter("dayOfWeek", dayOfWeek);
			result=(LotteryOpenSalePO) query.uniqueResult();
		}
		return result;
	}

	@Override
	public List<LotteryOpenSalePO> findOpenAndEndTime() {
		String hql="from LotteryOpenSalePO a ";
		Query query = createQuery(hql);
		return query.list();
	}

	@Override
	public LotteryOpenSalePO findOpenAndEndTimeById(Long id) {
		String hql="from LotteryOpenSalePO a where id=:id";
		Query query = createQuery(hql);
		query.setParameter("id", id);
		return (LotteryOpenSalePO) query.uniqueResult();
	}
}
