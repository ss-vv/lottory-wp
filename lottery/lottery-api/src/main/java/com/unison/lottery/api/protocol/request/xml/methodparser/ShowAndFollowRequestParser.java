package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public class ShowAndFollowRequestParser extends
		AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		Map<String,String> paramMap = new HashMap<String, String>();
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.FIRST_RESULT ,methodRequest.firstResult );
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.SCHEME_FILTER ,methodRequest.filter);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.CLIENT_VERSION_NAME ,methodRequest.clientVersion);
		paramMap.put("type", methodRequest.type);
		httpRequest.setAttribute(VONames.SHOW_AND_FOLLOW_VO_NAME, paramMap);
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
