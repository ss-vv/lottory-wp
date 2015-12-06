package com.unison.lottery.api.protocol.response.json.parser.methodparse;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.json.model.QueryImmediateJsonIndexInfoResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.QueryImmediateIndexInfoResponse;
import com.unison.lottery.api.protocol.response.model.Response;

/**
 *
 * @author baoxing.peng
 * @since 2015年3月24日下午7:08:13
 */
public class QueryImmediateIndexInfoResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		return (IHaveReturnStatus) httpRequest
				.getAttribute(VONames.QUERY_IMMEDIATE_INDEX_INFO_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QueryImmediateIndexInfoResponse immediateIndexInfoResponse = (QueryImmediateIndexInfoResponse) responseFromHttpRequest;
		if (immediateIndexInfoResponse != null
				&& immediateIndexInfoResponse.getReturnStatus() != null) {
			resultResponse.name = immediateIndexInfoResponse.getName();
			resultResponse.matchType = immediateIndexInfoResponse.getMatchType();
			if(immediateIndexInfoResponse.getLeagueShortName()!=null){
				resultResponse.leagueShortName = "";
				for(String league:immediateIndexInfoResponse.getLeagueShortName()){
					resultResponse.leagueShortName += league+",";
				}
				resultResponse.leagueShortName = StringUtils.removeEnd(resultResponse.leagueShortName, ",");
			}
			if(immediateIndexInfoResponse.getResultList()!=null){
				resultResponse.oddsResultList = immediateIndexInfoResponse.getResultList();
			}
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		// TODO Auto-generated method stub
		return SystemStatusKeyNames.QueryImmediateIndexInfo.QUERY_INDEX_INFO_FAIL;
	}
	
	protected Response getResponse() { 
		return new QueryImmediateJsonIndexInfoResponse();
	}

}
