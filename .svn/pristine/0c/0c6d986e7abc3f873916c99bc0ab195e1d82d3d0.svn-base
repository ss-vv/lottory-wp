package com.unison.lottery.api.protocol.response.json.parser.methodparse;

import javax.servlet.http.HttpServletRequest;

import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.GrabRedEnvalopeResponse;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;

public class GrabRedEnvalopeResponseParser extends AbstractMethodResponseParser {

	@Override
	protected IHaveReturnStatus getResponseFromHttpRequest(
			HttpServletRequest httpRequest) {
		return (IHaveReturnStatus) httpRequest.getAttribute(VONames.GRAB_RED_ENVALOPE_RESPONSE_VO_NAME);
	}

	@Override
	protected void handle(Response resultResponse,
			Object responseFromHttpRequest) {
		if(responseFromHttpRequest!=null){
			GrabRedEnvalopeResponse grabRedEnvalopeResponse = (GrabRedEnvalopeResponse)responseFromHttpRequest;
			resultResponse.nickname = grabRedEnvalopeResponse.getNickName();
			resultResponse.imageUrl = grabRedEnvalopeResponse.getImageUrl();
			resultResponse.grabResult = grabRedEnvalopeResponse.getGrabResult();
			resultResponse.grabAmount = grabRedEnvalopeResponse.getGrabAmount();
			resultResponse.envalopeId = grabRedEnvalopeResponse.getEnvalopeId();
			resultResponse.envalopeNum = grabRedEnvalopeResponse.getEnvalopeNum();
			resultResponse.redEnvalopeAmount = grabRedEnvalopeResponse.getRedEnvalopeAmount();
			resultResponse.minuteOfGrabed = grabRedEnvalopeResponse.getMinuteOfGrabed();
			resultResponse.grabedAmount = grabRedEnvalopeResponse.getGrabedAmount();
			resultResponse.envalopeGrabHistory = grabRedEnvalopeResponse.getEnvalopeGrabHistory();
			resultResponse.redFree = grabRedEnvalopeResponse.getFree();
			resultResponse.redGrant = grabRedEnvalopeResponse.getGrant();
		}
	}

	@Override
	protected String getDefaultReturnStatusKeyName() {
		return null;
	}
	
	protected Response getResponse() { 
		return new Response();
	}

}
