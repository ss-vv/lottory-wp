package com.xhcms.lottery.commons.util;

import java.util.List;

import com.xhcms.lottery.commons.persist.entity.TicketPresetPO;

/**
 * 根据赛果，将票变成一个方案
 * @author Wang Lei
 *
 */
public class PrizesResolver {

	private PrizesStrategy[] prizesStrategys = null;

    public boolean prizesByTickets(List<TicketPresetPO> tickets, MatchResults matchResults) {
    	if(null == tickets || tickets.isEmpty()){
    		throw new IllegalArgumentException("Error！tickets is null！");
    	}
    	return getStrategy(tickets.get(0).getPlayId()).prizesByTickets(tickets, matchResults);
    }

    public void setPrizesStrategys(PrizesStrategy[] prizesStrategys) {
        this.prizesStrategys = prizesStrategys;
    }

    private PrizesStrategy getStrategy(String playId) {
        for (int i = 0, j = this.prizesStrategys.length; i < j; i++) {
            if (this.prizesStrategys[i].match(playId)) {
                return this.prizesStrategys[i];
            }
        }
        throw new IllegalArgumentException("Unsupport play: " + playId);
    }
}
