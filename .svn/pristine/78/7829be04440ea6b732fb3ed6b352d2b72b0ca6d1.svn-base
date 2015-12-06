package com.xhcms.lottery.commons.persist.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.xhcms.lottery.commons.data.BetMatch;
import com.xhcms.lottery.commons.util.bonus.BetContent;

/**
 *	奖金计算器
 * @author lei.li@davcai.com
 */
public interface BonusCalculatorService {
	
	/**
	 * 
	 * @param betMatchs
	 * @param lotteryId
	 * @param matchScoreMap
	 * @return map，键是比赛id，值是可共存投注项集合
	 */
	Map<Long, List<List<BetContent>>> groupOptionsByLottery(List<BetMatch> betMatchs, String lotteryId, Map<Long, BigDecimal> matchScoreMap);
}