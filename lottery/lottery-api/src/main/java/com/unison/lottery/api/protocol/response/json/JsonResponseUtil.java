package com.unison.lottery.api.protocol.response.json;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.protocol.response.model.Response;

public class JsonResponseUtil {

private static final Logger logger=LoggerFactory.getLogger(JsonResponseUtil.class);
	
	public static void outputResponse(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Response resultResponse) throws IOException{
		String jsonString = resultResponse.toJsonString(resultResponse);
		httpResponse.getWriter().print(jsonString);
		httpResponse.getWriter().flush();
		logger.debug("jsonResponseString=" + jsonString);
	}
}
