package com.xhcms.lottery.admin.persist.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoQueryBalanceImpl;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.QueryBalanceResponse4OneLoop;
import com.xhcms.lottery.admin.persist.service.QueryBalanceService;

public class QueryBalanceImpl implements QueryBalanceService {
	
	@Autowired
	private YuanChengChuPiaoQueryBalanceImpl queryBalanceImpl; 
	
	public QueryBalanceResponse4OneLoop queryBalanceResponse4OneLoop() {
		return queryBalanceImpl.queryBalanceForOneLoop();
	}

	public YuanChengChuPiaoQueryBalanceImpl getQueryBalanceImpl() {
		return queryBalanceImpl;
	}

	public void setQueryBalanceImpl(
			YuanChengChuPiaoQueryBalanceImpl queryBalanceImpl) {
		this.queryBalanceImpl = queryBalanceImpl;
	}

}
