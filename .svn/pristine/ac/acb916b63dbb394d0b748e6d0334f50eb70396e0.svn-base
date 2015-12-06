package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public class QueryAlipayRSAKeyMethodRequestParser extends
		AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		boolean result=true;
		
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.RECHARE_AMOUNT ,methodRequest.rechargeAmount);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.VOUCHER_USER_ID ,methodRequest.voucherUserId);
		
		httpRequest.setAttribute(VONames.QUERY_ALIPAY_RSA_KEY_REQUEST_VO_NAME, paramMap);
		
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
