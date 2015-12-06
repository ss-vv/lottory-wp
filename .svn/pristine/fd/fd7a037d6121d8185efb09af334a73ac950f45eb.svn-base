package com.xhcms.lottery.account.service;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.bonus.BonusDetail;

/**
 * 奖金明细服务
 * 
 * @author Yang Bo
 *
 */
public interface BonusDetailService {

    /**
     * 计算奖金明细
     * @param 投注方案
     * @return 奖金明细
     * @throws 如果玩法不可计算奖金明细，目前只能是竞彩足球、篮球。
     */
    BonusDetail computeBonusDetail(BetScheme scheme) throws BonusDetailException;
}
