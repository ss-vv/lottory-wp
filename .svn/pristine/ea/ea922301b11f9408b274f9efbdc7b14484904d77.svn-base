package com.xhcms.lottery.dc.task.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;





import com.xhcms.commons.job.Job;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.event.RemoteTicketMessage;
import com.xhcms.lottery.dc.persist.service.TicketRemoteService;
/**
 * 远程出票---查看票的状态
 * @author haohao
 *
 */
public class CheckRemoteTicketStatusTask extends Job{
	
	    
    @Autowired
    private MessageSender messageSender;
    @Autowired
    private TicketRemoteService TicketRemoteService;
	@Override
	protected void execute() throws Exception {
		List<String> ticketOrders=TicketRemoteService.listRequiredTicket();
		 if(ticketOrders.size() > 0){
			 RemoteTicketMessage tm = new RemoteTicketMessage();
             tm.setType("CheckRemoteTicketMessage");
             tm.setRemoteTicket(ticketOrders);
	         messageSender.send(tm);
	     }
	}

}
