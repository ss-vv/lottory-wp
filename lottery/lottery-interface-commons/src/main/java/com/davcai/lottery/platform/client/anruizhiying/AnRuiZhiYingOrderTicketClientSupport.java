package com.davcai.lottery.platform.client.anruizhiying;


import com.davcai.lottery.platform.client.LotteryPlatformBaseResponse;
import com.davcai.lottery.platform.client.anruizhiying.constants.AnRuiZhiYingTransCode;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingOrderTicketResponse;
import com.davcai.lottery.platform.client.anruizhiying.model.AnRuiZhiYingResponse;

public class AnRuiZhiYingOrderTicketClientSupport extends
		AbstractAnRuiZhiYingClientSupport {
	private int maxRetryTime=3;//最多重试次数

	@Override
	protected AnRuiZhiYingResponse parseResponse(String responseStr) {
		AnRuiZhiYingOrderTicketResponse response=new AnRuiZhiYingOrderTicketResponse(responseStr);
		return response;
	}

	@Override
	protected String getTransCode() {
		return AnRuiZhiYingTransCode.ORDER_TICKET;
	}

	


	@Override
	protected int getMaxRetryTime() {
		return maxRetryTime;
	}

	public void setMaxRetryTime(int maxRetryTime) {
		this.maxRetryTime = maxRetryTime;
	}


	@Override
	protected boolean shouldRetry(LotteryPlatformBaseResponse result) {
		if (null == result
				|| (result instanceof AnRuiZhiYingOrderTicketResponse && ((AnRuiZhiYingOrderTicketResponse) result)
						.getStatus() == AnRuiZhiYingOrderTicketResponse.EMPTY_RESPONSE)) {
			return true;
		}
		return false;
	}

}
