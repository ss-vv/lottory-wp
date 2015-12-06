package com.davcai.lottery.platform.client.yuanchengchupiao;


import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoOrderTicketResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;

public class YuanChengChuPiaoOrderTicketClientSupport extends AbstractYuanChengChuPiaoClientSupport{

	@Override
	protected boolean shouldRetry(LotteryPlatformBaseResponse result) {
		return false;
	}

	@Override
	protected int getMaxRetryTime() {
		return 1;
	}
	@Override
	protected YuanChengChuPiaoResponse parseOrignalResponse(
			String orignalResponse) {
		YuanChengChuPiaoOrderTicketResponse response=null;
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			response=mapper.readValue(orignalResponse, YuanChengChuPiaoOrderTicketResponse.class);
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
