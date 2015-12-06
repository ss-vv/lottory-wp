package com.xhcms.lottery.commons.util;

import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetScheme;

public class BetResolver {

    private BetStrategy[] strategies = null;

    public Bet resolve(BetScheme scheme) {
        return getStrategy(scheme.getPlayId()).resolve(scheme);
    }

    public void setStrategies(BetStrategy[] strategies) {
        this.strategies = strategies;
    }

    private BetStrategy getStrategy(String playId) {
        for (int i = 0, j = this.strategies.length; i < j; i++) {
            if (this.strategies[i].match(playId)) {
                return this.strategies[i];
            }
        }
        throw new IllegalArgumentException("Unsupport play: " + playId);
    }
    
}
