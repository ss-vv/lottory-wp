package com.xhcms.lottery.mc.sms;

import org.junit.Test;

import com.xhcms.lottery.commons.event.SMSSendMessage;
import com.xhcms.lottery.mc.sms.client.SMSSendWithAspirePlatformClient;
import com.xhcms.lottery.mc.sms.handler.SMSSendWithAspireHandler;
import com.xhcms.lottery.mc.sms.platform.AspireSmsPlatform;

public class SmsSendHandlerTest {
	
	@Test
	public void testHandler(){
		SMSSendWithAspireHandler handler=new SMSSendWithAspireHandler();
		SMSSendWithAspirePlatformClient client=new SMSSendWithAspirePlatformClient();
		AspireSmsPlatform aspireSmsPlatform=new AspireSmsPlatform();
		aspireSmsPlatform.setUserId("030000008");
		aspireSmsPlatform.setPassword("aaa@111");
		aspireSmsPlatform.setRequestUrl("http://111.13.19.27/smsbill/mt");
		aspireSmsPlatform.setTemplateId("030000008_001");
		aspireSmsPlatform.setSignature("【大V彩】");
		client.setAspireSmsPlatform(aspireSmsPlatform);
		handler.setClient(client);
		SMSSendMessage message=new SMSSendMessage();
		message.setContent("abcdef");
		message.setMobile("18601944885");
		handler.handle(message);
	}

}
