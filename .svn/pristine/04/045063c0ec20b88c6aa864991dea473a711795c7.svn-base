package com.xhcms.lottery.commons.persist.service;

import java.util.Date;
import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.BetSchemePreset;
import com.xhcms.lottery.commons.data.PresetResult;
import com.xhcms.lottery.commons.util.MatchResults;

/**
 * 预兑奖服务
 * @author Wang Lei
 *
 */
public interface PresetAwardService {

	/**
	 * 核对预兑奖方案和票
	 */
	void checkPresetAward();

	List<BetSchemePreset> list(BetSchemePreset betSchemePreset, Date from,
			Date to, Paging paging);
	
	/**
	 * 查找竞彩足球可预兑奖方案
	 * @param matchIds
	 * @return
	 */
	List<Long> findAllowPrizesFB(List<Long> matchIds);
	
	/**
	 * 查找竞彩篮球可预兑奖方案
	 * @param matchIds
	 * @return
	 */
	List<Long> findAllowPrizesBB(List<Long> matchIds);
	
	/**
	 * 竞彩预兑奖
	 * @param schemeId
	 * @param matchResults 
	 * @return 
	 */
	boolean presetPrizes(Long schemeId, MatchResults matchResults);

	MatchResults computeFBMatchResults(List<Long> matchIds);

	MatchResults computeBBMatchResults(List<Long> matchIds);

	/**
	 * 撤销预兑奖方案
	 * @param id
	 */
	void cancelPresetPrizes(List<Long> id);
	/**
	 * 传统足彩预兑奖,批量接口
	 * @param issueNumbers 期号列表
	 * @return 预兑奖结果列表,每一项是某一期某一个玩法对应的预兑奖结果，包括可派奖的方案数，跳过的方案数，未中奖的方案数以及是否预兑奖成功
	 */
	List<PresetResult> presetCTZC(List<String> issueNumbers);

	/**
	 * 传统足彩预兑奖
	 * @param issueNumber 期号
	 * @return 预兑奖结果列表,每一项是某一期某一个玩法对应的预兑奖结果，包括可派奖的方案数，跳过的方案数，未中奖的方案数以及是否预兑奖成功
	 */
	public abstract PresetResult presetCTZC(String issueNumber);
	
}
