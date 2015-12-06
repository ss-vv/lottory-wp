package com.xhcms.lottery.commons.persist.dao.impl;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.persist.dao.UserScoreDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-follow-spring.xml")
public class UserShowScoreDaoImplTest {

	@Autowired
	private UserScoreDao userShowScoreDao;
	
	@Test
	@Transactional
	public void testGetUserScoreByUserIdLottoryId() {
		userShowScoreDao.getUserScoreByUserIdLottoryId(1,"-1");
	}

}
