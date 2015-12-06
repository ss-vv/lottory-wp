package com.xhcms.lottery.commons.persist.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.data.UserScore;
import com.xhcms.lottery.commons.persist.dao.BetSchemeDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-follow-spring.xml")
public class UserScoreServiceTest {

	@Autowired
	private UserScoreService userScoreService;
	@Autowired
	private BetSchemeDao betSchemeDao;
	
//	@Test
//	@Transactional(value="txManager")
	public void testCountUserScore() {
		List<BetSchemePO> betSchemeList =new ArrayList<BetSchemePO>();
		try{
		betSchemeList = betSchemeDao.getWinSchemeList(); 
		}catch(Exception e){
			e.printStackTrace();
		}
		for(BetSchemePO betSchemePO:betSchemeList){
			userScoreService.countUserScore(betSchemePO);
		}
		
	}
	
	@Test
	public void testTopUserScore() {
		List<UserScore> userScores = userScoreService.listTopUserScoreOfLottery("",20);
		for(UserScore userScore:userScores){
		System.out.println(userScore.getId());
		}
		
	}
	
	@Test
	public void testUserScore() {
		UserScore userScore = userScoreService.getUserScoreByUserIdLotteryId(616, "-1");
		System.out.println(userScore);
		
	}

	@Test
	public void testCountUserScoreByAward() {
		BetSchemePO ss = new BetSchemePO();
		ss.setTotalAmount(2);
		ss.setAfterTaxBonus(new BigDecimal(230.00));
		ss.setShowScheme(1);
		ss.setType(0);
		ss.setSponsorId(173L);
		ss.setLotteryId("JCZQ");
		try{
		userScoreService.countUserScore(ss);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}
}
