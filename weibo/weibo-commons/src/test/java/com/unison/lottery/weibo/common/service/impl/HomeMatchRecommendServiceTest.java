package com.unison.lottery.weibo.common.service.impl;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.weibo.common.service.HomeMatchRecommendService;
import com.unison.lottery.weibo.data.HomeMatchRecommend;
import com.xhcms.lottery.lang.LotteryId;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-db-spring.xml")
public class HomeMatchRecommendServiceTest {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private HomeMatchRecommendService matchRecommend; 

	@Test
	public void testFindMatchRecommend() {
		List<HomeMatchRecommend> list = matchRecommend.queryByLottery(LotteryId.JCZQ, 1);
		logger.info("result={}", list);
	}
}