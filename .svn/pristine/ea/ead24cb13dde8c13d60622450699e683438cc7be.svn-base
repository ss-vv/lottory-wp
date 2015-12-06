package com.xhcms.lottery.dc.task.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.event.TicketMessage;
import com.xhcms.lottery.dc.persist.service.TicketService;

/**
 * 
 * <p>Title: 检查出票情况</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged
 * @version 1.0.0
 */
public class CheckTicketTask extends Job {

    @Autowired
    private TicketService ticketService;
    
    @Autowired
    private MessageSender messageSender;
    
    @Override
    protected void execute() {
        List<Long> tickets = ticketService.listRequiredTicket();
        
        if(tickets.size() > 0){
            TicketMessage tm = new TicketMessage();
            tm.setType("CheckTicketMessage");
            tm.setTickets(tickets);
            
            messageSender.send(tm);
        }
    }
    
    @Override
    public String toString(){
        return "CheckTicketTask";
    }
}
