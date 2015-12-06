package com.unison.lottery.api.protocol.request.json.parser;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;



import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.framework.log.model.DetailLog;
import com.unison.lottery.api.framework.request.IRequestParser;
import com.unison.lottery.api.framework.utils.PhoneNumberUtil;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.request.json.model.JsonMethodRequest;
import com.unison.lottery.api.protocol.request.xml.methodparser.IRequestParserForMethod;
import com.unison.lottery.api.protocol.request.xml.model.MethodRequest;



public class JsonRequestParser implements IRequestParser {
	
	
	private  final Logger logger=LoggerFactory.getLogger(getClass());
	private Map<String,IRequestParserForMethod> methodRequestParsersMap;

	@Override
	public boolean parseRequestToObject(HttpServletRequest httpRequest) {
		boolean parseSucc = false;
		if(null!=httpRequest){
			MethodRequest methodRequest;
			
			String userPhone = getUserPhone(httpRequest);
			httpRequest.setAttribute(Constants.USER_PHONE_NAME, userPhone);
			
			String userAgent = getUserAgent(httpRequest);
			httpRequest.setAttribute(Constants.USER_AGENT_NAME, userAgent);
			try {
				methodRequest = MethodRequest.parseJson(httpRequest.getInputStream());
				if (null != methodRequest) {
					
					String clientVersion = getClientVersion(methodRequest);
					httpRequest.setAttribute(Constants.CLIENT_VERSION_NAME, clientVersion);
					
					String validId = getValidId(methodRequest);
					httpRequest.setAttribute(Constants.VALIDID_NAME, validId);
					
					String channel = getChannel(methodRequest);
					httpRequest.setAttribute(Constants.CHANNEL_NAME, channel);
					
					
					
					logger.info("请求ip["+httpRequest.getRemoteAddr()+"]用户手机号["+userPhone+"]用户id["+validId+"]用户UA["+userAgent+"]客户端版本["+clientVersion+"]渠道["+channel+"]发起调用名["+methodRequest.name+"]");
					
					if (parseMethodRequestToObject(methodRequest, httpRequest)) {
						DetailLog detailLog=new DetailLog();
						detailLog.setRequestObj(methodRequest);
						httpRequest.setAttribute(Constants.DETAIL_LOG_NAME,detailLog);
						parseSucc = true;
					}

				}
			} catch (IOException e) {
			
				e.printStackTrace();
				parseSucc = false;
			}
		}
		

		return parseSucc;
	}

	private String getUserAgent(HttpServletRequest httpRequest) {
		String userAgent=(String)httpRequest.getHeader("User-Agent");
		if(StringUtils.isBlank(userAgent)){
			userAgent="N/A";
		}
		return userAgent;
	}

	private String getUserPhone(HttpServletRequest httpRequest) {
		String userPhone=httpRequest.getHeader(Constants.X_UP_CALLING_LINE_ID);
		userPhone=PhoneNumberUtil.normalPhoneNumber(userPhone);
		return userPhone;
	}

	private boolean parseMethodRequestToObject(MethodRequest methodRequest,
			HttpServletRequest httpRequest) {
		boolean result=false;
		if(methodRequestParsersMap.containsKey(methodRequest.name)){
			result=methodRequestParsersMap.get(methodRequest.name).parseMethodRequestToObject(methodRequest,httpRequest);
		}
		return result;
	}

	/**
	 * @param methodRequest
	 * @return
	 */
	private String getChannel(MethodRequest methodRequest) {
		String channel=methodRequest.channel;
		/*if(StringUtils.isEmpty(channel)){
			channel="N/A";
		}*/
		return channel;
	}

	/**
	 * @param methodRequest
	 * @return
	 */
	private String getValidId(MethodRequest methodRequest) {
		String validId=methodRequest.validId;
		if(StringUtils.isEmpty(validId)){
			validId="N/A";
		}
		return validId;
	}

	/**
	 * @param httpRequest
	 * @param methodRequest
	 * @return
	 */
	private String getClientVersion(MethodRequest methodRequest) {
		String clientVersion=methodRequest.clientVersion;
		if(StringUtils.isEmpty(clientVersion)){
			clientVersion="N/A";
		}
		
		return clientVersion;
	}

	

	public void setMethodRequestParsersMap(Map<String,IRequestParserForMethod> methodRequestParsersMap) {
		this.methodRequestParsersMap = methodRequestParsersMap;
	}

	public Map<String,IRequestParserForMethod> getMethodRequestParsersMap() {
		return methodRequestParsersMap;
	}

}
