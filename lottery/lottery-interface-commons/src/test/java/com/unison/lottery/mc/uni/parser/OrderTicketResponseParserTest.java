package com.unison.lottery.mc.uni.parser;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value="/spring-interface.xml")
public class OrderTicketResponseParserTest {

	@Autowired
	private OrderTicketResponseParser parser;
	
	@Test
	public void testParseBody() throws ParseException {
		String response="transcode=102&msg=<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><head transcode=\"102\" partnerid=\"349182\" version=\"1.0\" time=\"20120828104745\" /><body><tickets><ticket ticketId=\"315161\" multiple=\"1\" issueNumber=\"20120828\" betType=\"P2_1\" betUnits=\"1\" betMoney=\"2\" statusCode=\"000\" message=\"接收成功\" palmid=\"6400517\" detailmessage=\"\" /></tickets></body></msg>&key=410CFEAEB8E7EE3F822A491B9074F500&partnerid=349182";
		OrderTicketResponseParserStatus status = new OrderTicketResponseParserStatus();
		int code = parser.parse(response, status);
		Assert.assertEquals(102, code);
		Assert.assertEquals(status.getResponses().size(), 1);
	}

}
