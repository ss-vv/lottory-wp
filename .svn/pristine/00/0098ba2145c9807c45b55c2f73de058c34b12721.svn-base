package com.unison.lottery.api.protocol.status;

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

import com.unison.lottery.api.protocol.common.SystemStatusKeyNames;
import com.unison.lottery.api.protocol.response.model.LoginResponse;
import com.unison.lottery.api.protocol.status.ReturnStatus;
import com.xhcms.lottery.utils.CryptoUitls;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-statusRepository-service-spring.xml")

public class StatusRepositoryTest extends StatusRepositoryTestBase {

	@Autowired
	private IStatusRepository statusRepository;

	@Test
	public void testGetSystemStatusBySystemKey() throws Exception  {
		//statusRepository.init();
		ReturnStatus returnStatus=statusRepository.getSystemStatusBySystemKey(SystemStatusKeyNames.Login.SUCC);
		System.out.println("returnStatus="+returnStatus);
		assertTrue(null!=returnStatus);
		assertTrue(StringUtils.isNotBlank(returnStatus.getStatusCodeForClient()));
		assertTrue(StringUtils.isNotBlank(returnStatus.getDescForClient()));
	}
	
	
	
	
	
	

}
