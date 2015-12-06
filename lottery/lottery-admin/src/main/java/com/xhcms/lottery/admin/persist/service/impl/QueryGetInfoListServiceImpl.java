package com.xhcms.lottery.admin.persist.service.impl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoQueryBalanceImpl;
import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoQueryGetInfoListImpl;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.QueryGetInfoListResponse4OneLoop;
import com.xhcms.lottery.admin.persist.service.QueryBalanceService;
import com.xhcms.lottery.admin.persist.service.QueryGetInfoListService;

public class QueryGetInfoListServiceImpl implements QueryGetInfoListService {
	
	@Autowired
	private YuanChengChuPiaoQueryGetInfoListImpl queryGetInfoListImpl; 
	
	public YuanChengChuPiaoQueryGetInfoListImpl getQueryGetInfoListImpl() {
		return queryGetInfoListImpl;
	}
	
	public void setQueryGetInfoListImpl(YuanChengChuPiaoQueryGetInfoListImpl queryGetInfoListImpl) {
		this.queryGetInfoListImpl = queryGetInfoListImpl;
	}

	@Override
	public QueryGetInfoListResponse4OneLoop queryGetInfoListResponse4OneLoop(String Page,String Start_time,String Stop_time) {
		System.out.println(queryGetInfoListImpl.getClientSupport().getInterfaceUrl());
		System.out.println(queryGetInfoListImpl.getClientSupport().getContentKey());
		
		
		return queryGetInfoListImpl.queryGetInfoListForOneLoop(Page, Start_time, Stop_time);
	}
}
