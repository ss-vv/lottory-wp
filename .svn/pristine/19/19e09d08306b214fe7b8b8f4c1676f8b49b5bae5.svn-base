package com.davcai.lottery.platform.client;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoOrderTicketResultClientSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoValidateAwardAmountSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResultResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;

/**
 * 验证中奖金额
 * @author liwen
 *
 * 2015年7月22日
 */
public class YuanChengChuPiaoValidateAmountTest {
	@Test
	public void testValidateAmount(){
		try {
			
			YuanChengChuPiaoValidateAwardAmountSupport clientSupport=new YuanChengChuPiaoValidateAwardAmountSupport();
			clientSupport.setInterfaceUrl("http://121.40.218.112:8889/?m=checkTickets");
			clientSupport.setApiId("DVCP201507201547");
			clientSupport.setSecret("X8J73GIE93VN2LYP");
			clientSupport.setContentKey("123456");
			Map<String, Object> params=new HashMap<String,Object>();
			params.put("OrderId", "TEST20150721170526RwORZD");
			params.put("AwardAmount", "1000");
			
			LotteryPlatformBaseResponse response = clientSupport.doPost(params);
			assertTrue(response instanceof YuanChengChuPiaoResponse);
			System.out.println("response-->>"+response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	};
}
