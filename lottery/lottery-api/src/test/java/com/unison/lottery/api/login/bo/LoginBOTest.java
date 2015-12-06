package com.unison.lottery.api.login.bo;

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
import com.unison.lottery.api.protocol.status.ClientStatusCode;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.utils.CryptoUitls;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-login-service-spring.xml")

public class LoginBOTest extends LoginBOTestBase {

	@Autowired
	private LoginBO loginBO;

	@Test
	public void whenUserNameIsCorrectAndPasswordWithMD5IsCorrectThenLoginSucc() throws Exception  {
		User user=new User();
		user.setLoginName("testUser");
		user.setPassword("testUser");
		String loginName=user.getLoginName();
		LoginResponse loginResponse=loginBO.loginWithNameAndPassword(user);
		assertTrue(null!=loginResponse);
		ReturnStatus returnStatus=loginResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Login.SUCC,returnStatus.getStatusCodeForClient());
		assertTrue(null!=loginResponse.getUser());
		assertTrue(StringUtils.isNotBlank(loginResponse.getUser().getValidId()));
		assertTrue(null!=loginResponse.getAccount());
		
	}
	
	@Test
	public void whenUserNameOrPasswordBlankThenLoginFail() throws Exception  {
		User user=new User();
		user.setLoginName("");
		user.setPassword("");
		String loginName=user.getLoginName();
		LoginResponse loginResponse=loginBO.loginWithNameAndPassword(user);
		assertTrue(null!=loginResponse);
		ReturnStatus returnStatus=loginResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Login.FAIL_NAME_OR_PASSWORD_BLANK,returnStatus.getStatusCodeForClient());
		assertTrue(null==loginResponse.getUser());
		
	}
	
	@Test
	public void whenUserNameIsWrongThenLoginFail() throws Exception  {
		User user=new User();
		user.setLoginName("test");
		user.setPassword("test");
		String loginName=user.getLoginName();
		LoginResponse loginResponse=loginBO.loginWithNameAndPassword(user);
		assertTrue(null!=loginResponse);
		ReturnStatus returnStatus=loginResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Login.FAIL_LOGIN_NAME_WRONG,returnStatus.getStatusCodeForClient());
		assertTrue(null==loginResponse.getUser());
		
	}
	
	@Test
	public void whenPasswordIsWrongThenLoginFail() throws Exception  {
		User user=new User();
		user.setLoginName("c0yne");
		user.setPassword("c0yne");
		String loginName=user.getLoginName();
		LoginResponse loginResponse=loginBO.loginWithNameAndPassword(user);
		assertTrue(null!=loginResponse);
		ReturnStatus returnStatus=loginResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Login.FAIL_PASSWORD_WRONG,returnStatus.getStatusCodeForClient());
		assertTrue(null==loginResponse.getUser());
		
	}
	
	@Test
	public void whenLoginSuccThenValidIdShouldChange() throws Exception  {
		User user=new User();
		user.setLoginName("testUser");
		user.setPassword("testUser");
		String loginName=user.getLoginName();
		
		//第一次成功登陆
		LoginResponse loginResponse=loginBO.loginWithNameAndPassword(user);
		assertTrue(null!=loginResponse);
		ReturnStatus returnStatus=loginResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Login.SUCC,returnStatus.getStatusCodeForClient());
		assertTrue(null!=loginResponse.getUser());
		String validId1=loginResponse.getUser().getValidId();
		assertTrue(StringUtils.isNotBlank(validId1));
		assertTrue(null!=loginResponse.getAccount());
		System.out.println("validId1="+validId1);
		
		//第二次成功登陆
		loginResponse=loginBO.loginWithNameAndPassword(user);
		assertTrue(null!=loginResponse);
		returnStatus=loginResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Login.SUCC,returnStatus.getStatusCodeForClient());
		assertTrue(null!=loginResponse.getUser());
		String validId2=loginResponse.getUser().getValidId();
		assertTrue(StringUtils.isNotBlank(validId2));
		assertTrue(null!=loginResponse.getAccount());
		System.out.println("validId2="+validId2);
		
		//两次登陆，validId应该不一样
		assertFalse(validId1.equals(validId2));
		
	}
	
	
	
	

}
