package com.unison.lottery.api.protocol.request.json.methodparser;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.methodparser.AbstractRequestParserWithUserParser;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;

public class QueryGroupMembersMethodRequestParser extends AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("groupid", methodRequest.groupid);
		paramMap.put("channel", methodRequest.channel);
		paramMap.put("pages", methodRequest.page);
		httpRequest.setAttribute(VONames.QUERY_GROUP_MEMBERS_REQUEST_VO_NAME, paramMap);
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
