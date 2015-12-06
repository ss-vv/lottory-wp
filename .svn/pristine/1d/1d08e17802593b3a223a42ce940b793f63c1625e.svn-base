package com.unison.lottery.api.modifypassword.bo;

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
import com.unison.lottery.api.protocol.response.model.BindMobileResponse;
import com.unison.lottery.api.protocol.response.model.GetVerifyCodeResponse;
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.response.model.ModifyPasswordResponse;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.protocol.status.ClientStatusCode;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.utils.CryptoUitls;
import com.xhcms.ucenter.lang.PasswordType;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-modifyPassword-service-spring.xml")

public class ModifyPasswordBOTest extends ModifyPasswordBOTestBase {

	@Autowired
	private ModifyPasswordBO bo;

	@Test
	public void whenNewPasswordIsNotBlankAndOldPasswordIsRightThenSucc() throws Exception  {
		User user=new User();
		user.setId("3905");
		
		String oldPassword="testUser";
		String newPassword="testUser2";
		ModifyPasswordResponse response=bo.modifyPasswordForUser(oldPassword, newPassword, user,PasswordType.LOGIN);
		
		assertTrue(null!=response);
		ReturnStatus returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.ModifyPassword.SUCC,returnStatus.getStatusCodeForClient());
		
		
		user.setId("3905");
		
		oldPassword="tixiantest";
		newPassword="tixiantest2";
		response=bo.modifyPasswordForUser(oldPassword, newPassword, user,PasswordType.WITHDRAW);
		
		assertTrue(null!=response);
		returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.ModifyPassword.SUCC,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenOldPasswordIsWrongThenFail() throws Exception  {
		User user=new User();
		user.setId("3905");
		
		String oldPassword="testUser2";
		String newPassword="testUser2";
		ModifyPasswordResponse response=bo.modifyPasswordForUser(oldPassword, newPassword, user,PasswordType.LOGIN);
		
		assertTrue(null!=response);
		ReturnStatus returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.ModifyPassword.FAIL_OLD_PASSWORD_IS_WRONG,returnStatus.getStatusCodeForClient());
		
		user.setId("3905");
		
		oldPassword="testUser2";
		newPassword="testUser2";
		response=bo.modifyPasswordForUser(oldPassword, newPassword, user,PasswordType.WITHDRAW);
		
		assertTrue(null!=response);
		returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.ModifyPassword.FAIL_OLD_PASSWORD_IS_WRONG,returnStatus.getStatusCodeForClient());
	}
	
	@Test
	public void whenOldPasswordIsRightAndNewPasswordIsBlankThenFail() throws Exception  {
		User user=new User();
		user.setId("3905");
		
		String oldPassword="testUser";
		String newPassword="";
		ModifyPasswordResponse response=bo.modifyPasswordForUser(oldPassword, newPassword, user,PasswordType.LOGIN);
		
		assertTrue(null!=response);
		ReturnStatus returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.ModifyPassword.FAIL_NEW_PASSWORD_IS_BLANK,returnStatus.getStatusCodeForClient());
		
		user.setId("3905");
		
		oldPassword="tixiantest";
		newPassword="";
		response=bo.modifyPasswordForUser(oldPassword, newPassword, user,PasswordType.WITHDRAW);
		
		assertTrue(null!=response);
		returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.ModifyPassword.FAIL_NEW_PASSWORD_IS_BLANK,returnStatus.getStatusCodeForClient());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
