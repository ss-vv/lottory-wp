/**
 * 
 */
package com.xhcms.ucenter.sso.ticket;


/**
 * @author bean
 *
 */
public interface IExpirationPolicy {
    public boolean isExpired(ITicketState ticketState);
}
