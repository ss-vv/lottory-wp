package com.unison.lottery.api.login.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ConfigurableWebApplicationContext;






import com.unison.lottery.api.login.service.ILoginService;
import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.common.VONames;
import com.unison.lottery.api.protocol.response.service.IOutputLoginFailResponseService;


/**
 * Servlet Filter implementation class LoginFilter
 */

public abstract class AbstractLoginFilter implements Filter {

	protected ConfigurableWebApplicationContext ctx;
	protected ILoginService loginService;
	protected IOutputLoginFailResponseService outputLoginFailResponseService;
	private static final Logger logger=LoggerFactory.getLogger(AbstractLoginFilter.class);
    /**
     * Default constructor. 
     */
    public AbstractLoginFilter() {
        
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
		User user=(User) request.getAttribute(VONames.USER_VO_NAME);
		User userResult=loginService.tryGetAsAuthenticate(user);
		if(null!=userResult){
			logger.info("用户合法："+userResult);
			loginService.updateExpiredTime(userResult);
			request.setAttribute(VONames.USER_AFTER_LOGIN_VO_NAME, userResult);
			chain.doFilter(request, response);
		}
		else{
			logger.info("用户不合法："+user);
			outputResponse(request, response);
			return ;
		}
		
	}

	private  void outputResponse(ServletRequest request, ServletResponse response) {
		outputLoginFailResponseService.outputResponse(request,response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
		
	}

}
