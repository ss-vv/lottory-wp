package com.unison.lottery.api.bindmobile.bo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.BindMobileResponse;
import com.unison.lottery.api.protocol.status.ClientStatusCode;
import com.unison.lottery.api.protocol.status.ReturnStatus;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-bindMobile-service-spring.xml")

public class BindMobileBOTest extends BindMobileBOTestBase {

	@Autowired
	private BindMobileBO bindMobileBO;

	@Test
	public void whenPhoneNumberIsNotBlankAndNotBindToOtherAndVerifyCodeIsRightThenSucc() throws Exception  {
		User user=new User();
		user.setId("3905");
		
		String phoneNumber="18601944885";
		String verifyCode="qkSOze";
		BindMobileResponse bindMobileResponse=bindMobileBO.bindMobileForUser(phoneNumber, verifyCode, user);
		
		assertTrue(null!=bindMobileResponse);
		ReturnStatus returnStatus=bindMobileResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.BindMobile.SUCC,returnStatus.getStatusCodeForClient());
	}
	
	@Test
	public void whenPhoneNumberIsBlankThenFail() throws Exception  {
		User user=new User();
		user.setId("75");
		
		String phoneNumber="";
		String verifyCode="qkSOze";
		BindMobileResponse bindMobileResponse=bindMobileBO.bindMobileForUser(phoneNumber, verifyCode, user);
		
		assertTrue(null!=bindMobileResponse);
		ReturnStatus returnStatus=bindMobileResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.BindMobile.FAIL_PHONE_NUMBER_IS_BLANK,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenPhoneNumberIsNotBlankAndVerifyCodeIsWrongThenFail() throws Exception  {
		User user=new User();
		user.setId("3905");
		
		String phoneNumber="18601944885";
		String verifyCode="qkSOze2";
		BindMobileResponse bindMobileResponse=bindMobileBO.bindMobileForUser(phoneNumber, verifyCode, user);
		
		assertTrue(null!=bindMobileResponse);
		ReturnStatus returnStatus=bindMobileResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.BindMobile.FAIL_VERIFY_CODE_IS_WRONG,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenPhoneNumberIsBindToOtherThenFail() throws Exception  {
		User user=new User();
		user.setId("2");
		
		String phoneNumber="13811073816";
		String verifyCode="qkSOzg";
		BindMobileResponse bindMobileResponse=bindMobileBO.bindMobileForUser(phoneNumber, verifyCode, user);
		
		assertTrue(null!=bindMobileResponse);
		ReturnStatus returnStatus=bindMobileResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.BindMobile.FAIL_PHONE_NUMBER_IS_BIND_TO_OTHER,returnStatus.getStatusCodeForClient());
	}
	
	@Test
	public void whenPhoneNumberIsNotEqualToPhoneNumberOfVerifyCodeThenFail() throws Exception  {
		User user=new User();
		user.setId("3905");
		
		String phoneNumber="18601944886";
		String verifyCode="qkSOze";
		BindMobileResponse bindMobileResponse=bindMobileBO.bindMobileForUser(phoneNumber, verifyCode, user);
		
		assertTrue(null!=bindMobileResponse);
		ReturnStatus returnStatus=bindMobileResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.BindMobile.FAIL_PHONE_NUMBER_IS_WRONG,returnStatus.getStatusCodeForClient());
	}
}
