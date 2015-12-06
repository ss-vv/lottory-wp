package com.unison.lottery.wap.persist.service.impl;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.persist.service.BetSchemeBaseService;
import com.xhcms.lottery.lang.LotteryId;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/test-bet-service-spring.xml")
public class SchemePresetTest extends BetServiceTestBase {

	@Autowired
	@Qualifier("betSchemeBaseService")
	private BetSchemeBaseService betSchemeBaseService;
	
	@Test
	public void testQueryPresetScheme() {
		Map<Long, List<Long>> list = betSchemeBaseService.queryPresetSchemeList(LotteryId.JCZQ.name());
		
		//Assert.assertTrue(null != list && list.size() > 0);
	}
}