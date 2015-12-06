package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import javax.servlet.http.HttpServletRequest;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.LotteryResultResponse;
import com.unison.lottery.api.protocol.response.model.Response;

/**
 * @desc
 * @createTime 2012-11-30
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class LotteryResultMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.LOTTERYRESULT_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		LotteryResultResponse response = (LotteryResultResponse)responseFromHttpRequest;
		if(null != response) {
			Result result = new Result();
			result.item = response.getList();
			result.name = MethodNames.LOTTERYRESLIST;
			result.pages = response.getTotalPage();
			result.page = String.valueOf(response.getPageNo());
			resultResponse.result = result;
			
			resultResponse.name = MethodNames.BONUSRESULT;
			resultResponse.desc = response.getDesc();
			resultResponse.status = response.getStatus();
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.LotteryInfo.LOTTERY_RESULT_FAIL;
	}
}