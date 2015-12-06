package com.unison.lottery.itf;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-*.xml"})
public class OrderResultProcessorTest {
	
	@Autowired
	private OrderResultProcessor processor;
	
	@Test
	public void testProcessOrderResult(){
		String notification = "transcode=007&msg=<?xml version=\"1.0\" encoding=\"UTF-8\"?><msg><head transcode=\"007\" partnerid=\"349182\" version=\"1.0\" time=\"20120828181011\" /><body><ticketresults><ticketresult lotteryId=\"JCSPF\" ticketId=\"371927\" palmId=\"6400657\" statusCode=\"003\" message=\"交易成功\" printodd=\"2-001:[平=3.3]/2-002:[平=2.92]\" Unprintodd=\"3.3;2.92\" printNo=\"ABC1-94A1DE9B9401\" maxBonus=\"19.27\" /><ticketresult lotteryId=\"JCSPF\" ticketId=\"371927\" palmId=\"6400871\" statusCode=\"003\" message=\"交易成功\" printodd=\"2-001:[平=3.3]/2-002:[平=2.92]\" Unprintodd=\"3.3;2.92\" printNo=\"83E8-631D06B7B36B\" maxBonus=\"19.27\" /><ticketresult lotteryId=\"JCSPF\" ticketId=\"371927\" palmId=\"6400872\" statusCode=\"003\" message=\"交易成功\" printodd=\"2-003:[负=2.8]/2-004:[负=4.2]\" Unprintodd=\"2.8;4.2\" printNo=\"9129-42E17FC2864C\" maxBonus=\"23.52\" /><ticketresult lotteryId=\"JCSPF\" ticketId=\"371927\" palmId=\"6400874\" statusCode=\"003\" message=\"交易成功\" printodd=\"2-027:[胜=1.48]/2-028:[胜=1.52]\" Unprintodd=\"1.48;1.52\" printNo=\"8E93-1EA9971BABD2\" maxBonus=\"4.50\" /></ticketresults></body></msg>&key=bf5549aaeba0f23986fbab8310c37d00&partnerid=349182";
		OrderNotifyProcessResult result = (OrderNotifyProcessResult) processor.process(notification);
		//Assert.assertTrue(result.getReturnResults().size()>0);
	}
}
