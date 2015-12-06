package com.unison.lottery.api.protocol.response.xml.parser.methodparse;



import javax.servlet.http.HttpServletRequest;










import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;

import com.unison.lottery.api.protocol.response.model.CheckUpdateResponse;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.protocol.response.model.Response;


public class CheckUpdateMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.CHECK_UPDATE_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		CheckUpdateResponse checkUpdateResponse=(CheckUpdateResponse) responseFromHttpRequest;
		if(null!=checkUpdateResponse){
			resultResponse.result=new Result();
			resultResponse.result.updateDescription=checkUpdateResponse.getUpdateDescription();
			resultResponse.result.updateUrl=checkUpdateResponse.getUpdateUrl();
			resultResponse.result.updateType=checkUpdateResponse.getUpdateType();
			resultResponse.result.version=checkUpdateResponse.getVersion();
		}
		

	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.CheckUpdate.NOT_NEED_UPDATE;
	}

}
