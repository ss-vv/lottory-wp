package com.unison.lottery.api.callBack;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.unison.lottery.api.callBack.bo.MakeWinAndLiveScoresBO;
import com.unison.lottery.api.callBack.model.LiveScores;
import com.unison.lottery.api.framework.utils.ProtocolUtils;

@WebServlet("/syncLiveScores")
public class LiveScoresServlet  extends HttpServlet{
	//比分直播推送

	private static final long serialVersionUID = 1L;
	
	Logger logger = LoggerFactory.getLogger(LiveScoresServlet.class);

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
		MakeWinAndLiveScoresBO makeWinAndLiveScoresBO = (MakeWinAndLiveScoresBO) ctx.getBean("makeWinAndLiveScoresBO");
		PrintWriter writer =resp.getWriter();
		String msg = null;	
		String orignalStr = getOrignalRequest(req);
		ObjectMapper mapper = new ObjectMapper();
		LiveScores liveScores = mapper.readValue(orignalStr, LiveScores.class);
		if (makeWinAndLiveScoresBO.makeWinAndLiveScoresParams2Groups(liveScores)) {
			msg="ok";
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
	
	private String getOrignalRequest(HttpServletRequest request) throws IOException {
		ServletInputStream orignalStream=request.getInputStream();
		String orignalRequest;
		BufferedInputStream bis=new BufferedInputStream(orignalStream);
		orignalRequest=ProtocolUtils.readStrings(bis);
		logger.debug("orignalRequest={}",orignalRequest);
		return orignalRequest;
	}
}
