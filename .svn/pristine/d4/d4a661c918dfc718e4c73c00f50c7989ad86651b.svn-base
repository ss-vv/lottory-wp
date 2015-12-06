package com.unison.lottery.api.protocol.response.xml;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.common.MethodNames;
import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.model.LoginFailResponse;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.service.IOutputLoginFailResponseService;
import com.unison.lottery.api.protocol.response.util.XmlResponseUtil;
import com.unison.lottery.api.protocol.status.IStatusRepository;
import com.unison.lottery.api.protocol.status.ReturnStatus;





public class OutputLoginFailXmlResponseService implements IOutputLoginFailResponseService {
	
	private static final Logger logger=LoggerFactory.getLogger(OutputLoginFailXmlResponseService.class);
	private IStatusRepository statusRepository;

	@Override
	public void outputResponse(ServletRequest request, ServletResponse response) {
		HttpServletRequest httpRequest=(HttpServletRequest)request;
		HttpServletResponse httpResponse=(HttpServletResponse)response;
		String methodName=(String) httpRequest.getAttribute(Constants.METHOD_REQUEST_NAME);
		//Response resultResponse=new Response();
		//resultResponse.name=methodName;
		ReturnStatus loginFailStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Login.SHOULD_LOGIN);
		if(null!=loginFailStatus){
			//resultResponse.desc=loginFailStatus.getDescForClient();
			//resultResponse.status=loginFailStatus.getStatusCodeForClient();
			LoginFailResponse loginFailResponse=new LoginFailResponse();
			loginFailResponse.setReturnStatus(loginFailStatus);
			request.setAttribute(VONames.LOGIN_FAIL_RESPONSE_VO_NAME, loginFailResponse);
			request.setAttribute(Constants.METHOD_RESPONSE_NAME_KEY, MethodNames.LOGIN_FAIL);
		}
		
		
		
		
		
	}

	public void setStatusRepository(IStatusRepository statusRepository) {
		this.statusRepository = statusRepository;
	}

	public IStatusRepository getStatusRepository() {
		return statusRepository;
	}

}
