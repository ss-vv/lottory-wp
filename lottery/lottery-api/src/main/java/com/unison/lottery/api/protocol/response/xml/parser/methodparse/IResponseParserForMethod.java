package com.unison.lottery.api.protocol.response.xml.parser.methodparse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IResponseParserForMethod {

	void parseObjectToResponse(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse);

}
