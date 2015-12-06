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

/**
 * 远程出票回调接口
 * @author Next
 *
 */
public class YuanChengChuPiaoNotifyServlet extends HttpServlet{

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static final long serialVersionUID = -5912426165152L;
	
	private ConfigurableWebApplicationContext ctx;

	public YuanChengChuPiaoNotifyServlet() {
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
				
		INotifyProcessor processor=(INotifyProcessor) ctx.getBean("");	//anRuiOrderResultProcessor
		request.setCharacterEncoding("GB2312");
		String _data=request.getParameter("_data");
		logger.debug("_data={}",_data);
		Map<String,?> resultMap = processor.process(_data);
		
		if(resultMap != null)
		{
			logger.debug("Return Result: {}", resultMap);
			response.getWriter().print(0);
		}
		else
		{
			response.getWriter().print(-1);
		}
		response.getWriter().flush();
	}
	
}
