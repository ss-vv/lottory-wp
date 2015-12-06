package com.davcai.lottery.platform.client.yuanchengchupiao;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResultResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class YuanChengChuPiaoOrderTicketResultClientSupport extends
		AbstractYuanChengChuPiaoClientSupport {


	@Override
	protected boolean shouldRetry(LotteryPlatformBaseResponse result) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected int getMaxRetryTime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected YuanChengChuPiaoOrderTicketResultResponse parseOrignalResponse(
			String orignalResponse) {
		// TODO Auto-generated method stu
		YuanChengChuPiaoOrderTicketResultResponse response=null;
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			response=mapper.readValue(orignalResponse, YuanChengChuPiaoOrderTicketResultResponse.class);
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
		return response;
		
	}
}
