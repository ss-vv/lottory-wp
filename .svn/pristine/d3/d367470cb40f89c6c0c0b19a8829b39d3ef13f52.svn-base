package com.unison.lottery.pm.persist.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.unison.lottery.pm.data.CountResult;
import com.unison.lottery.pm.data.RechargeResult;
import com.unison.lottery.pm.persist.PMGrantAccountDao;
import com.xhcms.lottery.commons.persist.dao.impl.AccountDaoImpl;
import com.xhcms.lottery.commons.persist.entity.RechargePO;
import com.xhcms.lottery.lang.EntityStatus;

/**
 * @author yongli zhu
 * 
 */
public class PMGrantAccountDaoImpl extends AccountDaoImpl implements
		PMGrantAccountDao {

	private static final long serialVersionUID = -5371683051544156582L;

	@Override
	public List<Long> getUserListFromRecharge(Collection<Long> ids, Date from,
			Date to) {
		List<Long> idList = new ArrayList<Long>();
		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				RechargePO rechargePO = (RechargePO)createQuery(
						"from RechargePO where userId=? and payTime>=? and payTime<=? and status=? order by payTime asc ")
						.setLong(0, id).setTimestamp(1, from)
						.setTimestamp(2, to)
						.setInteger(3, EntityStatus.RECHARGE_FINISH).setMaxResults(1).uniqueResult();//.list();
				if (rechargePO != null) {
					BigDecimal amount = rechargePO.getAmount();
					if (amount.compareTo(new BigDecimal(50.00)) >= 0) {
						idList.add(rechargePO.getUserId());
					}
				}
			}
		}

		return idList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<RechargeResult> getFirstRechargeRecord(Date from, Date to) {	
		
		List<RechargeResult> rechargeResult = createQuery(
			" select new com.unison.lottery.pm.data.RechargeResult(userId, amount, min(payTime) as payTime) " +
			" from RechargePO where payTime>=? and payTime<=? and channelId=3 " +
			" and status=? group by userId order by payTime asc ")
			.setTimestamp(0, from).setTimestamp(1, to)
			.setInteger(2, EntityStatus.RECHARGE_FINISH).list();
		
	 	return rechargeResult;
	}
	
	@Override
	public CountResult getRecharge(String ids) {
		return (CountResult)this.createQuery(" select new com.unison.lottery.pm.data.CountResult(userId,count(id) as num,sum(amount) as amount) "+
						" from RechargePO where status=90 and userId in ("+ids+")").uniqueResult();
	}
	
	@Override
	public CountResult getBet(String ids) {
		return (CountResult)this.createQuery(" select new com.unison.lottery.pm.data.CountResult(userId,count(id) as num, sum(amount) as amount) "+
					" from OrderPO where type=320 and userId in ("+ids+")").uniqueResult();
	}
}
