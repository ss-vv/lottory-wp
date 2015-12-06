/**
 * 
 */
package com.xhcms.ucenter.sso.ticket;

import org.springframework.util.Assert;

/**
 * @author bean
 *
 */
public abstract class AbstractTicketRegistry implements ITicketRegistry {

	@Override
	public ITicket getTicket(String ticketId, Class<? extends ITicket> clazz) {
        Assert.notNull(clazz, "clazz cannot be null");

        final ITicket ticket = this.getTicket(ticketId);

        if (ticket == null) {
            return null;
        }

        if (!clazz.isAssignableFrom(ticket.getClass())) {
            throw new ClassCastException("Ticket [" + ticket.getId()
                + " is of type " + ticket.getClass()
                + " when we were expecting " + clazz);
        }

        return ticket;
	}
}
