package com.unison.lottery.itf.web.action;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.unison.lottery.itf.INotifyProcessor;

public class AnRuiOrderNotifyAction extends AnRuiNotifyBaseAction {

	private static final long serialVersionUID = -5977961740791723697L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private INotifyProcessor processor;
	
	
	public INotifyProcessor getProcessor() {
		return processor;
	}

	public void setProcessor(INotifyProcessor processor) {
		this.processor = processor;
	}

	@Override
	public String execute() throws Exception {
		//String msg = getRequestFromBody();
		getRequest().setCharacterEncoding("GB2312");
		String printResualt=getRequest().getParameter("printresualt");
		byte[] bytes1 = printResualt.getBytes("iso8859-1");
		byte[] bytes2 = printResualt.getBytes("GB2312");
		int i=0;
		for(byte byteItem:bytes1){
			System.out.println("bytes1["+i+"]="+byteItem);
			i++;
		}
		int j=0;
		for(byte byteItem:bytes2){
			System.out.println("bytes2["+j+"]="+byteItem);
			j++;
		}
		
		printResualt=new String(printResualt.getBytes("iso8859-1"),"utf-8");
		logger.debug("printResualt={}",printResualt);
		Map<String,?> resultMap = processor.process(printResualt);
		
		if(resultMap != null){
			logger.debug("Return Result: {}", resultMap);
		}
		return SUCCESS;
	}


	
}

