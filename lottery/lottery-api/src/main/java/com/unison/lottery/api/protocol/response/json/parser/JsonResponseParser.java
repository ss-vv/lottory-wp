package com.unison.lottery.api.protocol.response.json.parser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.protocol.response.AbstractResponseParserAssembleByMethodParser;
import com.unison.lottery.api.protocol.response.json.JsonResponseUtil;
import com.unison.lottery.api.protocol.response.model.Response;

public class JsonResponseParser extends AbstractResponseParserAssembleByMethodParser {
	static final Logger logger=LoggerFactory.getLogger(JsonResponseParser.class);

	protected void outputResponse(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Response myResponse) {
		try {
			JsonResponseUtil.outputResponse(httpRequest, httpResponse, myResponse);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("发送响应信息时出现异常=" + e.getMessage());
		}
		
	}
}
