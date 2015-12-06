package com.unison.lottery.api.protocol.request.json.methodparser;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.methodparser.AbstractRequestParserWithUserParser;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;

/**
 *
 * @author baoxing.peng
 * @since 2015年3月26日上午9:23:01
 */
public class QueryImmediateIndexDetailsRequestParser extends
		AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		String corpId = methodRequest.corpId;
		String oddsType = methodRequest.oddsType;
		Long matchId = methodRequest.matchId;
		String matchType = methodRequest.matchType;
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("corpId", corpId);
		param.put("oddsType", oddsType);
		param.put("matchId", matchId);
		param.put("matchType", matchType);
		httpRequest.setAttribute(VONames.QUERY_IMMEDIATE_INDEX_DETAILS_REQUEST_VO_NAME, param);
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
