package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.ShowWinListPO;
/**
 * @author yonglizhu
 */
public interface ShowWinListDao extends Dao<ShowWinListPO> {

	List<ShowWinListPO> findShowWinList(Paging paging, String lotteryId);
	
	ShowWinListPO findShowWinListBySponsorIdLotteryId(long sponsorId, String lotteryId);
	
	void saveShowWinList(ShowWinListPO showWinListPO);
	
	void updateShowWinList(ShowWinListPO showWinListPO);
	
	void deleteShowWinList();
	/**
	 * 不分彩种显示前5名
	 * @return
	 */
	List<Object[]> findTop5ShowWinList();
}
