package com.unison.lottery.mc.uni.client;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import com.xhcms.lottery.commons.data.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.unison.lottery.mc.uni.parser.OrderTicketResponseParserStatus;
import com.unison.lottery.mc.uni.parser.QueryIssueResponseParserStatus;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/spring-interface.xml")
public class OrderTicketClientTest
{
	@Autowired
	private OrderTicketClient client;
	

	@Autowired
	private QueryIssueClient issueClient;

	@Test
	public void testQueryIssue()
	{
		OrderTicketResponseParserStatus tmpResponseParserStatus = new OrderTicketResponseParserStatus();

		int totalMoney = 0;
		String issueNumber = "";
		String lotteryId = "ZQSSC";

		QueryIssueResponseParserStatus queryIssueResponseParserStatus = new QueryIssueResponseParserStatus();
		issueClient.postWithStatus(lotteryId, issueNumber, queryIssueResponseParserStatus);
		
		List<Ticket> tmpTicketItems = new ArrayList<Ticket>(100);
		Ticket ticketItem = new Ticket();
		ticketItem.setActualCode("12345");
		ticketItem.setId(2012010300101L);
		ticketItem.setNote(1);
		ticketItem.setMultiple(1);
		ticketItem.setMoney(2);
		//ticketItem.setPlayId("ZQSSC_5X_DS");
		ticketItem.setPassTypeId("ZQSSC_5X_DS");
		ticketItem.setIssue("20130104075");
		tmpTicketItems.add(ticketItem);
		totalMoney += ticketItem.getMoney();
		
		boolean ret = client.post(lotteryId, totalMoney, tmpTicketItems, tmpResponseParserStatus);
		assertTrue(ret);
		System.out.println("Fetched results: " + tmpResponseParserStatus.getResponses());
	}
}
