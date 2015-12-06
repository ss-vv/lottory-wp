package com.davcai.lottery.platform.client;

import static org.junit.Assert.assertTrue;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoQueryBalanceSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoQueryGetInfoListSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.PayoutOrIncomeEntry;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoQueryBalanceResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoQueryGetInfoListResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.MessageIdGenerator;

/**
 * 获取余额测试
 * @author Next
 *
 */
public class YuanChengChuPiaoGetInfoListTest {
	@Test
	public void testOrderTicket() {
		System.out.println("XXX");
		YuanChengChuPiaoQueryGetInfoListSupport clientSupport=new YuanChengChuPiaoQueryGetInfoListSupport();
		clientSupport.setApiId("DVCP201507201547");
		clientSupport.setSecret("X8J73GIE93VN2LYP");
		clientSupport.setInterfaceUrl("http://121.40.218.112:8889/?m=getInfoList");
		clientSupport.setContentKey("123456");
		//clientSupport.setPostContentType("multipart/form-data");
		Map<String, Object> params=new HashMap<String,Object>();
//		System.out.println(MessageIdGenerator.generateId("TEST123456789012"));
//		params.put("OrderId", MessageIdGenerator.generateId("TEST123456789012"));
//		params.put("Content", "70015131^7003521031^7004540301^");
//		params.put("Game_type", "Z51");
//     	params.put("Times", "10");
//		params.put("Invest_Count", "1");
//		params.put("Cross_Type", "301");
//		params.put("Pay", "2000");
//		
//		params.put("Issued", "20150715");
//		params.put("Deadline", "1436929522");
	
		params.put("Start_Time" ,"1438963200");
		params.put("End_Time" , "1439049600");
		params.put("Page" , "1");
		
		
		System.out.println(params.toString());

		LotteryPlatformBaseResponse response = clientSupport.doPost(params);
        assertTrue(response instanceof YuanChengChuPiaoQueryGetInfoListResponse);
        System.out.println("response-->>"+response);
		System.out.println("response-->>");
		YuanChengChuPiaoQueryGetInfoListResponse resp = (YuanChengChuPiaoQueryGetInfoListResponse) response;
		List<PayoutOrIncomeEntry>  payout = resp.getPayout();
		for(PayoutOrIncomeEntry p:payout)
		{
			System.out.println(p.getAmount()+" "+p.getOrderId());
		}
	}
}
