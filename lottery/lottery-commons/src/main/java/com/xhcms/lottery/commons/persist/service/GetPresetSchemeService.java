package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.data.Ticket;
import com.xhcms.lottery.commons.persist.entity.BBMatchPO;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;
import com.xhcms.lottery.commons.persist.entity.FBMatchPO;

/**
 * 获取预兑奖方案信息服务
 * @author Wang Lei
 *
 */
public interface GetPresetSchemeService {
	
	/**
     * 得到一个方案，如果是预派奖方案，则返回预派奖方案信息
     * @param schemeId
     * @return
     */
	public BetSchemePO getRightSchemeById(Long schemeId);

	public BetSchemePO getRightSchemeByPO(BetSchemePO sourcePO);

	public BetScheme getRightSchemeByDO(BetScheme betScheme);

	public List<Ticket> findTicketsByBetSchemePO(BetSchemePO sourcePO);

	FBMatchPO getRightScore(BetSchemePO spo, FBMatchPO matchPO);

	BBMatchPO getRightScore(BetSchemePO spo, BBMatchPO matchPO);
	
	List<BetSchemePO> findFollowSchemes(BetSchemePO spo);
	
}
