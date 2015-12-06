package com.xhcms.lottery.admin.sihro;

import junit.framework.Assert;

import org.apache.shiro.subject.Subject;
import org.junit.Test;

import com.xhcms.lottery.admin.data.Admin;
import com.xhcms.lottery.admin.shiro.service.ShiroService;

public class ShiroServiceTest {

	@Test
	public void testShiro() {
		Admin admin = new Admin();
		admin.setUsername("handongyang");
		admin.setPassword("202cb962ac59075b964b07152d234b70");
		
		Subject subJect = ShiroService.getSubject();
		ShiroService.login(admin.getUsername(), admin.getPassword());
		boolean isPermitted = subJect.isPermitted("viewScheme");
		Assert.assertTrue(isPermitted);
	}
	
}
