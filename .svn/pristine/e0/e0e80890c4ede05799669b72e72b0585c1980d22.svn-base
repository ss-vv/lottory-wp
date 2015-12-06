package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import javax.servlet.http.HttpServletRequest;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.common.model.Result;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.model.SelectFollowResponse;

/**
 * @desc
 * @createTime 2012-11-30
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class SelectFollowMethodResponseParser extends
		AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.SELECTFOLLOW_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		SelectFollowResponse response = (SelectFollowResponse)responseFromHttpRequest;
		if(null != response) {
			Result result = new Result();
			result.item = response.getList();
			result.name = MethodNames.SELECTFOLLOW;
			resultResponse.result = result;
			
			resultResponse.name = MethodNames.SELECTFOLLOW;
			resultResponse.desc = response.getDesc();
			resultResponse.status = response.getStatus();
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return SystemStatusKeyNames.SelectFollow.SELECT_FOLLOW_FAIL;
	}
}