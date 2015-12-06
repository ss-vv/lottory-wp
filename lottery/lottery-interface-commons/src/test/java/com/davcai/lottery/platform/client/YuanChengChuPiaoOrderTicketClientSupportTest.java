package com.davcai.lottery.platform.client;


import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.davcai.lottery.platform.client.yuanchengchupiao.YuanChengChuPiaoOrderTicketClientSupport;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.util.MessageIdGenerator;

import net.sf.json.JSONObject;

/**
 * 出票测试
 * @author Next
 *
 */
public class YuanChengChuPiaoOrderTicketClientSupportTest{
	
	@Test
	public void testOrderTicket() {
		YuanChengChuPiaoOrderTicketClientSupport clientSupport=new YuanChengChuPiaoOrderTicketClientSupport();
		clientSupport.setApiId("TEST123456789012");
		clientSupport.setSecret("ABCDEFGHIJKLMNOP");
		clientSupport.setInterfaceUrl("http://121.40.218.112:8889/?m=ticket");
		clientSupport.setContentKey("123456");
		//clientSupport.setPostContentType("multipart/form-data");
		Map<String, Object> params=new HashMap<String,Object>();
		String orderid=MessageIdGenerator.generateId("TEST123456789012");
		System.out.println("订单号orderid="+orderid);
		params.put("OrderId", orderid);
		
		
		JSONObject obj = JSONObject.fromObject(params);
		
//		//竞彩-足彩胜平负 单关      ---     测试订单号  用于获取信息测试
//		params = Z51_101(params);//TEST20150719114549FTwfmM
//		obj = JSONObject.fromObject(params);
//		System.out.println("竞彩-足彩胜平负 单关");
//		System.out.println(obj.toString());
//		
//		
//		
//		//竞彩-足彩胜平负 三串一
//		params = Z51_301(params);//TEST20150719115344sXVvXx
//		obj = JSONObject.fromObject(params);
//		System.out.println("竞彩-足彩胜平负 三串一");
//		System.out.println(obj.toString());
//		
//		
//		//竞彩-足彩胜平负 四串十一
//		params = Z51_4011(params);
//		obj = JSONObject.fromObject(params);//TEST20150719114752QoHkfJ
//		System.out.println("竞彩-足彩胜平负 四串十一");
//		System.out.println(obj.toString());
//		
//		//竞彩-足彩比分 单关
//		params = Z52_101(params);//TEST20150719115514VfDKmS
//		obj = JSONObject.fromObject(params);
//		System.out.println("竞彩-足彩比分 单关");
//		System.out.println(obj.toString());
//		
//		//竞彩-足彩比分 单关
//		params = Z52_401(params);//TEST20150719115531LjdNrT
//		obj = JSONObject.fromObject(params);
//		System.out.println("竞彩-足彩比分 单关");
//		System.out.println(obj.toString());
//		
//		//竞彩-足彩比分 四串六
//		params = Z52_406(params);//TEST20150719115547UXCmrY
//		obj = JSONObject.fromObject(params);
//		System.out.println("竞彩-足彩比分 四串六");
//		System.out.println(obj.toString());
//		
//		//竞彩-足彩总进球 单关
//		params = Z53_101(params);//TEST20150719115603dEcMhH
//		obj = JSONObject.fromObject(params);
//		System.out.println("竞彩-足彩总进球 单关");
//		System.out.println(obj.toString());
//		
//		//竞彩-足彩总进球 三串一
//		params = Z53_301(params);//TEST20150719115623sbVCED
//		obj = JSONObject.fromObject(params);
//		System.out.println("竞彩-足彩总进球 三串一");
//		System.out.println(obj.toString());
//		
//		//竞彩-足彩总进球 四串十一
//		params = Z53_4011(params);//TEST20150719191832TOAGCD
//		obj = JSONObject.fromObject(params);
//		System.out.println("竞彩-足彩总进球 四串十一");
//		System.out.println(obj.toString());
//		
//		//竞彩-足彩半全场胜平负 二串一
//		params = Z54_201(params);//TEST20150719115658HJHEFH
//		obj = JSONObject.fromObject(params);
//		System.out.println("竞彩-足彩半全场胜平负 二串一");
//		System.out.println(obj.toString());
//		
//		//竞彩-足彩半全场胜平负 四串一
//		params = Z54_401(params);//TEST20150719115716pvxslr
//		obj = JSONObject.fromObject(params);
//		System.out.println("竞彩-足彩半全场胜平负 四串一");
//		System.out.println(obj.toString());
//		
//		//竞彩-足彩半全场胜平负 四串十一
//		params = Z54_4011(params);//TEST20150719115732FHaQkc
//		obj = JSONObject.fromObject(params);
//		System.out.println("竞彩-足彩半全场胜平负 四串十一");
//		System.out.println(obj.toString());
		
		
		LotteryPlatformBaseResponse response = clientSupport.doPost(params);
		assertTrue(response instanceof YuanChengChuPiaoOrderTicketResponse);
		System.out.println("response-->>"+response);
		
		
		
	}
	
