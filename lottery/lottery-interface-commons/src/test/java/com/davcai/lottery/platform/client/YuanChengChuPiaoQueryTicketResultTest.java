package com.davcai.lottery.platform.client;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoOrderTicketResultClientSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResultResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.MessageIdGenerator;

public class YuanChengChuPiaoQueryTicketResultTest {

	@Test
	public void testTicketResult(){
		YuanChengChuPiaoOrderTicketResultClientSupport  client=new YuanChengChuPiaoOrderTicketResultClientSupport();
		client.setApiId("TEST123456789012");
		client.setSecret("ABCDEFGHIJKLMNOP");
		client.setInterfaceUrl("http://222.92.36.78:8011/?m=getTicketStatus");
		client.setContentKey("123456");
		Map<String,Object> params=new HashMap<String,Object>();
		Object []obj=new Object[1];
		Map<String,String> p=new HashMap<String,String>();
		p.put("OrderId", "TEST20150618152628ZCQEKM");// //MessageIdGenerator.generateId("TEST123456789012")
		Map<String,String> p1=new HashMap<String,String>();
		p1.put("OrderId", "TEST20150618152629pPLATg");//MessageIdGenerator.generateId("TEST123456789013")
		obj[0]=p;
		//obj[1]=p1;
		params.put("OrderInfo", obj); 
		LotteryPlatformBaseResponse response=client.doPost(params);
		if(response instanceof YuanChengChuPiaoOrderTicketResultResponse){
			System.out.println(response+"-----");
		}
		System.out.println(response);
		
	}
}
