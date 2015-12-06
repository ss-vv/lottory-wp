package com.unison.lottery.api.protocol.request.json.methodparser;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.methodparser.AbstractRequestParserWithUserParser;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;

public class QueryScoreLiveInfoRequestParser extends AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("matchType", methodRequest.matchType);
		paramMap.put("subType", methodRequest.subType);
		paramMap.put("leagueShortName", methodRequest.leagueShortName);
		
		httpRequest.setAttribute(VONames.QUERY_SCORE_LIVE_REQUEST_VO_NAME, paramMap);
		return true;
	}

	@Override
	protected boolean decideShouldParseParamList() {
		return false;
	}

	@Override
	protected boolean decideShouldParseExtendRequestParams() {
		return true;
	}

	@Override
	protected boolean decideShouldParseUser() {
		return true;
	}
}
