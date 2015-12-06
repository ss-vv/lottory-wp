package com.unison.lottery.api.framework.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


import com.unison.lottery.api.protocol.common.Constants;
import com.unison.lottery.api.protocol.response.model.HaveReturnStatusResponse;


public abstract class AbstractProcessServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2435513391784777562L;
	/**
	 * 
	 */
	protected ConfigurableWebApplicationContext ctx;

	public AbstractProcessServlet() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ctx = (ConfigurableWebApplicationContext) WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
	
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
				doPost(request,response);
			}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
				HaveReturnStatusResponse returnResponse = process(request);
				handleReturnResponse(request, returnResponse);
				
			}

	private void handleReturnResponse(HttpServletRequest request, HaveReturnStatusResponse processResponse) {
		if(null!=processResponse){
			request.setAttribute(getResponseVOName(), processResponse);
		}
		request.setAttribute(Constants.METHOD_RESPONSE_NAME_KEY, getMethodName());
	}

	protected abstract String getMethodName() ;

	protected abstract String getResponseVOName() ;

	protected abstract HaveReturnStatusResponse process(HttpServletRequest request) ;

}