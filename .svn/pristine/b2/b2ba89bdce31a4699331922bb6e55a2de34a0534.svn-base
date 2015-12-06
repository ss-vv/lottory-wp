package com.xhcms.lottery.commons.persist.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.persist.entity.RecommendUserPO;
import com.xhcms.lottery.lang.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-follow-spring.xml")
public class FollowServiceTest {

	@Autowired
	private FollowService followService;
	
	@Test
	public void testListRecUser() {
		Paging paging = new Paging();
		paging.setPageNo(1);
		paging.setMaxResults(Paging.DEFAULT_MAX_RESULTS);
		List<RecommendUserPO> users = followService.listRecUser(paging);
		System.out.println(users);
		assertTrue(users.size()>0);
	}

	//@Test
	public void testRecommend() {
		Paging paging = new Paging();
		paging.setPageNo(1);
		paging.setMaxResults(Paging.DEFAULT_MAX_RESULTS);
		List<RecommendUserPO> users = followService.listRecUser(paging);
		int oldUserCount = users.size();
		
		List<String> lotteries = new LinkedList<String>();
		lotteries.add(Constants.JCLQ);
		lotteries.add(Constants.JCZQ);
		followService.recommendUser(25, lotteries , 1);
		users = followService.listRecUser(paging);
		assertTrue(users.size()==oldUserCount+2);
	}
	
	@Test
	public void testCancel(){
		long userId = 26;
		String lotteryId = Constants.JCLQ;
		List<String> lotteries = new LinkedList<String>();
		lotteries.add(lotteryId);
		followService.recommendUser(26, lotteries , 1);
		RecommendUserPO recUser = followService.findRecommendUser(userId, lotteryId);
		assertNotNull(recUser);
		followService.cancelRecommendUser(recUser.getId());
		recUser = followService.findRecommendUser(userId, lotteryId);
		assertNull(recUser);
	}
}
