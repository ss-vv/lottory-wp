package com.unison.lottery.api.login.filter;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unison.lottery.api.login.service.ILoginService;
import com.unison.lottery.api.protocol.response.service.IOutputLoginFailResponseService;

public class LoginWithJsonResponseFilter extends AbstractLoginFilter {

	public LoginWithJsonResponseFilter() {
        super();
    }

	

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ctx = (ConfigurableWebApplicationContext) WebApplicationContextUtils
		.getRequiredWebApplicationContext(fConfig.getServletContext());
		loginService=(ILoginService) ctx.getBean("loginService");
		outputLoginFailResponseService=(IOutputLoginFailResponseService) ctx.getBean("outputLoginFailXmlResponseService");
	}
	
}
