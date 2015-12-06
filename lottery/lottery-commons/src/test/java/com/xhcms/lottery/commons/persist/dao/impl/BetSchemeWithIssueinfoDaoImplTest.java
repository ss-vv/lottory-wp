package com.xhcms.lottery.commons.persist.dao.impl;

import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.xhcms.lottery.commons.persist.dao.BetSchemeWithIssueInfoDao;
import com.xhcms.lottery.commons.persist.entity.BetSchemeWithIssueInfoPO;
import com.xhcms.lottery.lang.Constants;
import com.xhcms.lottery.lang.EntityStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-betscheme-spring.xml")
public class BetSchemeWithIssueinfoDaoImplTest extends DbUnitTestBetSchemeBase {

	@Autowired
	BetSchemeWithIssueInfoDao betSchemeWithIssueInfoDao;
	
	@Transactional
	@Test
	public void testFindByStatusAndLotteryIdListAndTargetTimeBetweenStartTimeAndZMCloseTime() {
		int status=EntityStatus.TICKET_ALLOW_BUY;
		
		Calendar calendar=Calendar.getInstance();
		calendar.set(2012, 8, 10, 10, 5, 10);
		Date targetDate=calendar.getTime();
		java.sql.Date from=new java.sql.Date(targetDate.getTime());//2012-09-10 10:05:10
		
		List<String> targetLotteryIdList=new ArrayList<String>();
		targetLotteryIdList.add(Constants.JX11);
		
		calendar.set(2012, 8, 13, 9, 25, 10);
		Date targetTime=calendar.getTime();//2012-09-13 09:25:10
		
		List<BetSchemeWithIssueInfoPO> betSchemes = betSchemeWithIssueInfoDao		
		.findByStatusWithSpecifiedTime(
				status, from, targetLotteryIdList, targetTime);
		assertFalse(betSchemes.isEmpty());
		System.out.println("betSchemes="+betSchemes);
		
	}
	
	
	
	
}
