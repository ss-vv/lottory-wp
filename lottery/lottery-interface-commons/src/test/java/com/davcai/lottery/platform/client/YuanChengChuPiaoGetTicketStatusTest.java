package com.davcai.lottery.platform.client;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoOrderTicketResultClientSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResultResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.MessageIdGenerator;

/**
 * 获取出票状态测试
 * @author Next
 *
 */
public class YuanChengChuPiaoGetTicketStatusTest {
	@Test
	public void testOrderTicket() {
		YuanChengChuPiaoOrderTicketResultClientSupport clientSupport=new YuanChengChuPiaoOrderTicketResultClientSupport();
		clientSupport.setApiId("DVCP201507201547");
		clientSupport.setSecret("X8J73GIE93VN2LYP");
		clientSupport.setInterfaceUrl("http://121.40.218.112:8889/?m=getTicketStatus");
		clientSupport.setContentKey("123456");
		//clientSupport.setPostContentType("multipart/form-data");
		Map<String, Object> params=new HashMap<String,Object>();
		System.out.println(MessageIdGenerator.generateId("DVCP201507201547"));
		
		List<Order> orders = new ArrayList<>();
		Order order = new Order();
		order.setOrderId("TEST20150720183108PujsrU");
		/*[
				  orderId=TEST20150721170526lPuydC
				  error=1
				  message=Ticket_Success
				  content=2001@2.880|2002@6.000
				]]
				  error=0
				  message=Success
				]
		 **/
		orders.add(order);
		
		params.put("OrderInfo",orders);
//		params.put("Deadline", "1436929522");
//		
		System.out.println(params.toString());
		LotteryPlatformBaseResponse response = clientSupport.doPost(params);
		assertTrue(response instanceof YuanChengChuPiaoOrderTicketResultResponse);
		System.out.println("response-->>"+response);
	}
}
class Order
{
	String OrderId;

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}


	
}