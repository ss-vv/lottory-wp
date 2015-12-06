package com.davcai.lottery.platform.client.yuanchengchupiao;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.davcai.lottery.platform.client.AbstractQueryBalanceClient;
import com.davcai.lottery.platform.client.AbstractQueryGetInfoListClient;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.QueryBalanceResponse4OneLoop;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.QueryGetInfoListResponse4OneLoop;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoQueryBalanceResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoQueryGetInfoListResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;

public class YuanChengChuPiaoQueryGetInfoListImpl extends AbstractQueryGetInfoListClient {
	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	@Qualifier("yuanChengChuPiaoGetInfoListSupport")
	private AbstractYuanChengChuPiaoClientSupport clientSupport;
	
	@Override
	public QueryGetInfoListResponse4OneLoop queryGetInfoListForOneLoop(String Page,String Start_time,String End_time) {
		YuanChengChuPiaoQueryGetInfoListResponse resp=null;
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("Page",Page);
		params.put("Start_Time",Start_time);
		params.put("End_Time",End_time);
		resp=(YuanChengChuPiaoQueryGetInfoListResponse)clientSupport.doPost(params);
		return toQueryGetInfoList4OneLoop(resp);
	}
	
	public AbstractYuanChengChuPiaoClientSupport getClientSupport() {
		return clientSupport;
	}

	public void setClientSupport(AbstractYuanChengChuPiaoClientSupport clientSupport) {
		this.clientSupport = clientSupport;
	}

	private QueryGetInfoListResponse4OneLoop toQueryGetInfoList4OneLoop(YuanChengChuPiaoQueryGetInfoListResponse resp) {
		QueryGetInfoListResponse4OneLoop response4OneLoop=new QueryGetInfoListResponse4OneLoop();
		response4OneLoop.setError(resp.getError());
		response4OneLoop.setMessage(resp.getMessage());
		response4OneLoop.setPayout(resp.getPayout());
		response4OneLoop.setIncome(resp.getIncome());
		return response4OneLoop;
	}


}
