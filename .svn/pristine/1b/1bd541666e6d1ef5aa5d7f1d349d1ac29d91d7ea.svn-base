package com.unison.lottery.mc.uni.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.unison.lottery.mc.uni.client.QueryOrderResultClient;
import com.unison.lottery.mc.uni.parser.QueryOrderResultStatus;
import com.xhcms.commons.mq.MessageHandler;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.event.TicketMessage;
import com.xhcms.lottery.mc.persist.service.TicketService;

/**
 * 出票交易结果查询 Message Queue Handler.
 * @author Yang Bo
 */
public class QueryOrderResultMQHandler implements MessageHandler<TicketMessage> {

	private static final long serialVersionUID = -3433403980742723470L;

	private Logger log = LoggerFactory.getLogger(getClass());

    private QueryOrderResultClient client;

    @Autowired
	private TicketService ticketService;

    private static final int MAX_TICKETS_PER_REQUEST = 50;
    
    @Override
    public void handle(TicketMessage tm) {
        List<Long> tickets = new ArrayList<Long>(MAX_TICKETS_PER_REQUEST);

        int counter = 0;
        for(Long t: tm.getTickets()){
            if(counter == MAX_TICKETS_PER_REQUEST){
                postAndProcess(tickets);
                counter = 0;
                tickets.clear();
            }
            if(log.isDebugEnabled()){
                log.debug("check ticket order: " + t);
            }
            tickets.add(t);
            counter++;
        }
        if(counter > 0){
            postAndProcess(tickets);
        }
    }
    
    private void postAndProcess(List<Long> tickets){
    	QueryOrderResultStatus status = new QueryOrderResultStatus();
    	client.post(tickets, status);
    	Map<Long, Ticket> ticketIdObjMap = status.getTickets();
        if (ticketIdObjMap.size() > 0) {
            ticketService.check(ticketIdObjMap);
        }
    }

    public void setClient(QueryOrderResultClient client) {
        this.client = client;
    }

}
