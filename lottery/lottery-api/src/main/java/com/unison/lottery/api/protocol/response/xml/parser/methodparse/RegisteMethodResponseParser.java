package com.unison.lottery.api.protocol.response.xml.parser.methodparse;



import javax.servlet.http.HttpServletRequest;
 









import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;

import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.protocol.response.model.Response;


public class RegisteMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.REGISTE_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		RegisteResponse registeResponse=(RegisteResponse) responseFromHttpRequest;
		if(null!=registeResponse&&null!=registeResponse.getReturnStatus()){
			resultResponse.result=new Result();
			resultResponse.result.validId=registeResponse.getValidId();
			resultResponse.result.nickname=registeResponse.getNickname();
			resultResponse.result.imageUrl=registeResponse.getImageUrl();
			resultResponse.result.hx_username=registeResponse.getHxUsername();
			resultResponse.result.hx_password=registeResponse.getHxPassword();
		}
		

	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.Registe.FAIL;
	}

}
