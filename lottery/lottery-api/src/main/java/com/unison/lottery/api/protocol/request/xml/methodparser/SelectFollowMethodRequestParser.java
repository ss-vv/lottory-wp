package com.unison.lottery.api.protocol.request.xml.methodparser;

import javax.servlet.http.HttpServletRequest;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;

/**
 * @desc
 * @createTime 2012-11-30
 * @author lei.li@unison.net.cn
 * @version 1.0
 */
public class SelectFollowMethodRequestParser extends
		AbstractRequestParserWithUserParser {

	@Override
	protected boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		return false;
	}

	@Override
	protected boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		String firstResult = methodRequest.firstResult;
		String betSchemeID = methodRequest.schemeId;
		String type = methodRequest.type;
		httpRequest.setAttribute("type", type);
		httpRequest.setAttribute("firstResult", firstResult);
		httpRequest.setAttribute("betSchemeID", betSchemeID);
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