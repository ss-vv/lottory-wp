package com.xhcms.lottery.mc.sms;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import com.xhcms.lottery.mc.sms.client.SMSSendWithAspirePlatformClient;
import com.xhcms.lottery.mc.sms.platform.AspireSmsPlatform;

public class SMSSendWithAspirePlatformClientTest {
	
	private SMSSendClientInterface client;
	

	@Before
	public void setUp(){
		client=new SMSSendWithAspirePlatformClient();
		AspireSmsPlatform aspireSmsPlatform=new AspireSmsPlatform();
		aspireSmsPlatform.setUserId("030000008");
		aspireSmsPlatform.setPassword("aaa@111");
		aspireSmsPlatform.setRequestUrl("http://111.13.19.27/smsbill/mt");
		aspireSmsPlatform.setTemplateId("030000008_001");
		aspireSmsPlatform.setSignature("【大V彩】");
		SMSSendWithAspirePlatformClient SMSSendWithAspirePlatformClient=(com.xhcms.lottery.mc.sms.client.SMSSendWithAspirePlatformClient) client;
		SMSSendWithAspirePlatformClient.setAspireSmsPlatform(aspireSmsPlatform);
	}
	
	@Test
	public void testSendSMS(){
		
		SMSSendRequest smsSendRequest=new SMSSendRequest();
		List<String> destPhones=new ArrayList<String>();
		destPhones.add("18601944885");
		smsSendRequest.setDestPhones(destPhones);
		Map<String, Object> params=new HashMap<String,Object>();
		params.put(SMSSendRequest.VERIFY_CODE_KEY, "123456");
		smsSendRequest.setParams(params);
		SMSSendResult sendResult =client.sendSMS(smsSendRequest);
		System.out.println(sendResult);
		assertTrue(sendResult.getStatus()==SMSSendResultStatus.SUCC);
		assertTrue(StringUtils.equals(sendResult.getMessage(), SMSSendResultMessage.SUCC));
		assertTrue(null==sendResult.getFailDestPhones()||sendResult.getFailDestPhones().isEmpty());
		assertTrue(null==sendResult.getWrongFormatDestPhones()||sendResult.getWrongFormatDestPhones().isEmpty());
		assertTrue(StringUtils.equals(sendResult.getSuccDestPhones().get(0), "18601944885"));
	}
	
	@Test
	public void testSendSMSWithNullSMSSendRequest(){
		SMSSendRequest smsSendRequest=null;
		SMSSendResult sendResult =client.sendSMS(smsSendRequest);
		System.out.println(sendResult);
		assertTrue(sendResult.getStatus()==SMSSendResultStatus.FAIL);
		assertTrue(StringUtils.equals(sendResult.getMessage(), SMSSendResultMessage.FAIL));
		assertTrue(null==sendResult.getFailDestPhones()||sendResult.getFailDestPhones().isEmpty());
		assertTrue(null==sendResult.getWrongFormatDestPhones()||sendResult.getWrongFormatDestPhones().isEmpty());
		assertTrue(null==sendResult.getSuccDestPhones()||sendResult.getSuccDestPhones().isEmpty());
	}
	
	@Test
	public void testSendSMSToManyPhones(){
		SMSSendRequest smsSendRequest=new SMSSendRequest();
		List<String> destPhones=new ArrayList<String>();
		destPhones.add("18601944885");
		destPhones.add("13811073816");
		smsSendRequest.setDestPhones(destPhones);
		Map<String, Object> params=new HashMap<String,Object>();
		params.put(SMSSendRequest.VERIFY_CODE_KEY, "123456");
		smsSendRequest.setParams(params);
		SMSSendResult sendResult = client.sendSMS(smsSendRequest);
		System.out.println(sendResult);
		assertTrue(sendResult.getStatus()==SMSSendResultStatus.SUCC);
		assertTrue(StringUtils.equals(sendResult.getMessage(), SMSSendResultMessage.SUCC));
		assertTrue(null==sendResult.getFailDestPhones()||sendResult.getFailDestPhones().isEmpty());
		assertTrue(null==sendResult.getWrongFormatDestPhones()||sendResult.getWrongFormatDestPhones().isEmpty());
		assertTrue(StringUtils.equals(sendResult.getSuccDestPhones().get(0), "18601944885"));
		assertTrue(StringUtils.equals(sendResult.getSuccDestPhones().get(1), "13811073816"));
	}
	
	@Test
	public void testSendSMSWithEmptyParams(){
		SMSSendRequest smsSendRequest=new SMSSendRequest();
		List<String> destPhones=new ArrayList<String>();
		destPhones.add("18601944885");
		destPhones.add("13811073816");
		smsSendRequest.setDestPhones(destPhones);
		Map<String, Object> params=new HashMap<String,Object>();
		smsSendRequest.setParams(params);
		SMSSendResult sendResult = client.sendSMS(smsSendRequest);
		System.out.println(sendResult);
		assertTrue(sendResult.getStatus()==SMSSendResultStatus.EMPTY_PARAMS);
		assertTrue(StringUtils.equals(sendResult.getMessage(), SMSSendResultMessage.EMPTY_PARAMS));
		assertTrue(null==sendResult.getSuccDestPhones()||sendResult.getSuccDestPhones().isEmpty());
		assertTrue(null==sendResult.getWrongFormatDestPhones()||sendResult.getWrongFormatDestPhones().isEmpty());
		assertTrue(StringUtils.equals(sendResult.getFailDestPhones().get(0), "18601944885"));
		assertTrue(StringUtils.equals(sendResult.getFailDestPhones().get(1), "13811073816"));
	}
	
