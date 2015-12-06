package com.xhcms.lottery.commons.persist.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.persist.dao.BetPartnerDao;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-db-spring.xml")
public class BetPartnerDaoImplTest {
	@Autowired
	private BetPartnerDao betPartnerDao;
	
	@Test
	public void test(){}
	
//	@Test
//	@Transactional
	public void testFind() {
		betPartnerDao.findBySchemeId(20625);
	}

}
