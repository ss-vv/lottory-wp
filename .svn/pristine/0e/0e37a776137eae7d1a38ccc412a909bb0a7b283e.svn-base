package com.davcai.lottery.platform.client.yuanchengchupiao;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoQueryBalanceResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;

import net.sf.json.JSONObject;

public class YuanChengChuPiaoQueryBalanceSupport extends AbstractYuanChengChuPiaoClientSupport{

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
	
	private static YuanChengChuPiaoQueryBalanceResponse parseJSON(String orignalResponse)
	{
		YuanChengChuPiaoQueryBalanceResponse res = new YuanChengChuPiaoQueryBalanceResponse();
		JSONObject obj = JSONObject.fromObject(orignalResponse);
		
		res.setError(obj.getString("error"));
		res.setBalance(obj.getString("Balance"));
		res.setMessage(obj.getString("message"));
		return res;
	}

}
