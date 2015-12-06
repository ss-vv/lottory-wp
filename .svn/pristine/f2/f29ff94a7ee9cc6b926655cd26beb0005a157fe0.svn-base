package com.xhcms.lottery.pb.dao;

import java.util.List;

import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.pb.po.PartnerWithdrawPO;

public interface PartnerWithdrawDao extends Dao<PartnerWithdrawPO>{
	PartnerWithdrawPO findByUuid(String uuid);
	PartnerWithdrawPO findByTransactionId(String transactionId);
	public List<PartnerWithdrawPO> listByStatus(int resultMaxSize,
			Long id,int partnerWithdrawResultStatus);
}
