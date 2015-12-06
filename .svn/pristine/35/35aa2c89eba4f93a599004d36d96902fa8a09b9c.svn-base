package com.xhcms.lottery.mc.sms.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.event.SMSSendMessage;
import com.xhcms.lottery.mc.sms.SMSSendClientInterface;
import com.xhcms.lottery.mc.sms.SMSSendRequest;
import com.xhcms.lottery.mc.sms.SMSSendResult;

public class SMSSendWithAspireHandler implements MessageHandler<SMSSendMessage> {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -2246070898240275767L;

	private static final Logger logger = LoggerFactory.getLogger(SMSSendWithAspireHandler.class);
	
	@Autowired
	private SMSSendClientInterface client;

	@Override
	public void handle(SMSSendMessage smsSendMessage) {
		if(null!=smsSendMessage){
			SMSSendRequest smsSendRequest=new SMSSendRequest();
			List<String> destPhones=new ArrayList<String>();
			destPhones.add(smsSendMessage.getMobile());
			smsSendRequest.setDestPhones(destPhones);
			Map<String, Object> params=new HashMap<String, Object>();
			params.put(SMSSendRequest.VERIFY_CODE_KEY, smsSendMessage.getContent());
			smsSendRequest.setParams(params);
			SMSSendResult sendResult = client.sendSMS(smsSendRequest);
			logger.debug("发送短信对象:{},结果为:{}",smsSendMessage,sendResult);
		}
		
		
	}

	public SMSSendClientInterface getClient() {
		return client;
	}

	public void setClient(SMSSendClientInterface client) {
		this.client = client;
	}

}
