package com.davcai.lottery.platform.client;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoCheckTicketsStatusClientSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoOrderTicketClientSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResultResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 查询这些票是否正常兑奖
 * @author Next
 *
 */
public class YuanChengChuPiaoCheckTicketsStatusClientTest {
	@Test
	public void testOrderTicket() {
		YuanChengChuPiaoCheckTicketsStatusClientSupport clientSupport=new YuanChengChuPiaoCheckTicketsStatusClientSupport();
		clientSupport.setApiId("DVCP201507201547");
		clientSupport.setSecret("X8J73GIE93VN2LYP");
		clientSupport.setInterfaceUrl("http://121.40.218.112:8889/?m=checkTicketsStatus");
		clientSupport.setContentKey("123456");
		//clientSupport.setPostContentType("multipart/form-data");
		Map<String, Object> params=new HashMap<String,Object>();
		
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		obj.put("OrderId", "TEST20150720183108PujsrU");
		arr.add(obj);
		
		params.put("OrderInfo", arr);
		
		LotteryPlatformBaseResponse response = clientSupport.doPost(params);
		assertTrue(response instanceof YuanChengChuPiaoOrderTicketResultResponse);
		System.out.println("response-->>"+response);
		
		
		
	}
}
