package com.xhcms.lottery.commons.persist.service;

import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@ContextConfiguration("/test-db-spring.xml")
@Transactional
public class MatchColorServiceTest {

    @Autowired
    private MatchColorService matchColorService;
    
    @Ignore
	@Test
  	public void testSaveMatchColor() {
    	String leagueName = "联合会杯";
    	String leagueNameForShort = "会杯";
    	String lotteryId = "JCZQ";
    	String color = "#ff0000";
    	
    	Long id = matchColorService.saveMatchColor(leagueName, leagueNameForShort, lotteryId, color);
    	Assert.assertTrue(null != id);
    }
  
}
