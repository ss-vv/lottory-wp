/**
 * 
 */
package com.xhcms.ucenter.sso.ticket.impl;

import com.xhcms.ucenter.sso.ticket.IExpirationPolicy;
import com.xhcms.ucenter.sso.ticket.ITicketState;

/**
 * @author bean
 *
 */
public class TimeoutExpirationPolicy implements IExpirationPolicy {
    private long timeToKillInMilliSeconds;
    
    public TimeoutExpirationPolicy(long timeToKillInMilliSeconds) {
    	this.timeToKillInMilliSeconds = timeToKillInMilliSeconds;
    }

    @Override
    public boolean isExpired(final ITicketState ticketState) {
        return (ticketState == null)
            || (System.currentTimeMillis() - ticketState.getLastTimeUsed() >= this.timeToKillInMilliSeconds);
    }
}