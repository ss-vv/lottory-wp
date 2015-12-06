package com.unison.lottery.api.protocol.response.json.parser.methodparse;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.json.model.QueryScoreLiveJsonResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.QueryScoreLiveInfoResponse;
import com.unison.lottery.api.protocol.response.model.Response;

public class QueryScoreLiveInfoResponseParser extends AbstractMethodResponseParser {

	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.QUERY_SCORE_LIVE_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QueryScoreLiveInfoResponse queryScoreLiveInfoResponse=(QueryScoreLiveInfoResponse) responseFromHttpRequest;
		if(null != queryScoreLiveInfoResponse && null != queryScoreLiveInfoResponse.getReturnStatus()){
				resultResponse.queryScoreLiveInfoResponse = queryScoreLiveInfoResponse;
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.Login.FAIL;
	}

	protected Response getResponse() {
		return new QueryScoreLiveJsonResponse();
	}

}
