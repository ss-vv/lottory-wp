package com.unison.lottery.api.protocol.request.xml.methodparser;

import javax.servlet.http.HttpServletRequest;



import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;




public abstract class AbstractRequestParserForMethod implements IRequestParserForMethod {

	@Override
	public boolean parseMethodRequestToObject(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		boolean parseSucc = false;
		boolean parseUser = false;
		boolean parseExtendRequestParams = false;
		boolean parseMethodName = false;
		boolean parseParamList = false;
		
		
		boolean shouldParseUser = false;		
		boolean shouldParseExtendRequestParams = false;		
		boolean shouldParseParamList = false;
		
		if (null != methodRequest) {
			httpRequest.setAttribute(Constants.METHOD_REQUEST_NAME,methodRequest.name);
			parseMethodName = true;
		}
		shouldParseUser = decideShouldParseUser();
		shouldParseExtendRequestParams = decideShouldParseExtendRequestParams();
		shouldParseParamList = decideShouldParseParamList();
		if(shouldParseUser){
			parseUser=parseUser(methodRequest,httpRequest);
		}
		if(shouldParseExtendRequestParams){
			parseExtendRequestParams=parseExtendRequestParams(methodRequest,httpRequest);
		}
		if (shouldParseParamList) {
			parseParamList=parseParamList(methodRequest,httpRequest);
		}
		parseSucc = decideParseSucc(parseUser, shouldParseUser,
				parseMethodName, shouldParseExtendRequestParams,
				parseExtendRequestParams, shouldParseParamList, parseParamList);
		return parseSucc;
	}

	private boolean decideParseSucc(boolean parseUser, boolean shouldParseUser,
			boolean parseMethodName, boolean shouldParseExtendRequestParams,
			boolean parseExtendRequestParams, boolean shouldParseParamList,
			boolean parseParamList) {
		return parseMethodName
				&& ((shouldParseUser && parseUser) || (!shouldParseUser))
				&& ((shouldParseExtendRequestParams && parseExtendRequestParams) || (!shouldParseExtendRequestParams))
				&& ((shouldParseParamList && parseParamList) || (!shouldParseParamList));
	}

	protected  abstract boolean parseParamList(MethodRequest methodRequest,
			HttpServletRequest httpRequest) ;

	protected  abstract boolean parseExtendRequestParams(MethodRequest methodRequest,
			HttpServletRequest httpRequest) ;

	protected  abstract boolean parseUser(MethodRequest methodRequest,
			HttpServletRequest httpRequest) ;
	
	protected  abstract boolean decideShouldParseParamList() ;

	protected  abstract boolean decideShouldParseExtendRequestParams() ;

	protected  abstract boolean decideShouldParseUser() ;

}
