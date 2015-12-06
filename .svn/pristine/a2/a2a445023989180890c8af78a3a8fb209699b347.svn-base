/**
 * 
 */
package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.CustomMadeDao;
import com.xhcms.lottery.commons.persist.entity.CustomMadePO;

/**
 * @author Bean.Long
 *
 */
public class CustomMadeDaoImpl extends DaoImpl<CustomMadePO> implements CustomMadeDao {
	private static final long serialVersionUID = -5521473755075176403L;
	
	public CustomMadeDaoImpl() {
		super(CustomMadePO.class);
	}

	@Override
	public CustomMadePO findCustomMade(long userId, long followedUserId) {
		return (CustomMadePO)createQuery("from CustomMadePO p where p.userId=? and p.followedUser.id=?").
				setLong(0, userId).
				setLong(1, followedUserId).
				uniqueResult();
	}

	@Override
	public List<CustomMadePO> findMyCustomMades(long userId, Paging paging) {
		PagingQuery<CustomMadePO> query = pagingQuery(paging);
		query.desc("id");
		return query.add(Restrictions.eq("userId", userId))
				.add(Restrictions.or(Restrictions.eq("followBuy", true),
						Restrictions.eq("groupBuy", true))).list();
	}

	@Override
	public List<CustomMadePO> findFollowMeCustomMades(long userId, Paging paging) {
		PagingQuery<CustomMadePO> query = pagingQuery(paging);
		
		query.alias("followedUser", "fu");
		query.add(Restrictions.eq("fu.id", userId));
		query.desc("id");
		
		return query.list();
	}

	@Override
	public List<CustomMadePO> topFollowMeCustomMades(long userId, int maxResult) {
		TopQuery<CustomMadePO> query = topQuery(maxResult);
		
		query.alias("followedUser", "fu");
		query.add(Restrictions.eq("fu.id", userId));
		query.desc("id");
		
		return query.list();
	}

	
	@Override
	public int countCustomMades(Long fuid) {
		Query query = createQuery("select count(*) from CustomMadePO p where p.followedUser.id=?");
		query.setLong(0, fuid);
		return ((Number)query.uniqueResult()).intValue();
	}
}
