/**
 * 
 */
package com.xhcms.ucenter.sso.ticket.impl;

import org.springframework.util.Assert;

import com.xhcms.ucenter.sso.ticket.IExpirationPolicy;
import com.xhcms.ucenter.sso.ticket.ITicketState;

/**
 * @author bean
 *
 */
public class MultiTimeUseOrTimeoutExpirationPolicy implements IExpirationPolicy {

    /** The time to kill in millseconds. */
    private final long timeToKillInMilliSeconds;

    /** The maximum number of uses before expiration. */
    private final int numberOfUses;

    public MultiTimeUseOrTimeoutExpirationPolicy(final int numberOfUses,
        final long timeToKillInMilliSeconds) {
        this.timeToKillInMilliSeconds = timeToKillInMilliSeconds;
        this.numberOfUses = numberOfUses;
        
        Assert.isTrue(this.numberOfUses > 0, "numberOfUsers must be greater than 0.");
        Assert.isTrue(this.timeToKillInMilliSeconds > 0, "timeToKillInMilliseconds must be greater than 0.");

    }
    
    @Override
    public boolean isExpired(final ITicketState ticketState) {
        return (ticketState == null)
            || (ticketState.getCountOfUses() >= this.numberOfUses)
            || (System.currentTimeMillis() - ticketState.getLastTimeUsed() >= this.timeToKillInMilliSeconds);
    }
}
