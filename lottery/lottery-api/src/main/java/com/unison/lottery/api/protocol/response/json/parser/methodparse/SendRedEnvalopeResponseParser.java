package com.unison.lottery.api.protocol.response.json.parser.methodparse;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.model.SendRedEnvalopeResponse;

public class SendRedEnvalopeResponseParser extends AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.SEND_RED_ENVALOPE_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		SendRedEnvalopeResponse sendRedEnvalopeResponse = (SendRedEnvalopeResponse)responseFromHttpRequest;
		resultResponse.envalopeId = String.valueOf(sendRedEnvalopeResponse.getEnvalopeId());
		resultResponse.resultStatus = sendRedEnvalopeResponse.getResultStatus();
		resultResponse.resultDesc = sendRedEnvalopeResponse.getResultDesc();//添加描述字段
		resultResponse.greetings = sendRedEnvalopeResponse.getGreetings();//添加祝福语
		resultResponse.redFree = sendRedEnvalopeResponse.getFree(); //现金余额
		resultResponse.redFund = sendRedEnvalopeResponse.getFund(); //可用余额
		resultResponse.redFrozenFund = sendRedEnvalopeResponse.getFrozenFund();//冻结现金余额
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return null;
	}
	
	protected Response getResponse() { 
		return new Response();
	}
}
