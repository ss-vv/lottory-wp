package com.unison.lottery.api.protocol.request.json.methodparser;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.methodparser.AbstractRequestParserWithUserParser;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;

public class SendRedEnvalopeRequestParser extends AbstractRequestParserWithUserParser{

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("redEnvalopeAmount", methodRequest.redEnvalopeAmount);
		paramMap.put("envalopeNum", methodRequest.envalopeNum);
		paramMap.put("groupId", methodRequest.groupid);
		paramMap.put("hxUserName", methodRequest.hxUserName);
		paramMap.put("greetings", methodRequest.greetings);
		httpRequest.setAttribute(VONames.SEND_RED_ENVALOPE_REQUEST_VO_NAME, paramMap);
		return true;
	}

	@Override
	protected boolean decideShouldParseParamList() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected boolean decideShouldParseExtendRequestParams() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected boolean decideShouldParseUser() {
		// TODO Auto-generated method stub
		return true;
	}

}
