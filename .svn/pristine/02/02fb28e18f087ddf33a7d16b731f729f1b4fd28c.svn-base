package com.unison.lottery.itf.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unison.lottery.itf.INotifyProcessor;


public class AnRuiOrderNotifyServlet extends HttpServlet {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 */
	private static final long serialVersionUID = -5912422876029177092L;
	
	private ConfigurableWebApplicationContext ctx;

	public AnRuiOrderNotifyServlet() {
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
				
		INotifyProcessor processor=(INotifyProcessor) ctx.getBean("anRuiOrderResultProcessor");	
		request.setCharacterEncoding("GB2312");
		String printResualt=request.getParameter("printresualt");
		logger.debug("printResualt={}",printResualt);
		Map<String,?> resultMap = processor.process(printResualt);
		
		if(resultMap != null){
			logger.debug("Return Result: {}", resultMap);
			response.getWriter().print(0);
		}
		else{
			response.getWriter().print(-1);
		}
		response.getWriter().flush();
	}
}
