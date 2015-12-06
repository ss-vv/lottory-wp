package com.unison.lottery.api.protocol.request.xml.methodparser;



import javax.servlet.http.HttpServletRequest;


import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public class ActivityNotifyMethodRequestParser extends
		AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		boolean result=false;
		String firstResult=methodRequest.firstResult;
		httpRequest.setAttribute(VONames.ACTIVITY_NOTIFY_REQUEST_VO_NAME, firstResult);
		
		result=true;
		return result;
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
