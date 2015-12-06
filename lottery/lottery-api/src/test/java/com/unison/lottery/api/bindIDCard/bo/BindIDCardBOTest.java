package com.unison.lottery.api.bindIDCard.bo;

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
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.response.model.RegisteResponse;
import com.unison.lottery.api.protocol.status.ClientStatusCode;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.utils.CryptoUitls;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-bindIDCard-service-spring.xml")

public class BindIDCardBOTest extends BindIDCardBOTestBase {

	@Autowired
	private BindIDCardBO bindIDCardBO;

	@Test
	public void whenNotBindThenBindSucc() throws Exception  {
		User user=new User();
		user.setId("6");
		
		String iDCard="11010719831006272x";
		BindIDCardResponse bindIDCardResponse=bindIDCardBO.bindIDCardForUser(iDCard, user);
		
		assertTrue(null!=bindIDCardResponse);
		ReturnStatus returnStatus=bindIDCardResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.BindIDCard.SUCC,returnStatus.getStatusCodeForClient());
		
		
	}
	
	@Test
	public void whenHaveBeenBindThenBindFail() throws Exception  {
		User user=new User();
		user.setId("75");
		
		String iDCard="11010719831006272x";
		BindIDCardResponse bindIDCardResponse=bindIDCardBO.bindIDCardForUser(iDCard, user);
		
		assertTrue(null!=bindIDCardResponse);
		ReturnStatus returnStatus=bindIDCardResponse.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.BindIDCard.FAIL,returnStatus.getStatusCodeForClient());
		
	}
	
	
	
	
	
	
	

}