	@Test
	public void testSendSMSWithEmptyDestPhones(){
		SMSSendRequest smsSendRequest=new SMSSendRequest();
		List<String> destPhones=new ArrayList<String>();
		smsSendRequest.setDestPhones(destPhones);
		Map<String, Object> params=new HashMap<String,Object>();
		smsSendRequest.setParams(params);
		params.put(SMSSendRequest.VERIFY_CODE_KEY, "123456");
		SMSSendResult sendResult = client.sendSMS(smsSendRequest);
		System.out.println(sendResult);
		assertTrue(sendResult.getStatus()==SMSSendResultStatus.EMPTY_DEST_PHONES);
		assertTrue(StringUtils.equals(sendResult.getMessage(), SMSSendResultMessage.EMPTY_DEST_PHONES));
		assertTrue(null==sendResult.getFailDestPhones()||sendResult.getFailDestPhones().isEmpty());
		assertTrue(null==sendResult.getSuccDestPhones()||sendResult.getSuccDestPhones().isEmpty());
		assertTrue(null==sendResult.getWrongFormatDestPhones()||sendResult.getWrongFormatDestPhones().isEmpty());
	}
	
	@Test
	public void testSendSMSWithWrongFormatPhones(){
		SMSSendRequest smsSendRequest=new SMSSendRequest();
		List<String> destPhones=new ArrayList<String>();
		destPhones.add("18601944885");
		destPhones.add("1381107381");
		destPhones.add("+8613811073816");
		smsSendRequest.setDestPhones(destPhones);
		Map<String, Object> params=new HashMap<String,Object>();
		params.put(SMSSendRequest.VERIFY_CODE_KEY, "123456");
		smsSendRequest.setParams(params);
		
		SMSSendResult sendResult = client.sendSMS(smsSendRequest);
		System.out.println(sendResult);
		assertTrue(sendResult.getStatus()==SMSSendResultStatus.SUCC);
		assertTrue(StringUtils.equals(sendResult.getMessage(), SMSSendResultMessage.SUCC));
		assertTrue(null==sendResult.getFailDestPhones()||sendResult.getFailDestPhones().isEmpty());
		assertTrue(StringUtils.equals(sendResult.getWrongFormatDestPhones().get(0), "1381107381"));
		assertTrue(null!=sendResult.getSuccDestPhones());
		assertTrue(StringUtils.equals(sendResult.getSuccDestPhones().get(0), "18601944885"));
		assertTrue(StringUtils.equals(sendResult.getSuccDestPhones().get(1), "13811073816"));
	}
	
	@Test
	public void testSendSMSWithAllWrongFormatPhones(){
		SMSSendRequest smsSendRequest=new SMSSendRequest();
		List<String> destPhones=new ArrayList<String>();
		destPhones.add("186019448856");
		destPhones.add("1381107381");
		smsSendRequest.setDestPhones(destPhones);
		Map<String, Object> params=new HashMap<String,Object>();
		params.put(SMSSendRequest.VERIFY_CODE_KEY, "123456");
		smsSendRequest.setParams(params);
		
		SMSSendResult sendResult = client.sendSMS(smsSendRequest);
		System.out.println(sendResult);
		assertTrue(sendResult.getStatus()==SMSSendResultStatus.FAIL);
		assertTrue(StringUtils.equals(sendResult.getMessage(), SMSSendResultMessage.FAIL));
		assertTrue(null==sendResult.getFailDestPhones()||sendResult.getFailDestPhones().isEmpty());
		assertTrue(null==sendResult.getSuccDestPhones()||sendResult.getSuccDestPhones().isEmpty());
		assertTrue(StringUtils.equals(sendResult.getWrongFormatDestPhones().get(0), "186019448856"));
		assertTrue(StringUtils.equals(sendResult.getWrongFormatDestPhones().get(1), "1381107381"));
		
	}
	
	@Test
	public void testSendSMSWithoutVerifyCode(){
		SMSSendRequest smsSendRequest=new SMSSendRequest();
		List<String> destPhones=new ArrayList<String>();
		destPhones.add("18601944885");
		destPhones.add("13811073816");
		smsSendRequest.setDestPhones(destPhones);
		Map<String, Object> params=new HashMap<String,Object>();
		params.put("test", "test");
		smsSendRequest.setParams(params);
		SMSSendResult sendResult = client.sendSMS(smsSendRequest);
		System.out.println(sendResult);
		assertTrue(sendResult.getStatus()==SMSSendResultStatus.NEED_VERIFY_CODE_PARAM);
		assertTrue(StringUtils.equals(sendResult.getMessage(), SMSSendResultMessage.NEED_VERIFY_CODE_PARAM));
		assertTrue(null==sendResult.getSuccDestPhones());
		assertTrue(null==sendResult.getWrongFormatDestPhones());
		assertTrue(StringUtils.equals(sendResult.getFailDestPhones().get(0), "18601944885"));
		assertTrue(StringUtils.equals(sendResult.getFailDestPhones().get(1), "13811073816"));
		
	}

}
