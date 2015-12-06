package com.xhcms.lottery.commons.event;

import java.util.List;

import com.xhcms.commons.mq.XHMessage;

public class TicketMessage implements XHMessage {

    private static final long serialVersionUID = -8649422773498947338L;
    
    private int priority;
    
    private String type;
    
    private List<Long> tickets;
    
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

    public List<Long> getTickets() {
        return tickets;
    }

    public void setTickets(List<Long> tickets) {
        this.tickets = tickets;
    }

    public void setType(String type) {
        this.type = type;
    }

}
