package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.ShowWinListDao;
import com.xhcms.lottery.commons.persist.entity.ShowWinListPO;

/**
 * 
 * @author yonglizhu
 *
 */
public class ShowWinListDaoImpl extends DaoImpl<ShowWinListPO> implements ShowWinListDao{

	private static final long serialVersionUID = 1331627063695256513L;
	
	public ShowWinListDaoImpl() {
		super(ShowWinListPO.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ShowWinListPO> findShowWinList(Paging paging, String lotteryId) {
		
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
				.addOrder(Order.desc("followTotalBonus"))
				.addOrder(Order.desc("followTotalAmount"));

		return criteria.setFirstResult(paging.getFirstResult())
				.setMaxResults(paging.getMaxResults()).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ShowWinListPO findShowWinListBySponsorIdLotteryId(long sponsorId,
			String lotteryId) {
		return (ShowWinListPO) createCriteria()
				.add(Restrictions.eq("sponsorId", sponsorId))
				.add(Restrictions.eq("lotteryId", lotteryId)).uniqueResult();
	}
	
	@Override
	public void saveShowWinList(ShowWinListPO showWinListPO) {
		save(showWinListPO);
	}
	
	@Override
	public void updateShowWinList(ShowWinListPO showWinListPO) {
		update(showWinListPO);
	}
	
	@Override
	public void deleteShowWinList() {
		createQuery("delete from ShowWinListPO").executeUpdate();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Object[]> findTop5ShowWinList() {
	           //"select sum(l.follow_total_bonus+l.after_tax_bonus) b,sum(l.show_scheme_count) s,l.sponsor_id from lt_show_win_list l "
               // +" where l.created_time>concat(DATE_SUB(curdate(), INTERVAL 7 DAY), ' 00:00:00') and "
			   // +" l.created_time<= concat(DATE_SUB(curdate(), INTERVAL 1 DAY), ' 23:59:59')"
               // +" group by l.sponsor_id having b>0 order by b desc,s desc limit 0,5";
	  String sql="select sum(t.total_money+s.after_tax_bonus)  money,count(s.sponsor_id) spnum, s.sponsor_id,t.num  from"
				+"  (select l.followed_scheme_id,count(l.followed_scheme_id) num,sum(l.after_tax_bonus) total_money"
				+"   from lt_bet_scheme l where"
				+"   l.`status` in('5203','12') and l.type=2 and l.is_publish_show=1 and l.after_tax_bonus> l.total_amount"
				+"   and l.created_time>concat(DATE_SUB(curdate(), INTERVAL 7 DAY), ' 00:00:00') and "
				+"   l.created_time<=concat(DATE_SUB(curdate(), INTERVAL 1 DAY), ' 23:59:59') "
				+"   group by l.followed_scheme_id order by l.created_time desc"
				+"   ) t ,lt_bet_scheme s "
				+" where s.id=t.followed_scheme_id "
				+" group by s.sponsor_id order by money desc limit 0,5";
	  
		return createSQLQuery(sql).list();
	}
}
