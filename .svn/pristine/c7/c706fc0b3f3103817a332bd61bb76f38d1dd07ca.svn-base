package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;

public class QueryCTZCMatchRequestParser extends
		AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put(Constants.PLAY_ID, methodRequest.playId);
		paramMap.put(Constants.ISSUE_NUMBER, methodRequest.issueNumber);
		httpRequest.setAttribute(VONames.QUERY_CTZC_MATCH_VO_NAME, paramMap);
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
