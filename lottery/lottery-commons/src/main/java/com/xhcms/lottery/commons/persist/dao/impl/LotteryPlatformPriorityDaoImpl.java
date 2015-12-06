package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.LotteryPlatformPriorityDao;
import com.xhcms.lottery.commons.persist.entity.LotteryPlatformPriorityPO;

public class LotteryPlatformPriorityDaoImpl extends DaoImpl<LotteryPlatformPriorityPO> implements
		LotteryPlatformPriorityDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8271670673340663602L;
	

	public LotteryPlatformPriorityDaoImpl() {
		super(LotteryPlatformPriorityPO.class);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LotteryPlatformPriorityPO> findByLotteryInterfaceNameAndLotteryId(
			String lotteryInterfaceName, String lotteryId) {
		String hql="from LotteryPlatformPriorityPO "
				+ "where interfaceName=:interfaceName "
				+ "and lotteryId=:lotteryId ";
		Query query = createQuery(hql);
		query.setParameter("interfaceName", lotteryInterfaceName);
		query.setParameter("lotteryId", lotteryId);
		return query.list();
	}

	@Override
	public LotteryPlatformPriorityPO findOneByInterfaceName(
			String lotteryInterfaceName, String lotteryId) {
		String hql="from LotteryPlatformPriorityPO "
				+ " where interfaceName=:interfaceName "
				+ " and priority>0 and lotteryId=:lotteryId";
		Query query = createQuery(hql);
		query.setParameter("interfaceName", lotteryInterfaceName);
		query.setParameter("lotteryId", lotteryId);
		return (LotteryPlatformPriorityPO) query.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LotteryPlatformPriorityPO> listShiTiDianPriorityList(
			List<String> shiTiDianLotteryPlatformIdList,
			List<String> shiTiDianLotteryIdList,String lotteryId, String lotteryPlatformId) {
		if(null!=shiTiDianLotteryPlatformIdList&&!shiTiDianLotteryPlatformIdList.isEmpty()
				&&null!=shiTiDianLotteryIdList&&!shiTiDianLotteryIdList.isEmpty()){
			Criteria critera = createCriteria();
			critera.add(Restrictions.eq("interfaceName", "orderTicket"));
			critera.add(Restrictions.in("lotteryId", shiTiDianLotteryIdList));
			critera.add(Restrictions.in("lotteryPlatformId", shiTiDianLotteryPlatformIdList));
			if(StringUtils.isNotBlank(lotteryId)){
				if(!StringUtils.equals(lotteryId, "all")){
					critera.add(Restrictions.eq("lotteryId", lotteryId));
				}
			}
			if(StringUtils.isNotBlank(lotteryPlatformId)){
				if(!StringUtils.equals(lotteryPlatformId, "all")){
					critera.add(Restrictions.eq("lotteryPlatformId", lotteryPlatformId));
				}
			}
			critera.addOrder(Order.asc("lotteryId"));
			critera.addOrder(Order.asc("lotteryPlatformId"));
			return critera.list();
		}
		return null;
	}

	@Override
	public void updatePriorityAndPriorityOfBigTicket(Long id, int priority,
			int priorityOfBigTicket) {
		if(null!=id&&id>0&&priority>=0&&priorityOfBigTicket>=0){
			String hql="update LotteryPlatformPriorityPO "
					+ "set priority=:priority ,"
					+ "priorityOfBigTicket=:priorityOfBigTicket "
					+ "where "
					+ "id=:id";
			Query query = createQuery(hql);
			query.setParameter("priority", priority);
			query.setParameter("priorityOfBigTicket", priorityOfBigTicket);
			query.setParameter("id", id);
			query.executeUpdate();
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> listShiTiDianAliasNameList(
			List<String> shiTiDianLotteryPlatformIdList) {
		if(null!=shiTiDianLotteryPlatformIdList&&!shiTiDianLotteryPlatformIdList.isEmpty()){
			String hql="select distinct lotteryPlatformAliasName,lotteryPlatformId "
					+ "from LotteryPlatformPriorityPO "
					+ "where "
					+ "interfaceName='orderTicket' "
					+ "and lotteryPlatformId in (:shiTiDianLotteryPlatformIdList) "
					+ "order by lotteryId asc,lotteryPlatformId ";
			Query query = createQuery(hql);
			query.setParameterList("shiTiDianLotteryPlatformIdList", shiTiDianLotteryPlatformIdList);
			return query.list();
		}
		return null;
	}

}
