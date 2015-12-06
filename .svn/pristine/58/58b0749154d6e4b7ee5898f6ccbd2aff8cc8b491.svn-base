package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.FollowWinListDao;
import com.xhcms.lottery.commons.persist.entity.FollowWinListPO;

/**
 * 
 * @author yonglizhu
 *
 */
public class FollowWinListDaoImpl extends DaoImpl<FollowWinListPO> implements FollowWinListDao{

	private static final long serialVersionUID = 8129140706451825734L;
	
	public FollowWinListDaoImpl() {
		super(FollowWinListPO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FollowWinListPO> findFollowWinList(Paging paging,
			String lotteryId) {
		int totalCount = 0;
		Object obj = createCriteria()
				.setProjection(Projections.rowCount())
				.add(Restrictions.eq("lotteryId", lotteryId))
				.add(Restrictions.gt("returnRate", 1)).uniqueResult();
		if (obj != null) {
			totalCount = ((Long) obj).intValue();
			if(totalCount > 100) {
				totalCount = 100;
			} 
			paging.setTotalCount(totalCount);
		}
		
		Criteria criteria = createCriteria()
				.add(Restrictions.eq("lotteryId", lotteryId))
				.add(Restrictions.gt("returnRate", 1))
				.addOrder(Order.desc("afterTaxBonus"))
				.addOrder(Order.desc("totalAmount"));

		return criteria.setFirstResult(paging.getFirstResult())
				.setMaxResults(paging.getMaxResults()).list();
	}
	
	@Override
	public FollowWinListPO findFollowWinListBySponsorIdLotteryId(long followerId, String lotteryId) {
		return (FollowWinListPO)createCriteria().add(Restrictions.eq("followerId", followerId))
		.add(Restrictions.eq("lotteryId", lotteryId)).uniqueResult();
	}
	
	@Override
	public void saveFollowWinList(FollowWinListPO followWinListPO) {
		save(followWinListPO);
	}
	
	@Override
	public void updateFollowWinList(FollowWinListPO followWinListPO) {
		update(followWinListPO);
	}
	
	@Override
	public void deleteFollowWinList() {
		createQuery("delete from FollowWinListPO").executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findTop5FollowWinList() {
		String sql=" select w1.money,w1.win_follow,w1.sponsor_id,w2.all_follow from "
					+"   (select count(l.followed_scheme_id) win_follow,"
					+" 	    sum(l.after_tax_bonus) money,l.sponsor_id from lt_bet_scheme l "
					+" 	    where l.`type`=2 and l.is_publish_show=1 and l.`status` in('5203','12')"
					+"      and l.created_time >concat(DATE_SUB(curdate(), INTERVAL 7 DAY), ' 00:00:00')"
					+"      and l.created_time<=concat(DATE_SUB(curdate(), INTERVAL 1 DAY), ' 23:59:59')"
					+"      and l.after_tax_bonus> l.total_amount "
					+" 	    group by l.sponsor_id order by l.created_time desc"
					+" 	 ) w1,"
					+"   (select count(s.followed_scheme_id) all_follow,s.sponsor_id from "
					+" 	   lt_bet_scheme s"
					+"     where s.`type`=2 and s.is_publish_show=1 "
					+" 	   and s.`status` in('5203','5202','12')"
					+"     and s.created_time >concat(DATE_SUB(curdate(), INTERVAL 7 DAY), ' 00:00:00')"
					+"     and s.created_time<=concat(DATE_SUB(curdate(), INTERVAL 1 DAY), ' 23:59:59')"
					+"     group by s.sponsor_id order by s.created_time desc"
					+" 	) w2 "
					+"  where w1.sponsor_id=w2.sponsor_id order by w1.money desc limit 0,5";
			return createSQLQuery(sql).list();
	}
}
