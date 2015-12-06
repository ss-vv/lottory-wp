package com.unison.lottery.api.framework.filter.security.json;

import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unison.lottery.api.framework.filter.AbstractProtocolFilterAssembleByRequestParserAndResponseParserAndLogManager;
import com.unison.lottery.api.framework.log.ILogManager;
import com.unison.lottery.api.framework.request.IRequestParser;
import com.unison.lottery.api.framework.response.IResponseParser;

public class JsonProtocolFilter extends
		AbstractProtocolFilterAssembleByRequestParserAndResponseParserAndLogManager {

	private ConfigurableWebApplicationContext ctx;
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		ctx = (ConfigurableWebApplicationContext) WebApplicationContextUtils
		.getRequiredWebApplicationContext(fConfig.getServletContext());
		this.requestParser=(IRequestParser) ctx.getBean("requestParserForJson");
		this.responseParser=(IResponseParser)ctx.getBean("responseParserForJson");
		this.logManager=(ILogManager)ctx.getBean("jsonLogManager");
	}
	
}
