package com.xhcms.lottery.mc.ticket;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.unison.lottery.mc.uni.handler.OrderTicketMQHandler;
import com.xhcms.lottery.commons.event.BuyTicketMessage;
import com.xhcms.lottery.lang.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-db-spring.xml",
		"/spring-service.xml", "/test-orderTicketMQHandler-spring.xml"})
public class OrderTicketMQHandlerTest extends BuyTicketHandlerDBUnitTestBase{

    @Autowired
    private OrderTicketMQHandler buyTicketHandler;
    
    @Test
    @Ignore
    public void testBuyJCZQTicket() {
        BuyTicketMessage tm = new BuyTicketMessage();
        tm.setLotteryId("JCZQ");
        
        List<Long> schemes = new ArrayList<Long>();
        schemes.add(1L);
        tm.setSchemes(schemes);
        
        buyTicketHandler.handle(tm);
    }
    
    @Test
    @Rollback(value=false)
    @Transactional
	public void testBuyHFTicket() {
		BuyTicketMessage tm = new BuyTicketMessage();
		tm.setLotteryId(Constants.ZM_LOTTERYID_JX11X5);

		List<Long> schemes = new ArrayList<Long>();
		schemes.add(146678L);
		tm.setSchemes(schemes);

		buyTicketHandler.handle(tm);
	}
    

}
