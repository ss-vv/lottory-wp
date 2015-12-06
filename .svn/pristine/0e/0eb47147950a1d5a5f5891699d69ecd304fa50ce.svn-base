package com.unison.lottery.api.framework.filter.security;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unison.lottery.api.framework.log.ILogManager;
import com.unison.lottery.api.framework.request.IRequestParser;
import com.unison.lottery.api.framework.response.IResponseParser;
import com.unison.lottery.api.framework.utils.DecryptAndEncryptService;
import com.unison.lottery.api.framework.utils.DecryptException;
import com.unison.lottery.api.framework.utils.EncryptException;
import com.unison.lottery.api.protocol.common.Constants;

public class DecryptAndEncryptFilter extends AbstractDecryptAndEncryptFilter {
	
	
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
		
		
	}
	@Override
	protected ServletResponse encrypt(HttpServletResponse httpResponse,HttpServletRequest httpRequest)
			throws EncryptException {
		
		return new EncryptResponseWrapper(httpResponse);
	}
//	private String getSeed(HttpServletRequest httpRequest) {
//		return httpRequest.getHeader(Constants.SEED_PARAMETER_NAME);
//	}
	@Override
	protected ServletRequest decrypt(HttpServletRequest httpRequest)
			throws DecryptException {
		return new DecryptRequestWrapper(httpRequest);
	}

	

}
