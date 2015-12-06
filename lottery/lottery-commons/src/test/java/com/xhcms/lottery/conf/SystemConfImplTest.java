package com.xhcms.lottery.conf;

import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-issue-spring.xml"})
public class SystemConfImplTest {

	@Autowired
	private SystemConf systemConfImpl;
	
	@Test
	public void test() {
		Integer value=systemConfImpl.getIntegerValueByKey("interval_minute_for_zm_close_time");
		System.out.println("value="+value);
		Assert.assertTrue(null!=value);
		value=systemConfImpl.getIntegerValueByKey("interval_minute_for_lc_stop_time");
		System.out.println("value="+value);
		Assert.assertTrue(null!=value);
	}
}
