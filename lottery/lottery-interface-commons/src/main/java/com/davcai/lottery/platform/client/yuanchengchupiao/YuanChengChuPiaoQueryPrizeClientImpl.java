package com.davcai.lottery.platform.client.yuanchengchupiao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.davcai.lottery.platform.client.AbstractQueryPrizeAmountClient;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.QueryPrizeAmountResponse4OneLoop;
import com.davcai.lottery.platform.client.yuanchengchupiao.model.YuanChengChuPiaoQueryPrizeResponse;
/**
 * 查询奖金接口  不用了
 * @author Next
 *
 */
public class YuanChengChuPiaoQueryPrizeClientImpl extends AbstractQueryPrizeAmountClient{

	Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	@Qualifier("yuanChengChuPiaoQueryPrizeClientSupport")
	private AbstractYuanChengChuPiaoClientSupport clientSupport;
	@Override
	public QueryPrizeAmountResponse4OneLoop queryPrizeAmountForOneLoop(Date Start_Time,
			Date End_Time) {
		YuanChengChuPiaoQueryPrizeResponse resp = new YuanChengChuPiaoQueryPrizeResponse();
		Map<String, Object> params = makeParams(Start_Time, End_Time);
		resp= (YuanChengChuPiaoQueryPrizeResponse) clientSupport.doPostWithRetry(params);
		return toQueryPrizeAmountResponse4OneLoop(resp);
	}
	private Map<String, Object> makeParams(Date startTime,Date endTime){
		
		Map<String,Object> params = new HashMap<String, Object>();
		int start = (int) (startTime.getTime() / 1000);
		int end = (int) (endTime.getTime() / 1000);
		params.put("Start_Time", start+"");
		params.put("End_Time", end+"");
		
		return params;
	}
	private QueryPrizeAmountResponse4OneLoop toQueryPrizeAmountResponse4OneLoop(YuanChengChuPiaoQueryPrizeResponse response){
		if(null==response){
			return null;
		}
		
		QueryPrizeAmountResponse4OneLoop response4OneLoop = new QueryPrizeAmountResponse4OneLoop();
		response4OneLoop.setStatus(Integer.valueOf(response.getError()));
		response4OneLoop.setDesc(response.getMessage());
		BigDecimal b = new BigDecimal(response.getAmount());  
		BigDecimal amount = b.setScale(2, BigDecimal.ROUND_HALF_UP);
		response4OneLoop.setAmount(amount);
		
		return response4OneLoop;
	}
	
}
