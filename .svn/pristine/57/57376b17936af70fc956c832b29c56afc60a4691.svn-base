package com.unison.lottery.api.protocol.request.xml.methodparser;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;

public class QueryVouchersRequestParser extends
		AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		Map<String,Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.VOUCHER_TYPE ,methodRequest.voucherType );
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.PAGE ,methodRequest.page);
		paramMap.put(com.unison.lottery.api.protocol.common.Constants.PERPAGE_COUNT ,methodRequest.perpageCount);
		
		httpRequest.setAttribute(VONames.QUERY_VOUCHERS_RESPONSE_VO_NAME, paramMap);
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
