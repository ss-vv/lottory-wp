package com.unison.lottery.api.protocol.response.xml.parser.methodparse;



import javax.servlet.http.HttpServletRequest;










import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;

import com.unison.lottery.api.protocol.response.model.CheckUpdateResponse;
import com.unison.lottery.api.protocol.response.model.QueryAlipayRSAKeyResponse;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.protocol.response.model.Response;


public class QueryAlipayRSAKeyMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.QUERY_ALIPAY_RSA_KEY_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		QueryAlipayRSAKeyResponse queryAlipayRSAKeyResponse=(QueryAlipayRSAKeyResponse) responseFromHttpRequest;
		if(null!=queryAlipayRSAKeyResponse){
			resultResponse.resultString=queryAlipayRSAKeyResponse.getResultString();
			
		}
		

	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.QueryAlipayRSAKey.FAIL;
	}

}
