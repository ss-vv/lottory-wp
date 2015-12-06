package com.unison.lottery.api.getverifycode.bo;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.security.NoSuchAlgorithmException;

import javax.crypto.NoSuchPaddingException;

import static org.junit.Assert.assertTrue;




import org.apache.commons.lang.StringUtils;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.api.bindIDCard.bo.BindIDCardBO;
import com.unison.lottery.api.framework.utils.MD5Utils;
import com.unison.lottery.api.login.bo.LoginBO;
import com.unison.lottery.api.model.User;

import com.unison.lottery.api.protocol.response.model.BindIDCardResponse;
import com.unison.lottery.api.protocol.response.model.GetVerifyCodeResponse;
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.protocol.status.ClientStatusCode;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.utils.CryptoUitls;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-getVerifyCode-service-spring.xml")

public class GetVerifyCodeBOTest extends GetVerifyCodeBOTestBase {

	@Autowired
	private GetVerifyCodeBO getVerifyCodeBO;

	@Test
	public void whenPhoneNumberIsNotBlankThenSucc() throws Exception  {
		User user=new User();
		user.setId("75");
		user.setValidId("6fed04e4-cbd1-40fe-9249-64e6ed4568ac");
		
		String phoneNumber="18601944885";
		String type="bindMobile";
		GetVerifyCodeResponse getVerifyCodeResponse=getVerifyCodeBO.getVerifyCode(phoneNumber,user,type);
		
		assertTrue(null!=getVerifyCodeResponse);
		ReturnStatus returnStatus=getVerifyCodeResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.GetVerifyCode.SUCC,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenPhoneNumberIsBlankThenFail() throws Exception  {
		User user=new User();
		user.setId("75");
		
		String phoneNumber="";
		String type="bindMobile";
		GetVerifyCodeResponse getVerifyCodeResponse=getVerifyCodeBO.getVerifyCode(phoneNumber,user,type);
		
		assertTrue(null!=getVerifyCodeResponse);
		ReturnStatus returnStatus=getVerifyCodeResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.GetVerifyCode.FAIL_PHONE_NUMBER_IS_BLANK,returnStatus.getStatusCodeForClient());
		
		
	}
	
	
	
	
	
	
	
	

}
