package com.unison.lottery.mc.uni.client;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.mc.uni.parser.QueryIssueResponseParserStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/spring-interface.xml")
public class QueryIssueClientTest {

	@Autowired
	private QueryIssueClient client;
	
	/**
	 * 
	 * 
	 */
	@Test
	public void testQueryIssue()  {
		QueryIssueResponseParserStatus queryIssueResponseParserStatus = new QueryIssueResponseParserStatus();
		String issueNumber="";
		String lotteryId="JX11X5";
		boolean ret = client.postWithStatus(lotteryId,issueNumber,queryIssueResponseParserStatus);
		assertTrue(ret);
		System.out.println("Fetched results: " + queryIssueResponseParserStatus.getIssueinfos());
		
		
		
		
	}
	
	

	

}
