package com.xhcms.lottery.commons.util;

import java.util.List;

import com.xhcms.lottery.commons.persist.entity.TicketPresetPO;

/**
 * 赛果策略类
 * @author Wang Lei
 *
 */
public interface PrizesStrategy {
	
    /**
     * 判断策略是否适用于指定玩法
     * @param playId 玩法ID
     * @return
     */
    boolean match(String playId);
    
    /**
     * 用一张票生成一个未中奖投注内容赔率为0的方案
     * @param tickets
     * @return results
     */
	boolean prizesByTickets(List<TicketPresetPO> tickets, MatchResults results);
}
