package com.unison.lottery.api.framework.filter;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;








import com.unison.lottery.api.framework.log.ILogManager;
import com.unison.lottery.api.framework.request.IRequestParser;
import com.unison.lottery.api.framework.response.IResponseParser;


public  abstract class AbstractProtocolFilterAssembleByRequestParserAndResponseParserAndLogManager extends AbstractProtocolFilter {

	protected IRequestParser requestParser;
	
	protected IResponseParser responseParser;
	
	protected ILogManager logManager;
	
	

	@Override
	protected void parseConnectionExceptionToResponse(
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		responseParser.parseConnectionExceptionToResponse(
				httpRequest, httpResponse);

	}

	@Override
	protected void parseObjectToResponse(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) {
		responseParser.parseObjectToResponse( httpRequest,
				 httpResponse);

	}

	@Override
	protected boolean parseRequestToObject(HttpServletRequest httpRequest) {
		
		return requestParser.parseRequestToObject(httpRequest);
	}
	
	protected  void recordDetailLog(HttpServletRequest httpRequest){
		
		this.logManager.recordActivityDetailLog(httpRequest);
		
	}

	
}
