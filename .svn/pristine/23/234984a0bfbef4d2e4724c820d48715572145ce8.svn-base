package com.unison.lottery.api.protocol.response.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.api.protocol.response.model.Response;



public class XmlResponseUtil {
	
	private static final Logger logger=LoggerFactory.getLogger(XmlResponseUtil.class);
	
	public static void outputResponse(HttpServletRequest httpRequest, HttpServletResponse httpResponse, Response resultResponse) throws IOException{
		String xmlString = resultResponse.toXmlString();
		httpResponse.getWriter().print(xmlString);
		httpResponse.getWriter().flush();
		logger.debug("xmlString=" + xmlString);
	}

}
