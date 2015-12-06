package com.davcai.lottery.platform.client;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoGetBonusListClientSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.GetBonusListResponse4OneLoop;

/**
 * 获取中奖列表测试
 * @author Next
 *
 */
public class YuanChengChuPiaoGetBonusListTest {
	@Test
	public void getBonusList()
	{
		YuanChengChuPiaoGetBonusListClientSupport clientSupport = new YuanChengChuPiaoGetBonusListClientSupport();
		clientSupport.setApiId("TEST123456789012");
		clientSupport.setSecret("ABCDEFGHIJKLMNOP");
		clientSupport.setInterfaceUrl("http://222.92.36.78:8011/?m=getBonusList");
		clientSupport.setContentKey("123456");
		
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("Start_Time", "0000000000");
		params.put("End_Time",   "1437041811");
		
		System.out.println(params.toString());
		LotteryPlatformBaseResponse response = clientSupport.doPost(params);
		assertTrue(response instanceof GetBonusListResponse4OneLoop);
		System.out.println("response-->>"+response);
	}
}
