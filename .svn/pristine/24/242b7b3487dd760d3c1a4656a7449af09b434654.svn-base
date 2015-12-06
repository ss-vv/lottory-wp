package com.xhcms.lottery.dc.task.ticket;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.commons.job.Job;
import com.xhcms.commons.mq.MessageSender;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.event.QueryPrizeMessage;
import com.xhcms.lottery.dc.persist.service.TicketService;
/**
 * 
 * <p>Title: 检查中奖情况</p>
 * <p>Description: </p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged, Yang Bo
 * @version 1.0.0
 */
public abstract class AbstractZunAoCheckBonusTask extends AbstractCheckBonusTask {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @Autowired
	protected TicketService ticketService;
    
    @Autowired
    private MessageSender messageSender;
    
   

	protected abstract List<Ticket> getTicketsToHandle() ;
    /**
     * 如果某玩法没有当日的ticket，就强行加入一张ticket
     * @param tickets
     */
    protected void preProcess(List<Ticket> tickets) {
    	List<String> playsInToday = new LinkedList<String>();
    	Date today = new Date();
    	for (Ticket ticket : tickets) {
    		if (DateUtils.isSameDay(today,ticket.getCreatedTime())){
    			playsInToday.add(ticket.getPlayId());
    		}
    	}
    	List<Ticket> addedFakeTickets = new LinkedList<Ticket>();
    	for (Ticket ticket : tickets) {
    		if (!playsInToday.contains(ticket.getPlayId())){
    			Ticket fakeTicket = new Ticket();
    			fakeTicket.setPlayId(ticket.getPlayId());
    			fakeTicket.setCreatedTime(today);
    			addedFakeTickets.add(fakeTicket);
    		}
    	}
    	tickets.addAll(addedFakeTickets);
    	logger.debug("To Query Prize tickets: {}", tickets);
    	logger.debug("Added Fake Tickets to Query Prize: {}", addedFakeTickets);
	}

	@Override
    public String toString(){
        return "AbstractZunAoCheckBonusTask";
    }
}
