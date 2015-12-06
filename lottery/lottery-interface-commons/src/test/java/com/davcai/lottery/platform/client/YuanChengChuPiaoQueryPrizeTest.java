package com.davcai.lottery.platform.client;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoGetBonusListClientSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoOrderTicketResultClientSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoQueryPrizeClientSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResultResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.MessageIdGenerator;

public class YuanChengChuPiaoQueryPrizeTest {
	@Test
	public void testOrderTicket() {
		YuanChengChuPiaoGetBonusListClientSupport clientSupport=new YuanChengChuPiaoGetBonusListClientSupport();
		clientSupport.setApiId("DVCP201507201547");
		clientSupport.setSecret("X8J73GIE93VN2LYP");
		clientSupport.setInterfaceUrl("http://121.40.218.112:8889/?m=getBonusList");
		clientSupport.setContentKey("123456");
		//clientSupport.setPostContentType("multipart/form-data");
		Map<String, Object> params=new HashMap<String,Object>();
		System.out.println(MessageIdGenerator.generateId("DVCP201507201547"));
		
		
		params.put("Start_Time","0000000000");
		params.put("End_Time", "1437553691");
//		
		System.out.println(params.toString());
		LotteryPlatformBaseResponse response = clientSupport.doPost(params);
		assertTrue(response instanceof YuanChengChuPiaoOrderTicketResultResponse);
		System.out.println("response-->>"+response);
	}
}
	
