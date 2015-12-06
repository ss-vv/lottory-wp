package com.xhcms.lottery.commons.util;

import com.xhcms.lottery.commons.data.Bet;
import com.xhcms.lottery.commons.data.BetScheme;

/**
 * 
 * <p>Title: 分解投注方案策略类</p>
 * <p>Description: 计算投注方案总注数，最大和最小可能奖金；根据出票要求分解投注方案</p>
 * <p>Copyright：Copyright (c) 2011</p>
 * <p>Company：XingHui Spirit (Beijing) Technical Co.,Ltd.</p>
 * 
 * @author wanged
 * @version 1.0.0
 */
public interface BetStrategy {

    /**
     * 判断策略是否适用于指定玩法
     * @param playId 玩法ID
     * @return
     */
    boolean match(String playId);
    
    /**
     * 分解投注方案.
     * TODO: 让方法抛出解析异常。
     * @param s 方案
     * @return
     */
    Bet resolve(BetScheme s);
}
