package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public class SchemeTicketRequestParser extends
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
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.SCHEME_ID ,methodRequest.schemeId );
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.DISPLAY_MODE ,methodRequest.displayMode);
		httpRequest.setAttribute(VONames.SCHEME_TICKET_VO_NAME, paramMap);
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
