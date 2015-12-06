package com.unison.lottery.api.protocol.response.json.parser.methodparse;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.json.model.PublishSchemeJsonResponse;
import com.unison.lottery.api.protocol.response.json.model.QueryGroupMembersJsonResponse;
import com.unison.lottery.api.protocol.response.json.model.QuerySysSchemesJsonResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.QueryGroupMembersResponse;
import com.unison.lottery.api.protocol.response.model.QuerySysSchemeResponse;
import com.unison.lottery.api.protocol.response.model.Response;

public class QuerySysSchemeResponseParser extends AbstractMethodResponseParser {

	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.SYS_SCHEME_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QuerySysSchemeResponse querySysSchemeResponse=(QuerySysSchemeResponse) responseFromHttpRequest;
		if(null != querySysSchemeResponse && null != querySysSchemeResponse.getReturnStatus()){
				resultResponse.querySysSchemeResponse = querySysSchemeResponse;
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.Login.FAIL;
	}

	protected Response getResponse() {
		return new QuerySysSchemesJsonResponse();
	}

}
