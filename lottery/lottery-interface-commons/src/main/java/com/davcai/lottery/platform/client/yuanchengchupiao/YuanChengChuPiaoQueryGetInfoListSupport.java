package com.davcai.lottery.platform.client.yuanchengchupiao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.PayoutOrIncomeEntry;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoQueryBalanceResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoQueryGetInfoListResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class YuanChengChuPiaoQueryGetInfoListSupport extends AbstractYuanChengChuPiaoClientSupport{

	@Override
	protected YuanChengChuPiaoResponse parseOrignalResponse(String orignalResponse) {
		/**
		YuanChengChuPiaoQueryBalanceResponse response=null;
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			System.out.println("orignalResponse1111"+orignalResponse);
			response=mapper.readValue(orignalResponse, YuanChengChuPiaoQueryBalanceResponse.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		return parseJSON(orignalResponse);
	}

	@Override
	protected boolean shouldRetry(LotteryPlatformBaseResponse result) {
		return false;
	}

	@Override
	protected int getMaxRetryTime() {
		return 1;
	}
	
	private static YuanChengChuPiaoQueryGetInfoListResponse parseJSON(String orignalResponse)
	{
		System.out.println("orignalResponse="+orignalResponse);
		YuanChengChuPiaoQueryGetInfoListResponse res = new YuanChengChuPiaoQueryGetInfoListResponse();
		JSONObject obj = JSONObject.fromObject(orignalResponse);
		
		JSONArray payoutArray=null;
		
		res.setError(obj.getString("error"));
		res.setMessage(obj.getString("message"));
		System.out.println(res);
		List<PayoutOrIncomeEntry> Payout = new ArrayList<PayoutOrIncomeEntry>();
		if(obj!=null){
			payoutArray= obj.getJSONArray("Payout");
		}
		else{
			payoutArray=new JSONArray();
		}
		for(int i=0;i<payoutArray.size();i++)
		{
			JSONObject payout = payoutArray.getJSONObject(i);
			PayoutOrIncomeEntry pobj =new PayoutOrIncomeEntry();
			pobj.setOrderId(payout.getString("OrderId"));
			pobj.setAmount(payout.getString("Amount"));
			Payout.add(pobj);
		}
		res.setPayout(Payout);
		List<PayoutOrIncomeEntry> Income = new ArrayList<PayoutOrIncomeEntry>();
		JSONArray incomeArray = obj.getJSONArray("Income");
		for(int i=0;i<incomeArray.size();i++)
		{
			JSONObject income = incomeArray.getJSONObject(i);
			PayoutOrIncomeEntry pobj =new PayoutOrIncomeEntry();
			pobj.setOrderId(income.getString("OrderId"));
			pobj.setAmount(income.getString("Amount"));
			Income.add(pobj);
		}
		res.setIncome(Income);
		return res;
	}

}
