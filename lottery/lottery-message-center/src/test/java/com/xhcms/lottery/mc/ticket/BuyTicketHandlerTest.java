package com.xhcms.lottery.mc.ticket;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xhcms.lottery.commons.event.BuyTicketMessage;
import com.xhcms.lottery.mc.jc.handler.JCBuyHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-db.xml", "/spring-activemq.xml", "/spring-service.xml"})
public class BuyTicketHandlerTest extends BuyTicketHandlerDBUnitTestBase{

    @Autowired
    private JCBuyHandler buyTicketHandler;
    
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
    
   
    

}
