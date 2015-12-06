/**
 * 
 */
package com.xhcms.ucenter.sso.ticket.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.xhcms.ucenter.sso.ticket.AbstractTicketRegistry;
import com.xhcms.ucenter.sso.ticket.ITicket;

/**
 * @author bean
 *
 */
public class TicketRegistryDefault extends AbstractTicketRegistry {
	private static Logger log = LoggerFactory.getLogger(TicketRegistryDefault.class);
	
    private final Map<String, ITicket> cache;
    
    public TicketRegistryDefault() {
        this.cache = new ConcurrentHashMap<String, ITicket>();
    }
    
	@Override
	public void addTicket(ITicket ticket) {
        Assert.notNull(ticket, "ticket cannot be null");

        if (log.isDebugEnabled()) {
            log.debug("Added ticket [" + ticket.getId() + "] to registry.");
        }
        
        cache.put(ticket.getId(), ticket);		
	}

	@Override
	public boolean deleteTicket(String ticketId) {
        if (ticketId == null) {
            return false;
        }
        
        if (log.isDebugEnabled()) {
            log.debug("Removing ticket [" + ticketId + "] from registry");
        }

        return (this.cache.remove(ticketId) != null);
	}

	@Override
	public ITicket getTicket(String ticketId) {
        if (ticketId == null) {
            return null;
        }

        if (log.isDebugEnabled()) {
            log.debug("Attempting to retrieve ticket [" + ticketId + "]");
        }
        final ITicket ticket = this.cache.get(ticketId);

        if (ticket != null) {
            log.debug("Ticket [" + ticketId + "] found in registry.");
        }

        return ticket;	
	}

	@Override
	public Collection<ITicket> getTickets() {
        return Collections.unmodifiableCollection(this.cache.values());
	}

}
