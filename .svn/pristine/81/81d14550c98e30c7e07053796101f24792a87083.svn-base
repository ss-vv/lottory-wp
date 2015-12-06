package com.unison.lottery.api.protocol.response.json.parser.methodparse;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.json.model.QueryGroupInfoJsonResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.QueryGroupInfoResponse;
import com.unison.lottery.api.protocol.response.model.Response;

public class LoginFailMethodResponseParser extends AbstractMethodResponseParser{

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.LOGIN_FAIL_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
			resultResponse.queryGroupInfoResponse = new QueryGroupInfoResponse();
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.Login.SHOULD_LOGIN;
	}

	protected Response getResponse() {
		return new QueryGroupInfoJsonResponse();
	}
}
