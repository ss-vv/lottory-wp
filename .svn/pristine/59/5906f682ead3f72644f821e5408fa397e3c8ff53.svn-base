package com.xhcms.lottery.commons.persist.dao.impl;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.commons.persist.dao.PMRechargeRedeemedDao;
import com.xhcms.lottery.commons.persist.entity.PMRechargeRedeemedPO;
import com.xhcms.lottery.lang.EntityType;

/**
 * @author zhuyongli
 * 
 */
public class PMRechargeRedeemedDaoImpl extends DaoImpl<PMRechargeRedeemedPO> implements PMRechargeRedeemedDao {

	private static final long serialVersionUID = -4554113092813592928L;

	public PMRechargeRedeemedDaoImpl() {
        super(PMRechargeRedeemedPO.class);
    }

	@Override
	public List<PMRechargeRedeemedPO> find(Paging paging, Date from, Date to,
			int status, String username, String operatorName) {
		PagingQuery<PMRechargeRedeemedPO> pq = pagingQuery(paging);
		if(from!=null){
			pq.add(Restrictions.ge("createdTime",from));
		}
		if(to!=null){
			pq.add(Restrictions.lt("createdTime",to));
		}
		if (status != -1) {
            pq.add(Restrictions.eq("status", status));
        }
		if(StringUtils.isNotBlank(username)) {
			pq.add(Restrictions.eq("username", username));
		}
		if(StringUtils.isNotBlank(operatorName)) {
			pq.add(Restrictions.eq("operatorName", operatorName));
		}

		return pq.desc("createdTime").asc("operateTime").list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PMRechargeRedeemedPO> findByIds(List<Long> ids) {
		return createCriteria().add(Restrictions.in("id", ids))
				.add(Restrictions.eq("status", EntityType.RECHARGE_REDEEMED_UNSUBMIT)).list();
	}
	
	@Override
	public PMRechargeRedeemedPO find(Long userId, Long promotionId, String idNumber, String mobile, String email) {
		return (PMRechargeRedeemedPO)createCriteria()
				.add(Restrictions.eq("promotionId", promotionId))
				.add(Restrictions.or(Restrictions.eq("userId", userId), 
					Restrictions.or(Restrictions.eq("idNumber", idNumber), 
					Restrictions.or(Restrictions.eq("mobile", mobile),Restrictions.eq("email", email)))))
					.uniqueResult();
	}

}
