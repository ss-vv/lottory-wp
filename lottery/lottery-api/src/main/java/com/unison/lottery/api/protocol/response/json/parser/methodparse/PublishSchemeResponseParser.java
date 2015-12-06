package com.unison.lottery.api.protocol.response.json.parser.methodparse;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.json.model.PublishSchemeJsonResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.PublishSchemeResponse;
import com.unison.lottery.api.protocol.response.model.QueryGroupMembersResponse;
import com.unison.lottery.api.protocol.response.model.Response;

public class PublishSchemeResponseParser extends AbstractMethodResponseParser {

	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.PUBLISH_SCHEME_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		PublishSchemeResponse publishSchemeResponse=(PublishSchemeResponse) responseFromHttpRequest;
		if(null != publishSchemeResponse && null != publishSchemeResponse.getReturnStatus()){
				resultResponse.publishSchemeResponse = publishSchemeResponse;
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.Login.FAIL;
	}

	protected Response getResponse() {
		return new PublishSchemeJsonResponse();
	}
}
