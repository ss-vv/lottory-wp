package com.xhcms.lottery.commons.persist.dao;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.BetSchemePreset;
import com.xhcms.lottery.commons.persist.entity.BetSchemePresetPO;

public interface BetSchemePresetDao extends Dao<BetSchemePresetPO> {

	List<BetSchemePresetPO> list(BetSchemePreset betSchemePreset, Date from,
			Date to, Paging paging);

	/**
	 * 查找竞彩可预兑奖方案id集合
	 * @param matchIds
	 * @param lotteryId
	 */
	public List<Object[]> findAllowPrizesJC(List<Long> matchIds, String jczq);
	
	public List<BetSchemePresetPO> findByIds(List<Long> ids);

	List<BetSchemePresetPO> findFollowSchemes(Long followedSchemeId);

	/**
	 * 查找传统足彩可预兑奖方案id集合,要求期状态不能是未开售和销售中，方案状态是出票成功，方案不在lt_bet_scheme_preset表里
	 * @param issueNumber
	 * @param playId
	 * @return
	 */
	List<Long> findAllowPrizesCTZC(String issueNumber, String playId);
	
}