	/**
	 * 竞彩-足彩胜平负 单关
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z51_101(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "70010^");		
		//玩法
		params.put("Game_type", "Z51");
		//注数
		params.put("Invest_Count", "1");
		//倍数
		params.put("Times", "1");
		//过关方式
		params.put("Cross_Type", "101");
		//金额
		params.put("Pay", "200");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1436929522");
		return params;
	}
	/**
	 * 竞彩-足彩胜平负 三串一
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z51_301(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "70010^70021^70033^");		
		//玩法
		params.put("Game_type", "Z51");
		//注数
		params.put("Invest_Count", "1");
		//倍数
		params.put("Times", "1");
		//过关方式
		params.put("Cross_Type", "301");
		//金额
		params.put("Pay", "200");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1436929522");
		return params;
	}
	
	/**
	 * 竞彩-足彩胜平负  四串十一
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z51_4011(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "70010^70021^70033^70040^");		
		//玩法
		params.put("Game_type", "Z51");
		//注数
		params.put("Invest_Count", "11");
		//倍数
		params.put("Times", "10");
		//过关方式
		params.put("Cross_Type", "4011");
		//金额
		params.put("Pay", "22000");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1436929522");
		return params;
	}
	
	
	/**
	 * 竞彩-足彩比分 单关
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z52_101(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "700111^");		
		//玩法
		params.put("Game_type", "Z52");
		//注数
		params.put("Invest_Count", "1");
		//倍数
		params.put("Times", "1");
		//过关方式
		params.put("Cross_Type", "101");
		//金额
		params.put("Pay", "200");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1437042538");
		return params;
	}
	
	/**
	 * 竞彩-足彩比分 四串一
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z52_401(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "700211^700321^700110^700435^");		
		//玩法
		params.put("Game_type", "Z52");
		//注数
		params.put("Invest_Count", "1");
		//倍数
		params.put("Times", "1");
		//过关方式
		params.put("Cross_Type", "401");
		//金额
		params.put("Pay", "200");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1437042538");
		return params;
	}
	
	
	/**
	 * 竞彩-足彩比分 四串六
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z52_406(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "700211^700321^700110^700435^");		
		//玩法
		params.put("Game_type", "Z52");
		//注数
		params.put("Invest_Count", "6");
		//倍数
		params.put("Times", "1");
		//过关方式
		params.put("Cross_Type", "406");
		//金额
		params.put("Pay", "1200");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1437042538");
		return params;
	}
	
	/**
	 * 竞彩-足彩总进球 单关
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z53_101(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "70013^");		
		//玩法
		params.put("Game_type", "Z53");
		//注数
		params.put("Invest_Count", "1");
		//倍数
		params.put("Times", "1");
		//过关方式
		params.put("Cross_Type", "101");
		//金额
		params.put("Pay", "200");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1437042538");
		return params;
	}
	
	/**
	 * 竞彩-足彩总进球 三串一
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z53_301(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "70013^70021^70035^");		
		//玩法
		params.put("Game_type", "Z53");
		//注数
		params.put("Invest_Count", "1");
		//倍数
		params.put("Times", "1");
		//过关方式
		params.put("Cross_Type", "301");
		//金额
		params.put("Pay", "200");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1437042538");
		return params;
	}
	
	/**
	 * 竞彩-足彩总进球 四串十一
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z53_4011(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "70013^70021^70035^70041^");		
		//玩法
		params.put("Game_type", "Z53");
		//注数
		params.put("Invest_Count", "11");
		//倍数
		params.put("Times", "1");
		//过关方式
		params.put("Cross_Type", "301");
		//金额
		params.put("Pay", "2200");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1437042538");
		return params;
	}
	
	
	/**
	 * 竞彩-足彩半全场胜平负 二串一
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z54_201(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "700133^700233^");		
		//玩法
		params.put("Game_type", "Z54");
		//注数
		params.put("Invest_Count", "1");
		//倍数
		params.put("Times", "1");
		//过关方式
		params.put("Cross_Type", "201");
		//金额
		params.put("Pay", "200");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1437042538");
		return params;
	}
	
	/**
	 * 竞彩-足彩半全场胜平负   四串一
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z54_401(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "700133^700211^700313^700410^");		
		//玩法
		params.put("Game_type", "Z54");
		//注数
		params.put("Invest_Count", "1");
		//倍数
		params.put("Times", "1");
		//过关方式
		params.put("Cross_Type", "401");
		//金额
		params.put("Pay", "200");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1437042538");
		return params;
	}
	
	
	/**
	 * 竞彩-足彩半全场胜平负 四串十一
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z54_4011(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "700133^700211^700313^700410^");
		//玩法
		params.put("Game_type", "Z54");
		//注数
		params.put("Invest_Count", "11");
		//倍数
		params.put("Times", "10");
		//过关方式
		params.put("Cross_Type", "4011");
		//金额
		params.put("Pay", "22000");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1437042538");
		return params;
	}
	
	/**
	 * 竞彩-足球让球胜平负 单关
	 * @param params
	 * @return
	 */
	private Map<String, Object> Z56_101(Map<String, Object> params)
	{
		//彩票内容
		params.put("Content", "700133^700211^700313^700410^");
		//玩法
		params.put("Game_type", "Z54");
		//注数
		params.put("Invest_Count", "1");
		//倍数
		params.put("Times", "1");
		//过关方式
		params.put("Cross_Type", "101");
		//金额
		params.put("Pay", "200");
		//期号当天日期
		params.put("Issued", "20150719");
		//最早一场比赛开始时间
		params.put("Deadline", "1437042538");
		return params;
	}
}
