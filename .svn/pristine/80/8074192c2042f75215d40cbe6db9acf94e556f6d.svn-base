package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public class BindMobileMethodRequestParser extends
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
		String phoneNumber=methodRequest.phoneNumber;
		String verifyCode=methodRequest.verifyCode;
		Map<String,String> map=new HashMap<String,String>();
		map.put("phoneNumber", phoneNumber);
		map.put("verifyCode", verifyCode);
		httpRequest.setAttribute(VONames.BIND_MOBILE_REQUEST_VO_NAME, map);
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
