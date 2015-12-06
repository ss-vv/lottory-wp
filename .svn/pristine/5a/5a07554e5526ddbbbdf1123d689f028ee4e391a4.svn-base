package com.xhcms.lottery.commons.persist.dao;

import java.util.List;

import com.xhcms.commons.lang.Paging;
import com.xhcms.commons.persist.Dao;
import com.xhcms.lottery.commons.data.BetScheme;
import com.xhcms.lottery.commons.persist.entity.RecommendUserPO;

public interface RecommendUserDao extends Dao<RecommendUserPO> {
	
	List<RecommendUserPO> findAllWithPaging(Paging paging);
	
	List<RecommendUserPO> getRecommendUser(BetScheme scheme);

	public RecommendUserPO findByUserIdAndLotteryId(long userId, String lotteryId);
}
