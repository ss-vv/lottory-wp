package com.xhcms.lottery.admin.persist.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoQueryPrizeClientImpl;
import com.xhcms.lottery.admin.persist.service.QueryPrizeAmountService;

public class QueryPrizeAmountServiceImpl implements QueryPrizeAmountService {
	
	@Autowired
	private YuanChengChuPiaoQueryPrizeClientImpl queryPrizeClientImpl;
	@Override
	public BigDecimal queryPrizeAmount(Date startTime, Date endTime) {
		BigDecimal big;
		try {
			big=queryPrizeClientImpl.queryPrizeAmountForOneLoop(startTime, endTime).getAmount();
			return big.setScale(2, BigDecimal.ROUND_HALF_UP);
			//return queryPrizeClientImpl.queryPrizeAmountForOneLoop(startTime, endTime).getAmount();
		} catch (Exception e) {
			e.printStackTrace();
			big = new BigDecimal("15.126");
			return big.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	
	}
}
