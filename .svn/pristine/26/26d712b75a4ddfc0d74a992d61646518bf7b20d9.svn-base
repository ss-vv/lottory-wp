package com.xhcms.lottery.commons.persist.service;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.data.NewRegistUser;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-user-spring.xml")
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void testCountUser() {
		Collection<NewRegistUser> data = userService.findNewRegUserGroupByPid(null, null, null);
		if(null != data) {
			System.out.println(data.size());
		}
	}
	
}