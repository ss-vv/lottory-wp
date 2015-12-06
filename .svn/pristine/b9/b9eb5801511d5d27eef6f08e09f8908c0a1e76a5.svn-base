package com.unison.lottery.mc.uni.client;

import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.mc.uni.parser.QueryJCOddsParserStatus;

/**
 * 
 * @author Yang Bo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-test.xml","/spring-service.xml","/spring-db.xml"})
public class QueryJCOddsClientTest {

	@Autowired
	private QueryJCOddsClient client;
	
	@Test
	public void testJCZQDG() {
		String type = "jczqdg";
		QueryJCOddsParserStatus oddsParserStatus = new QueryJCOddsParserStatus(type);
		boolean ret = client.postWithStatus(type, oddsParserStatus);
		assertTrue(ret);
		System.out.println("Fetched JC Odds["+oddsParserStatus.getOdds().size()+"]: " + oddsParserStatus.getOdds());
	}

	@Test
	public void testJCZQGG() {
		String type = "jczqgg";
		QueryJCOddsParserStatus oddsParserStatus = new QueryJCOddsParserStatus(type);
		boolean ret = client.postWithStatus(type, oddsParserStatus);
		assertTrue(ret);
		System.out.println("Fetched JC Odds["+oddsParserStatus.getOdds().size()+"]: " + oddsParserStatus.getOdds());
	}

	@Test
	public void testJCLQDG() {
		String type = "jclqdg";
		QueryJCOddsParserStatus oddsParserStatus = new QueryJCOddsParserStatus(type);
		boolean ret = client.postWithStatus(type, oddsParserStatus);
		assertTrue(ret);
		System.out.println("Fetched JC Odds["+oddsParserStatus.getOdds().size()+"]: " + oddsParserStatus.getOdds());
	}

	@Test
	public void testJCLQGG() {
		String type = "jclqgg";
		QueryJCOddsParserStatus oddsParserStatus = new QueryJCOddsParserStatus(type);
		
		Date startTime = DateUtils.addDays(new Date(), 0);
		boolean ret = client.postWithStatus(type, startTime , oddsParserStatus);
		assertTrue(ret);
		System.out.println("Fetched JC Odds["+oddsParserStatus.getOdds().size()+"]: " + oddsParserStatus.getOdds());
	}

}
