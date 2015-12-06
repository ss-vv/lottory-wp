package com.unison.lottery.api.framework.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;








/**
 * Servlet Filter implementation class ProtocolFilter
 */

public abstract class AbstractProtocolFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractProtocolFilter.class);
	
	

    /**
     * Default constructor. 
     */
    public AbstractProtocolFilter() {
        
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		//httpRequest.setCharacterEncoding("utf-8");
		//httpResponse.setCharacterEncoding("utf-8");
		//logger.info("请求ip:["+httpRequest.getRemoteAddr()+"]");
		if (parseRequestToObject(httpRequest)) {
			chain.doFilter(httpRequest, httpResponse);
			parseObjectToResponse(httpRequest, httpResponse);
			
		} else {
			logger.info("来自" + httpRequest.getRemoteAddr() + "的请求无法解析！");
			parseConnectionExceptionToResponse(httpRequest, httpResponse);
		}
		recordDetailLog(httpRequest);
	}

	protected abstract void recordDetailLog(HttpServletRequest httpRequest);

	protected abstract void parseConnectionExceptionToResponse(
			HttpServletRequest httpRequest, HttpServletResponse httpResponse) ;

	protected abstract void parseObjectToResponse(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse) ;

	protected abstract boolean parseRequestToObject(HttpServletRequest httpRequest) ;
	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
		
	}

	

	

}
