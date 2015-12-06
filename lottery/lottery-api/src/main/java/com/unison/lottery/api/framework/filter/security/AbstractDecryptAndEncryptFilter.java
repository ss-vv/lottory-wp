package com.unison.lottery.api.framework.filter.security;

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

import com.unison.lottery.api.framework.utils.DecryptException;
import com.unison.lottery.api.framework.utils.EncryptException;
import com.unison.lottery.api.protocol.common.Constants;








/**
 * Servlet Filter implementation class ProtocolFilter
 */

public abstract class AbstractDecryptAndEncryptFilter implements Filter {
	
	private static final Logger logger = LoggerFactory.getLogger(AbstractDecryptAndEncryptFilter.class);
	
	

    /**
     * Default constructor. 
     */
    public AbstractDecryptAndEncryptFilter() {
        
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
		try{
			
			chain.doFilter(decrypt(httpRequest), encrypt(httpResponse,httpRequest));
			
		}
		catch(DecryptException de){
			logger.info("来自ip{}的请求无法解密！,异常为{}" ,httpRequest.getRemoteAddr(),de.getMessage());
			httpResponse.setStatus(Constants.PROTOCOL_DATA_EXCEPTION);
		}
		catch(EncryptException ee){
			logger.info("来自ip{}的请求的响应无法加密！,异常为{}" ,httpRequest.getRemoteAddr(),ee.getMessage());
			httpResponse.setStatus(Constants.PROTOCOL_DATA_EXCEPTION);
		}
			
			
			
		
	}

	
	
	protected abstract ServletResponse encrypt(HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws EncryptException ;

	protected abstract ServletRequest decrypt(HttpServletRequest httpRequest) throws DecryptException ;

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

	

	

}
