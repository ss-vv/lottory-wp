package com.davcai.lottery.platform.client.anruizhiying;


import org.springframework.beans.factory.annotation.Autowired;

import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.anruizhiying.constants.AnRuiZhiYingTransCode;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingResponse;
import com.davcai.lottery.platform.client.anruizhiying.parser.IAnRuiZhiYingRespParser;

public class AnRuiZhiYingQueryPrizeClientSupport extends
		AbstractAnRuiZhiYingClientSupport {
	private int maxRetryTime=0;//最多重试次数
	@Autowired
	private IAnRuiZhiYingRespParser anRuiZhiYingQueryPrizeParser;

	@Override
	protected AnRuiZhiYingResponse parseResponse(String responseStr) {
		return anRuiZhiYingQueryPrizeParser.parseResp(responseStr);
	}

	@Override
	protected String getTransCode() {
		return AnRuiZhiYingTransCode.QUERY_PRIZE;
	}



	@Override
	protected int getMaxRetryTime() {
		return maxRetryTime;
	}

	public void setMaxRetryTime(int maxRetryTime) {
		this.maxRetryTime = maxRetryTime;
	}

	public IAnRuiZhiYingRespParser getAnRuiZhiYingQueryPrizeParser() {
		return anRuiZhiYingQueryPrizeParser;
	}

	public void setAnRuiZhiYingQueryPrizeParser(
			IAnRuiZhiYingRespParser anRuiZhiYingQueryPrizeParser) {
		this.anRuiZhiYingQueryPrizeParser = anRuiZhiYingQueryPrizeParser;
	}


	@Override
	protected boolean shouldRetry(LotteryPlatformBaseResponse result) {
		return false;
	}

}
