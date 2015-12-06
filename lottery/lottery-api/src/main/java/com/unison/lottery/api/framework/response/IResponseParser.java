package com.unison.lottery.api.framework.response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IResponseParser {

	void parseConnectionExceptionToResponse(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse);

	void parseObjectToResponse(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse);

}
