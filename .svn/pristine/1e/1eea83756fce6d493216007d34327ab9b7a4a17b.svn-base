package com.unison.lottery.api.registe.bo;

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

import com.unison.lottery.api.framework.utils.MD5Utils;
import com.unison.lottery.api.login.bo.LoginBO;
import com.unison.lottery.api.model.User;

import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.protocol.status.ClientStatusCode;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.utils.CryptoUitls;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-registe-service-spring.xml")

public class RegisteBOTest extends RegisteBOTestBase {

	@Autowired
	private RegisteBO registeBO;

	@Test
	public void whenUserNameIsNewAndPasswordRealNamePhoneNumberIsNotBlankThenRegisteSucc() throws Exception  {
		User user=new User();
		user.setLoginName("testUser123");
		user.setPassword("testUser123");
		user.setRealName("测试用户");
		user.setPhoneNumber("11111111111");
		user.setChannel("客户端渠道1");
		RegisteResponse registeResponse=registeBO.regist(user);
		
		assertTrue(null!=registeResponse);
		ReturnStatus returnStatus=registeResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Registe.SUCC,returnStatus.getStatusCodeForClient());
		assertTrue(StringUtils.isNotBlank(registeResponse.getValidId()));
		
	}
	
	@Test
	public void whenUserNameIsBlankOrPasswordIsBlankThenRegisteFail() throws Exception  {
		User user=new User();
		user.setLoginName("");
		user.setPassword("");
		user.setRealName("测试用户");
		user.setPhoneNumber("11111111111");
		user.setChannel("客户端渠道1");
		RegisteResponse registeResponse=registeBO.regist(user);
		
		assertTrue(null!=registeResponse);
		ReturnStatus returnStatus=registeResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Registe.FAIL_PASSWORD_AND_USERNAME_SHOULD_NOT_BLANK,returnStatus.getStatusCodeForClient());
		assertTrue(StringUtils.isBlank(registeResponse.getValidId()));
		
	}
	
	@Test
	public void whenRealNameOrPhoneNumberIsBlankThenRegisteFail() throws Exception  {
		User user=new User();
		user.setLoginName("testUser123");
		user.setPassword("testUser123");
		user.setRealName("");
		user.setPhoneNumber("");
		user.setChannel("客户端渠道1");
		RegisteResponse registeResponse=registeBO.regist(user);
		
		assertTrue(null!=registeResponse);
		ReturnStatus returnStatus=registeResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Registe.FAIL_REALNAME_AND_PHONENUMBER_SHOULD_NOT_BLANK,returnStatus.getStatusCodeForClient());
		assertTrue(StringUtils.isBlank(registeResponse.getValidId()));
		
	}
	
	@Test
	public void whenNotNewUserThenRegisteFail() throws Exception  {
		User user=new User();
		user.setLoginName("testUser");
		user.setPassword("testUser123");
		user.setRealName("");
		user.setPhoneNumber("");
		user.setChannel("客户端渠道1");
		RegisteResponse registeResponse=registeBO.regist(user);
		
		assertTrue(null!=registeResponse);
		ReturnStatus returnStatus=registeResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Registe.FAIL_NOT_NEW_USER,returnStatus.getStatusCodeForClient());
		assertTrue(StringUtils.isBlank(registeResponse.getValidId()));
		
	}
	
	
	
	
	
	

}
