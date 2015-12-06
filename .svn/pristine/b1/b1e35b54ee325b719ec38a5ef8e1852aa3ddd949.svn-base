package com.unison.lottery.api.protocol.response.json.parser.methodparse;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.json.model.QueryGroupInfoJsonResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.QueryGroupInfoResponse;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.json.parser.methodparse.AbstractMethodResponseParser;

public class QueryGroupInfoResponseParser extends AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.QUERY_GROUP_INFO_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QueryGroupInfoResponse queryGroupInfoResponse=(QueryGroupInfoResponse) responseFromHttpRequest;
		if(null != queryGroupInfoResponse && null != queryGroupInfoResponse.getReturnStatus()){
				resultResponse.queryGroupInfoResponse = queryGroupInfoResponse;
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.Login.FAIL;
	}

	protected Response getResponse() {
		return new QueryGroupInfoJsonResponse();
	}
	
}
