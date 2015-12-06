package com.xhcms.lottery.pb.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xhcms.commons.persist.hibernate.DaoImpl;
import com.xhcms.lottery.pb.dao.PartnerWithdrawDao;
import com.xhcms.lottery.pb.po.PartnerWithdrawPO;

@SuppressWarnings({ "unchecked", "deprecation" })
@Repository
public class PartnerWithdrawDaoImpl  extends DaoImpl<PartnerWithdrawPO> implements PartnerWithdrawDao{

	private static final long serialVersionUID = -3908505438835936653L;

	@Override
	public PartnerWithdrawPO findByUuid(String uuid) {
		List<PartnerWithdrawPO> list =  
				createQuery("select w from PartnerWithdrawPO w where w.uuid=?")
					.setString(0, uuid).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public PartnerWithdrawPO findByTransactionId(String transactionId) {
		List<PartnerWithdrawPO> list =  
				createQuery("select w from PartnerWithdrawPO w where w.transactionId=?")
					.setString(0, transactionId).list();
		return list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<PartnerWithdrawPO> listByStatus(int resultMaxSize,
			Long id,int partnerWithdrawResultStatus) {
		List<PartnerWithdrawPO> list =  
				createQuery("select pw from PartnerWithdrawPO pw "
						+ " where pw.status=?")
				.setInteger(0, Integer.valueOf(partnerWithdrawResultStatus)).list();
		return list;
	}
}
