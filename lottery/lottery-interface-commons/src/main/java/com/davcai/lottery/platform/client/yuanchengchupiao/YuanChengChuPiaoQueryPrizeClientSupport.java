package com.davcai.lottery.platform.client.yuanchengchupiao;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoQueryPrizeResponse;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoResponse;
/**
 * 查询奖金接口  不用了
 * @author Next
 *
 */
public class YuanChengChuPiaoQueryPrizeClientSupport extends AbstractYuanChengChuPiaoClientSupport{
	
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
		YuanChengChuPiaoQueryPrizeResponse response=null;
		ObjectMapper mapper = new ObjectMapper();
		try {
			response = mapper.readValue(orignalResponse,
					YuanChengChuPiaoQueryPrizeResponse.class);
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
