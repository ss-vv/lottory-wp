package com.xhcms.lottery.alarm.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.alarm.service.SendSmsService;
import com.xhcms.lottery.commons.event.SMSSendMessage;

@Service
public class SendSmsServiceImpl implements SendSmsService {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
    private MessageSender messageSender;
	
	@Override
	public void send(String smsContent,Set<String> phonesNums) {
		SMSSendMessage sm = new SMSSendMessage();
		sm.setContent(smsContent);
		sm.setExt("00000");
		sm.setMsgId("12345");
		for (String p : phonesNums) {
			sm.setMobile(p);
			messageSender.send(sm);
		}
	}
}
