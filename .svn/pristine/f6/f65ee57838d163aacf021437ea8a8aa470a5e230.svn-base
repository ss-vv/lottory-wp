package com.unison.lottery.api.protocol.response.xml.parser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.protocol.response.AbstractResponseParserAssembleByMethodParser;
import com.unison.lottery.api.protocol.response.model.Response;
import com.unison.lottery.api.protocol.response.util.XmlResponseUtil;






public  class XmlResponseParser extends AbstractResponseParserAssembleByMethodParser {
	
	static final Logger logger=LoggerFactory.getLogger(XmlResponseParser.class);

	protected void outputResponse(HttpServletRequest httpRequest,
			HttpServletResponse httpResponse, Response myResponse) {
		try {
			XmlResponseUtil.outputResponse(httpRequest, httpResponse, myResponse);
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("发送响应信息时出现异常=" + e.getMessage());
		}
		
	}

}
