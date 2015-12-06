package com.xhcms.lottery.mc.sms;

import static org.junit.Assert.*;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.xhcms.lottery.mc.sms.platform.AspireSmsPlatform;
import com.xhcms.lottery.mc.sms.platform.AspireSmsPlatformRequest;
import com.xhcms.lottery.mc.sms.platform.AspireSmsPlatformResult;

public class AspireSmsPlatformTest {
	
	@Test
	public void testSendSms(){
		AspireSmsPlatform aspireSmsPlatform=new AspireSmsPlatform();
		aspireSmsPlatform.setUserId("030000008");
		aspireSmsPlatform.setPassword("aaa@111");
		aspireSmsPlatform.setRequestUrl("http://111.13.19.27/smsbill/mt");
		aspireSmsPlatform.setTemplateId("030000008_001");
		aspireSmsPlatform.setSignature("【大V彩】");
		String phone="18601944885,13811073816";
		String port=null;
		String data="123456";
		AspireSmsPlatformResult result = aspireSmsPlatform.send(phone,port,data);
		System.out.println(result);
		assertTrue(result.getRetCode()==1000);
	}
	
	@Test
	public void testParseRequestToXml(){
		AspireSmsPlatformRequest request=new AspireSmsPlatformRequest();
		request.setData("20140303|50|100|80|150");
		request.setPassword("AaBb2014");
		request.setPhone("13604435733,13945335434,13301113434,18688337443,18999210453");
		request.setPort("80");
		request.setSignature("【招商银行】");
		request.setTemplateId("001");
		request.setUserId("030610655");
		String xml=request.toXml();
		System.out.println(xml);
		String expectedXml = 
		"<request>"
		+"<userId>030610655</userId>"
		+"<password>AaBb2014</password>"
		+"<templateId>001</templateId>"
		+"<phone>13604435733,13945335434,13301113434,18688337443,18999210453</phone>"
		+"<port>80</port>"
		+"<data>20140303|50|100|80|150</data>"
		+"<signature>【招商银行】</signature>"
		+"</request>";

		assertTrue(StringUtils.equals(xml, expectedXml));
	}

}
