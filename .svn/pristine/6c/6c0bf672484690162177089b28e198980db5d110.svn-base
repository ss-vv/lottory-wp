package com.unison.lottery.api.withdraw.bo;

import static org.junit.Assert.*;


import java.math.BigDecimal;






import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.api.model.User;


import com.unison.lottery.api.protocol.response.model.WithdrawResponse;
import com.unison.lottery.api.protocol.status.ClientStatusCode;
import com.unison.lottery.api.protocol.status.ReturnStatus;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-withdraw-service-spring.xml")

public class WithdrawBOTest extends WithdrawBOTestBase {

	@Autowired
	private WithdrawBO bo;

	@Test
	public void whenWithdrawPasswordIsRightAndAmountIsBigEnoughAndBankIsBindThenSucc() throws Exception  {
		User user=new User();
		user.setId("3905");
		
		String withdrawPassword="tixiantest";
		BigDecimal amount=new BigDecimal(12);
		String realIP="127.0.0.1";
		WithdrawResponse response=bo.withdrawForUser(withdrawPassword, amount, realIP, user);
		
		assertTrue(null!=response);
		ReturnStatus returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Withdraw.SUCC,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenWithdrawPasswordIsWrongThenFail() throws Exception  {
		User user=new User();
		user.setId("3905");
		
		String withdrawPassword="testUser";
		BigDecimal amount=new BigDecimal(12);
		String realIP="127.0.0.1";
		WithdrawResponse response=bo.withdrawForUser(withdrawPassword, amount, realIP, user);
		
		assertTrue(null!=response);
		ReturnStatus returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Withdraw.FAIL_WITHDRAW_PASSWORD_IS_WRONG,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenBankNotBindThenFail() throws Exception  {
		User user=new User();
		user.setId("75");
		
		String withdrawPassword="testUser";
		BigDecimal amount=new BigDecimal(12);
		String realIP="127.0.0.1";
		WithdrawResponse response=bo.withdrawForUser(withdrawPassword, amount, realIP, user);
		
		assertTrue(null!=response);
		ReturnStatus returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Withdraw.FAIL_BANK_NOT_BIND,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenAmountTooSmallThenFail() throws Exception  {
		User user=new User();
		user.setId("3905");
		
		String withdrawPassword="tixiantest";
		BigDecimal amount=new BigDecimal(1);
		String realIP="127.0.0.1";
		WithdrawResponse response=bo.withdrawForUser(withdrawPassword, amount, realIP, user);
		
		assertTrue(null!=response);
		ReturnStatus returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Withdraw.FAIL_AMOUNT_TOO_SMALL,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenFoundIsNotEnoughThenFail() throws Exception  {
		User user=new User();
		user.setId("3905");
		
		String withdrawPassword="tixiantest";
		BigDecimal amount=new BigDecimal(20);
		String realIP="127.0.0.1";
		WithdrawResponse response=bo.withdrawForUser(withdrawPassword, amount, realIP, user);
		
		assertTrue(null!=response);
		ReturnStatus returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.Withdraw.FAIL_FOUND_NOT_ENOUGH,returnStatus.getStatusCodeForClient());
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
