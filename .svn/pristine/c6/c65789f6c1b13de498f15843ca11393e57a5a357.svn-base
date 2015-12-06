package com.xhcms.lottery.commons.persist.dao.impl;

import java.sql.Timestamp;
import java.util.Date;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.ValidIdDao;
import com.xhcms.lottery.commons.persist.entity.ValidIdPO;


public class ValidIdDaoImpl extends DaoImpl<ValidIdPO> implements ValidIdDao{

	 /**
	 * 
	 */
	private static final long serialVersionUID = -7405783572152682999L;

	public ValidIdDaoImpl() {
	        super(ValidIdPO.class);
	    }

	@Override
	public String findUserIdByValidIdAndCurrentTime(String validId) {
		Date now=new Date();
		return findUserIdByValidIdAndTargetTime(validId, now);

	}

	@Override
	public String findUserIdByValidIdAndTargetTime(String validId, Date targetTime) {
		Timestamp timestamp=new Timestamp(targetTime.getTime());
		String hql="select UserId from ValidIdPO " +
				"where " +
				"validId=:validId " +
				"and expiredTime >= :now";
		Long userId=(Long) createQuery(hql).setParameter("validId", validId)
				.setParameter("now", timestamp).uniqueResult();
		if(null!=userId){
			return userId.toString();
		}
		return null ;
	}

	@Override
	public void updateExpiredTime(String userId,String validId, Date expiredTime) {
		Long userIdLong=Long.parseLong(userId);
		Timestamp timestamp=new Timestamp(expiredTime.getTime());
		String hql="update ValidIdPO " +
				"set " +
				"expiredTime = :expiredTime " +
				"where " +
				"validId=:validId " +
				"and UserId=:userId";
		createQuery(hql).setParameter("validId", validId)
				.setParameter("expiredTime", timestamp)
				.setParameter("userId", userIdLong)
				.executeUpdate();
		
	}
}
