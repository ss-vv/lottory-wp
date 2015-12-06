package com.unison.lottery.mc.uni.client;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.mc.uni.parser.QueryMatchResultsParserStatus;

/**
 * 
 * @author Yang Bo
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-test.xml","/spring-service.xml","/spring-db.xml"})
public class QueryMatchResultsClientTest {

	@Autowired
	private QueryMatchResultsClient client;
	
//	@Autowired
//	private Persister<ZCResult> zcMRP;
	
	@Test
	public void testJCZQ() {
		String type = "jczq";
		QueryMatchResultsParserStatus matchParserStatus = new QueryMatchResultsParserStatus(type);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String issue = format.format(new Date());
		boolean ret = client.postWithStatus(type, issue, matchParserStatus);
		assertTrue(ret);
		System.out.println("Fetched matches: " + matchParserStatus.getMatchResults());
		
//		List data = matchParserStatus.getMatchResults();
//		zcMRP.persist(data);
	}

	@Test
	public void testJCLQ() {
		String type = "jclq";
		QueryMatchResultsParserStatus matchParserStatus = new QueryMatchResultsParserStatus(type);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = DateUtils.addDays(new Date(), -1);
		String issue = format.format(startDate);
		boolean ret = client.postWithStatus(type, issue, matchParserStatus);
		assertTrue(ret);
		System.out.println("Fetched matches: " + matchParserStatus.getMatchResults());
	}

}
