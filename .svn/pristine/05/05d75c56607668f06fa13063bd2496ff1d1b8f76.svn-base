package com.xhcms.lottery.commons.persist.dao.impl;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.BetSwitchDao;
import com.xhcms.lottery.commons.persist.entity.AccountPO;
import com.xhcms.lottery.commons.persist.entity.BetSwitchPO;

@SuppressWarnings("deprecation")
public class BetSwitchDaoImpl extends DaoImpl<BetSwitchPO> implements BetSwitchDao {

	

	@Override
	public Long countCanBet(String channel, String lotteryId) {
		String hql="select count(*) "
				+ "from BetSwitchPO a "
				+ "where "
				+"a.channel=:channel "
				+"and a.lotteryId=:lotteryId "
				+"and a.status=0 ";
		Query query=createQuery(hql);
		query.setParameter("channel", channel);
		query.setParameter("lotteryId", lotteryId);
		return (Long) query.uniqueResult();

	}

}
