package com.xhcms.lottery.mc.jc.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.event.BuyTicketMessage;
import com.xhcms.lottery.mc.jc.client.JCBuyClient;
import com.xhcms.lottery.mc.persist.service.TicketService;

public class JCBuyHandler implements MessageHandler<BuyTicketMessage> {

    private static final long serialVersionUID = 2828216194427467394L;
    
    private static final Logger logger = LoggerFactory.getLogger(JCBuyHandler.class);
    
    @Autowired
    private TicketService ticketService;
    
    private JCBuyClient client;
    
    @Override
    public void handle(BuyTicketMessage tm) {
        List<Ticket> tickets = new ArrayList<Ticket>();
        
        StringBuffer sb = new StringBuffer();
        
        for(Long schemeId: tm.getSchemes()){
            tickets.addAll(ticketService.buyTicket(schemeId));
            sb.append(sb.length() > 0 ? ","+schemeId : schemeId);
        }
        
        long startBuyTicketTime = System.currentTimeMillis();
        logger.debug("Begin to BuyTicket, SchemeIds is:{}", sb.toString());
        
        List<Ticket> ts = new ArrayList<Ticket>(100);
        int totalMoney = 0;
        int counter = 0;
        for(Ticket t : tickets){
            // 出票张数不超过100张
            if(counter == 100){
            	boolean success = client.post(tm.getLotteryId(), totalMoney, ts);
            	if(!success) {
                    logger.error("BuyTicket failed, LotteryId:{}, TicketCount:{}", tm.getLotteryId(), ts.size());
                    return;
                }
            	
                ts.clear();
                totalMoney = 0;
                counter = 0;
            }
            
            ts.add(t);
            counter++;
            totalMoney += t.getMoney();
        }
        
        if(ts.size() > 0){
            boolean success = client.post(tm.getLotteryId(), totalMoney, ts);
            if(!success) {
            	logger.error("BuyTicket failed, LotteryId:{}, TicketCount:{}", tm.getLotteryId(), ts.size());
            }
        }
        
        logger.debug("End to BuyTicket, SchemeIds is:{}, Time Costs:{} ms", sb.toString(), System.currentTimeMillis()-startBuyTicketTime);
    }

    public void setClient(JCBuyClient client) {
        this.client = client;
    }

}
