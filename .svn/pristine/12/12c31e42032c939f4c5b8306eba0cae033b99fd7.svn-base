package com.unison.lottery.mc.uni.client;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.mc.uni.parser.QueryMatchesParserStatus;

/**
 * 
 * @author Yang Bo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-test.xml","/spring-service.xml","/spring-db.xml"})
public class QueryMatchesClientTest {

	@Autowired
	private QueryMatchesClient client;
	
	@Test
	public void testJCZQMatches() {
		String type = "jczq";
		QueryMatchesParserStatus matchParserStatus = new QueryMatchesParserStatus(type);
		boolean ret = client.postWithStatus(type, matchParserStatus);
		assertTrue(ret);
		System.out.println("Fetched matches: " + matchParserStatus.getMatches());
	}

	@Test
	public void testJCLQMatches() {
		String type = "jclq";
		QueryMatchesParserStatus matchParserStatus = new QueryMatchesParserStatus(type);
		boolean ret = client.postWithStatus(type, matchParserStatus);
		assertTrue(ret);
		System.out.println("Fetched matches: " + matchParserStatus.getMatches());
	}

}
