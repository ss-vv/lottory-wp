/**
 * 
 */
package com.xhcms.ucenter.sso.ticket.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xhcms.ucenter.sso.ticket.IRegistryCleaner;
import com.xhcms.ucenter.sso.ticket.ITicket;
import com.xhcms.ucenter.sso.ticket.ITicketRegistry;

/**
 * @author bean
 *
 */
public class DefaultTicketRegistryCleaner implements IRegistryCleaner {
	private static final Logger logger = LoggerFactory.getLogger(DefaultTicketRegistryCleaner.class);
	
	@Autowired
	private ITicketRegistry ticketRegistry;
							
	@Override
	public void clean() {
        try {
            final List<ITicket> ticketsToRemove = new ArrayList<ITicket>();
            final Collection<ITicket> ticketsInCache;
            ticketsInCache = this.ticketRegistry.getTickets();
            for (final ITicket ticket : ticketsInCache) {
                if (ticket.isExpired()) {
                    ticketsToRemove.add(ticket);
                }
            }

            logger.info(ticketsToRemove.size() + " tickets found to be removed.");
            for (final ITicket ticket : ticketsToRemove) {
                // CAS-686: Expire TGT to trigger single sign-out
                
            	if (ticket instanceof GrantingTicket) {
                    ((GrantingTicket) ticket).expire();
                }
                
                this.ticketRegistry.deleteTicket(ticket.getId());
            }
        } finally {
            logger.debug("Releasing ticket cleanup lock.");
        }
	}

}
