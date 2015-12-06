package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public class ModifyPasswordMethodRequestParser extends
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
		String oldPassword=methodRequest.oldPassword;
		String newPassword=methodRequest.newPassword;
		String type=methodRequest.type;
		Map<String,String> map=new HashMap<String,String>();
		map.put("oldPassword", oldPassword);
		map.put("newPassword", newPassword);
		map.put("type", type);
		httpRequest.setAttribute(VONames.MODIFY_PASSWORD_REQUEST_VO_NAME, map);
		
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
