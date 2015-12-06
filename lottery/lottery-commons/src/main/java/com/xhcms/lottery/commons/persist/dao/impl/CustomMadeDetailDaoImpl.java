/**
 * 
 */
package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.CustomMadeDetailDao;
import com.xhcms.lottery.commons.persist.entity.CustomMadeDetailPO;

/**
 * @author Bean.Long
 *
 */
public class CustomMadeDetailDaoImpl extends DaoImpl<CustomMadeDetailPO>
		implements CustomMadeDetailDao {
	private static final long serialVersionUID = -1355626150809514902L;

	public CustomMadeDetailDaoImpl() {
		super(CustomMadeDetailPO.class);
	}

	@Override
	public List<CustomMadeDetailPO> findCustomMadeDetails(long userId, long followedUserId, Paging paging) {
		PagingQuery<CustomMadeDetailPO> pagingQuery = pagingQuery(paging);
		
		pagingQuery.alias("customMade", "cm");
		pagingQuery.alias("cm.followedUser", "fu");
		
		pagingQuery.add(Restrictions.eq("cm.userId", userId));
		pagingQuery.add(Restrictions.eq("fu.id", followedUserId));
		
		return pagingQuery.list();
	}

	@Override
	public int countFollowCount(long userId, long followedUserId, 
			Date startTime, Date endTime) {
		Number count = ((Number)createQuery("select count(*) from CustomMadeDetailPO p where p.createTime>=? and p.createTime<? and" +
				" p.customMade.userId=? and p.customMade.followedUser.id=?")
				.setDate(0, startTime)
				.setDate(1, endTime)
				.setLong(2, userId)
				.setLong(3, followedUserId)
				.uniqueResult());
		
		return count.intValue();
	}

	@Override
	public int sumMoneny(long userId, long followedUserId,
			Date startTime, Date endTime) {
		Number number = (Number)createQuery("select sum(p.betMoney) as betMoney from  CustomMadeDetailPO p where p.createTime>=?" +
				" and p.createTime<? and p.customMade.userId=? and p.customMade.followedUser.id=?")
				.setDate(0, startTime)
				.setDate(1, endTime)
				.setLong(2, userId)
				.setLong(3, followedUserId)
				.uniqueResult();
		if(number != null)
			return number.intValue();
		else 
			return 0;
	}
}
