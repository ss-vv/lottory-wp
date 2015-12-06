package com.xhcms.lottery.commons.event;

import java.util.List;

import com.xhcms.commons.mq.XHMessage;
import com.xhcms.lottery.commons.data.Ticket;

/**
 * 查询中奖情况消息。
 * @author Yang Bo
 *
 */
public class QueryPrizeMessage implements XHMessage {

    private static final long serialVersionUID = -8649422773498947338L;
    
    private int priority;
    
    private String type;
    
    private List<Ticket> tickets;
    
    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setType(String type) {
        this.type = type;
    }

	public List<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(List<Ticket> tickets) {
		this.tickets = tickets;
	}

}
