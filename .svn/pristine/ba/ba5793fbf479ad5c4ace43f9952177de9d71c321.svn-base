/**
 * 
 */
package com.xhcms.ucenter.sso.ticket;

import java.util.Collection;

/**
 * @author bean
 *
 */
public interface ITicketRegistry {
    void addTicket(ITicket ticket);
    ITicket getTicket(String ticketId, Class<? extends ITicket> clazz);
    ITicket getTicket(String ticketId);
    boolean deleteTicket(String ticketId);
    Collection<ITicket> getTickets();
}
