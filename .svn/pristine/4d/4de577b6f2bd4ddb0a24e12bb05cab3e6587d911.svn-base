package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.persist.entity.FollowWinListPO;
/**
 * @author yonglizhu
 */
public interface FollowWinListDao extends Dao<FollowWinListPO> {

	List<FollowWinListPO> findFollowWinList(Paging paging, String lotteryId);
	
	FollowWinListPO findFollowWinListBySponsorIdLotteryId(long followerId, String lotteryId);
	
	void saveFollowWinList(FollowWinListPO followWinListPO);
	
	void updateFollowWinList(FollowWinListPO followWinListPO);
	
	void deleteFollowWinList();
	
	List<Object[]> findTop5FollowWinList();
}
