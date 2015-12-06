package com.xhcms.lottery.commons.persist.service;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.lottery.commons.data.ShowWinListVo;
import com.xhcms.lottery.commons.persist.entity.BetSchemePO;

/**
 * 中奖榜服务
 * @author yonglizhu
 */
public interface WinListService {
	
	/**
	 * 查询晒单中奖榜
	 * @param lotteryId
	 */
	void findShowWinList(Paging paging, String lotteryId);
	
	/**
	 * 查询跟单中奖榜
	 * @param lotteryId
	 */
	void findFollowWinList(Paging paging, String lotteryId);
	
	/**
	 * 派奖时计算用户晒单跟单中奖榜
	 * @param betSchemePO
	 */
	void countWinList(BetSchemePO betSchemePO);

	
}
