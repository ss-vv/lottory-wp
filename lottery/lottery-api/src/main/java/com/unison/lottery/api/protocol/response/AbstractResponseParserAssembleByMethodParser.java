package com.unison.lottery.api.protocol.response;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.framework.response.IResponseParser;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.xml.parser.methodparse.IResponseParserForMethod;



public abstract  class AbstractResponseParserAssembleByMethodParser implements IResponseParser {

	static final Logger logger=LoggerFactory.getLogger(AbstractResponseParserAssembleByMethodParser.class);
	private Map<String,IResponseParserForMethod> methodResponseParsersMap;
	

	public AbstractResponseParserAssembleByMethodParser() {
		super();
	}

	@Override
	public void parseObjectToResponse(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		String responseName = (String) httpRequest.getAttribute(Constants.METHOD_RESPONSE_NAME_KEY);
		logger.info("responseName="+responseName);
		if (StringUtils.isNotBlank(responseName)) {
			handleResponseForName(responseName, httpRequest, httpResponse);
		} else {
			logger.info("应用处理中没有设置返回对应的名称！");
		}
	
	}

	private void handleResponseForName(String responseName, HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		if(methodResponseParsersMap.containsKey(responseName)){
			methodResponseParsersMap.get(responseName).parseObjectToResponse(httpRequest,httpResponse);
		}
		
	}

	public void setMethodResponseParsersMap(Map<String,IResponseParserForMethod> methodResponseParsersMap) {
		this.methodResponseParsersMap = methodResponseParsersMap;
	}

	public Map<String,IResponseParserForMethod> getMethodResponseParsersMap() {
		return methodResponseParsersMap;
	}

	
	public  void parseConnectionExceptionToResponse(
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		String responseName=(String)httpRequest.getAttribute(Constants.METHOD_RESPONSE_NAME_KEY);		
		if(StringUtils.isBlank(responseName)){
			responseName=Constants.CONNECTION_EXCEPTION_NAME;	
		}
		handleConnectionExceptionResponseForName(responseName,httpRequest,httpResponse);	
	}

	

	protected abstract void outputResponse(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Response myResponse) ;

	private void handleConnectionExceptionResponseForName(String responseName,
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		Response myResponse=new Response();
		myResponse.name=responseName;
		myResponse.desc=Constants.CONNECTION_EXCEPTION_DESC;
		myResponse.status=Constants.CONNECTION_EXCEPTION_STATUS_CODE;
		//String responStr=myResponse.toXmlString();
		outputResponse(httpRequest, httpResponse,myResponse);
		
	}

}