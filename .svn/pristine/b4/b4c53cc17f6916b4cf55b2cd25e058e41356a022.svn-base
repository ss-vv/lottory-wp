package com.unison.lottery.api.protocol.response.json.parser.methodparse;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.framework.log.model.DetailLog;
import com.unison.lottery.api.framework.utils.PhoneNumberUtil;
import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.response.json.JsonResponseUtil;
import com.unison.lottery.api.protocol.response.model.IHaveReturnStatus;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.xml.parser.methodparse.IResponseParserForMethod;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;

public abstract class AbstractMethodResponseParser implements IResponseParserForMethod{

	static final Logger logger=LoggerFactory.getLogger(AbstractMethodResponseParser.class);
	private IStatusRepository statusRepository;
	

	

	@Override
	public void parseObjectToResponse(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
		if(null!=httpRequest&&null!=httpResponse){
			Response resultResponse = getResponse();
			setResponseName(httpRequest, resultResponse);
			
			initDefaultReturnStatus(resultResponse);
			try{
				IHaveReturnStatus responseFromHttpRequest = getResponseFromHttpRequest(httpRequest);
				if (null != responseFromHttpRequest) {
					ReturnStatus returnStatus = responseFromHttpRequest.getReturnStatus();
					if( null != returnStatus){
						resultResponse.desc = returnStatus.getDescForClient();
						resultResponse.status = returnStatus.getStatusCodeForClient();
						handle(resultResponse,responseFromHttpRequest);
						
					}				
				}
				
	
			}catch (Exception e) {
				e.printStackTrace();
			}
			outputResponse(httpRequest,httpResponse, resultResponse);
		}
	}

	protected Response getResponse() {
		return new Response();
	}

	protected abstract IHaveReturnStatus getResponseFromHttpRequest(HttpServletRequest httpRequest) ;

	protected abstract void handle(Response resultResponse, Object responseFromHttpRequest);

	private void outputResponse(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Response resultResponse) {
		try {
			JsonResponseUtil.outputResponse(httpRequest, httpResponse, resultResponse);
			DetailLog detailLog=(DetailLog) httpRequest.getAttribute(Constants.DETAIL_LOG_NAME);
			if(null!=detailLog){
				detailLog.setResponseObj(resultResponse);
				httpRequest.setAttribute(Constants.DETAIL_LOG_NAME,detailLog);
			}
			String userPhone=(String) httpRequest.getAttribute(Constants.USER_PHONE_NAME);
			userPhone=PhoneNumberUtil.normalPhoneNumber(userPhone);
			String userAgent=(String)httpRequest.getAttribute(Constants.USER_AGENT_NAME);
			if(StringUtils.isEmpty(userAgent)){
				userAgent="N/A";
			}
			String clientVersion=(String) httpRequest.getAttribute(Constants.CLIENT_VERSION_NAME);
			if(StringUtils.isEmpty(clientVersion)){
				clientVersion="N/A";
			}
			String validId=(String) httpRequest.getAttribute(Constants.VALIDID_NAME);
			if(StringUtils.isEmpty(validId)){
				validId="N/A";
			}
			String channel=(String) httpRequest.getAttribute(Constants.CHANNEL_NAME);
			if(StringUtils.isEmpty(channel)){
				channel="N/A";
			}
			
			logger.info("来自ip["+httpRequest.getRemoteAddr()+"]的用户手机号["+userPhone+"]用户id["+validId+"]用户ua["+userAgent+"]客户端版本号["+clientVersion+"]渠道["+channel+"]的请求["+resultResponse.name+"]已有响应");
			//LogManager.recordActivityLog(httpRequest.getRemoteAddr(),userPhone,provinceCode,cityCode,userAgent,serviceId,clientVersion,isNewUser,resultResponse.name);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("发送响应信息时出现异常=" + e.getMessage());
		}
		
	}

	/**
	 * @param response
	 */
	private void initDefaultReturnStatus(Response response) {
		ReturnStatus defaultFailStatus = this.statusRepository.getSystemStatusBySystemKey(getDefaultReturnStatusKeyName());
		if(null!=defaultFailStatus){
			response.desc = defaultFailStatus.getDescForClient();
			response.status = defaultFailStatus.getStatusCodeForClient();
		}
	}

	protected abstract String getDefaultReturnStatusKeyName() ;

	/**
	 * @param httpRequest
	 * @param response
	 */
	private void setResponseName(HttpServletRequest httpRequest, Response response) {
		String name=(String) httpRequest
			.getAttribute(Constants.METHOD_RESPONSE_NAME_KEY);
		response.name=name;
	}

	/**
	 * @return the statusRepository
	 */
	public IStatusRepository getStatusRepository() {
		return statusRepository;
	}

	/**
	 * @param statusRepository the statusRepository to set
	 */
	public void setStatusRepository(IStatusRepository statusRepository) {
		this.statusRepository = statusRepository;
	}

	

}
