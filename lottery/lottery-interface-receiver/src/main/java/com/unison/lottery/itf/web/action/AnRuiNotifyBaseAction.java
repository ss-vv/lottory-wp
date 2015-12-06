package com.unison.lottery.itf.web.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

public class AnRuiNotifyBaseAction extends ActionSupport implements ServletRequestAware{

	private static final long serialVersionUID = -5977961740791723697L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	private HttpServletRequest request;
	
	protected String getRequestFromBody() {
		String msg = null;
		try {
			InputStreamReader input = new InputStreamReader(request.getInputStream(), "UTF-8");
			BufferedReader reader = new BufferedReader(input);
			int readed = -1;
			StringWriter writer = new StringWriter();
			while((readed=reader.read())!=-1){
				writer.write(readed);
			}
			msg = writer.toString();
			logger.debug("Received Notification: {}", msg);
			
		
		} catch (IOException e) {
			logger.error("Read request body failed!", e);
		}
		return msg;
	}
	
	

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public HttpServletRequest getRequest() {
		return request;
	}
	
	
}
