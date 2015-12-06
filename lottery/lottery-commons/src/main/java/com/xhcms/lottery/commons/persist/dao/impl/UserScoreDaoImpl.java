package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.UserScoreDao;
import com.xhcms.lottery.commons.persist.entity.UserScorePO;
import com.xhcms.lottery.lang.Constants;

/**
 * 
 * @author yonglizhu
 *
 */
public class UserScoreDaoImpl extends DaoImpl<UserScorePO> implements UserScoreDao{

	private static final long serialVersionUID = -3154992771040400430L;

	public UserScoreDaoImpl(){
		super(UserScorePO.class);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public UserScorePO getUserScoreByUserIdLottoryId(long userId,
			String lotteryId) {
		return (UserScorePO) createCriteria()
				.add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("lotteryId", lotteryId)).uniqueResult();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserScorePO> findTopUserScoreOfLottery(String lotteryId,
			int maxResults) {
		return topQuery(maxResults)
				.add(Restrictions.eq("lotteryId", lotteryId))
				.add(Restrictions.gt("showScore", new Long(0)))
				.desc("showScore").list();
	}

	@Override
	public void saveUserShowScore(UserScorePO userShowScorePO){
		save(userShowScorePO);
	}
	
	@Override
	public void updateUserShowScore(UserScorePO userShowScorePO){
		update(userShowScorePO);
	}
	
	@Override
	public void deleteUserScore(){
		createQuery("delete from UserScorePO").executeUpdate();
	}

	@Override
	public List<UserScorePO> findTopGroupbuyUserScores(String lottery, String orderBy,
			int maxResults) {
		return topQuery(maxResults)
					.add(Restrictions.eq("lotteryId", lottery))
					.add(Restrictions.gt(orderBy, 0L))
					.desc(orderBy).list();
	}
}