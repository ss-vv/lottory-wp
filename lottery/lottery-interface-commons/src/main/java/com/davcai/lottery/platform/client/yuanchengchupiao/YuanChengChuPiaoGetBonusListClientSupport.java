package com.davcai.lottery.platform.client.yuanchengchupiao;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.GetBonusListResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.GetBonusListResponse4OneLoop;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;

/**
 * 查询奖金列表  不用了
 * @author Next
 *
 */
public class YuanChengChuPiaoGetBonusListClientSupport extends AbstractYuanChengChuPiaoClientSupport{

	@Override
	protected YuanChengChuPiaoResponse parseOrignalResponse(String orignalResponse) {
		GetBonusListResponse4OneLoop response=null;
		ObjectMapper mapper = new ObjectMapper(); 
		try {
			response=mapper.readValue(orignalResponse, GetBonusListResponse4OneLoop.class);
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

	@Override
	protected boolean shouldRetry(LotteryPlatformBaseResponse result) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected int getMaxRetryTime() {
		// TODO Auto-generated method stub
		return 1;
	}

	
	
	
}
