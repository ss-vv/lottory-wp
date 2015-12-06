package com.xhcms.lottery.mc.jc.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.event.TicketMessage;
import com.xhcms.lottery.mc.jc.client.JCTicketClient;

public class JCTicketHandler implements MessageHandler<TicketMessage> {
    private static final long serialVersionUID = 2828216194427467394L;
    private Logger log = LoggerFactory.getLogger(getClass());

    private JCTicketClient client;

    @Override
    public void handle(TicketMessage tm) {
        List<Long> tickets = new ArrayList<Long>(100);

        int counter = 0;
        for(Long t: tm.getTickets()){
            if(counter == 100){
                client.post(tickets);
                counter = 0;
                tickets.clear();
            }
            if(log.isDebugEnabled()){
                log.debug("check ticket : " + t);
            }
            tickets.add(t);
            counter++;
        }
        if(counter > 0){
            client.post(tm.getTickets());
        }
    }

    public void setClient(JCTicketClient client) {
        this.client = client;
    }

}
