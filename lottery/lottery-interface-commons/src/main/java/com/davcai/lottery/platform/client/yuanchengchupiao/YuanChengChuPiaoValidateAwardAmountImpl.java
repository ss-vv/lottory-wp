package com.davcai.lottery.platform.client.yuanchengchupiao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.davcai.lottery.platform.client.AbstractValidateAwardAmountClient;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;

public class YuanChengChuPiaoValidateAwardAmountImpl extends AbstractValidateAwardAmountClient {
	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	@Qualifier("yuanChengChuPiaoValidateAwardAmountSupport")
	private AbstractYuanChengChuPiaoClientSupport clientSupport;
	@Override
	public YuanChengChuPiaoResponse validateAmount(String orderId, String awardAmount) {
		YuanChengChuPiaoResponse resp=new YuanChengChuPiaoResponse();
		Map<String, Object> params = makeParams(orderId, awardAmount);
		resp=(YuanChengChuPiaoResponse) clientSupport.doPostWithRetry(params);
		return resp;
	}
	private Map<String, Object> makeParams(String orderID, String awardAmount) {
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("OrderId", orderID);
		params.put("AwardAmount", awardAmount);
		return params;
	}

}
