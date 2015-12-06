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
import com.unison.lottery.itf.NotifyProcessor;
import com.unison.lottery.itf.ProcessResult;

/**
 * 接收通知的action。
 * 
 * @author Yang Bo
 */
public class ReceiveAction extends ActionSupport implements ServletRequestAware{
	private static final long serialVersionUID = -7680328672949315965L;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	// request info
	private HttpServletRequest request;
	
	// 处理通知的结果，用于反馈给接口
	private ProcessResult<?> result;
	
	// 解析http内容的解析器
	private NotifyProcessor processor;
	
	
	@Override
	public String execute() throws Exception {
		String msg = getRequestFromBody();
		result = processor.process(msg);
		logger.debug("Return Result: {}", result);
		return SUCCESS;
	}

	private String getRequestFromBody() {
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

	public ProcessResult<?> getResult() {
		return result;
	}

	public NotifyProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(NotifyProcessor processor) {
		this.processor = processor;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}
