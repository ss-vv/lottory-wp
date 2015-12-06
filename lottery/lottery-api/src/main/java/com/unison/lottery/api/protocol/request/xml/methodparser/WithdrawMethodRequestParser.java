package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public class WithdrawMethodRequestParser extends
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
		BigDecimal amount=methodRequest.amount;
		String withdrawPassword=methodRequest.password;
		String realIP=httpRequest.getHeader("X-Real-IP");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("amount", amount);
		map.put("withdrawPassword", withdrawPassword);
		map.put("realIP", realIP);
		httpRequest.setAttribute(VONames.WITHDRAW_REQUEST_VO_NAME, map);
		
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
