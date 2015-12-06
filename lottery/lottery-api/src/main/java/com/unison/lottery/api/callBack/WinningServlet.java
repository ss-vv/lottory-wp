package com.unison.lottery.api.callBack;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unison.lottery.api.callBack.bo.MakeWinAndLiveScoresBO;
import com.unison.lottery.api.callBack.model.WinAndLiveScores;

@WebServlet("/syncWinningScores")
public class WinningServlet  extends HttpServlet{

	//中奖喜报推送

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Logger logger = LoggerFactory.getLogger(WinningServlet.class);

	protected ConfigurableWebApplicationContext ctx;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ctx = (ConfigurableWebApplicationContext) WebApplicationContextUtils
				.getRequiredWebApplicationContext(getServletContext());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Map<String, Object> map = makeParamsToMap(req); 
		MakeWinAndLiveScoresBO makeWinAndLiveScoresBO = (MakeWinAndLiveScoresBO) ctx.getBean("makeWinAndLiveScoresBO");
		PrintWriter writer =resp.getWriter();
		String msg = null;	
		String type = (String) map.get("type");
		String schemeId = (String) map.get("schemeId");
		WinAndLiveScores winAndLiveScores = new WinAndLiveScores();
		winAndLiveScores.type = type;
		if(StringUtils.isNotBlank(schemeId)){
			if(makeWinAndLiveScoresBO.makeWinParams2Groups(winAndLiveScores, schemeId)){
				msg="ok";
			} else {
				msg="error";
			}
		} else {
			msg = "error";
		}
		resp.setContentType("text/html;charset=utf8");
		writer.write(msg);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doPost(req, resp);
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> makeParamsToMap(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<String> e = (Enumeration<String>) request.getParameterNames();
		while (e.hasMoreElements()) {
			String parName = (String) e.nextElement();
			Object value = request.getParameter(parName);
			map.put(parName, value);
		}
		return map;
	}

}
