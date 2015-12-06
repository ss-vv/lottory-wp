package com.unison.lottery.api.protocol.response.json.parser.methodparse;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.json.model.QueryGroupMembersJsonResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.PublishSchemeResponse;
import com.unison.lottery.api.protocol.response.model.QueryGroupMembersResponse;
import com.unison.lottery.api.protocol.response.model.Response;

public class QueryGroupMembersResponseParser extends AbstractMethodResponseParser {

	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.QUERY_GROUP_MEMBERS_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QueryGroupMembersResponse queryGroupMembersResponse=(QueryGroupMembersResponse) responseFromHttpRequest;
		if(null != queryGroupMembersResponse && null != queryGroupMembersResponse.getReturnStatus()){
				resultResponse.queryGroupMembersResponse = queryGroupMembersResponse;
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.Login.FAIL;
	}

	protected Response getResponse() {
		return new QueryGroupMembersJsonResponse();
	}
}
