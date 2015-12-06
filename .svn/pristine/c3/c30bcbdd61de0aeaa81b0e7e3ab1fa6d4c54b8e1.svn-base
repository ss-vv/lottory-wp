package com.unison.lottery.mc.uni.client;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.unison.lottery.mc.uni.parser.QueryPrizeParserStatus;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.mc.persist.service.TicketService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/spring-interface.xml", "/spring-service.xml", "/spring-db.xml"})
public class QueryPrizeClientTest implements IPrizeProcessor {

	@Autowired
	private QueryPrizeClient client;

    @Autowired
	private TicketService ticketService;


    @Test
    public void testEmpty(){}
    
	//@Test
	public void test() {
		QueryPrizeParserStatus parserStatus = new QueryPrizeParserStatus();
		String zmLotteryId = "JCSPF";
		String issue = "2012-10-20";
		client.postWithStatus(zmLotteryId, issue, parserStatus, this);
	}
	
	@Override
	public void process(QueryPrizeParserStatus prizeStatus) {
		Map<Long, Ticket> ts = prizeStatus.getTicketIdTickets();
		for (Ticket t : ts.values()){
			System.out.println(t);
		}
		Ticket t = ts.get(514693L);
		if (t!=null){
			System.out.println("Get prize ticket: " + t);
			System.out.println("Get Ticket prize status: " + 
			t.getStatus() + ", actual_status: "+ t.getActualStatus());
		}
		if(ts.size() > 0){
            ticketService.prize(ts);
        }
	}
	
}
