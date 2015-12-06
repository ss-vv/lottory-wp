package com.davcai.lottery.platform.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.davcai.lottery.platform.client.yuanchengchupiao.AbstractYuanChengChuPiaoClientSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResultResponse;

public class QueryRemoteTicketResultClientImpl implements QueryRemoteTicketResultClient{
	@Autowired
	@Qualifier("yuanChengChuPiaoOrderTicketResultClientSupport")
	private AbstractYuanChengChuPiaoClientSupport clientSupport;
	@Override
	public YuanChengChuPiaoOrderTicketResultResponse queryTicketResult(List<String> remoteTickets) {
		Map<String,Object> rtMap=new HashMap<String,Object>();
		Object []obj=new Object[remoteTickets.size()];
		for(int i=0;i<remoteTickets.size();i++){
			Map<String,String> orderId=new HashMap<String,String>();
			orderId.put("OrderId", remoteTickets.get(i));
			obj[i]=orderId;
		}
		rtMap.put("OrderInfo", obj);
		return (YuanChengChuPiaoOrderTicketResultResponse)clientSupport.doPost(rtMap);
	}

}
