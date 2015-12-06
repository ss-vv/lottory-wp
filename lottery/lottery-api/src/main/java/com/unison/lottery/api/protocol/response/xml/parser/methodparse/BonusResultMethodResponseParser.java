package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import javax.servlet.http.HttpServletRequest;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.BonusResultResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;

/**
 * @desc
 * @createTime 2012-11-30
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class BonusResultMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.BONUSRESULT_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		BonusResultResponse response = (BonusResultResponse)responseFromHttpRequest;
		if(null != response) {
			Result result = new Result();
			result.item = response.getList();
			result.name = "lotteryResList";
			resultResponse.result = result;
			
			resultResponse.name = MethodNames.BONUSRESULT;
			resultResponse.desc = response.getDesc();
			resultResponse.status = response.getStatus();
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.LotteryInfo.BONUS_RESULT_FAIL;
	}
}