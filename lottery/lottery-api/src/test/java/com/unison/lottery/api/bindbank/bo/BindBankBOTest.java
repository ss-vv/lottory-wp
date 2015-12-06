package com.unison.lottery.api.bindbank.bo;

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

import com.unison.lottery.api.protocol.response.model.BindBankResponse;
import com.unison.lottery.api.protocol.response.model.BindIDCardResponse;
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.protocol.status.ClientStatusCode;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.commons.data.Account;
import com.xhcms.lottery.utils.CryptoUitls;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-bindBank-service-spring.xml")

public class BindBankBOTest extends BindBankBOTestBase {

	@Autowired
	private BindBankBO bindBankBO;

	@Test
	public void whenNotBindAndWithdrawPasswordNotBlankThenBindSucc() throws Exception  {
		User user=new User();
		user.setId("75");
		
		Account account=new Account();
		account.setAccountNumber("11111111111");
		account.setBank("中国工商银行北京市分行");
		account.setCity("北京");
		account.setProvince("北京");
		account.setPassword("testtest");
		
		BindBankResponse bindBankResponse=bindBankBO.bindBankForUser(account, user);
		
		assertTrue(null!=bindBankResponse);
		ReturnStatus returnStatus=bindBankResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.BindBank.SUCC,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenNotBindAndWithdrawPasswordIsBlankThenBindFail() throws Exception  {
		User user=new User();
		user.setId("6");
		
		Account account=new Account();
		account.setAccountNumber("11111111111");
		account.setBank("中国工商银行北京市分行");
		account.setCity("北京");
		account.setProvince("北京");
		account.setPassword("");
		
		BindBankResponse bindBankResponse=bindBankBO.bindBankForUser(account, user);
		
		assertTrue(null!=bindBankResponse);
		ReturnStatus returnStatus=bindBankResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.BindBank.FAIL_WITHDRAW_PASSWORD_IS_BLANK,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenHaveBeenBindedAndWithdrawPasswordIsRightThenBindSucc() throws Exception  {
		User user=new User();
		user.setId("2809");
		
		Account account=new Account();
		account.setAccountNumber("11111111111");
		account.setBank("中国工商银行北京市分行");
		account.setCity("北京");
		account.setProvince("北京");
		account.setPassword("123456");
		
		BindBankResponse bindBankResponse=bindBankBO.bindBankForUser(account, user);
		
		assertTrue(null!=bindBankResponse);
		ReturnStatus returnStatus=bindBankResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.BindBank.SUCC,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenHaveBeenBindedAndWithdrawPasswordIsNotRightThenBindFail() throws Exception  {
		User user=new User();
		user.setId("2809");
		
		Account account=new Account();
		account.setAccountNumber("11111111111");
		account.setBank("中国工商银行北京市分行");
		account.setCity("北京");
		account.setProvince("北京");
		account.setPassword("1234567");
		BindBankResponse bindBankResponse=bindBankBO.bindBankForUser(account, user);
		
		assertTrue(null!=bindBankResponse);
		ReturnStatus returnStatus=bindBankResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.BindBank.FAIL_WITHDRAW_PASSWORD_IS_WRONG,returnStatus.getStatusCodeForClient());
		
		
	}
	
	
	
	
	
	

}
