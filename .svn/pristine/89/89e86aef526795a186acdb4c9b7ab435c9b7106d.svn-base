package com.unison.lottery.api.protocol.response.json.parser.methodparse;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.json.model.QueryImmediateJsonIndexDetailsResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.QueryImmediateIndexDetailsResponse;
import com.unison.lottery.api.protocol.response.model.Response;

public class QueryImmediateIndexDetailsResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		return (IHaveReturnStatus) httpRequest
				.getAttribute(VONames.QUERY_IMMEDIATE_INDEX_DETAILS_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QueryImmediateIndexDetailsResponse immediateIndexDetailsResponse = (QueryImmediateIndexDetailsResponse)responseFromHttpRequest;
		if(immediateIndexDetailsResponse!=null){
			resultResponse.oddsData=immediateIndexDetailsResponse.getOddsData();
			resultResponse.timestamp = immediateIndexDetailsResponse.getTimestamp();
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		// TODO Auto-generated method stub
		return SystemStatusKeyNames.QueryImmediateIndexDetails.QUERY_INDEX_DETAILS_FAIL;
	}
	
	protected Response getResponse() {
		return new QueryImmediateJsonIndexDetailsResponse();
	}
}
