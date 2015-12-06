package com.unison.lottery.api.checkupdate.bo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.api.model.User;
import com.unison.lottery.api.protocol.response.model.CheckUpdateResponse;
import com.unison.lottery.api.protocol.status.ClientStatusCode;
import com.unison.lottery.api.protocol.status.ReturnStatus;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-checkUpdate-service-spring.xml")

public class CheckUpdateBOTest extends CheckUpdateBOTestBase {

	@Autowired
	private CheckUpdateBO checkUpdateBO;

	@Test
	public void whenClientVersionIsNotNewestThenShouldReturnUpdateInfo() throws Exception  {
		User user=new User();
		user.setClientVersion("android-lc-0.0.9");
		CheckUpdateResponse response=checkUpdateBO.checkUpdate(user);
		
		assertTrue(null!=response);
		System.out.println("response="+response);
		ReturnStatus returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.CheckUpdate.SUCC,returnStatus.getStatusCodeForClient());
		assertTrue(StringUtils.isNotBlank(response.getUpdateUrl()));
		assertTrue(StringUtils.isNotBlank(response.getUpdateDescription()));
		assertTrue(StringUtils.isNotBlank(response.getUpdateType()));
		assertTrue(StringUtils.isNotBlank(response.getVersion()));
	}
	
	@Test
	public void whenClientVersionIsNewestThenShouldNotReturnUpdateInfo() throws Exception  {
		User user=new User();
		user.setClientVersion("android-lc-1.0.0");
		CheckUpdateResponse response=checkUpdateBO.checkUpdate(user);
		
		assertTrue(null!=response);
		System.out.println("response="+response);
		ReturnStatus returnStatus=response.getReturnStatus();
		assertTrue(null!=returnStatus);
		assertEquals(ClientStatusCode.CheckUpdate.NOT_NEED_UPDATE,returnStatus.getStatusCodeForClient());
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
